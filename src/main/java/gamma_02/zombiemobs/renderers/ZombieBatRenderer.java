package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieBat;
import gamma_02.zombiemobs.models.batCovertedModel;
import net.minecraft.client.render.entity.BatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.util.Identifier;

import static gamma_02.zombiemobs.RenderInit.BAT_LAYER;

public class ZombieBatRenderer extends MobEntityRenderer<ZombieBat, batCovertedModel>
{
    public ZombieBatRenderer(EntityRendererFactory.Context context)
    {
        super(context, new batCovertedModel(context.getPart(BAT_LAYER)), 0.5f);
    }

    @Override public Identifier getTexture(ZombieBat entity)
    {
        return new Identifier(ZombieMod.ModID, "textures/bat.png");
    }
}
