package gamma_02.zombiemobs.mixin;

import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.*;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

import static gamma_02.zombiemobs.ZombieMod.*;
import static gamma_02.zombiemobs.ZombieMod.ZOMBIE_ENDERMAN;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Shadow public abstract EntityType<?> getType();

    @Shadow public abstract UUID getUuid();

    @Shadow public abstract World getEntityWorld();

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci)
    {
        if (this.getType() == EntityType.AREA_EFFECT_CLOUD)
        {
            ServerWorld world = ZombieMod.getServer().getWorld(World.END);
            AreaEffectCloudEntity areaEffectCloud = (AreaEffectCloudEntity)ZombieMod.getServer().getWorld(World.END).getEntity(this.getUuid());
            if(areaEffectCloud.getOwner() != null ? areaEffectCloud.getOwner() instanceof ZombieEnderDragon : this.getEntityWorld() != null){
                float radius = areaEffectCloud.getRadius();

                Box box = new Box(areaEffectCloud.getBlockPos().add((double)radius, 1, (double)radius), areaEffectCloud.getBlockPos().add((double)(radius*-1), -1, (double)(radius*-1)));

                for(double boxx = 0; boxx<box.maxX;boxx++){
                    for(double boxy = 0; boxy<box.maxY;boxy++){
                        for(double boxz = 0; boxz<box.maxZ;boxz++){
                            BlockPos fillPos = new BlockPos(boxx, boxy, boxz);
                            world.breakBlock(fillPos, true);
                        }
                    }
                }
                Entity entity = null;
                List<Entity> entities = this.getEntityWorld().getOtherEntities(null, box);
                for (int i = 0;i<entities.size();i++){
                    entity = entities.get(i);
                    if(entity.getType() == EntityType.WITCH){
                        MobEntity mob = new ZombieWitch(ZOMBIE_WITCH, world);
                        //            MobEntity mob1 = new ZombieBat(ZOMBIE_BAT, world);
                        mob.setPos(entity.getX(), entity.getY()+1, entity.getZ());
                        //            mob1.setPos(this.getX(), this.getY()+1, this.getZ());
                        world.spawnEntity(mob);
                        //            world.spawnEntity(mob1);
                        System.out.println(mob);
                        //            mob.startRiding(mob1, true);

                    }else if(entity.getType() == EntityType.BAT){
                        MobEntity mob = new ZombieBat(ZOMBIE_BAT, world);
                        mob.setPos(entity.getX(), entity.getY(), entity.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(entity.getType() == EntityType.SKELETON){
                        MobEntity mob = new ZombieSkeleton(ZOMBIE_SKELETON, world);
                        mob.setPos(entity.getX(), entity.getY(), entity.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(entity.getType() == EntityType.PIG){
                        MobEntity mob = new ZombiePig(ZOMBIE_PIG, world);
                        mob.setPos(entity.getX(), entity.getY()+0.3, entity.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(entity.getType() == EntityType.SHEEP){
                        MobEntity mob = new ZombieSheep(ZOMBIE_SHEEP, world);
                        mob.setPos(entity.getX(), entity.getY()+0.3, entity.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(entity.getType() == EntityType.SILVERFISH){
                        MobEntity mob = new ZombieSilverfish(ZOMBIE_SILVERFISH, world);
                        MobEntity mob2 = new ZombieSilverfish(ZOMBIE_SILVERFISH, world);

                        mob.setPos(entity.getX(), entity.getY()+0.3, entity.getZ());
                        mob2.setPos(entity.getX(), entity.getY()+0.3, entity.getZ());
                        System.out.println(mob);
                        System.out.println(mob2);
                        world.spawnEntity(mob);
                        world.spawnEntity(mob2);
                    }else if(entity.getType() == EntityType.MAGMA_CUBE){
                        MobEntity mob = new ZombieMagmaCube(ZOMBIE_MAGMA_CUBE, world);
                        mob.setPos(entity.getX(), entity.getY()+0.3, entity.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(entity.getType() == EntityType.IRON_GOLEM){
                        MobEntity mob = new ZombieGolem(ZOMBIE_GOLEM, world);
                        mob.setPos(entity.getX(), entity.getY()+0.3, entity.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(entity.getType() == EntityType.CREEPER){
                        MobEntity mob = new ZombieCreeper(ZOMBIE_CREEPER, world);
                        mob.setPos(entity.getX(), entity.getY()+0.3, entity.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(entity.getType() == EntityType.SPIDER){
                        MobEntity mob = new ZombieSpider(ZOMBIE_SPIDER, world);
                        mob.setPos(entity.getX(), entity.getY()+0.3, entity.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);

                    }else if(entity.getType() == EntityType.BLAZE){
                        MobEntity mob = new ZombieBlaze(ZOMBIE_BLAZE, world);
                        mob.setPos(entity.getX(), entity.getY()+0.3, entity.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(entity.getType() == EntityType.ENDERMAN){
                        MobEntity mob = new ZombieEnderman(ZOMBIE_ENDERMAN, world);
                        mob.setPos(entity.getX(), entity.getY()+0.3, entity.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }
                }

            }
        }

    }

}

