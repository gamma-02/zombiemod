package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.RenderInit;
import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieSilverfish;
import gamma_02.zombiemobs.models.silverfishCovertedModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import java.awt.image.renderable.RenderableImage;

public class SilverfishRenderer extends MobEntityRenderer<ZombieSilverfish, silverfishCovertedModel>
{

    public SilverfishRenderer(EntityRendererFactory.Context context)
    {
        super(context, new silverfishCovertedModel(context.getPart(RenderInit.SILVERFISH_LAYER)), 0.5f);
    }

    @Override public Identifier getTexture(ZombieSilverfish entity)
    {

        return new Identifier(ZombieMod.ModID, "textures/silverfish_01.png");

    }
}
