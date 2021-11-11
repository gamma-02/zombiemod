package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieSkeleton;
import gamma_02.zombiemobs.models.skeletonCovertedModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public class SkeletonZombieRenderer extends GeoEntityRenderer<ZombieSkeleton>
{
    public SkeletonZombieRenderer(EntityRendererFactory.Context context)
    {
        super(context, new skeletonCovertedModel());
        this.shadowRadius = 0.5f;
    }

    @Override public Identifier getTexture(ZombieSkeleton entity)
    {
        return new Identifier(ZombieMod.ModID, "textures/skeleton_01.png");
    }
}
