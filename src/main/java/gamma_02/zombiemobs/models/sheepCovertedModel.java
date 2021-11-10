// Made with Model Converter by Globox_Z
// Generate all required imports
// Made with Blockbench 4.0.3
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	package gamma_02.zombiemobs.models;

import com.ibm.icu.text.Normalizer2;
import gamma_02.zombiemobs.entities.ZombieSheep;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class sheepCovertedModel extends QuadrupedEntityModel<ZombieSheep>
{
	private float headPitchModifier;


public sheepCovertedModel(ModelPart root) {
	super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = QuadrupedEntityModel.getModelData(12, Dilation.NONE);
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(
				EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F), ModelTransform.pivot(0.0F, 6.0F, -8.0F));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(28, 8).cuboid(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F), ModelTransform.of(0.0F, 5.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public void setAngles(ZombieSheep sheepEntity, float f, float g, float h, float i, float j) {
		super.setAngles(sheepEntity, f, g, h, i, j);
		this.head.pitch = this.headPitchModifier;
	}
	}