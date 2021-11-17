package gamma_02.zombiemobs.mixin;

import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieDragonPart;
import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    @Shadow public abstract void increaseStat(Stat<?> stat, int amount);

    @Shadow public abstract void resetLastAttackedTicks();

    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    @Shadow public abstract void addExhaustion(float exhaustion);

    @Shadow public abstract void increaseStat(Identifier stat, int amount);

    @Shadow public abstract void addEnchantedHitParticles(Entity target);

    @Shadow public abstract void addCritParticles(Entity target);

    @Shadow public abstract void spawnSweepAttackParticles();

    @Shadow protected abstract void takeShieldHit(LivingEntity attacker);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world)
    {
        super(entityType, world);
    }
    /**
     * @author gamma_02
     * @reason lul
     */
    @Overwrite
    public void attack(Entity target) {

        System.out.println("why the fuck is this not working");
        if(target instanceof ZombieDragonPart || target instanceof ZombieEnderDragon){
            System.out.println("why the fuck is this not working");
        }
        if (target.isAttackable()) {
            if (!target.handleAttack(this)) {
                float f = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                float g;
                if (target instanceof LivingEntity) {
                    g = EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity)target).getGroup());
                } else {
                    g = EnchantmentHelper.getAttackDamage(this.getMainHandStack(), EntityGroup.DEFAULT);
                }

                float h = this.getAttackCooldownProgress(0.5F);
                f *= 0.2F + h * h * 0.8F;
                g *= h;
                this.resetLastAttackedTicks();
                if (f > 0.0F || g > 0.0F) {
                    boolean bl = h > 0.9F;
                    boolean bl2 = false;
                    int i = 0;
                    i = i + EnchantmentHelper.getKnockback(this);
                    if (this.isSprinting() && bl) {
                        this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, this.getSoundCategory(), 1.0F, 1.0F);
                        ++i;
                        bl2 = true;
                    }

                    boolean bl3 = bl && this.fallDistance > 0.0F && !this.onGround && !this.isClimbing() && !this.isTouchingWater() && !this.hasStatusEffect(
                            StatusEffects.BLINDNESS) && !this.hasVehicle() && target instanceof LivingEntity;
                    bl3 = bl3 && !this.isSprinting();
                    if (bl3) {
                        f *= 1.5F;
                    }

                    f += g;
                    boolean bl4 = false;
                    double d = (double)(this.horizontalSpeed - this.prevHorizontalSpeed);
                    if (bl && !bl3 && !bl2 && this.onGround && d < (double)this.getMovementSpeed()) {
                        ItemStack itemStack = this.getStackInHand(Hand.MAIN_HAND);
                        if (itemStack.getItem() instanceof SwordItem) {
                            bl4 = true;
                        }
                    }

                    float itemStack = 0.0F;
                    boolean bl5 = false;
                    int j = EnchantmentHelper.getFireAspect(this);
                    if (target instanceof LivingEntity) {
                        itemStack = ((LivingEntity)target).getHealth();
                        if (j > 0 && !target.isOnFire()) {
                            bl5 = true;
                            target.setOnFireFor(1);
                        }
                    }

                    Vec3d vec3d = target.getVelocity();
                    boolean bl6 = target.damage(DamageSource.player(ZombieMod.getServer().getPlayerManager().getPlayer(this.getUuid())), f);
                    if (bl6) {
                        if (i > 0) {
                            if (target instanceof LivingEntity) {
                                ((LivingEntity)target).takeKnockback((double)((float)i * 0.5F), (double) MathHelper.sin(this.getYaw() * 0.017453292F), (double)(-MathHelper.cos(this.getYaw() * 0.017453292F)));
                            } else {
                                target.addVelocity((double)(-MathHelper.sin(this.getYaw() * 0.017453292F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.getYaw() * 0.017453292F) * (float)i * 0.5F));
                            }

                            this.setVelocity(this.getVelocity().multiply(0.6D, 1.0D, 0.6D));
                            this.setSprinting(false);
                        }

                        if (bl4) {
                            float k = 1.0F + EnchantmentHelper.getSweepingMultiplier(this) * f;
                            List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0D, 0.25D, 1.0D));
                            Iterator var19 = list.iterator();

                            label166:
                            while(true) {
                                LivingEntity livingEntity;
                                do {
                                    do {
                                        do {
                                            do {
                                                if (!var19.hasNext()) {
                                                    this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
                                                    this.spawnSweepAttackParticles();
                                                    break label166;
                                                }

                                                livingEntity = (LivingEntity)var19.next();
                                            } while(livingEntity == this);
                                        } while(livingEntity == target);
                                    } while(this.isTeammate(livingEntity));
                                } while(livingEntity instanceof ArmorStandEntity && ((ArmorStandEntity)livingEntity).isMarker());

                                if (this.squaredDistanceTo(livingEntity) < 9.0D) {
                                    livingEntity.takeKnockback(0.4000000059604645D, (double)MathHelper.sin(this.getYaw() * 0.017453292F), (double)(-MathHelper.cos(this.getYaw() * 0.017453292F)));
                                    livingEntity.damage(DamageSource.player(ZombieMod.getServer().getPlayerManager()
                                            .getPlayer(this.getUuid())), k);
                                }
                            }
                        }

                        if (target instanceof ServerPlayerEntity && target.velocityModified) {
                            ((ServerPlayerEntity)target).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(target));
                            target.velocityModified = false;
                            target.setVelocity(vec3d);
                        }

                        if (bl3) {
                            this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, this.getSoundCategory(), 1.0F, 1.0F);
                            this.addCritParticles(target);
                        }

                        if (!bl3 && !bl4) {
                            if (bl) {
                                this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, this.getSoundCategory(), 1.0F, 1.0F);
                            } else {
                                this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, this.getSoundCategory(), 1.0F, 1.0F);
                            }
                        }

                        if (g > 0.0F) {
                            this.addEnchantedHitParticles(target);
                        }

                        this.onAttacking(target);
                        if (target instanceof LivingEntity) {
                            EnchantmentHelper.onUserDamaged((LivingEntity)target, this);
                        }

                        EnchantmentHelper.onTargetDamaged(this, target);
                        ItemStack k = this.getMainHandStack();
                        Entity list = target;
                        if (target instanceof EnderDragonPart) {
                            list = ((EnderDragonPart)target).owner;
                        }else if(target instanceof ZombieDragonPart){
                            list = ((ZombieDragonPart)target).owner;
                        }

                        if (!this.world.isClient && !k.isEmpty() && list instanceof LivingEntity) {
                            k.postHit((LivingEntity)list, ZombieMod.getServer().getPlayerManager().getPlayer(this.getUuid()));
                            if (k.isEmpty()) {
                                this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                            }
                        }

                        if (target instanceof LivingEntity) {
                            float l = itemStack - ((LivingEntity)target).getHealth();
                            this.increaseStat(Stats.DAMAGE_DEALT, Math.round(l * 10.0F));
                            if (j > 0) {
                                target.setOnFireFor(j * 4);
                            }

                            if (this.world instanceof ServerWorld && l > 2.0F) {
                                int livingEntity = (int)((double)l * 0.5D);
                                ((ServerWorld)this.world).spawnParticles(ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getBodyY(0.5D), target.getZ(), livingEntity, 0.1D, 0.0D, 0.1D, 0.2D);
                            }
                        }

                        this.addExhaustion(0.1F);
                    } else {
                        this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, this.getSoundCategory(), 1.0F, 1.0F);
                        if (bl5) {
                            target.extinguish();
                        }
                    }
                }

            }
        }
    }
}
