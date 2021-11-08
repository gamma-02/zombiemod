package gamma_02.zombiemobs.entities;

import gamma_02.zombiemobs.ZombieMod;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

import java.util.Collection;
import java.util.Iterator;

public class ZombieCreeper extends CreeperEntity
{
    private int lastFuseTime;
    private int currentFuseTime;
    private int fuseTime;


    public ZombieCreeper(EntityType<? extends ZombieCreeper> entityType, World world)
    {
        super(entityType, world);
    }
    @Override
    public void tick(){
        if (this.isAlive()) {
            this.lastFuseTime = this.currentFuseTime;
            if (this.isIgnited()) {
                this.setFuseSpeed(1);

            }

            int i = this.getFuseSpeed();
            if (i > 0 && this.currentFuseTime == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
                this.emitGameEvent(GameEvent.PRIME_FUSE);
            }

            this.currentFuseTime += i;
            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }

            if (this.currentFuseTime >= this.fuseTime) {
                this.currentFuseTime = this.fuseTime;
                this.explode();
            }
        }
        super.tick();
    }


    private void explode(){
        if (!this.world.isClient) {
            Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            float f = this.shouldRenderOverlay() ? 2.0F : 1.0F;
            this.dead = true;
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), (float)3 * f, destructionType);
            this.discard();
            this.spawnEffectsCloud();
            MobEntity mob1 = new ZombieCreeperLeg(ZombieMod.ZOMBIE_CREEPER_LEG, this.world);
            MobEntity mob2 = new ZombieCreeperLeg(ZombieMod.ZOMBIE_CREEPER_LEG, this.world);
            MobEntity mob3 = new ZombieCreeperLeg(ZombieMod.ZOMBIE_CREEPER_LEG, this.world);
            MobEntity mob4 = new ZombieCreeperLeg(ZombieMod.ZOMBIE_CREEPER_LEG, this.world);
            mob1.setPos(this.getX()+0.1, this.getY()+0.1, this.getZ()+0.1);
            mob2.setPos(this.getX()+0.1, this.getY()+0.1, this.getZ()-0.1);
            mob3.setPos(this.getX()-0.1, this.getY()+0.1, this.getZ()+0.1);
            mob4.setPos(this.getX()-0.1, this.getY()+0.1, this.getZ()-0.1);

        }

    }
    private void spawnEffectsCloud() {
        Collection<StatusEffectInstance> collection = this.getStatusEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
            areaEffectCloudEntity.setRadius(2.5F);
            areaEffectCloudEntity.setRadiusOnUse(-0.5F);
            areaEffectCloudEntity.setWaitTime(10);
            areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
            Iterator var3 = collection.iterator();

            while(var3.hasNext()) {
                StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var3.next();
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
            }

            this.world.spawnEntity(areaEffectCloudEntity);
        }

    }
    public void onDeath(DamageSource source){

        ZombieMod.getTimeout.add(this);

    }


}
