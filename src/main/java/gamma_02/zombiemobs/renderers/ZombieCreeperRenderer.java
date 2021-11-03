package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.RenderInit;
import gamma_02.zombiemobs.entities.ZombieCreeper;
import gamma_02.zombiemobs.models.creeperCovertedModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import static gamma_02.zombiemobs.ZombieMod.ModID;

public class ZombieCreeperRenderer extends MobEntityRenderer<ZombieCreeper, creeperCovertedModel>
{
    public ZombieCreeperRenderer(EntityRendererFactory.Context context)
    {
        super(context, new creeperCovertedModel(context.getPart(RenderInit.CREEPER_LAYER)), 0.5f);
    }

    @Override public Identifier getTexture(ZombieCreeper entity)
    {
        return new Identifier(ModID, "textures/creeper_01.png");
    }
}
