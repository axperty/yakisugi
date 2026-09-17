package com.axperty.yakisugi.client.renderer;

import com.axperty.yakisugi.Yakisugi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.model.loading.v1.FabricBakedModelManager;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class KatanaItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private static final ResourceLocation INVENTORY_MODEL =
            ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "item/katana_inventory");
    private static final ResourceLocation HANDHELD_MODEL =
            ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "item/katana_handheld");

    // null = the no-direction quads, keeps it one loop
    private static final Direction[] QUAD_GROUPS = {
            Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, null
    };

    @Override
    public void render(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                        MultiBufferSource buffer, int packedLight, int overlay) {
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
        // undo the -0.5 vanilla
        poseStack.translate(0.5, 0.5, 0.5);
        model.getTransforms().getTransform(displayContext).apply(leftHand, poseStack);
        poseStack.translate(-0.5, -0.5, -0.5);

        // ItemRenderer.render() caused the inventory shading bug, needs test
        // Fixed
        VertexConsumer buf = ItemRenderer.getFoilBuffer(buffer, RenderType.cutout(), true, stack.hasFoil());
        PoseStack.Pose pose = poseStack.last();
        RandomSource rand = RandomSource.create();
        for (Direction dir : QUAD_GROUPS) {
            rand.setSeed(42L);
            for (BakedQuad quad : model.getQuads(null, dir, rand)) {
                buf.putBulkData(pose, quad, 1F, 1F, 1F, 1F, packedLight, overlay);
            }
        }

        poseStack.popPose();
    }
}
