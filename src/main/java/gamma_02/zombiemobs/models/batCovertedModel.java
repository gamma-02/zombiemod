// Made with Model Converter by Globox_Z
// Generate all required imports
// Made with Blockbench 4.0.3
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	package gamma_02.zombiemobs.models;

import gamma_02.zombiemobs.entities.ZombieBat;
import gamma_02.zombiemobs.renderers.ZombieBatRenderer;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class batCovertedModel extends EntityModel<ZombieBat>
{
private final ModelPart head;
	private final ModelPart rightEar;
	private final ModelPart leftEar;
	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart rightWingTip;
	private final ModelPart leftWing;
	private final ModelPart leftWingTip;
public batCovertedModel(ModelPart root) {
this.head = root.getChild("head");
this.leftEar = this.head.getChild("leftEar");
this.rightEar = this.head.getChild("rightEar");
this.body = root.getChild("body");
this.leftWing = this.body.getChild("leftWing");
this.leftWingTip = this.leftWing.getChild("leftWingTip");
this.rightWing = this.body.getChild("rightWing");
this.rightWingTip = this.rightWing.getChild("rightWingTip");
}
public static TexturedModelData getTexturedModelData() {
ModelData modelData = new ModelData();
ModelPartData modelPartData = modelData.getRoot();
ModelPartData modelPartData1 = modelPartData.addChild("head", ModelPartBuilder.create().uv(0,0).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), ModelTransform.pivot(0.0F,0.0F,0.0F));
modelPartData1.addChild("rightEar", ModelPartBuilder.create().uv(24,0).cuboid(-4.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F,0.0F,0.0F));
modelPartData1.addChild("leftEar", ModelPartBuilder.create().uv(24,0).cuboid(1.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F, true), ModelTransform.pivot(0.0F,0.0F,0.0F));
ModelPartData modelPartData2 = modelPartData.addChild("body", ModelPartBuilder.create().uv(0,16).cuboid(-3.0F, 4.0F, -3.0F, 6.0F, 12.0F, 6.0F).uv(0,34).cuboid(-5.0F, 16.0F, 0.0F, 10.0F, 16.0F, 1.0F), ModelTransform.pivot(0.0F,0.0F,0.0F));
ModelPartData modelPartData3 = modelPartData2.addChild("rightWing", ModelPartBuilder.create().uv(42,0).cuboid(-12.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F), ModelTransform.pivot(0.0F,0.0F,0.0F));
modelPartData3.addChild("rightWingTip", ModelPartBuilder.create().uv(24,16).cuboid(-8.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F), ModelTransform.pivot(-12.0F,1.0F,1.5F));
ModelPartData modelPartData4 = modelPartData2.addChild("leftWing", ModelPartBuilder.create().uv(42,0).cuboid(2.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F, true), ModelTransform.pivot(0.0F,0.0F,0.0F));
modelPartData4.addChild("leftWingTip", ModelPartBuilder.create().uv(24,16).cuboid(0.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F, true), ModelTransform.pivot(12.0F,1.0F,1.5F));
return TexturedModelData.of(modelData,64,64);
}
@Override
public void setAngles(ZombieBat entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
this.body.pitch = 0.5236F;
this.rightWing.yaw = -0.1745F;
this.rightWingTip.yaw = -0.2618F;
this.leftWing.yaw = 0.1745F;
this.leftWingTip.yaw = 0.2618F;
		//previously the render function, render code was moved to a method below
}
@Override
public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
}
public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
}
}