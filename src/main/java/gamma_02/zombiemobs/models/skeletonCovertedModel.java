// Made with Model Converter by Globox_Z
// Generate all required imports
// Made with Blockbench 4.0.3
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	package gamma_02.zombiemobs.models;

import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieSkeleton;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class skeletonCovertedModel extends AnimatedGeoModel<ZombieSkeleton>
{

	@Override public Identifier getModelLocation(ZombieSkeleton object)
	{
		return new Identifier(ZombieMod.ModID, "geo/skeleton_03.geo.json");
	}

	@Override public Identifier getTextureLocation(ZombieSkeleton object)
	{
		return new Identifier(ZombieMod.ModID, "textures/skeleton_01.png");
	}

	@Override public Identifier getAnimationFileLocation(ZombieSkeleton animatable)
	{
		return new Identifier(ZombieMod.ModID, "animations/skeleton.animation.json");
	}
}