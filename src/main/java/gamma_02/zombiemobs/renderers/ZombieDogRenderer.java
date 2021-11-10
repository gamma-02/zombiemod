package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.RenderInit;
import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieDog;
import gamma_02.zombiemobs.models.ZombieDogModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

public class ZombieDogRenderer extends MobEntityRenderer<ZombieDog, ZombieDogModel>
{
    public ZombieDogRenderer(EntityRendererFactory.Context context)
    {
        super(context, new ZombieDogModel(context.getPart(RenderInit.DOG_LAYER)), 0.5f);
    }

    protected float getAnimationProgress(WolfEntity wolfEntity, float f) {
        return wolfEntity.getTailAngle();
    }

    public void render(ZombieDog wolfEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (wolfEntity.isFurWet()) {
            float h = wolfEntity.getFurWetBrightnessMultiplier(g);
            (this.model).setColorMultiplier(h, h, h);
        }

        super.render(wolfEntity, f, g, matrixStack, vertexConsumerProvider, i);
        if (wolfEntity.isFurWet()) {
            (this.model).setColorMultiplier(1.0F, 1.0F, 1.0F);
        }

    }

    public Identifier getTexture(ZombieDog wolfEntity) {
        return new Identifier(ZombieMod.ModID, "textures/wolf_01.png");
    }
}
