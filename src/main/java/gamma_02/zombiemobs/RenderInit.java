package gamma_02.zombiemobs;

import com.sun.jna.platform.win32.WinBase;
import gamma_02.zombiemobs.entities.ZombieCreeperLeg;
import gamma_02.zombiemobs.entities.ZombieDog;
import gamma_02.zombiemobs.entities.ZombieMagmaCube;
import gamma_02.zombiemobs.entities.ZombieSkeleton;
import gamma_02.zombiemobs.models.*;
import gamma_02.zombiemobs.renderers.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.PufferfishEntityRenderer;
import net.minecraft.client.render.entity.model.BlazeEntityModel;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.entity.ai.brain.task.CelebrateRaidWinTask;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

import javax.swing.text.html.parser.Entity;

public class RenderInit implements ClientModInitializer
{
    public static final EntityModelLayer WITCH_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "witch_layer"), "main");
    public static final EntityModelLayer BAT_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "bat_layer"), "main");
    public static final EntityModelLayer PIG_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "pig_layer"), "main");
    public static final EntityModelLayer SHEEP_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "sheep_layer"), "main");
    public static final EntityModelLayer SILVERFISH_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "silverfish_layer"), "main");
    public static final EntityModelLayer MAGMACUBE_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "magma_layer"), "main");
    public static final EntityModelLayer GOLEM_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "golem_layer"), "main");
    public static final EntityModelLayer CREEPER_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "zombie_creeper_layer"), "main");
    public static final EntityModelLayer CREEPER_LEG_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "zombie_creeper_leg_layer"), "main");
    public static final EntityModelLayer BLAZE_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "zombie_blaze_layer"), "main");
    public static final EntityModelLayer SPIDER_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "zombie_spider_layer"), "main");
    public static final EntityModelLayer ENDERMAN_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "zombie_enderman_layer"),"main");
    public static final EntityModelLayer DRAGON_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "zombie_dragon_layer"), "main");
    public static final EntityModelLayer DOG_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "zombie_dog"), "main");
    @Override public void onInitializeClient()
    {
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_WITCH, ZombieWitchRendere::new);
        EntityModelLayerRegistry.registerModelLayer(WITCH_LAYER, witchCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_BAT, ZombieBatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BAT_LAYER, batCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_SKELETON, SkeletonZombieRenderer::new);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_PIG, ZombiePigRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(PIG_LAYER, pigCovertedModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SHEEP_LAYER, sheepCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_SHEEP, ZombieSheepRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SILVERFISH_LAYER, silverfishCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_SILVERFISH, SilverfishRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MAGMACUBE_LAYER, ZombieMagmacubeModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_MAGMA_CUBE, ZombieMagmaCubeRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(GOLEM_LAYER, irongolemCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_GOLEM, ZombieGolemRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(CREEPER_LAYER, creeperCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_CREEPER, ZombieCreeperRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(CREEPER_LEG_LAYER, creeperLegCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_CREEPER_LEG, ZombieCreeperLegRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BLAZE_LAYER, BlazeEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_BLAZE, ZombieBlazeEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SPIDER_LAYER, SpiderEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_SPIDER, ZombieSpiderRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ENDERMAN_LAYER, EndermanEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_ENDERMAN, ZombieEndermanRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(DRAGON_LAYER, ZombieEnderdragonRenderer::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_ENDER_DRAGON, ZombieEnderdragonRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(DOG_LAYER, ZombieDogModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_DOG, ZombieDogRenderer::new);



    }
}
