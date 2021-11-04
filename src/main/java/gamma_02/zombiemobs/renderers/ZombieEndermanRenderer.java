package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.RenderInit;
import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieEnderman;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EndermanEyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.system.CallbackI;

public class ZombieEndermanRenderer extends MobEntityRenderer<ZombieEnderman, EndermanEntityModel<ZombieEnderman>>
{
    public Identifier TEXTURE = new Identifier(ZombieMod.ModID, "textures/enderman.png");
    public ZombieEndermanRenderer(EntityRendererFactory.Context context)
    {
        super(context, new EndermanEntityModel<>(context.getPart(RenderInit.ENDERMAN_LAYER)), 0.5f);
        this.addFeature(new EndermanBlockFeatureRenderer(this));
        this.addFeature(new ZombieEndermanEyesFeatureRenderer<>(this));
    }

    @Override public Identifier getTexture(ZombieEnderman entity)
    {
        return TEXTURE;
    }
    public void render(ZombieEnderman endermanEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        BlockState blockState = endermanEntity.getCarriedBlock();
        EndermanEntityModel<EndermanEntity> endermanEntityModel = (EndermanEntityModel)this.getModel();
        endermanEntityModel.carryingBlock = blockState != null;
        endermanEntityModel.angry = endermanEntity.isAngry();
        super.render(endermanEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

class EndermanBlockFeatureRenderer extends FeatureRenderer<ZombieEnderman, EndermanEntityModel<ZombieEnderman>>
{
    public EndermanBlockFeatureRenderer(FeatureRendererContext<ZombieEnderman, EndermanEntityModel<ZombieEnderman>> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ZombieEnderman endermanEntity, float f, float g, float h, float j, float k, float l) {
        BlockState blockState = endermanEntity.getCarriedBlock();
        if (blockState != null) {
            matrixStack.push();
            matrixStack.translate(0.0D, 0.6875D, -0.75D);
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(20.0F));
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(45.0F));
            matrixStack.translate(0.25D, 0.1875D, 0.25D);
            float m = 0.5F;
            matrixStack.scale(-0.5F, -0.5F, 0.5F);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(blockState, matrixStack, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV);
            matrixStack.pop();
        }
    }
}
class ZombieEndermanEyesFeatureRenderer<T extends ZombieEnderman> extends EyesFeatureRenderer<T, EndermanEntityModel<T>> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(ZombieMod.ModID,"textures/enderman_eyes.png"));

    public ZombieEndermanEyesFeatureRenderer(FeatureRendererContext<T, EndermanEntityModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}
