package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.RenderInit;
import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieMagmaCube;
import gamma_02.zombiemobs.models.ZombieMagmacubeModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.MagmaCubeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class ZombieMagmaCubeRenderer extends MobEntityRenderer<ZombieMagmaCube, ZombieMagmacubeModel<ZombieMagmaCube>>
{
    private static final Identifier TEXTURE = new Identifier(ZombieMod.ModID, "textures/magmacube_01.png");

    public ZombieMagmaCubeRenderer(EntityRendererFactory.Context context) {
        super(context, new ZombieMagmacubeModel<>(context.getPart(RenderInit.MAGMACUBE_LAYER)), 0.25F);
    }



    protected int getBlockLight(MagmaCubeEntity magmaCubeEntity, BlockPos blockPos) {
        return 15;
    }

    public Identifier getTexture(ZombieMagmaCube entity) {
        return TEXTURE;
    }

    protected void scale(ZombieMagmaCube magmaCubeEntity, MatrixStack matrixStack, float f) {
        int i = magmaCubeEntity.getSize();
        float g = MathHelper.lerp(f, magmaCubeEntity.lastStretch, magmaCubeEntity.stretch) / ((float)i * 0.5F + 1.0F);
        float h = 1.0F / (g + 1.0F);
        matrixStack.scale(h * (float)i, 1.0F / h * (float)i, h * (float)i);

    }


}

