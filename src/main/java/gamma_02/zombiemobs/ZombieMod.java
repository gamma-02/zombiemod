package gamma_02.zombiemobs;

import com.mojang.serialization.Decoder;
import gamma_02.zombiemobs.dragon.ZombieDragonFight;
import gamma_02.zombiemobs.entities.*;
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
    public static ZombieDragonFight zombieDragonFight;
    public static void setZombieDragonFight(ZombieDragonFight fight){
        zombieDragonFight = fight;
    }
    public static ZombieDragonFight getZombieDragonFight(){
        return zombieDragonFight;
    }
    public static final EntityType<ZombieWitch> ZOMBIE_WITCH = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_witch"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieWitch::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build());
    public static final EntityType<ZombieBat> ZOMBIE_BAT = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_bat"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieBat::new).dimensions(EntityDimensions.fixed(0.5f, 0.9f)).build());
    public static final EntityType<ZombieSkeleton> ZOMBIE_SKELETON = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_skeleton"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieSkeleton::new).dimensions(EntityDimensions.fixed(1.5f, 1f)).build());
    public static final EntityType<ZombiePig> ZOMBIE_PIG = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_pig"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombiePig::new).dimensions(EntityDimensions.fixed(0.9f, 0.9f)).build());
    public static final EntityType<ZombieSheep> ZOMBIE_SHEEP = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_sheep"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieSheep::new).dimensions(EntityDimensions.fixed(EntityType.SHEEP.getWidth(), EntityType.SHEEP.getHeight())).build());
    public static final EntityType<ZombieSilverfish> ZOMBIE_SILVERFISH = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_silverfish"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieSilverfish::new).dimensions(
                    EntityDimensions.fixed(EntityType.SILVERFISH.getWidth(), EntityType.SILVERFISH.getHeight())).build());
    public static final EntityType<ZombieMagmaCube> ZOMBIE_MAGMA_CUBE = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_magmacube"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieMagmaCube::new).dimensions(EntityDimensions.changing(EntityType.MAGMA_CUBE.getWidth(), EntityType.MAGMA_CUBE.getHeight())).build());
    public static final EntityType<ZombieGolem> ZOMBIE_GOLEM = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_golem"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieGolem::new).dimensions(EntityType.IRON_GOLEM.getDimensions()).build());
    public static final EntityType<ZombieCreeper> ZOMBIE_CREEPER = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_creeper"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieCreeper::new).dimensions(EntityType.CREEPER.getDimensions()).build());
    public static final EntityType<ZombieCreeperLeg> ZOMBIE_CREEPER_LEG = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "creeper_leg"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieCreeperLeg::new).dimensions(EntityDimensions.fixed(0.35f, 0.35f)).build());
    public static final EntityType<ZombieSpider> ZOMBIE_SPIDER = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_spider"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieSpider::new).dimensions(EntityType.SPIDER.getDimensions()).build());
    public static final EntityType<ZombieBlaze> ZOMBIE_BLAZE = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_blaze"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieBlaze::new).dimensions(EntityType.BLAZE.getDimensions()).build());
    public static final EntityType<ZombieEnderman> ZOMBIE_ENDERMAN = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_enderman"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieEnderman::new).dimensions(EntityType.ENDERMAN.getDimensions()).build());
    public static final EntityType<ZombieEnderDragon> ZOMBIE_ENDER_DRAGON = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "zombie_dragon"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ZombieEnderDragon::new).dimensions(EntityType.ENDER_DRAGON.getDimensions()).build());

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
        FabricDefaultAttributeRegistry.register(ZOMBIE_SHEEP, ZombieSheep.createPigAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_SILVERFISH, ZombieSilverfish.createSilverfishAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_MAGMA_CUBE, ZombieMagmaCube.createMagmaCubeAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_GOLEM, ZombieGolem.createZombieGolemAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_CREEPER, ZombieCreeper.createCreeperAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_CREEPER_LEG, ZombieCreeperLeg.createCreeperLegAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_SPIDER, ZombieSpider.createSpiderAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_BLAZE, ZombieBlaze.createZombieBlazeAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_ENDERMAN, ZombieEnderman.createEndermanAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_ENDER_DRAGON, ZombieEnderDragon.createEnderDragonAttributes());
    }
}
