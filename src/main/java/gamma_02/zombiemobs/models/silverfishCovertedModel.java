// Made with Model Converter by Globox_Z
// Generate all required imports
// Made with Blockbench 4.0.3
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	package gamma_02.zombiemobs.models;

import gamma_02.zombiemobs.entities.ZombieSilverfish;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class silverfishCovertedModel extends EntityModel<ZombieSilverfish>
{
private final ModelPart bodyPart_2;
	private final ModelPart bodyPart_0;
	private final ModelPart bodyPart_1;
	private final ModelPart bodyLayer_2;
	private final ModelPart bodyPart_3;
	private final ModelPart bodyPart_4;
	private final ModelPart bodyLayer_1;
	private final ModelPart bodyPart_5;
	private final ModelPart bodyPart_6;
	private final ModelPart bodyLayer_0;
public silverfishCovertedModel(ModelPart root) {
this.bodyPart_2 = root.getChild("bodyPart_2");
this.bodyLayer_0 = this.bodyPart_2.getChild("bodyLayer_0");
this.bodyPart_6 = this.bodyPart_2.getChild("bodyPart_6");
this.bodyPart_5 = this.bodyPart_2.getChild("bodyPart_5");
this.bodyPart_4 = this.bodyPart_2.getChild("bodyPart_4");
this.bodyLayer_1 = this.bodyPart_4.getChild("bodyLayer_1");
this.bodyPart_3 = this.bodyPart_2.getChild("bodyPart_3");
this.bodyPart_1 = this.bodyPart_2.getChild("bodyPart_1");
this.bodyLayer_2 = this.bodyPart_1.getChild("bodyLayer_2");
this.bodyPart_0 = this.bodyPart_2.getChild("bodyPart_0");
}
public static TexturedModelData getTexturedModelData() {
ModelData modelData = new ModelData();
ModelPartData modelPartData = modelData.getRoot();
ModelPartData modelPartData1 = modelPartData.addChild("bodyPart_2", ModelPartBuilder.create().uv(0,9).cuboid(-3.0F, 0.0F, -1.5F, 6.0F, 4.0F, 3.0F), ModelTransform.pivot(0.0F,20.0F,1.0F));
modelPartData1.addChild("bodyPart_0", ModelPartBuilder.create().uv(0,0).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F,2.0F,-4.5F));
ModelPartData modelPartData2 = modelPartData1.addChild("bodyPart_1", ModelPartBuilder.create().uv(0,4).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0F,1.0F,-2.5F));
modelPartData2.addChild("bodyLayer_2", ModelPartBuilder.create().uv(20,18).cuboid(-3.0F, 0.0F, -1.5F, 6.0F, 5.0F, 2.0F), ModelTransform.pivot(0.0F,-2.0F,0.0F));
modelPartData1.addChild("bodyPart_3", ModelPartBuilder.create().uv(0,16).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F), ModelTransform.pivot(0.0F,1.0F,3.0F));
ModelPartData modelPartData3 = modelPartData1.addChild("bodyPart_4", ModelPartBuilder.create().uv(0,22).cuboid(-1.0F, 0.0F, -1.5F, 2.0F, 2.0F, 3.0F), ModelTransform.pivot(0.0F,2.0F,6.0F));
modelPartData3.addChild("bodyLayer_1", ModelPartBuilder.create().uv(20,11).cuboid(-3.0F, 0.0F, -1.5F, 6.0F, 4.0F, 3.0F), ModelTransform.pivot(0.0F,-2.0F,0.0F));
modelPartData1.addChild("bodyPart_5", ModelPartBuilder.create().uv(11,0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F), ModelTransform.pivot(0.0F,3.0F,8.5F));
modelPartData1.addChild("bodyPart_6", ModelPartBuilder.create().uv(13,4).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F), ModelTransform.pivot(0.0F,3.0F,10.5F));
modelPartData1.addChild("bodyLayer_0", ModelPartBuilder.create().uv(20,0).cuboid(-5.0F, 0.0F, -1.5F, 10.0F, 8.0F, 3.0F), ModelTransform.pivot(0.0F,-4.0F,0.0F));
return TexturedModelData.of(modelData,64,32);
}
@Override
public void setAngles(ZombieSilverfish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
}
@Override
public void render(MatrixStack matrixStack, VertexConsumer	buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		
		bodyPart_2.render(matrixStack, buffer, packedLight, packedOverlay);
}
public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
}

}