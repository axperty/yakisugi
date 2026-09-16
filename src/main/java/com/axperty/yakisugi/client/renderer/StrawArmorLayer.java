package com.axperty.yakisugi.client.renderer;

import com.axperty.yakisugi.Yakisugi;
import com.axperty.yakisugi.client.model.StrawArmorModel;
import com.axperty.yakisugi.registry.ItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class StrawArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Yakisugi.MOD_ID, "textures/entity/straw_armor.png");

    private final StrawArmorModel<T> model;

    public StrawArmorLayer(RenderLayerParent<T, M> renderer, EntityModelSet entityModels) {
        super(renderer);
        this.model = new StrawArmorModel<>(entityModels.bakeLayer(StrawArmorModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity,
                        float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack headItem = entity.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestItem = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack feetItem = entity.getItemBySlot(EquipmentSlot.FEET);

        boolean showHead = headItem.is(ItemRegistry.STRAW_HAT.get());
        boolean showBody = chestItem.is(ItemRegistry.STRAW_MINO.get());
        boolean showFeet = feetItem.is(ItemRegistry.STRAW_BOOTS.get());

        if (!showHead && !showBody && !showFeet) {
            return;
        }

        M parent = this.getParentModel();
        model.head.copyFrom(parent.head);
        model.body.copyFrom(parent.body);
        model.leftArm.copyFrom(parent.leftArm);
        model.rightArm.copyFrom(parent.rightArm);
        // Fix shoes bug
        model.leftShoe.copyFrom(parent.leftLeg);
        model.rightShoe.copyFrom(parent.rightLeg);

        model.setHeadVisible(showHead);
        model.setBodyVisible(showBody);
        model.setFeetVisible(showFeet);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(TEXTURE));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);
    }
}
