// Made with Model Converter by Globox_Z
// Generate all required imports
// Made with Blockbench 4.0.3
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	package gamma_02.zombiemobs.models;

import gamma_02.zombiemobs.entities.ZombieSkeleton;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class skeletonCovertedModel extends EntityModel<ZombieSkeleton>
{
private final ModelPart waist;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart rightItem;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart leftItem;
public skeletonCovertedModel(ModelPart root ) {
this.waist = root.getChild("waist");
this.body = this.waist.getChild("body");
this.leftArm = this.body.getChild("leftArm");
this.leftItem = this.leftArm.getChild("leftItem");
this.rightArm = this.body.getChild("rightArm");
this.head = this.body.getChild("head");
this.rightItem = this.head.getChild("rightItem");
this.hat = this.head.getChild("hat");
}
public static TexturedModelData getTexturedModelData() {
ModelData modelData = new ModelData();
ModelPartData modelPartData = modelData.getRoot();
		
ModelPartData modelPartData1 = modelPartData.addChild("waist", ModelPartBuilder.create(), ModelTransform.pivot(0.0F,24.0F,0.0F));
ModelPartData modelPartData2 = modelPartData1.addChild("body", ModelPartBuilder.create().uv(16,16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F), ModelTransform.pivot(0.0F,-12.0F,0.0F));
ModelPartData modelPartData3 = modelPartData2.addChild("head", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.pivot(0.0F,0.0F,0.0F));
modelPartData3.addChild("hat", ModelPartBuilder.create().uv(32,0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F,0.0F,0.0F));
modelPartData3.addChild("rightItem", ModelPartBuilder.create(), ModelTransform.pivot(0.0F,-5.0F,-2.0F));
modelPartData2.addChild("rightArm", ModelPartBuilder.create().uv(0,16).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(-5.0F,2.0F,0.0F));
ModelPartData modelPartData4 = modelPartData2.addChild("leftArm", ModelPartBuilder.create().uv(40,16).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, true), ModelTransform.pivot(5.0F,2.0F,0.0F));
modelPartData4.addChild("leftItem", ModelPartBuilder.create(), ModelTransform.pivot(1.0F,7.0F,1.0F));
return TexturedModelData.of(modelData,64,32);
		
		
}
@Override
public void setAngles(ZombieSkeleton entity, float limbAngle, float limbDistance, float animationProgress,
		float headYaw, float headPitch){
this.body.pitch = 0.3491F;
this.head.pitch = -0.3491F;
this.rightItem.pitch = -1.5708F;
this.rightArm.pitch = -0.5236F;
this.leftArm.pitch = -0.5236F;
		//previously the render function, render code was moved to a method below
}
@Override
public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		
		waist.render(matrixStack, buffer, packedLight, packedOverlay);
}
public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
}


}