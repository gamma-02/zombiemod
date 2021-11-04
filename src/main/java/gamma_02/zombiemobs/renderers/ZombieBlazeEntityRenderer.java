package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.RenderInit;
import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieBlaze;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BlazeEntityModel;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

public class ZombieBlazeEntityRenderer extends MobEntityRenderer<ZombieBlaze, BlazeEntityModel<ZombieBlaze>>
{
    public ZombieBlazeEntityRenderer(EntityRendererFactory.Context context)
    {
        super(context, new BlazeEntityModel<>(context.getPart(RenderInit.BLAZE_LAYER)), 0.5f);
    }

    @Override public Identifier getTexture(ZombieBlaze entity)
    {
        return new Identifier(ZombieMod.ModID, "textures/blaze_01.png");
    }
}
