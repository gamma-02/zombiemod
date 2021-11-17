// Made with Model Converter by Globox_Z
// Generate all required imports
// Made with Blockbench 4.0.3
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	package gamma_02.zombiemobs.models;

import com.eliotlash.mclib.math.functions.classic.Mod;
import gamma_02.zombiemobs.entities.ZombieCreeper;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class creeperCovertedModel extends SinglePartEntityModel<ZombieCreeper>
{
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart leg0;
	private final ModelPart leg1;
	private final ModelPart leg2;
	private final ModelPart leg3;
public creeperCovertedModel(ModelPart root) {
	this.root = root;
this.body = this.root.getChild("body");
this.leg3 = this.body.getChild("leg3");
this.leg2 = this.body.getChild("leg2");
this.leg1 = this.body.getChild("leg1");
this.leg0 = this.body.getChild("leg0");
this.head = this.body.getChild("head");
}
public static TexturedModelData getTexturedModelData() {
ModelData modelData = new ModelData();
ModelPartData modelPartData = modelData.getRoot();
ModelPartData modelPartData1 = modelPartData.addChild("body", ModelPartBuilder.create().uv(16,16).cuboid(-4.0F, -18.0F, -2.0F, 8.0F, 12.0F, 4.0F), ModelTransform.pivot(0.0F,24.0F,0.0F));
modelPartData1.addChild("head", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.pivot(0.0F,-18.0F,0.0F));
modelPartData1.addChild("leg0", ModelPartBuilder.create().uv(0,16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), ModelTransform.pivot(-2.0F,-6.0F,4.0F));
modelPartData1.addChild("leg1", ModelPartBuilder.create().uv(0,16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), ModelTransform.pivot(2.0F,-6.0F,4.0F));
modelPartData1.addChild("leg2", ModelPartBuilder.create().uv(0,16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), ModelTransform.pivot(-2.0F,-6.0F,-4.0F));
modelPartData1.addChild("leg3", ModelPartBuilder.create().uv(0,16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), ModelTransform.pivot(2.0F,-6.0F,-4.0F));
return TexturedModelData.of(modelData,64,32);
}
@Override
public void setAngles(ZombieCreeper entity,float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	this.head.yaw = headYaw * 0.017453292F;
	this.head.pitch = headPitch * 0.017453292F;
	this.leg0.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
	this.leg1.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
	this.leg2.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
	this.leg3.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
}
@Override
public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		
		body.render(matrixStack, buffer, packedLight, packedOverlay);
}

	@Override public ModelPart getPart()
	{
		return this.root;
	}

	public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
}
	}