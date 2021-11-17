package gamma_02.zombiemobs.entities;

import gamma_02.zombiemobs.ZombieMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.TippedArrowItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.minecraft.item.Items.TIPPED_ARROW;

public class ZombieSkeleton extends SkeletonEntity implements IAnimatable
{
    private double lastx= 0;
    private double lastz = 0;
    private AnimationFactory animationFactory = new AnimationFactory(this);
    private AnimationBuilder builder = new AnimationBuilder();

    public ZombieSkeleton(EntityType<? extends ZombieSkeleton> entityType, World world)
    {
        super(entityType, world);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getArrowType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack, pullProgress);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3D) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        persistentProjectileEntity.setVelocity(d, e + g * 0.2D, f, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.ignoreCameraFrustum = true;

        this.world.spawnEntity(persistentProjectileEntity);
    }
    @Override
    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        return ProjectileUtil.createArrowProjectile(this, PotionUtil.setPotion(TIPPED_ARROW.getDefaultStack(), Potions.HARMING), damageModifier);
    }
    protected void initEquipment(LocalDifficulty difficulty) {
        super.initEquipment(difficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }
    public void onDeath(DamageSource source){

        ZombieMod.getTimeout.add(this);

    }
    private <E extends IAnimatable> PlayState basicMovement(AnimationEvent<E> event) {
        if (event.getLimbSwingAmount()>0.1F){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.zombie_skeleton.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.zombie_skeleton.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override public void registerControllers(AnimationData animationData)
    {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::basicMovement));
    }

    @Override public AnimationFactory getFactory()
    {
        return this.animationFactory;
    }

    @Override public void tick()
    {

        super.tick();
        this.lastx = this.getX();
        this.lastz = this.getZ();
    }
}
