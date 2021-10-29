package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.entities.ZombiePig;
import gamma_02.zombiemobs.models.pigCovertedModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

import static gamma_02.zombiemobs.RenderInit.PIG_LAYER;
import static gamma_02.zombiemobs.ZombieMod.ModID;
import static gamma_02.zombiemobs.ZombieMod.ZOMBIE_PIG;

public class ZombiePigRenderer extends MobEntityRenderer<ZombiePig, pigCovertedModel>
{

    public ZombiePigRenderer(EntityRendererFactory.Context context)
    {
        super(context, new pigCovertedModel(context.getPart(PIG_LAYER)), 0.5f);
    }

    @Override public Identifier getTexture(ZombiePig entity)
    {
        return new Identifier(ModID, "textures/pig_01.png");
    }
}
