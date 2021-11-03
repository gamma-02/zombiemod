// Made with Model Converter by Globox_Z
// Generate all required imports
// Made with Blockbench 4.0.3
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	package gamma_02.zombiemobs.models;

import gamma_02.zombiemobs.entities.ZombieGolem;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class irongolemCovertedModel extends EntityModel<ZombieGolem>
{
private final ModelPart body;
	private final ModelPart head;
	private final ModelPart arm0;
	private final ModelPart arm1;
	private final ModelPart leg0;
	private final ModelPart leg1;
public irongolemCovertedModel(ModelPart root) {
this.body = root.getChild("body");
this.leg1 = this.body.getChild("leg1");
this.leg0 = this.body.getChild("leg0");
this.arm1 = this.body.getChild("arm1");
this.arm0 = this.body.getChild("arm0");
this.head = this.body.getChild("head");
}
public static TexturedModelData getTexturedModelData() {
ModelData modelData = new ModelData();
ModelPartData modelPartData = modelData.getRoot();
ModelPartData modelPartData1 = modelPartData.addChild("body", ModelPartBuilder.create().uv(0,40).cuboid(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F).uv(0,70).cuboid(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F,-7.0F,0.0F));
modelPartData1.addChild("head", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F).uv(24,0).cuboid(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(0.0F,0.0F,-2.0F));
modelPartData1.addChild("arm0", ModelPartBuilder.create().uv(60,21).cuboid(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F), ModelTransform.pivot(0.0F,0.0F,0.0F));
modelPartData1.addChild("arm1", ModelPartBuilder.create().uv(60,58).cuboid(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F), ModelTransform.pivot(0.0F,0.0F,0.0F));
modelPartData1.addChild("leg0", ModelPartBuilder.create().uv(37,0).cuboid(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F), ModelTransform.pivot(-4.0F,18.0F,0.0F));
modelPartData1.addChild("leg1", ModelPartBuilder.create().uv(60,0).cuboid(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, true), ModelTransform.pivot(5.0F,18.0F,0.0F));
return TexturedModelData.of(modelData,128,128);
}
@Override
public void setAngles(ZombieGolem entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
}
@Override
public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		
		body.render(matrixStack, buffer, packedLight, packedOverlay);
}
public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
}
	}