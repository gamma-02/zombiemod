package gamma_02.zombiemobs.entities;

import gamma_02.zombiemobs.ZombieMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;

public class ZombieDog extends WolfEntity
{
    public ZombieDog(EntityType<? extends ZombieDog> entityType, World world)
    {
        super(entityType, world);
        this.setTamed(true);
        this.setOwner(world.getClosestPlayer(this, 30));
    }
    public static DefaultAttributeContainer.Builder createZombieWolfAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D).add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D);
    }

    @Override public void onDeath(DamageSource source)
    {
        ZombieMod.getTimeout.add(this);
    }
}
