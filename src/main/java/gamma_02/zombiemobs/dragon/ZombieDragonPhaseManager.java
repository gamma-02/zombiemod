package gamma_02.zombiemobs.dragon;

import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZombieDragonPhaseManager
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final ZombieEnderDragon dragon;
    private final ZombiePhase[] phases = new ZombiePhase[ZombiePhaseType.count()];
    private ZombiePhase current;

    public ZombieDragonPhaseManager(ZombieEnderDragon enderDragonEntity) {
        this.dragon = enderDragonEntity;
        this.setPhase(ZombiePhaseType.HOVER);
    }

    public void setPhase(ZombiePhaseType<?> type) {
        if (this.current == null || type != this.current.getType()) {
            if (this.current != null) {
                this.current.endPhase();
            }

            this.current = this.create(type);
            if (!this.dragon.world.isClient) {
                this.dragon.getDataTracker().set(ZombieEnderDragon.PHASE_TYPE, type.getTypeId());
            }

            LOGGER.debug((String)"Dragon is now in phase {} on the {}", (Object)type, (Object)(this.dragon.world.isClient ? "client" : "server"));
            this.current.beginPhase();
        }
    }

    public ZombiePhase getCurrent() {
        return this.current;
    }

    public <T extends ZombiePhase> T create(ZombiePhaseType<T> type) {
        int i = type.getTypeId();
        if (this.phases[i] == null) {
            this.phases[i] = type.create(this.dragon);
        }

        return (T) this.phases[i];
    }
}
