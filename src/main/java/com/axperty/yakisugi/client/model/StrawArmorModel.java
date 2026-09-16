package com.axperty.yakisugi.client.model;

import com.axperty.yakisugi.Yakisugi;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

// Adapted from a Blockbench export
public class StrawArmorModel extends EntityModel<EntityRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(Yakisugi.MOD_ID, "straw_armor"), "main");

    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart leftShoe;
    public final ModelPart rightShoe;
    public final ModelPart leftArm;
    public final ModelPart rightArm;

    public StrawArmorModel(ModelPart root) {
        super(root, RenderTypes::armorCutoutNoCull);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftShoe = root.getChild("left_shoe");
        this.rightShoe = root.getChild("right_shoe");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(96, 0).addBox(-4.0F, -6.55F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 33).addBox(-4.5F, -3.0F, -4.5F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.1F))
                .texOffs(16, 76).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.1F))
                .texOffs(0, 18).addBox(-6.5F, -1.0F, -6.6F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.1F))
                .texOffs(0, 0).addBox(-8.5F, 1.2F, -8.6F, 17.0F, 1.0F, 17.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -6.25F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 83).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("front_strands", CubeListBuilder.create().texOffs(68, 31).addBox(-5.0F, 1.0F, 0.0F, 10.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(74, 16).addBox(-5.0F, 4.0F, 1.0F, 10.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(74, 23).addBox(-5.0F, 7.0F, 2.0F, 10.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, -0.3491F, 0.0F, 0.0F));

        body.addOrReplaceChild("back_strands", CubeListBuilder.create().texOffs(52, 18).addBox(-5.0F, 0.0F, -1.0F, 10.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.2618F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_shoe", CubeListBuilder.create().texOffs(56, 60).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
                .texOffs(72, 60).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.55F)), PartPose.offset(1.9F, 12.0F, 0.0F));

        partdefinition.addOrReplaceChild("right_shoe", CubeListBuilder.create().texOffs(72, 60).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.55F))
                .texOffs(56, 60).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(38, 60).addBox(-1.0F, -2.1F, -2.0F, 5.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(5.0F, 2.0F, 0.0F));

        leftArm.addOrReplaceChild("outer_left_strands", CubeListBuilder.create().texOffs(56, 44).addBox(-2.0F, -2.0F, -2.0F, 6.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offsetAndRotation(1.0F, -0.2F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(38, 60).mirror().addBox(-4.0F, -2.1F, -2.0F, 5.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(-5.0F, 2.0F, 0.0F));

        rightArm.addOrReplaceChild("outer_right_strands", CubeListBuilder.create().texOffs(56, 44).mirror().addBox(-4.0F, -2.0F, -2.0F, 6.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -0.2F, 0.0F, 0.0F, 0.0F, 0.3054F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void setHeadVisible(boolean visible) {
        this.head.visible = visible;
    }

    public void setBodyVisible(boolean visible) {
        this.body.visible = visible;
        this.leftArm.visible = visible;
        this.rightArm.visible = visible;
    }

    public void setFeetVisible(boolean visible) {
        this.leftShoe.visible = visible;
        this.rightShoe.visible = visible;
    }

    @Override
    public void setupAnim(EntityRenderState state) {

    }
}
