package gamma_02.zombiemobs.dragon;

import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class zombieHoverPhase extends AbstractZombiePhase
{
    private Vec3d target;

    public zombieHoverPhase(ZombieEnderDragon enderDragonEntity) {
        super(enderDragonEntity);
    }

    public void serverTick() {
        if (this.target == null) {
            this.target = this.dragon.getPos();
        }

    }

    public boolean isSittingOrHovering() {
        return true;
    }

    public void beginPhase() {
        this.target = null;
    }

    public float getMaxYAcceleration() {
        return 1.0F;
    }

    @Nullable
    public Vec3d getPathTarget() {
        return this.target;
    }

    public ZombiePhaseType<zombieHoverPhase> getType() {
        return ZombiePhaseType.HOVER;
    }
}
