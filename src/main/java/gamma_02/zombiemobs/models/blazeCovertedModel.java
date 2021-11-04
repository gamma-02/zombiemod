// Made with Model Converter by Globox_Z
// Generate all required imports
// Made with Blockbench 4.0.3
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	package gamma_02.zombiemobs.models;

import gamma_02.zombiemobs.entities.ZombieBlaze;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public class blazeCovertedModel<T extends Entity> extends SinglePartEntityModel<T>
{

	private final ModelPart head;
	private final ModelPart[] rods;
	private final ModelPart root;
public blazeCovertedModel(ModelPart root) {
	this.root = root;
	this.head = root.getChild("head");
	this.rods = new ModelPart[12];
	Arrays.setAll(this.rods, (index) -> {
		return root.getChild(getRodName(index));
	});
}
	private static String getRodName(int index) {
		return "upperBodyParts" + index;
	}

public static TexturedModelData getTexturedModelData() {
	ModelData modelData = new ModelData();
	ModelPartData modelPartData = modelData.getRoot();
	modelPartData.addChild(
			EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.NONE);
	float f = 0.0F;
	ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 16).cuboid(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);

	int i;
	float g;
	float h;
	float j;
	for(i = 0; i < 4; ++i) {
		g = MathHelper.cos(f) * 9.0F;
		h = -2.0F + MathHelper.cos((float)(i * 2) * 0.25F);
		j = MathHelper.sin(f) * 9.0F;
		modelPartData.addChild(getRodName(i), modelPartBuilder, ModelTransform.pivot(g, h, j));
		++f;
	}

	f = 0.7853982F;

	for(i = 4; i < 8; ++i) {
		g = MathHelper.cos(f) * 7.0F;
		h = 2.0F + MathHelper.cos((float)(i * 2) * 0.25F);
		j = MathHelper.sin(f) * 7.0F;
		modelPartData.addChild(getRodName(i), modelPartBuilder, ModelTransform.pivot(g, h, j));
		++f;
	}

	f = 0.47123894F;

	for(i = 8; i < 12; ++i) {
		g = MathHelper.cos(f) * 5.0F;
		h = 11.0F + MathHelper.cos((float)i * 1.5F * 0.5F);
		j = MathHelper.sin(f) * 5.0F;
		modelPartData.addChild(getRodName(i), modelPartBuilder, ModelTransform.pivot(g, h, j));
		++f;
	}

	return TexturedModelData.of(modelData, 64, 32);
}
@Override
public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		
//		upperBodyParts0.render(matrixStack, buffer, packedLight, packedOverlay);
//		upperBodyParts1.render(matrixStack, buffer, packedLight, packedOverlay);
//		upperBodyParts2.render(matrixStack, buffer, packedLight, packedOverlay);
//		upperBodyParts3.render(matrixStack, buffer, packedLight, packedOverlay);
//		upperBodyParts4.render(matrixStack, buffer, packedLight, packedOverlay);
//		upperBodyParts5.render(matrixStack, buffer, packedLight, packedOverlay);
//		upperBodyParts6.render(matrixStack, buffer, packedLight, packedOverlay);
//		upperBodyParts7.render(matrixStack, buffer, packedLight, packedOverlay);
//		upperBodyParts8.render(matrixStack, buffer, packedLight, packedOverlay);
//		upperBodyParts9.render(matrixStack, buffer, packedLight, packedOverlay);
//		upperBodyParts10.render(matrixStack, buffer, packedLight, packedOverlay);
//		upperBodyParts11.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
}

	@Override public ModelPart getPart()
	{
		return null;
	}

	public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
}

	@Override public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress,
			float headYaw, float headPitch)
	{
		float f = animationProgress * 3.1415927F * -0.1F;

		int i;
		for(i = 0; i < 4; ++i) {
			this.rods[i].pivotY = -2.0F + MathHelper.cos(((float)(i * 2) + animationProgress) * 0.25F);
			this.rods[i].pivotX = MathHelper.cos(f) * 9.0F;
			this.rods[i].pivotZ = MathHelper.sin(f) * 9.0F;
			++f;
		}

		f = 0.7853982F + animationProgress * 3.1415927F * 0.03F;

		for(i = 4; i < 8; ++i) {
			this.rods[i].pivotY = 2.0F + MathHelper.cos(((float)(i * 2) + animationProgress) * 0.25F);
			this.rods[i].pivotX = MathHelper.cos(f) * 7.0F;
			this.rods[i].pivotZ = MathHelper.sin(f) * 7.0F;
			++f;
		}

		f = 0.47123894F + animationProgress * 3.1415927F * -0.05F;

		for(i = 8; i < 12; ++i) {
			this.rods[i].pivotY = 11.0F + MathHelper.cos(((float)i * 1.5F + animationProgress) * 0.5F);
			this.rods[i].pivotX = MathHelper.cos(f) * 5.0F;
			this.rods[i].pivotZ = MathHelper.sin(f) * 5.0F;
			++f;
		}

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}
}