package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.RenderInit;
import gamma_02.zombiemobs.entities.ZombieSpider;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.SpiderEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.RabbitEntityModel;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import static gamma_02.zombiemobs.ZombieMod.ModID;

public class ZombieSpiderRenderer extends MobEntityRenderer<ZombieSpider, SpiderEntityModel<ZombieSpider>>
{
    public ZombieSpiderRenderer(EntityRendererFactory.Context context)
    {
        super(context, new SpiderEntityModel<>(context.getPart(RenderInit.SPIDER_LAYER)), 0.5f);
        this.addFeature(new ZombieSpiderEyesFeatureRenderer<>(this));
    }

    @Override public Identifier getTexture(ZombieSpider entity)
    {
        return new Identifier(ModID, "textures/spider_01.png");
    }
}
class ZombieSpiderEyesFeatureRenderer<T extends Entity, M extends SpiderEntityModel<T>> extends
        EyesFeatureRenderer<T, M>{

    public ZombieSpiderEyesFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext)
    {
        super(featureRendererContext);
    }

    @Override public RenderLayer getEyesTexture()
    {
        return RenderLayer.getEyes(new Identifier(ModID, "textures/spider_eyes.png"));
    }
}