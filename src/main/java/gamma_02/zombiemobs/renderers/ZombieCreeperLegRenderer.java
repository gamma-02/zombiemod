package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.RenderInit;
import gamma_02.zombiemobs.entities.ZombieCreeperLeg;
import gamma_02.zombiemobs.models.creeperLegCovertedModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import static gamma_02.zombiemobs.ZombieMod.ModID;

public class ZombieCreeperLegRenderer extends MobEntityRenderer<ZombieCreeperLeg, creeperLegCovertedModel<ZombieCreeperLeg>>
{
    public ZombieCreeperLegRenderer(EntityRendererFactory.Context context)
    {
        super(context, new creeperLegCovertedModel<>(context.getPart(RenderInit.CREEPER_LEG_LAYER)), 0.5f);
    }

    @Override public Identifier getTexture(ZombieCreeperLeg entity)
    {
        return new Identifier(ModID, "textures/creeper_01.png");
    }
    public void render(ZombieCreeperLeg slimeEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.25F * (float)slimeEntity.getSize();
        super.render(slimeEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
    protected void scale(ZombieCreeperLeg slimeEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.999F, 0.999F, 0.999F);
        matrixStack.translate(0.0D, 0.001D, 0.0D);
        float h = 1;
        float i = MathHelper.lerp(f, slimeEntity.lastStretch, slimeEntity.stretch) / (h * 0.5F + 1.0F);
        float j = 1.0F / (i + 1.0F);
        matrixStack.scale(j * h, 1.0F / j * h, j * h);
    }
}
