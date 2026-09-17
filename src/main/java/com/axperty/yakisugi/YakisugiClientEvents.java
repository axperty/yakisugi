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
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class YakisugiClientEvents implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerBlockColors();
        registerRenderers();
        registerLayerDefinitions();
        addLayers();
        registerAdditionalModels();
        registerBuiltinItemRenderer();
        registerBlockRenderLayers();
    }

    // Shoji bone transparency fix
    private void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SHOJI_BONE, RenderType.cutout());
    }

    private void registerBlockColors() {
        // Same water tint as vanilla's water cauldron, follows biome
        ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) ->
                state.getValue(TsukubaiBlock.WATER) && level != null && pos != null
                        ? BiomeColors.getAverageWaterColor(level, pos) : -1,
                BlockRegistry.AGED_STONE_TSUKUBAI, BlockRegistry.POLISHED_AGED_STONE_TSUKUBAI);
    }

    private void registerRenderers() {
        EntityRendererRegistry.register(EntityTypesRegistry.CHARRED_BOAT, context -> new CharredBoatRenderer(context, false));
        EntityRendererRegistry.register(EntityTypesRegistry.CHARRED_CHEST_BOAT, CharredChestBoatRenderer::new);
        EntityRendererRegistry.register(EntityTypesRegistry.SLIGHTLY_CHARRED_BOAT, context -> new SlightlyCharredBoatRenderer(context, false));
        EntityRendererRegistry.register(EntityTypesRegistry.SLIGHTLY_CHARRED_CHEST_BOAT, SlightlyCharredChestBoatRenderer::new);
        EntityRendererRegistry.register(EntityTypesRegistry.SHURIKEN, ThrownItemRenderer::new);
        BlockEntityRendererProvider<SignBlockEntity> signRenderer = SignRenderer::new;
        BlockEntityRendererRegistry.register(BlockEntityTypesRegistry.MOD_SIGN, signRenderer);

        BlockEntityRendererProvider<SignBlockEntity> hangingSignRenderer = HangingSignRenderer::new;
        BlockEntityRendererRegistry.register(BlockEntityTypesRegistry.MOD_HANGING_SIGN, hangingSignRenderer);
    }

    private void registerLayerDefinitions() {
        EntityModelLayerRegistry.registerModelLayer(StrawArmorModel.LAYER_LOCATION, StrawArmorModel::createBodyLayer);
    }

    // Straw armor fix
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void addLayers() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerRenderer || entityType == EntityType.ARMOR_STAND) {
                registrationHelper.register(new StrawArmorLayer(entityRenderer, context.getModelSet()));
            }
        });
    }

    private void registerAdditionalModels() {
        // Makes the Katana have two different textures
        ModelLoadingPlugin.register(context -> context.addModels(
                ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "item/katana_inventory"),
                ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "item/katana_handheld")
        ));
    }

    private void registerBuiltinItemRenderer() {
        BuiltinItemRendererRegistry.INSTANCE.register(ItemRegistry.KATANA, new KatanaItemRenderer());
    }
}
