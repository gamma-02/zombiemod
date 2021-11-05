package gamma_02.zombiemobs.dragon;

import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ZombiePhaseType<T extends ZombiePhase> {
    private static ZombiePhaseType<?>[] types = new ZombiePhaseType[0];
    public static final ZombiePhaseType<ZombieHoldingPatternPhase> HOLDING_PATTERN = register(ZombieHoldingPatternPhase.class, "ZombieHoldingPattern");
    public static final ZombiePhaseType<ZombieStrafePlayerPhase> STRAFE_PLAYER = register(ZombieStrafePlayerPhase.class, "ZombieStrafePlayer");
    public static final ZombiePhaseType<ZombieLandingApproachPhase> LANDING_APPROACH = register(ZombieLandingApproachPhase.class, "ZombieLandingApproach");
    public static final ZombiePhaseType<ZombieLandingPhase> LANDING = register(ZombieLandingPhase.class, "ZombieLanding");
    public static final ZombiePhaseType<TakeOffPhaseZombie> TAKEOFF = register(TakeOffPhaseZombie.class, "ZombieTakeoff");
    public static final ZombiePhaseType<SittingFlamingPhaseZombie> SITTING_FLAMING = register(SittingFlamingPhaseZombie.class, "ZombieSittingFlaming");
    public static final ZombiePhaseType<SittingScanningZombie> SITTING_SCANNING = register(SittingScanningZombie.class, "ZombieSittingScanning");
    public static final ZombiePhaseType<ZombieSittingAttackPhase> SITTING_ATTACKING = register(ZombieSittingAttackPhase.class, "ZombieSittingAttacking");
    public static final ZombiePhaseType<ZombieChargingPhase> CHARGING_PLAYER = register(ZombieChargingPhase.class, "ZombieChargingPlayer");
    public static final ZombiePhaseType<ZombieDyingPhase> DYING = register(ZombieDyingPhase.class, "ZombieDying");
    public static final ZombiePhaseType<zombieHoverPhase> HOVER = register(zombieHoverPhase.class, "ZombieHover");
    private final Class<? extends ZombiePhase> phaseClass;
    private final int id;
    private final String name;

    public ZombiePhaseType(int i, Class<? extends ZombiePhase> class_, String string) {
        this.id = i;
        this.phaseClass = class_;
        this.name = string;
    }

    public ZombiePhase create(ZombieEnderDragon dragon) {
        try {
            Constructor<? extends ZombiePhase> constructor = this.getConstructor();
            return (ZombiePhase)constructor.newInstance(dragon);
        } catch (Exception var3) {
            throw new Error(var3);
        }
    }

    protected Constructor<? extends ZombiePhase> getConstructor() throws NoSuchMethodException {
        return this.phaseClass.getConstructor(ZombieEnderDragon.class);
    }

    public int getTypeId() {
        return this.id;
    }

    public String toString() {
        return this.name + " (#" + this.id + ")";
    }

    public static ZombiePhaseType<?> getFromId(int id) {
        return id >= 0 && id < types.length ? types[id] : HOLDING_PATTERN;
    }

    public static int count() {
        return types.length;
    }

    private static <T extends ZombiePhase> ZombiePhaseType<T> register(Class<T> phaseClass, String name) {
        ZombiePhaseType<T> phaseType = new ZombiePhaseType(types.length, phaseClass, name);
        types = (ZombiePhaseType[]) Arrays.copyOf(types, types.length + 1);
        types[phaseType.getTypeId()] = phaseType;
        return phaseType;
    }
}
