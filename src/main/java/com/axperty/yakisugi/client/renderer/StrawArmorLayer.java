package com.axperty.yakisugi.client.renderer;

import com.axperty.yakisugi.Yakisugi;
import com.axperty.yakisugi.client.model.StrawArmorModel;
import com.axperty.yakisugi.registry.ItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class StrawArmorLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Yakisugi.MOD_ID, "textures/entity/straw_armor.png");

    private final StrawArmorModel model;

    public StrawArmorLayer(RenderLayerParent<S, M> renderer, EntityModelSet entityModels) {
        super(renderer);
        this.model = new StrawArmorModel(entityModels.bakeLayer(StrawArmorModel.LAYER_LOCATION));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, S state, float yRot, float xRot) {
        ItemStack headItem = state.headEquipment;
        ItemStack chestItem = state.chestEquipment;
        ItemStack feetItem = state.feetEquipment;

        boolean showHead = headItem.is(ItemRegistry.STRAW_HAT.get());
        boolean showBody = chestItem.is(ItemRegistry.STRAW_MINO.get());
        boolean showFeet = feetItem.is(ItemRegistry.STRAW_BOOTS.get());

        if (!showHead && !showBody && !showFeet) {
            return;
        }

        M parent = this.getParentModel();
        model.head.loadPose(parent.head.storePose());
        model.body.loadPose(parent.body.storePose());
        model.leftArm.loadPose(parent.leftArm.storePose());
        model.rightArm.loadPose(parent.rightArm.storePose());
        // Fix shoes bug
        model.leftShoe.loadPose(parent.leftLeg.storePose());
        model.rightShoe.loadPose(parent.rightLeg.storePose());

        model.setHeadVisible(showHead);
        model.setBodyVisible(showBody);
        model.setFeetVisible(showFeet);

        collector.submitModel(model, state, poseStack, TEXTURE, packedLight, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
    }
}
