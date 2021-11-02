package gamma_02.zombiemobs;

import com.sun.jna.platform.win32.WinBase;
import gamma_02.zombiemobs.entities.ZombieSkeleton;
import gamma_02.zombiemobs.models.*;
import gamma_02.zombiemobs.renderers.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

import javax.swing.text.html.parser.Entity;

public class RenderInit implements ClientModInitializer
{
    public static final EntityModelLayer WITCH_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "witch_layer"), "main");
    public static final EntityModelLayer BAT_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "bat_layer"), "main");
    public static final EntityModelLayer SKELETON_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "skeleton_layer"), "main");
    public static final EntityModelLayer PIG_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "pig_layer"), "main");
    public static final EntityModelLayer SHEEP_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "sheep_layer"), "main");
    public static final EntityModelLayer SILVERFISH_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "silverfish_layer"), "main");
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
        EntityModelLayerRegistry.registerModelLayer(SHEEP_LAYER, sheepCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_SHEEP, ZombieSheepRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SILVERFISH_LAYER, silverfishCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_SILVERFISH, SilverfishRenderer::new);

    }
}
