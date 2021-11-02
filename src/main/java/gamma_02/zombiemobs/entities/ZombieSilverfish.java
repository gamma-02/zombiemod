package gamma_02.zombiemobs.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InfestedBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.EnumSet;
import java.util.Random;

public class ZombieSilverfish extends HostileEntity
{

    public ZombieSilverfish(EntityType<? extends ZombieSilverfish> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
    }

    public double getHeightOffset() {
        return 0.1D;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.13F;
    }

    public static DefaultAttributeContainer.Builder createSilverfishAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D);
    }

    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.EVENTS;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SILVERFISH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SILVERFISH_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SILVERFISH_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SILVERFISH_STEP, 0.15F, 1.0F);
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {

            return super.damage(source, amount);
        }
    }

    public void tick() {
        this.bodyYaw = this.getYaw();
        super.tick();
    }

    public void setBodyYaw(float bodyYaw) {
        this.setYaw(bodyYaw);
        super.setBodyYaw(bodyYaw);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return InfestedBlock.isInfestable(world.getBlockState(pos.down())) ? 10.0F : super.getPathfindingFavor(pos, world);
    }

    public static boolean canSpawn(EntityType<SilverfishEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (canSpawnIgnoreLightLevel(type, world, spawnReason, pos, random)) {
            PlayerEntity playerEntity = world.getClosestPlayer((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0D, true);
            return playerEntity == null;
        } else {
            return false;
        }
    }

    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }



}
