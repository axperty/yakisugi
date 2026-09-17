package com.axperty.yakisugi.client.renderer;

import com.axperty.yakisugi.Yakisugi;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.model.loading.v1.FabricBakedModelManager;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class KatanaItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private static final ResourceLocation INVENTORY_MODEL =
            ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "item/katana_inventory");
    private static final ResourceLocation HANDHELD_MODEL =
            ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "item/katana_handheld");

    @Override
    public void render(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                        MultiBufferSource buffer, int packedLight, int packedOverlay) {
        boolean handheld = displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                || displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                || displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                || displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
        boolean leftHand = displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                || displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;

        Minecraft minecraft = Minecraft.getInstance();
        FabricBakedModelManager modelManager = (FabricBakedModelManager) minecraft.getModelManager();
        BakedModel model = modelManager.getModel(handheld ? HANDHELD_MODEL : INVENTORY_MODEL);

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        ItemTransform transform = model.getTransforms().getTransform(displayContext);
        transform.apply(leftHand, poseStack);
        poseStack.translate(-0.5, -0.5, -0.5);

        minecraft.getItemRenderer().render(stack, displayContext, leftHand, poseStack, buffer, packedLight, packedOverlay, model);

        poseStack.popPose();
    }
}
