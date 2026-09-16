package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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

@EventBusSubscriber(modid = Yakisugi.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class EventsRegistry {

    private static final ResourceLocation STRAW_BOOTS_NO_SLOW_ID = ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "straw_boots_no_slow");
    private static final AttributeModifier STRAW_BOOTS_NO_SLOW_MODIFIER = new AttributeModifier(
            STRAW_BOOTS_NO_SLOW_ID, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        // Straw Mino makes the player not freeze in snow
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.STRAW_MINO.get())) {
            player.setTicksFrozen(0);
        }

        // Straw Boots make the player run faster in snow and soul sand
        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null) {
            BlockState below = player.getBlockStateOn();
            boolean shouldCancelSlow = player.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.STRAW_BOOTS.get())
                    && (below.is(BlockTags.SOUL_SPEED_BLOCKS) || below.is(BlockTags.SNOW));
            boolean hasModifier = speedAttribute.hasModifier(STRAW_BOOTS_NO_SLOW_ID);

            if (shouldCancelSlow && !hasModifier) {
                speedAttribute.addTransientModifier(STRAW_BOOTS_NO_SLOW_MODIFIER);
            } else if (!shouldCancelSlow && hasModifier) {
                speedAttribute.removeModifier(STRAW_BOOTS_NO_SLOW_ID);
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
                        ItemStack cropItem = cropBlock.getCloneItemStack(level, pos, state);
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
