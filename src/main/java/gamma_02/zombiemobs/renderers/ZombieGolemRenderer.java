package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.RenderInit;
import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieGolem;
import gamma_02.zombiemobs.models.irongolemCovertedModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.IronGolemCrackFeatureRenderer;
import net.minecraft.client.render.entity.feature.IronGolemFlowerFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class ZombieGolemRenderer extends MobEntityRenderer<ZombieGolem, irongolemCovertedModel>
{
    private static final Identifier TEXTURE = new Identifier(ZombieMod.ModID, "textures/iron_golem_01.png");

    public ZombieGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new irongolemCovertedModel(context.getPart(RenderInit.GOLEM_LAYER)), 0.7F);
        this.addFeature(new ZombieGolemCrackRenderer(this));

    }

    public Identifier getTexture(ZombieGolem ironGolemEntity) {
        return TEXTURE;
    }

    protected void setupTransforms(ZombieGolem ironGolemEntity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(ironGolemEntity, matrixStack, f, g, h);
        if (!((double)ironGolemEntity.limbDistance < 0.01D)) {
            float i = 13.0F;
            float j = ironGolemEntity.limbAngle - ironGolemEntity.limbDistance * (1.0F - h) + 6.0F;
            float k = (Math.abs(j % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(6.5F * k));
        }
    }
}
