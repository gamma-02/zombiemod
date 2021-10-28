package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieSkeleton;
import gamma_02.zombiemobs.models.skeletonCovertedModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

import static gamma_02.zombiemobs.RenderInit.SKELETON_LAYER;

public class SkeletonZombieRenderer extends MobEntityRenderer<ZombieSkeleton, skeletonCovertedModel>
{
    public SkeletonZombieRenderer(EntityRendererFactory.Context context)
    {
        super(context, new skeletonCovertedModel(context.getPart(SKELETON_LAYER)), 0.5f);
    }

    @Override public Identifier getTexture(ZombieSkeleton entity)
    {
        return new Identifier(ZombieMod.ModID, "textures/skeleton_01.png");
    }
}
