package gamma_02.zombiemobs;

import gamma_02.zombiemobs.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TimeOut {

    public ConcurrentHashMap<Long, Vec3d> pos = new ConcurrentHashMap<>();
    public ConcurrentHashMap<Long, EntityType> entityType = new ConcurrentHashMap<>();
    public long ticks;
    private World world;

    public TimeOut(World world){
        this.world = world;
    }

    public void add(Entity entity){
        pos.put(this.ticks+300, entity.getPos());
        entityType.put(this.ticks+300, entity.getType());
    }

    public void tick(){

        this.revive();
        this.ticks++;
        
    }


    public void revive(){


            if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_WITCH){
                ZombieWitch d = new ZombieWitch(ZombieMod.ZOMBIE_WITCH, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);

            }else if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_BLAZE){
                ZombieBlaze d = new ZombieBlaze(ZombieMod.ZOMBIE_BLAZE, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);
            }else if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_BAT){
                ZombieBat d = new ZombieBat(ZombieMod.ZOMBIE_BAT, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);

            }else if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_CREEPER){
                ZombieCreeper d = new ZombieCreeper(ZombieMod.ZOMBIE_CREEPER, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);

            }else if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_PIG){
                ZombiePig d = new ZombiePig(ZombieMod.ZOMBIE_PIG, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);

            }else if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_ENDERMAN){
                ZombieEnderman d = new ZombieEnderman(ZombieMod.ZOMBIE_ENDERMAN, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);

            }else if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_GOLEM){
                ZombieGolem d = new ZombieGolem(ZombieMod.ZOMBIE_GOLEM, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);

            }else if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_MAGMA_CUBE){
                ZombieMagmaCube d = new ZombieMagmaCube(ZombieMod.ZOMBIE_MAGMA_CUBE, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);

            }else if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_SHEEP){
                ZombieSheep d = new ZombieSheep(ZombieMod.ZOMBIE_SHEEP, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);

            }else if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_SILVERFISH){
                ZombieSilverfish d = new ZombieSilverfish(ZombieMod.ZOMBIE_SILVERFISH, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);

            }else if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_SPIDER){
                ZombieSpider d = new ZombieSpider(ZombieMod.ZOMBIE_SPIDER, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);

            }else if(entityType.get(this.ticks) == ZombieMod.ZOMBIE_SKELETON){
                ZombieSkeleton d = new ZombieSkeleton(ZombieMod.ZOMBIE_SKELETON, this.world);
                d.setPosition(pos.get(this.ticks));
                ZombieMod.server.getWorld(this.world.getRegistryKey()).spawnEntity(d);

            }




    }
    public void setWorld(World world){
        this.world = world;
    }
}
