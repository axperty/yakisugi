package com.axperty.yakisugi.mixin;

import com.axperty.yakisugi.registry.BlockRegistry;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import com.axperty.yakisugi.api.ICharringCampfire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin extends BlockEntity implements ICharringCampfire {

    @Unique
    private final Object2IntMap<BlockPos> yakisugi$charringMap = new Object2IntOpenHashMap<>();

    public CampfireBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    // Get the charring map
    @Override
    public Object2IntMap<BlockPos> yakisugi$getCharringMap() {
        return yakisugi$charringMap;
    }

    // save the charring map to the campfire block entity
    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void yakisugi$saveAdditional(ValueOutput output, CallbackInfo ci) {
        if (!yakisugi$charringMap.isEmpty()) {
            ValueOutput.ValueOutputList list = output.childrenList("YakisugiCharringMap");
            for (Object2IntMap.Entry<BlockPos> entry : yakisugi$charringMap.object2IntEntrySet()) {
                ValueOutput entryOutput = list.addChild();
                entryOutput.putInt("X", entry.getKey().getX());
                entryOutput.putInt("Y", entry.getKey().getY());
                entryOutput.putInt("Z", entry.getKey().getZ());
                entryOutput.putInt("Time", entry.getIntValue());
            }
        }
    }

    // Load the charring map from the campfire block entity
    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void yakisugi$loadAdditional(ValueInput input, CallbackInfo ci) {
        yakisugi$charringMap.clear();
        for (ValueInput entryInput : input.childrenListOrEmpty("YakisugiCharringMap")) {
            BlockPos pos = new BlockPos(entryInput.getIntOr("X", 0), entryInput.getIntOr("Y", 0), entryInput.getIntOr("Z", 0));
            yakisugi$charringMap.put(pos, entryInput.getIntOr("Time", 0));
        }
    }

    // Main campfire functionality
    @Inject(method = "cookTick", at = @At("TAIL"))
    private static void yakisugi$cookTick(ServerLevel level, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, RecipeManager.CachedCheck<?, ?> cachedCheck, CallbackInfo ci) {
        if (!state.getValue(CampfireBlock.LIT)) return;
        if (level.getGameTime() % 20 != 0) return;

        ICharringCampfire charringCampfire = (ICharringCampfire) blockEntity;
        Object2IntMap<BlockPos> map = charringCampfire.yakisugi$getCharringMap();

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            for (int yOffset = 0; yOffset <= 2; yOffset++) {
                BlockPos targetPos = pos.offset(dir[0], yOffset, dir[1]);
                if (level.getBlockState(targetPos).is(BlockTags.PLANKS)) {
                    yakisugi$processCharring(level, targetPos, map);
                }
            }
        }

        map.keySet().removeIf(p -> !level.getBlockState(p).is(BlockTags.PLANKS));
    }

    @Unique
    private static void yakisugi$processCharring(Level level, BlockPos targetPos, Object2IntMap<BlockPos> map) {
        final int CHARCOAL_THRESHOLD = 1000;
        final int CHARRED_THRESHOLD = 1200;

        BlockState targetState = level.getBlockState(targetPos);
        int time = map.getInt(targetPos);
        time += 20;

        boolean isSlightlyCharred = targetState.is(BlockRegistry.SLIGHTLY_CHARRED_PLANKS.get());
        boolean isCharred = targetState.is(BlockRegistry.CHARRED_PLANKS.get());

        if (isCharred) {
            if (time >= CHARCOAL_THRESHOLD) {
                level.destroyBlock(targetPos, false);
                ItemEntity itemEntity = new ItemEntity(level, targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5, new ItemStack(Items.CHARCOAL));
                level.addFreshEntity(itemEntity);
                map.removeInt(targetPos);
            } else {
                map.put(targetPos, time);
            }
        } else if (time >= CHARRED_THRESHOLD) {
            if (!isSlightlyCharred) {
                level.setBlockAndUpdate(targetPos, BlockRegistry.SLIGHTLY_CHARRED_PLANKS.get().defaultBlockState());
                map.put(targetPos, 0);
            } else {
                level.setBlockAndUpdate(targetPos, BlockRegistry.CHARRED_PLANKS.get().defaultBlockState());
                map.put(targetPos, 0);
            }
            level.levelEvent(1502, targetPos, 0);
        } else {
            map.put(targetPos, time);
            if (level.getRandom().nextInt(10) == 0 && level instanceof ServerLevel serverLevel) {
                double px = targetPos.getX() + 0.5D + (level.getRandom().nextDouble() - 0.5D) * 0.5D;
                double py = targetPos.getY() + 0.5D + (level.getRandom().nextDouble() - 0.5D) * 0.5D;
                double pz = targetPos.getZ() + 0.5D + (level.getRandom().nextDouble() - 0.5D) * 0.5D;
                serverLevel.sendParticles(ParticleTypes.SMOKE, px, py, pz, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                if (level.getRandom().nextBoolean()) {
                    serverLevel.sendParticles(ParticleTypes.FLAME, px, py, pz, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
