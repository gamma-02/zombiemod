package gamma_02.zombiemobs.mixin;

import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Objects;

import static gamma_02.zombiemobs.ZombieMod.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity
{

    @Shadow public abstract DamageTracker getDamageTracker();

    @Shadow public abstract Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();

    @Shadow public abstract boolean isPartOfGame();

    @Shadow public abstract ItemStack getArrowType(ItemStack stack);

    @Shadow public @Nullable abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Shadow public abstract boolean canMoveVoluntarily();

    @Shadow public abstract void setCurrentHand(Hand hand);

    public int playerNoMovementTicks = 0;

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
            MobEntity mob = new ZombieMagmaCube(ZOMBIE_MAGMA_CUBE, world);
            mob.setPos(this.getX(), this.getY()+0.3, this.getZ());
            System.out.println(mob);
            world.spawnEntity(mob);
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
    }

}
