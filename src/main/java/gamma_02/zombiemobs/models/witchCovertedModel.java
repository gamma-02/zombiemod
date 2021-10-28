// Made with Model Converter by Globox_Z
// Generate all required imports
// Made with Blockbench 4.0.3
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	package gamma_02.zombiemobs.models;

import gamma_02.zombiemobs.entities.ZombieWitch;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class witchCovertedModel extends EntityModel<ZombieWitch>
{
private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart hat;
	private final ModelPart hat2;
	private final ModelPart hat3;
	private final ModelPart hat4;
	private final ModelPart body;
	private final ModelPart arms;
	private final ModelPart leg0;
	private final ModelPart leg1;
public witchCovertedModel(ModelPart root) {

this.head = root.getChild("head");
this.hat = this.head.getChild("hat");
this.hat2 = this.hat.getChild("hat2");
this.hat3 = this.hat2.getChild("hat3");
this.hat4 = this.hat3.getChild("hat4");
this.nose = this.head.getChild("nose");
this.body = root.getChild("body");
this.arms = root.getChild("arms");
this.leg0 = root.getChild("leg0");
this.leg1 = root.getChild("leg1");
}
public static TexturedModelData getTexturedModelData() {
ModelData modelData = new ModelData();
ModelPartData modelPartData = modelData.getRoot();
ModelPartData modelPartData1 = modelPartData.addChild("head", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), ModelTransform.pivot(0.0F,0.0F,0.0F));
modelPartData1.addChild("nose", ModelPartBuilder.create().uv(24,0).cuboid(-1.0F, -27.0F, -6.0F, 2.0F, 4.0F, 2.0F).uv(0,0).cuboid(0.0F, -26.0F, -6.75F, 1.0F, 1.0F, 1.0F, new Dilation(-0.25F)), ModelTransform.pivot(0.0F,24.0F,0.0F));
ModelPartData modelPartData2 = modelPartData1.addChild("hat", ModelPartBuilder.create().uv(0,64).cuboid(0.0F, -2.0187F, 0.0F, 10.0F, 2.0F, 10.0F), ModelTransform.pivot(-5.0F,-8.0313F,-5.0F));
ModelPartData modelPartData3 = modelPartData2.addChild("hat2", ModelPartBuilder.create().uv(0,76).cuboid(-5.0F, -5.5F, -5.0F, 7.0F, 4.0F, 7.0F), ModelTransform.pivot(6.75F,0.0313F,7.0F));
ModelPartData modelPartData4 = modelPartData3.addChild("hat3", ModelPartBuilder.create().uv(0,87).cuboid(-3.25F, -5.5F, -3.0F, 4.0F, 4.0F, 4.0F), ModelTransform.pivot(0.0F,-3.0F,0.0F));
modelPartData4.addChild("hat4", ModelPartBuilder.create().uv(0,95).cuboid(-1.5F, -4.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F,-3.0F,0.0F));
modelPartData.addChild("body", ModelPartBuilder.create().uv(16,20).cuboid(-4.0F, -24.0F, -3.0F, 8.0F, 12.0F, 6.0F).uv(0,38).cuboid(-4.0F, -24.0F, -3.0F, 8.0F, 18.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F,24.0F,0.0F));
modelPartData.addChild("arms", ModelPartBuilder.create().uv(40,38).cuboid(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F).uv(44,22).cuboid(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).uv(44,22).cuboid(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), ModelTransform.pivot(0.0F,2.0F,0.0F));
modelPartData.addChild("leg0", ModelPartBuilder.create().uv(0,22).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(-2.0F,12.0F,0.0F));
modelPartData.addChild("leg1", ModelPartBuilder.create().uv(0,22).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(2.0F,12.0F,0.0F));
return TexturedModelData.of(modelData,64,128);
}

@Override
public void render(MatrixStack matrixStack, VertexConsumer	buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		arms.render(matrixStack, buffer, packedLight, packedOverlay);
		leg0.render(matrixStack, buffer, packedLight, packedOverlay);
		leg1.render(matrixStack, buffer, packedLight, packedOverlay);
}
public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
}

	@Override public void setAngles(ZombieWitch entity, float limbAngle, float limbDistance, float animationProgress,
			float headYaw, float headPitch)
	{
		this.hat2.pitch = -0.0524F;
		this.hat2.roll = 0.0262F;
		this.hat3.pitch = -0.1047F;
		this.hat3.roll = 0.0524F;
		this.hat4.pitch = -0.2094F;
		this.hat4.roll = 0.1047F;
		this.arms.pitch = -0.7854F;
	}
}