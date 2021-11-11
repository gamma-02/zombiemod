package gamma_02.zombiemobs.entities;

import gamma_02.zombiemobs.ZombieMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class ZombiePig extends PathAwareEntity
{
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false) {
        public void stop() {
            super.stop();
            ZombiePig.this.setAttacking(false);
        }

        public void start() {
            super.start();
            ZombiePig.this.setAttacking(true);
        }
    };
    public ZombiePig(EntityType<? extends ZombiePig> entityType, World world)
    {
        super(entityType, world);
    }
    @Override
    protected void initGoals(){
        super.initGoals();

        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(3, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, false));

    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }


    @Override
    protected float getSoundVolume() {
        return 0.125f;
    }


    public static DefaultAttributeContainer.Builder createPigAttributes(){
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D).add(EntityAttributes.GENERIC_ARMOR, 2.0D);
    }

    public void tickMovement(){

        if(this.isAffectedByDaylight()){
            this.setOnFireFor(8);
        }
        if(this.world != null && !this.world.isClient){
            this.goalSelector.add(5, meleeAttackGoal);
        }

        super.tickMovement();

    }
    public void initDataTracker(){
        super.initDataTracker();
    }
    public int getXpToDrop(PlayerEntity player){
        this.experiencePoints = 0;
        return super.getXpToDrop(player);
    }
    public void tick(){
        super.tick();
    }
    protected void damage(LivingEntity target) {
        if (this.isAlive()) {

            if (this.squaredDistanceTo(target) < 0.6D * (double)1 * 0.6D * (double)1 && this.canSee(target) && target.damage(
                    DamageSource.mob(this), 3)) {
                this.playSound(SoundEvents.ENTITY_PIG_DEATH, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.applyDamageEffects(this, target);
            }
        }

    }
    public void onPlayerCollision(PlayerEntity player) {
        this.damage(player);


    }
    public boolean tryAttack(Entity target) {
        boolean bl = super.tryAttack(target);
        if (bl) {
            float f = this.world.getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
            if (this.getMainHandStack().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
                target.setOnFireFor(2 * (int)f);
            }
        }

        return bl;
    }
    public void mobTick(){
        super.mobTick();
    }
    public void onDeath(DamageSource source){

        ZombieMod.getTimeout.add(this);

    }




}
