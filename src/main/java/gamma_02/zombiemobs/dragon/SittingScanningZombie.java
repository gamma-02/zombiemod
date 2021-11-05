package gamma_02.zombiemobs.dragon;

import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractSittingPhase;
import net.minecraft.entity.boss.dragon.phase.ChargingPlayerPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SittingScanningZombie extends AbstractSittingZombiePhase
{
    private static final int field_30436 = 100;
    private static final int field_30437 = 10;
    private static final int field_30438 = 20;
    private static final int field_30439 = 150;
    private static final TargetPredicate PLAYER_WITHIN_RANGE_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(150.0D);
    private final TargetPredicate CLOSE_PLAYER_PREDICATE;
    private int ticks;

    public SittingScanningZombie(ZombieEnderDragon enderDragonEntity) {
        super(enderDragonEntity);
        this.CLOSE_PLAYER_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(20.0D).setPredicate((livingEntity) -> {
            return Math.abs(livingEntity.getY() - enderDragonEntity.getY()) <= 10.0D;
        });
    }

    public void serverTick() {
        ++this.ticks;
        LivingEntity livingEntity = this.dragon.world.getClosestPlayer(this.CLOSE_PLAYER_PREDICATE, this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
        if (livingEntity != null) {
            if (this.ticks > 25) {
                this.dragon.getPhaseManager().setPhase(ZombiePhaseType.SITTING_ATTACKING);
            } else {
                Vec3d vec3d = (new Vec3d(livingEntity.getX() - this.dragon.getX(), 0.0D, livingEntity.getZ() - this.dragon.getZ())).normalize();
                Vec3d vec3d2 = (new Vec3d((double) MathHelper.sin(this.dragon.getYaw() * 0.017453292F), 0.0D, (double)(-MathHelper.cos(this.dragon.getYaw() * 0.017453292F)))).normalize();
                float f = (float)vec3d2.dotProduct(vec3d);
                float g = (float)(Math.acos((double)f) * 57.2957763671875D) + 0.5F;
                if (g < 0.0F || g > 10.0F) {
                    double d = livingEntity.getX() - this.dragon.head.getX();
                    double e = livingEntity.getZ() - this.dragon.head.getZ();
                    double h = MathHelper.clamp(MathHelper.wrapDegrees(180.0D - MathHelper.atan2(d, e) * 57.2957763671875D - (double)this.dragon.getYaw()), -100.0D, 100.0D);
                    ZombieEnderDragon var10000 = this.dragon;
                    var10000.yawAcceleration *= 0.8F;
                    float i = (float)Math.sqrt(d * d + e * e) + 1.0F;
                    float j = i;
                    if (i > 40.0F) {
                        i = 40.0F;
                    }

                    var10000 = this.dragon;
                    var10000.yawAcceleration = (float)((double)var10000.yawAcceleration + h * (double)(0.7F / i / j));
                    this.dragon.setYaw(this.dragon.getYaw() + this.dragon.yawAcceleration);
                }
            }
        } else if (this.ticks >= 100) {
            livingEntity = this.dragon.world.getClosestPlayer(PLAYER_WITHIN_RANGE_PREDICATE, this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
            this.dragon.getPhaseManager().setPhase(ZombiePhaseType.TAKEOFF);
            if (livingEntity != null) {
                this.dragon.getPhaseManager().setPhase(ZombiePhaseType.CHARGING_PLAYER);
                (this.dragon.getPhaseManager().create(ZombiePhaseType.CHARGING_PLAYER)).setPathTarget(new Vec3d(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()));
            }
        }

    }

    public void beginPhase() {
        this.ticks = 0;
    }

    public ZombiePhaseType<SittingScanningZombie> getType() {
        return ZombiePhaseType.SITTING_SCANNING;
    }
}
