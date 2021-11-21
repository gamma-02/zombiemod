package gamma_02.zombiemobs.mixin;

import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.dragon.ZombieDragonFight;
import gamma_02.zombiemobs.dragon.ZombiePhaseType;
import gamma_02.zombiemobs.entities.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static gamma_02.zombiemobs.ZombieMod.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity
{

    private float lastHearts;
    @Shadow public abstract DamageTracker getDamageTracker();

    @Shadow public abstract Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();

    @Shadow public abstract boolean isPartOfGame();

    @Shadow public abstract ItemStack getArrowType(ItemStack stack);

    @Shadow public @Nullable abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Shadow public abstract boolean canMoveVoluntarily();

    @Shadow public abstract void setCurrentHand(Hand hand);

    @Shadow protected abstract int computeFallDamage(float fallDistance, float damageMultiplier);

    @Shadow protected abstract void fall(double heightDifference, boolean onGround, BlockState landedState,
            BlockPos landedPosition);

    @Shadow public abstract float getHeadYaw();

    @Shadow public abstract float getHealth();

    @Shadow public @Nullable abstract DamageSource getRecentDamageSource();

    @Shadow protected abstract void drop(DamageSource source);

    @Shadow public abstract void kill();

    public int playerNoMovementTicks = 0;

    public ZombieDragonFight zombieDragonFight = null;

    public LivingEntityMixin(EntityType<?> entityType, World world)
    {
        super(entityType, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeathMixin(DamageSource source, CallbackInfo ci)
    {
        System.out.println(this);
        System.out.println(this.getType());
        if(this.getType() == EntityType.WITCH){
            MobEntity mob = new ZombieWitch(ZOMBIE_WITCH, world);
//            MobEntity mob1 = new ZombieBat(ZOMBIE_BAT, world);
            mob.setPos(this.getX(), this.getY()+1, this.getZ());
//            mob1.setPos(this.getX(), this.getY()+1, this.getZ());
            world.spawnEntity(mob);
//            world.spawnEntity(mob1);
            System.out.println(mob);
//            mob.startRiding(mob1, true);

        }else if(this.getType() == EntityType.BAT){
            MobEntity mob = new ZombieBat(ZOMBIE_BAT, world);
            mob.setPos(this.getX(), this.getY(), this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);
        }else if(this.getType() == EntityType.SKELETON){
            MobEntity mob = new ZombieSkeleton(ZOMBIE_SKELETON, world);
            mob.setPos(this.getX(), this.getY(), this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);
        }else if(this.getType() == EntityType.PIG){
            MobEntity mob = new ZombiePig(ZOMBIE_PIG, world);
            mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);
        }else if(this.getType() == EntityType.SHEEP){
            MobEntity mob = new ZombieSheep(ZOMBIE_SHEEP, world);
            mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);
        }else if(this.getType() == EntityType.SILVERFISH){
            MobEntity mob = new ZombieSilverfish(ZOMBIE_SILVERFISH, world);
            MobEntity mob2 = new ZombieSilverfish(ZOMBIE_SILVERFISH, world);

            mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
            mob2.setPos(this.getX(), this.getY()+0.3, this.getZ());
            System.out.println(mob);
            System.out.println(mob2);
            world.spawnEntity(mob);
            world.spawnEntity(mob2);
        }else if(this.getType() == EntityType.MAGMA_CUBE){
            MagmaCubeEntity entity = (MagmaCubeEntity)world.getEntityById(this.getId());
            if(entity.getSize() == 1)
            {
                ZombieMagmaCube mob = new ZombieMagmaCube(ZOMBIE_MAGMA_CUBE, world);
                mob.setSize(1, true);
                mob.setPos(this.getX(), this.getY() + 0.3, this.getZ());
                System.out.println(mob);
                world.spawnEntity(mob);
            }
        }else if(this.getType() == EntityType.IRON_GOLEM){
            MobEntity mob = new ZombieGolem(ZOMBIE_GOLEM, world);
            mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);
        }else if(this.getType() == EntityType.CREEPER){
            MobEntity mob = new ZombieCreeper(ZOMBIE_CREEPER, world);
            mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);
        }else if(this.getType() == EntityType.SPIDER){
            MobEntity mob = new ZombieSpider(ZOMBIE_SPIDER, world);
            mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);

        }else if(this.getType() == EntityType.BLAZE){
            MobEntity mob = new ZombieBlaze(ZOMBIE_BLAZE, world);
            mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);
        }else if(this.getType() == EntityType.ENDERMAN){
            MobEntity mob = new ZombieEnderman(ZOMBIE_ENDERMAN, world);
            mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);
        }else if(this.getType() == EntityType.ENDER_DRAGON){
            EnderDragonEntity dragon = this.getDragonEntity();
            dragon.deathTime = 0;



            NbtCompound compound = dragon.getFight().toNbt();
            dragon.kill();
            compound.putBoolean("DragonKilled", false);
            compound.putBoolean("PreviouslyKilled", false);
            ZombieEnderDragon zombieDragon = new ZombieEnderDragon(ZOMBIE_ENDER_DRAGON, this.world);
            world.spawnEntity(zombieDragon);
            zombieDragonFight = new ZombieDragonFight(ZombieMod.getServer().getWorld(World.END), 1, compound, zombieDragon);
            ZombieMod.setZombieDragonFight(zombieDragonFight);
            System.out.println(zombieDragonFight.toNbt().getUuid("Dragon"));
            zombieDragon.setPos(this.getX(), this.getY()+0.3, this.getZ());



        }else if(this.getType() == EntityType.WOLF){

            ZombieDog mob = new ZombieDog(ZOMBIE_DOG, world);
            mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);

        }else if(this.getType() == EntityType.VILLAGER){
            ZombieVillagerEntity mob = new ZombieVillagerEntity(EntityType.ZOMBIE_VILLAGER, world);
            mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);
        }else if(this.getType() == EntityType.ZOMBIE_VILLAGER){
            getTimeout.add(world.getEntityById(this.getId()));
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci){
        if(this.getType() == ZombieMod.ZOMBIE_ENDERMAN){

            ZombieEnderman current = (ZombieEnderman)this.world.getEntityById(this.getId());
            if( current != null && !current.isPlayerNull())
            {
                if(current.playerIsStaring){
                    playerNoMovementTicks = 20;
                }
                if ( playerNoMovementTicks > 0)
                {
                    current.playerStaring.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
                    System.out.println(playerNoMovementTicks);
                    if(!current.playerIsStaring)
                    {
                        playerNoMovementTicks--;
                    }
                } else
                {
                    current.playerStaring.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);
                }

            }

        }
        if(lastHearts>this.getHealth()){
            if(this.getRecentDamageSource().getAttacker() instanceof ZombieEnderDragon ){
                ZombieEnderDragon dragon = (ZombieEnderDragon)this.getRecentDamageSource().getAttacker();
                if(dragon.getPhaseManager().getCurrent() == ZombiePhaseType.SITTING_ATTACKING || dragon.getPhaseManager().getCurrent() == ZombiePhaseType.SITTING_FLAMING){
                    if(this.getType() == EntityType.WITCH){
                        MobEntity mob = new ZombieWitch(ZOMBIE_WITCH, world);
                        //            MobEntity mob1 = new ZombieBat(ZOMBIE_BAT, world);
                        mob.setPos(this.getX(), this.getY()+1, this.getZ());
                        //            mob1.setPos(this.getX(), this.getY()+1, this.getZ());
                        world.spawnEntity(mob);
                        //            world.spawnEntity(mob1);
                        System.out.println(mob);
                        //            mob.startRiding(mob1, true);

                    }else if(this.getType() == EntityType.BAT){
                        MobEntity mob = new ZombieBat(ZOMBIE_BAT, world);
                        mob.setPos(this.getX(), this.getY(), this.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(this.getType() == EntityType.SKELETON){
                        MobEntity mob = new ZombieSkeleton(ZOMBIE_SKELETON, world);
                        mob.setPos(this.getX(), this.getY(), this.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(this.getType() == EntityType.PIG){
                        MobEntity mob = new ZombiePig(ZOMBIE_PIG, world);
                        mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(this.getType() == EntityType.SHEEP){
                        MobEntity mob = new ZombieSheep(ZOMBIE_SHEEP, world);
                        mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(this.getType() == EntityType.SILVERFISH){
                        MobEntity mob = new ZombieSilverfish(ZOMBIE_SILVERFISH, world);
                        MobEntity mob2 = new ZombieSilverfish(ZOMBIE_SILVERFISH, world);

                        mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
                        mob2.setPos(this.getX(), this.getY()+0.3, this.getZ());
                        System.out.println(mob);
                        System.out.println(mob2);
                        world.spawnEntity(mob);
                        world.spawnEntity(mob2);
                    }else if(this.getType() == EntityType.MAGMA_CUBE){
                        MagmaCubeEntity entity = (MagmaCubeEntity)world.getEntityById(this.getId());
                        if(entity.getSize() == 1)
                        {
                            ZombieMagmaCube mob = new ZombieMagmaCube(ZOMBIE_MAGMA_CUBE, world);
                            mob.setSize(1, true);
                            mob.setPos(this.getX(), this.getY() + 0.3, this.getZ());
                            System.out.println(mob);
                            world.spawnEntity(mob);
                        }
                    }else if(this.getType() == EntityType.IRON_GOLEM){
                        MobEntity mob = new ZombieGolem(ZOMBIE_GOLEM, world);
                        mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(this.getType() == EntityType.CREEPER){
                        MobEntity mob = new ZombieCreeper(ZOMBIE_CREEPER, world);
                        mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(this.getType() == EntityType.SPIDER){
                        MobEntity mob = new ZombieSpider(ZOMBIE_SPIDER, world);
                        mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);

                    }else if(this.getType() == EntityType.BLAZE){
                        MobEntity mob = new ZombieBlaze(ZOMBIE_BLAZE, world);
                        mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(this.getType() == EntityType.ENDERMAN){
                        MobEntity mob = new ZombieEnderman(ZOMBIE_ENDERMAN, world);
                        mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }else if(this.getType() == EntityType.WOLF){

                        ZombieDog mob = new ZombieDog(ZOMBIE_DOG, world);
                        mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);

                    }else if(this.getType() == EntityType.VILLAGER){
                        ZombieVillagerEntity mob = new ZombieVillagerEntity(EntityType.ZOMBIE_VILLAGER, world);
                        mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
                        System.out.println(mob);
                        world.spawnEntity(mob);
                    }
                    this.kill();
                }
            }
        }


    }

    public EnderDragonEntity getDragonEntity(){
        UUID id = ZombieMod.getServer().getWorld(World.END).getEnderDragonFight().toNbt().getUuid("Dragon");
        return (EnderDragonEntity)ZombieMod.getServer().getWorld(World.END).getEntity(id);
    }

    public ZombieDragonFight getZombieDragonFight(){
        return this.zombieDragonFight;
    }

}
