package gamma_02.zombiemobs;

import com.sun.jna.platform.win32.WinBase;
import gamma_02.zombiemobs.entities.ZombieSkeleton;
import gamma_02.zombiemobs.models.batCovertedModel;
import gamma_02.zombiemobs.models.pigCovertedModel;
import gamma_02.zombiemobs.models.skeletonCovertedModel;
import gamma_02.zombiemobs.renderers.SkeletonZombieRenderer;
import gamma_02.zombiemobs.renderers.ZombieBatRenderer;
import gamma_02.zombiemobs.renderers.ZombiePigRenderer;
import gamma_02.zombiemobs.renderers.ZombieWitchRendere;
import net.fabricmc.api.ClientModInitializer;
import gamma_02.zombiemobs.models.witchCovertedModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import javax.swing.text.html.parser.Entity;

public class RenderInit implements ClientModInitializer
{
    public static final EntityModelLayer WITCH_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "witch_layer"), "main");
    public static final EntityModelLayer BAT_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "bat_layer"), "main");
    public static final EntityModelLayer SKELETON_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "skeleton_layer"), "main");
    public static final EntityModelLayer PIG_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "pig_layer"), "main");
    @Override public void onInitializeClient()
    {
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_WITCH, ZombieWitchRendere::new);
        EntityModelLayerRegistry.registerModelLayer(WITCH_LAYER, witchCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_BAT, ZombieBatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BAT_LAYER, batCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_SKELETON, SkeletonZombieRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SKELETON_LAYER, skeletonCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_PIG, ZombiePigRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(PIG_LAYER, pigCovertedModel::getTexturedModelData);

    }
}
