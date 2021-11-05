package gamma_02.zombiemobs.dragon;

import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractSittingPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.sound.SoundEvents;

public class ZombieSittingAttackPhase extends AbstractSittingZombiePhase
{
    private static final int field_30432 = 40;
    private int ticks;

    public ZombieSittingAttackPhase(ZombieEnderDragon enderDragonEntity) {
        super(enderDragonEntity);
    }

    public void clientTick() {
        this.dragon.world.playSound(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, this.dragon.getSoundCategory(), 2.5F, 0.8F + this.dragon.getRandom().nextFloat() * 0.3F, false);
    }

    public void serverTick() {
        if (this.ticks++ >= 40) {
            this.dragon.getPhaseManager().setPhase(ZombiePhaseType.SITTING_FLAMING);
        }

    }

    public void beginPhase() {
        this.ticks = 0;
    }

    public ZombiePhaseType getType() {
        return ZombiePhaseType.SITTING_ATTACKING;
    }
}

