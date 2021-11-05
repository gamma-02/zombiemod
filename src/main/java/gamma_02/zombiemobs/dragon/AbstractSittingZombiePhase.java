package gamma_02.zombiemobs.dragon;

import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
public abstract class AbstractSittingZombiePhase extends AbstractZombiePhase
{
    public AbstractSittingZombiePhase(ZombieEnderDragon enderDragonEntity) {
        super(enderDragonEntity);
    }

    public boolean isSittingOrHovering() {
        return true;
    }

    public float modifyDamageTaken(DamageSource damageSource, float damage) {
        if (damageSource.getSource() instanceof PersistentProjectileEntity) {
            damageSource.getSource().setOnFireFor(1);
            return 0.0F;
        } else {
            return super.modifyDamageTaken(damageSource, damage);
        }
    }
}
