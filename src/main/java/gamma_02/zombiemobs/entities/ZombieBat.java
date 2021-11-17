package gamma_02.zombiemobs.entities;

import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.mixin.EntityMixin;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;
import java.util.function.Predicate;

public class ZombieBat extends HostileEntity
{
    private static final TrackedData<Byte> BAT_FLAGS;
    private static final int ROOSTING_FLAG = 1;
    private static final TargetPredicate CLOSE_PLAYER_PREDICATE;
    private static final TargetPredicate CLOSE_PLAYER_PREDICATE2;
    private int attackTicks;
    private BlockPos hangingPosition;
    public static final float field_30268 = 74.48451F;
    public static final int field_28637 = MathHelper.ceil(2.4166098F);


    private ActiveTargetGoal<PlayerEntity> attackPlayerGoal;

    public ZombieBat(EntityType<? extends ZombieBat> entityType, World world)
    {
        super(entityType, world);

    }

    protected void initGoals(){
        super.initGoals();
        this.attackPlayerGoal = new ActiveTargetGoal(this, PlayerEntity.class, true, false);
        this.targetSelector.add(3, this.attackPlayerGoal);
    }
    public boolean hasWings() {
        return !this.isRoosting() && this.age % field_28637 == 0;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BAT_FLAGS, (byte)0);
    }

    protected float getSoundVolume() {
        return 0.1F;
    }

    public float getSoundPitch() {
        return super.getSoundPitch() * 0.95F;
    }

    @Nullable
    public SoundEvent getAmbientSound() {
        return this.isRoosting() && this.random.nextInt(4) != 0 ? null : SoundEvents.ENTITY_BAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    public boolean isPushable() {
        return false;
    }

    protected void pushAway(Entity entity) {
    }

    protected void tickCramming() {
    }

    public static DefaultAttributeContainer.Builder createBatAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D);
    }

    public boolean isRoosting() {
        return (this.dataTracker.get(BAT_FLAGS) & 1) != 0;
    }

    public void setRoosting(boolean roosting) {
        byte b = this.dataTracker.get(BAT_FLAGS);
        if (roosting) {
            this.dataTracker.set(BAT_FLAGS, (byte)(b | 1));
        } else {
            this.dataTracker.set(BAT_FLAGS, (byte)(b & -2));
        }

    }

    public void tick() {
        super.tick();
        if(this.attackTicks > 0){
            this.attackTicks--;
        }
        if (this.isRoosting()) {
            this.setVelocity(Vec3d.ZERO);
            this.setPos(this.getX(), (double)MathHelper.floor(this.getY()) + 1.0D - (double)this.getHeight(), this.getZ());
        } else {
            this.setVelocity(this.getVelocity().multiply(1.0D, 0.6D, 1.0D));
        }

    }

    protected void mobTick() {
        super.mobTick();
        BlockPos blockPos = this.getBlockPos();
        BlockPos blockPos2 = blockPos.up();
        if (this.isRoosting()) {
            boolean bl = this.isSilent();
            if (this.world.getBlockState(blockPos2).isSolidBlock(this.world, blockPos)) {
                if (this.random.nextInt(200) == 0) {
                    this.headYaw = (float)this.random.nextInt(360);
                }

                if (this.world.getClosestPlayer(CLOSE_PLAYER_PREDICATE, this) != null) {
                    this.setRoosting(false);
                    if (!bl) {
                        this.world.syncWorldEvent((PlayerEntity)null, WorldEvents.BAT_TAKES_OFF, blockPos, 0);
                        this.setTarget( this.world.getClosestPlayer(CLOSE_PLAYER_PREDICATE, this));
                    }
                }
            } else {
                this.setRoosting(false);
                if (!bl) {
                    this.world.syncWorldEvent((PlayerEntity)null, WorldEvents.BAT_TAKES_OFF, blockPos, 0);
                }
            }
        } else {
            if (this.hangingPosition != null && (!this.world.isAir(this.hangingPosition) || this.hangingPosition.getY() <= this.world.getBottomY())) {
                this.hangingPosition = null;
            }

            if (this.hangingPosition == null || this.random.nextInt(30) == 0 || this.hangingPosition.isWithinDistance(this.getPos(), 2.0D)) {
                this.hangingPosition = new BlockPos(this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7), this.getY() + (double)this.random.nextInt(6) - 2.0D, this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7));
            }

            double bl = (double)this.hangingPosition.getX() + 0.5D - this.getX();
            double d = (double)this.hangingPosition.getY() + 0.1D - this.getY();
            double e = (double)this.hangingPosition.getZ() + 0.5D - this.getZ();


            Vec3d vec3d = this.getVelocity();
            Vec3d vec3d2 = getRotationVec3d(this.getPitch(), this.getYaw()).multiply(0.4,0.4,0.4);;
            if(this.world.getClosestPlayer(CLOSE_PLAYER_PREDICATE, this) != null && this.attackTicks <= 0){
                this.lookAtEntity(this.world.getClosestPlayer(CLOSE_PLAYER_PREDICATE, this), 360f, 360f);
                vec3d2 = getRotationVec3d(this.getPitch(), this.getYaw()).multiply(0.4,0.4,0.4);
            }


            float f = (float)(MathHelper.atan2(vec3d2.z, vec3d2.x) * 57.2957763671875D) - 90.0F;
            float g = MathHelper.wrapDegrees(f - this.getYaw());
            if(this.world.getClosestPlayer(CLOSE_PLAYER_PREDICATE2, this) == null || this.attackTicks > 0 )
            {
                vec3d2 = vec3d.add((Math.signum(bl) * 0.5D - vec3d.x) * 0.10000000149011612D, (Math.signum(d) * 0.699999988079071D - vec3d.y) * 0.10000000149011612D, (Math.signum(e) * 0.5D - vec3d.z) * 0.10000000149011612D);
                this.setYaw(this.getYaw() + g);
            }
            this.setVelocity(vec3d2);
            this.forwardSpeed = 0.5F;
            if (this.random.nextInt(100) == 0 && this.world.getBlockState(blockPos2).isSolidBlock(this.world, blockPos2)) {
                this.setRoosting(true);
            }
        }

    }

    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.EVENTS;
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    public boolean canAvoidTraps() {
        return true;
    }
    protected void damage(LivingEntity target) {

        if (this.isAlive()) {

            if (this.squaredDistanceTo(target) < 0.6D * (double)1 * 0.6D * (double)1 && this.canSee(target) && target.damage(DamageSource.mob(this), 3)) {
                this.playSound(SoundEvents.ENTITY_BAT_DEATH, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.applyDamageEffects(this, target);
            }
        }
        if(target instanceof PlayerEntity){
            this.attackTicks = 20;
        }

    }
    public void onPlayerCollision(PlayerEntity player) {

        this.damage(player);
    }
    @Override
    public boolean canBeControlledByRider() {
        return true;
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.world.isClient && this.isRoosting()) {
                this.setRoosting(false);
            }

            return super.damage(source, amount);
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(BAT_FLAGS, nbt.getByte("BatFlags"));
        this.attackTicks = nbt.getInt("AttackTimer");
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("BatFlags", this.dataTracker.get(BAT_FLAGS));
        nbt.putInt("AttackTimer", this.attackTicks);
    }


    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height / 2.0F;
    }
    public static final Predicate<LivingEntity> EXCEPT_CREATIVE_OR_SPECTATOR = (entity) -> (entity instanceof PlayerEntity) || !entity.isSpectator() && !((PlayerEntity)entity).isCreative();
    static {
        BAT_FLAGS = DataTracker.registerData(BatEntity.class, TrackedDataHandlerRegistry.BYTE);
        CLOSE_PLAYER_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(16.0D);
        CLOSE_PLAYER_PREDICATE2 = TargetPredicate.createAttackable().setPredicate(EXCEPT_CREATIVE_OR_SPECTATOR);
    }


    public void onDeath(DamageSource source){

        ZombieMod.getTimeout.add(this);

    }
    public static Vec3d getRotationVec3d(float pitch, float yaw) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d((i * j), (-k), (h * j));
    }

}
