package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.RenderInit;
import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieSheep;
import gamma_02.zombiemobs.models.sheepCovertedModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ZombieSheepRenderer extends MobEntityRenderer<ZombieSheep, sheepCovertedModel>
{

    public ZombieSheepRenderer(EntityRendererFactory.Context context)
    {
        super(context, new sheepCovertedModel(context.getPart(RenderInit.SHEEP_LAYER)), 0.5f);
    }

    @Override public Identifier getTexture(ZombieSheep entity)
    {
        return new Identifier(ZombieMod.ModID, "textures/sheep_01.png");
    }
}
