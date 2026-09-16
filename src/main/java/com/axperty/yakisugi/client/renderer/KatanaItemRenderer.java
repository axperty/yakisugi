package com.axperty.yakisugi.client.renderer;

import com.axperty.yakisugi.Yakisugi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class KatanaItemRenderer extends BlockEntityWithoutLevelRenderer {
    private static final ResourceLocation INVENTORY_MODEL =
            ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "item/katana_inventory");
    private static final ResourceLocation HANDHELD_MODEL =
            ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "item/katana_handheld");

    public KatanaItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                              MultiBufferSource buffer, int packedLight, int packedOverlay) {
        boolean handheld = displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                || displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                || displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                || displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
        boolean leftHand = displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                || displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;

        Minecraft minecraft = Minecraft.getInstance();
        BakedModel model = minecraft.getModelManager()
                .getModel(ModelResourceLocation.standalone(handheld ? HANDHELD_MODEL : INVENTORY_MODEL));

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        ItemTransform transform = model.getTransforms().getTransform(displayContext);
        transform.apply(leftHand, poseStack);
        poseStack.translate(-0.5, -0.5, -0.5);

        ItemRenderer itemRenderer = minecraft.getItemRenderer();
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBuffer(buffer, RenderType.cutout(), true, stack.hasFoil());
        itemRenderer.renderModelLists(model, stack, packedLight, packedOverlay, poseStack, vertexConsumer);

        poseStack.popPose();
    }
}
