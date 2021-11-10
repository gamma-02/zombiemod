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
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class pigCovertedModel extends QuadrupedEntityModel<ZombiePig>
{

public pigCovertedModel(ModelPart root) {
	super(root, false, 4.0F, 4.0F, 2.0F, 2.0F, 24);
}
	public static TexturedModelData getTexturedModelData() {
		Dilation dilation = Dilation.NONE;
		ModelData modelData = QuadrupedEntityModel.getModelData(6, dilation);
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, dilation).uv(16, 16).cuboid(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, dilation), ModelTransform.pivot(0.0F, 12.0F, -6.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}



}