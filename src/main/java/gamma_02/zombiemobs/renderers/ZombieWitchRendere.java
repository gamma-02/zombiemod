package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieWitch;
import gamma_02.zombiemobs.models.witchCovertedModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import static gamma_02.zombiemobs.RenderInit.WITCH_LAYER;

public class ZombieWitchRendere extends MobEntityRenderer<ZombieWitch, witchCovertedModel>
{
    public ZombieWitchRendere(EntityRendererFactory.Context context)
    {
        super(context, new witchCovertedModel(context.getPart(WITCH_LAYER)), 0.5f);
    }

    @Override public Identifier getTexture(ZombieWitch entity)
    {
        return new Identifier(ZombieMod.ModID, "textures/witch_01.png");
    }



}
