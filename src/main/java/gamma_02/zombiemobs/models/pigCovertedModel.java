// Made with Model Converter by Globox_Z
// Generate all required imports
// Made with Blockbench 4.0.3
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	package gamma_02.zombiemobs.models;

import gamma_02.zombiemobs.entities.ZombiePig;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class pigCovertedModel extends EntityModel<ZombiePig>
{
private final ModelPart body;
	private final ModelPart head;
	private final ModelPart leg0;
	private final ModelPart leg1;
	private final ModelPart leg2;
	private final ModelPart leg3;
public pigCovertedModel(ModelPart root) {
this.body = root.getChild("body");
this.head = root.getChild("head");
this.leg0 = root.getChild("leg0");
this.leg1 = root.getChild("leg1");
this.leg2 = root.getChild("leg2");
this.leg3 = root.getChild("leg3");
}
public static TexturedModelData getTexturedModelData() {
ModelData modelData = new ModelData();
ModelPartData modelPartData = modelData.getRoot();
modelPartData.addChild("body", ModelPartBuilder.create().uv(28,8).cuboid(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F), ModelTransform.pivot(0.0F,11.0F,2.0F));
modelPartData.addChild("head", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F).uv(16,16).cuboid(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F), ModelTransform.pivot(0.0F,12.0F,-6.0F));
modelPartData.addChild("leg0", ModelPartBuilder.create().uv(0,16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), ModelTransform.pivot(-3.0F,18.0F,7.0F));
modelPartData.addChild("leg1", ModelPartBuilder.create().uv(0,16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, true), ModelTransform.pivot(3.0F,18.0F,7.0F));
modelPartData.addChild("leg2", ModelPartBuilder.create().uv(0,16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), ModelTransform.pivot(-3.0F,18.0F,-5.0F));
modelPartData.addChild("leg3", ModelPartBuilder.create().uv(0,16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, true), ModelTransform.pivot(3.0F,18.0F,-5.0F));
return TexturedModelData.of(modelData,64,32);
}
@Override
public void setAngles(ZombiePig entity, float limbAngle, float limbDistance, float animationProgress,
		float headYaw, float headPitch){
this.body.pitch = 1.5708F;
		//previously the render function, render code was moved to a method below
}
@Override
public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		leg0.render(matrixStack, buffer, packedLight, packedOverlay);
		leg1.render(matrixStack, buffer, packedLight, packedOverlay);
		leg2.render(matrixStack, buffer, packedLight, packedOverlay);
		leg3.render(matrixStack, buffer, packedLight, packedOverlay);
}
public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
}

}