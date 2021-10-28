package gamma_02.zombiemobs;

import gamma_02.zombiemobs.models.batCovertedModel;
import gamma_02.zombiemobs.renderers.ZombieBatRenderer;
import gamma_02.zombiemobs.renderers.ZombieWitchRendere;
import net.fabricmc.api.ClientModInitializer;
import gamma_02.zombiemobs.models.witchCovertedModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class RenderInit implements ClientModInitializer
{
    public static final EntityModelLayer WITCH_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "witch_layer"), "main");
    public static final EntityModelLayer BAT_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "bat_layer"), "main");
    @Override public void onInitializeClient()
    {
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_WITCH, ZombieWitchRendere::new);
        EntityModelLayerRegistry.registerModelLayer(WITCH_LAYER, witchCovertedModel::getTexturedModelData);
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_BAT, ZombieBatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BAT_LAYER, batCovertedModel::getTexturedModelData);

    }
}
