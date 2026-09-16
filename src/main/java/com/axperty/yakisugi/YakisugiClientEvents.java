package com.axperty.yakisugi;

import com.axperty.yakisugi.registry.BlockRegistry;
import com.axperty.yakisugi.registry.BlockEntityTypesRegistry;
import com.axperty.yakisugi.client.model.StrawArmorModel;
import com.axperty.yakisugi.client.renderer.CharredBoatRenderer;
import com.axperty.yakisugi.client.renderer.CharredChestBoatRenderer;
import com.axperty.yakisugi.client.renderer.SlightlyCharredBoatRenderer;
import com.axperty.yakisugi.client.renderer.SlightlyCharredChestBoatRenderer;
import com.axperty.yakisugi.client.renderer.StrawArmorLayer;
import com.axperty.yakisugi.registry.EntityTypesRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.model.object.armorstand.ArmorStandArmorModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.List;

@EventBusSubscriber(modid = Yakisugi.MOD_ID, value = Dist.CLIENT)
public class YakisugiClientEvents {
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.BlockTintSources event) {
        // Same water tint as vanilla's water cauldron, follows biome
        BlockTintSource waterTint = new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return -1;
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return level != null && pos != null ? BiomeColors.getAverageWaterColor(level, pos) : -1;
            }
        };
        event.register(List.of(waterTint), BlockRegistry.AGED_STONE_TSUKUBAI.get(), BlockRegistry.POLISHED_AGED_STONE_TSUKUBAI.get());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypesRegistry.CHARRED_BOAT.get(), CharredBoatRenderer::new);
        event.registerEntityRenderer(EntityTypesRegistry.CHARRED_CHEST_BOAT.get(), CharredChestBoatRenderer::new);
        event.registerEntityRenderer(EntityTypesRegistry.SLIGHTLY_CHARRED_BOAT.get(), SlightlyCharredBoatRenderer::new);
        event.registerEntityRenderer(EntityTypesRegistry.SLIGHTLY_CHARRED_CHEST_BOAT.get(), SlightlyCharredChestBoatRenderer::new);
        event.registerEntityRenderer(EntityTypesRegistry.SHURIKEN.get(), ThrownItemRenderer::new);

        event.registerBlockEntityRenderer(BlockEntityTypesRegistry.MOD_SIGN.get(), StandingSignRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityTypesRegistry.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(StrawArmorModel.LAYER_LOCATION, StrawArmorModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerModelType skin : event.getSkins()) {
            AvatarRenderer<AbstractClientPlayer> renderer = event.getPlayerRenderer(skin);
            if (renderer != null) {
                renderer.addLayer(new StrawArmorLayer<>(renderer, event.getEntityModels()));
            }
        }

        // Straw armor fix
        LivingEntityRenderer<ArmorStand, ArmorStandRenderState, ArmorStandArmorModel> armorStandRenderer = event.getRenderer(EntityType.ARMOR_STAND);
        if (armorStandRenderer != null) {
            armorStandRenderer.addLayer(new StrawArmorLayer<>(armorStandRenderer, event.getEntityModels()));
        }
    }
}
