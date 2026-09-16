package com.axperty.yakisugi;

import com.axperty.yakisugi.block.custom.TsukubaiBlock;
import com.axperty.yakisugi.registry.BlockRegistry;
import com.axperty.yakisugi.registry.BlockEntityTypesRegistry;
import com.axperty.yakisugi.client.model.StrawArmorModel;
import com.axperty.yakisugi.client.renderer.CharredBoatRenderer;
import com.axperty.yakisugi.client.renderer.CharredChestBoatRenderer;
import com.axperty.yakisugi.client.renderer.KatanaItemRenderer;
import com.axperty.yakisugi.client.renderer.SlightlyCharredBoatRenderer;
import com.axperty.yakisugi.client.renderer.SlightlyCharredChestBoatRenderer;
import com.axperty.yakisugi.client.renderer.StrawArmorLayer;
import com.axperty.yakisugi.registry.EntityTypesRegistry;
import com.axperty.yakisugi.registry.ItemRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = Yakisugi.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class YakisugiClientEvents {
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        // Same water tint as vanilla's water cauldron, follows biome
        event.register((state, level, pos, tintIndex) ->
                state.getValue(TsukubaiBlock.WATER) && level != null && pos != null
                        ? BiomeColors.getAverageWaterColor(level, pos) : -1,
                BlockRegistry.AGED_STONE_TSUKUBAI.get(), BlockRegistry.POLISHED_AGED_STONE_TSUKUBAI.get());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypesRegistry.CHARRED_BOAT.get(), context -> new CharredBoatRenderer(context, false));
        event.registerEntityRenderer(EntityTypesRegistry.CHARRED_CHEST_BOAT.get(), CharredChestBoatRenderer::new);
        event.registerEntityRenderer(EntityTypesRegistry.SLIGHTLY_CHARRED_BOAT.get(), context -> new SlightlyCharredBoatRenderer(context, false));
        event.registerEntityRenderer(EntityTypesRegistry.SLIGHTLY_CHARRED_CHEST_BOAT.get(), SlightlyCharredChestBoatRenderer::new);
        event.registerEntityRenderer(EntityTypesRegistry.SHURIKEN.get(), ThrownItemRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityTypesRegistry.MOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityTypesRegistry.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(StrawArmorModel.LAYER_LOCATION, StrawArmorModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new StrawArmorLayer<>(renderer, event.getEntityModels()));
            }
        }

        // Straw armor fix
        LivingEntityRenderer<ArmorStand, ArmorStandArmorModel> armorStandRenderer = event.getRenderer(EntityType.ARMOR_STAND);
        if (armorStandRenderer != null) {
            armorStandRenderer.addLayer(new StrawArmorLayer<>(armorStandRenderer, event.getEntityModels()));
        }
    }

    @SubscribeEvent
    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        // Makes the Katana have two different textures
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "item/katana_inventory")));
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "item/katana_handheld")));
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new KatanaItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        }, ItemRegistry.KATANA.get());
    }
}
