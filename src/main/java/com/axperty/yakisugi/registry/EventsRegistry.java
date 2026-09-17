package com.axperty.yakisugi.registry;

import com.axperty.yakisugi.Yakisugi;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerPlayer;

public class EventsRegistry {

    private static final ResourceLocation STRAW_BOOTS_NO_SLOW_ID = ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "straw_boots_no_slow");
    private static final AttributeModifier STRAW_BOOTS_NO_SLOW_MODIFIER = new AttributeModifier(
            STRAW_BOOTS_NO_SLOW_ID, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                onPlayerTick(player);
            }
        });
        PlayerBlockBreakEvents.AFTER.register(EventsRegistry::onBlockBreak);
    }

    private static void onPlayerTick(Player player) {
        // straw mino = no freezing in snow
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.STRAW_MINO)) {
            player.setTicksFrozen(0);
        }

        // Straw Boots make the player run faster in snow and soul sand
        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null) {
            BlockState below = player.getBlockStateOn();
            boolean shouldCancelSlow = player.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.STRAW_BOOTS)
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
    private static void onBlockBreak(Level level, Player player, BlockPos pos, BlockState state, BlockEntity be) {
        if (player.isCreative()) return;

        Block block = state.getBlock();
        if (!(block instanceof CropBlock cropBlock) || !cropBlock.isMaxAge(state)) return;

        ItemStack headSlot = player.getItemBySlot(EquipmentSlot.HEAD);
        if (!headSlot.is(ItemRegistry.STRAW_HAT) || level.isClientSide()) return;

        if (Math.random() < 0.5) {
            ItemStack cropItem = cropBlock.getCloneItemStack(level, pos, state);
            if (!cropItem.isEmpty()) {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, cropItem));
            }
        }
    }
}
