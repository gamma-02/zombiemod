package gamma_02.zombiemobs;

import gamma_02.zombiemobs.entities.ZombieBat;
import gamma_02.zombiemobs.entities.ZombiePig;
import gamma_02.zombiemobs.entities.ZombieSkeleton;
import gamma_02.zombiemobs.entities.ZombieWitch;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

public class ZombieMod implements ModInitializer
{
    public static String ModID = "zombiemod";
    public static MinecraftServer server;
    public static final EntityType<ZombieWitch> ZOMBIE_WITCH = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_witch"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieWitch::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build());
    public static final EntityType<ZombieBat> ZOMBIE_BAT = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_bat"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieBat::new).dimensions(EntityDimensions.fixed(0.5f, 0.9f)).build());
    public static final EntityType<ZombieSkeleton> ZOMBIE_SKELETON = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_skeleton"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieSkeleton::new).dimensions(EntityDimensions.fixed(1.5f, 1f)).build());
    public static final EntityType<ZombiePig> ZOMBIE_PIG = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_pig"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombiePig::new).dimensions(EntityDimensions.fixed(0.9f, 0.9f)).build());
    public static void setServer(MinecraftServer server){
        ZombieMod.server = server;
    }
    @NotNull
    public static MinecraftServer getServer(){
        return ZombieMod.server;
    }
    @Override public void onInitialize()
    {
        FabricDefaultAttributeRegistry.register(ZOMBIE_WITCH, ZombieWitch.createWitchAttributes());
        ServerLifecycleEvents.SERVER_STARTED.register(ZombieMod::setServer);
        FabricDefaultAttributeRegistry.register(ZOMBIE_BAT, ZombieBat.createBatAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_SKELETON, ZombieSkeleton.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_PIG, ZombiePig.createPigAttributes());


    }
}
