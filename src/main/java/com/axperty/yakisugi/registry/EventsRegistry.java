package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = Yakisugi.MOD_ID)
public class EventsRegistry {

    private static final Identifier FUKA_GUTSU_NO_SLOW_ID = Identifier.fromNamespaceAndPath(Yakisugi.MOD_ID, "fuka_gutsu_no_slow");
    private static final AttributeModifier FUKA_GUTSU_NO_SLOW_MODIFIER = new AttributeModifier(
            FUKA_GUTSU_NO_SLOW_ID, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        // Mino keeps the player from freezing in powder snow
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.STRAW_MINO.get())) {
            player.setTicksFrozen(0);
        }

        // Fuka gutsu cancels the soul speed / snow slowdown
        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null) {
            BlockState below = player.getBlockStateOn();
            boolean shouldCancelSlow = player.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.STRAW_BOOTS.get())
                    && (below.is(BlockTags.SOUL_SPEED_BLOCKS) || below.is(BlockTags.SNOW));
            boolean hasModifier = speedAttribute.hasModifier(FUKA_GUTSU_NO_SLOW_ID);

            if (shouldCancelSlow && !hasModifier) {
                speedAttribute.addTransientModifier(FUKA_GUTSU_NO_SLOW_MODIFIER);
            } else if (!shouldCancelSlow && hasModifier) {
                speedAttribute.removeModifier(FUKA_GUTSU_NO_SLOW_ID);
            }
        }
    }

    // Checks if the player is breaking a crop and if wearing the wheat hat to increase the chance of giving more crops
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player == null || player.isCreative()) return;

        BlockState state = event.getState();
        Block block = state.getBlock();

        if (block instanceof CropBlock cropBlock && cropBlock.isMaxAge(state)) {
            ItemStack headSlot = player.getItemBySlot(EquipmentSlot.HEAD);
            if (headSlot.is(ItemRegistry.STRAW_HAT.get())) {
                if (Math.random() < 0.5) {
                    LevelAccessor levelAccessor = event.getLevel();
                    if (levelAccessor instanceof Level level && !level.isClientSide()) {
                        BlockPos pos = event.getPos();
                        ItemStack cropItem = cropBlock.getCloneItemStack(level, pos, state, false, player);
                        if (!cropItem.isEmpty()) {
                            ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, cropItem);
                            level.addFreshEntity(entity);
                        }
                    }
                }
            }
        }
    }
}
