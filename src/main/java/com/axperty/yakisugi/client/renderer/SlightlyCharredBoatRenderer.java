package com.axperty.yakisugi.client.renderer;

import com.axperty.yakisugi.Yakisugi;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;

public class SlightlyCharredBoatRenderer extends AbstractBoatRenderer {
    private final Model.Simple waterPatchModel;
    private final EntityModel<BoatRenderState> model;

    public SlightlyCharredBoatRenderer(EntityRendererProvider.Context context) {
        super(context, Identifier.fromNamespaceAndPath(Yakisugi.MOD_ID, "textures/entity/boat/slightly_charred.png"));
        this.waterPatchModel = new Model.Simple(context.bakeLayer(ModelLayers.BOAT_WATER_PATCH), texture -> RenderTypes.waterMask());
        this.model = new BoatModel(context.bakeLayer(ModelLayers.OAK_BOAT));
    }

    @Override
    protected EntityModel<BoatRenderState> model() {
        return this.model;
    }

    @Override
    protected void submitTypeAdditions(BoatRenderState state, PoseStack poseStack, SubmitNodeCollector collector, int light) {
        if (!state.isUnderWater) {
            collector.submitModel(this.waterPatchModel, Unit.INSTANCE, poseStack, this.texture, light, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        }
    }
}
