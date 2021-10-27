package gamma_02.zombiemobs;

import gamma_02.zombiemobs.entities.ZombieWitchRendere;
import net.fabricmc.api.ClientModInitializer;
import gamma_02.zombiemobs.entities.witchCovertedModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class RenderInit implements ClientModInitializer
{
    public static final EntityModelLayer WITCH_LAYER = new EntityModelLayer(new Identifier(ZombieMod.ModID, "witch_layer"), "main");
    @Override public void onInitializeClient()
    {
        EntityRendererRegistry.register(ZombieMod.ZOMBIE_WITCH, context -> new ZombieWitchRendere(context));
        EntityModelLayerRegistry.registerModelLayer(WITCH_LAYER, witchCovertedModel::getTexturedModelData);
    }
}
