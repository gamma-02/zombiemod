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
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.math.MathHelper;

public class batCovertedModel extends SinglePartEntityModel<ZombieBat>
{
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart rightEar;
	private final ModelPart leftEar;
	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart rightWingTip;
	private final ModelPart leftWingTip;

	public batCovertedModel(ModelPart modelPart) {
		this.root = modelPart;
		this.head = modelPart.getChild(EntityModelPartNames.HEAD);
		this.rightEar = modelPart.getChild("rightEar");
		this.leftEar = modelPart.getChild("leftEar");
		this.body = modelPart.getChild(EntityModelPartNames.BODY);
		this.rightWing = this.body.getChild(EntityModelPartNames.RIGHT_WING);
		this.rightWingTip = this.rightWing.getChild(EntityModelPartNames.RIGHT_WING_TIP);
		this.leftWing = this.body.getChild(EntityModelPartNames.LEFT_WING);
		this.leftWingTip = this.leftWing.getChild(EntityModelPartNames.LEFT_WING_TIP);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), ModelTransform.NONE);
		modelPartData.addChild("rightEar", ModelPartBuilder.create().uv(24,0).cuboid(-4.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F,0.0F,0.0F));
		modelPartData.addChild("leftEar", ModelPartBuilder.create().uv(24,0).cuboid(1.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F, true), ModelTransform.pivot(0.0F,0.0F,0.0F));
		ModelPartData modelPartData3 = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 16).cuboid(-3.0F, 4.0F, -3.0F, 6.0F, 12.0F, 6.0F).uv(0, 34).cuboid(-5.0F, 16.0F, 0.0F, 10.0F, 6.0F, 1.0F), ModelTransform.NONE);
		ModelPartData modelPartData4 = modelPartData3.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(42, 0).cuboid(-12.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F), ModelTransform.NONE);
		modelPartData4.addChild(EntityModelPartNames.RIGHT_WING_TIP, ModelPartBuilder.create().uv(24, 16).cuboid(-8.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F), ModelTransform.pivot(-12.0F, 1.0F, 1.5F));
		ModelPartData modelPartData5 = modelPartData3.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(42, 0).mirrored().cuboid(2.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F), ModelTransform.NONE);
		modelPartData5.addChild(EntityModelPartNames.LEFT_WING_TIP, ModelPartBuilder.create().uv(24, 16).mirrored().cuboid(0.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F), ModelTransform.pivot(12.0F, 1.0F, 1.5F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public ModelPart getPart() {
		return this.root;
	}

	public void setAngles(ZombieBat batEntity, float f, float g, float h, float i, float j) {
		if (batEntity.isRoosting()) {
			this.head.pitch = j * 0.017453292F;
			this.head.yaw = 3.1415927F - i * 0.017453292F;
			this.head.roll = 3.1415927F;
			this.head.setPivot(0.0F, -2.0F, 0.0F);
			this.rightWing.setPivot(-3.0F, 0.0F, 3.0F);
			this.leftWing.setPivot(3.0F, 0.0F, 3.0F);
			this.body.pitch = 3.1415927F;
			this.rightWing.pitch = -0.15707964F;
			this.rightWing.yaw = -1.2566371F;
			this.rightWingTip.yaw = -1.7278761F;
			this.leftWing.pitch = this.rightWing.pitch;
			this.leftWing.yaw = -this.rightWing.yaw;
			this.leftWingTip.yaw = -this.rightWingTip.yaw;
		} else {
			this.head.pitch = j * 0.017453292F;
			this.head.yaw = i * 0.017453292F;
			this.head.roll = 0.0F;
			this.head.setPivot(0.0F, 0.0F, 0.0F);
			this.rightWing.setPivot(0.0F, 0.0F, 0.0F);
			this.leftWing.setPivot(0.0F, 0.0F, 0.0F);
			this.body.pitch = 0.7853982F + MathHelper.cos(h * 0.1F) * 0.15F;
			this.body.yaw = 0.0F;
			this.rightWing.yaw = MathHelper.cos(h * 74.48451F * 0.017453292F) * 3.1415927F * 0.25F;
			this.leftWing.yaw = -this.rightWing.yaw;
			this.rightWingTip.yaw = this.rightWing.yaw * 0.5F;
			this.leftWingTip.yaw = -this.rightWing.yaw * 0.5F;
		}

	}
}