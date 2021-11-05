package gamma_02.zombiemobs.dragon;

import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractSittingPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SittingFlamingPhaseZombie extends AbstractSittingZombiePhase
{
    private static final int field_30433 = 200;
    private static final int field_30434 = 4;
    private static final int field_30435 = 10;
    private int ticks;
    private int timesRun;
    private AreaEffectCloudEntity dragonBreathEntity;

    public SittingFlamingPhaseZombie(ZombieEnderDragon enderDragonEntity) {
        super(enderDragonEntity);
    }

    public void clientTick() {
        ++this.ticks;
        if (this.ticks % 2 == 0 && this.ticks < 10) {
            Vec3d vec3d = this.dragon.getRotationVectorFromPhase(1.0F).normalize();
            vec3d.rotateY(-0.7853982F);
            double d = this.dragon.head.getX();
            double e = this.dragon.head.getBodyY(0.5D);
            double f = this.dragon.head.getZ();

            for(int i = 0; i < 8; ++i) {
                double g = d + this.dragon.getRandom().nextGaussian() / 2.0D;
                double h = e + this.dragon.getRandom().nextGaussian() / 2.0D;
                double j = f + this.dragon.getRandom().nextGaussian() / 2.0D;

                for(int k = 0; k < 6; ++k) {
                    this.dragon.world.addParticle(ParticleTypes.DRAGON_BREATH, g, h, j, -vec3d.x * 0.07999999821186066D * (double)k, -vec3d.y * 0.6000000238418579D, -vec3d.z * 0.07999999821186066D * (double)k);
                }

                vec3d.rotateY(0.19634955F);
            }
        }

    }

    public void serverTick() {
        ++this.ticks;
        if (this.ticks >= 200) {
            if (this.timesRun >= 4) {
                this.dragon.getPhaseManager().setPhase(ZombiePhaseType.TAKEOFF);
            } else {
                this.dragon.getPhaseManager().setPhase(ZombiePhaseType.SITTING_SCANNING);
            }
        } else if (this.ticks == 10) {
            Vec3d vec3d = (new Vec3d(this.dragon.head.getX() - this.dragon.getX(), 0.0D, this.dragon.head.getZ() - this.dragon.getZ())).normalize();
            float f = 5.0F;
            double d = this.dragon.head.getX() + vec3d.x * 5.0D / 2.0D;
            double e = this.dragon.head.getZ() + vec3d.z * 5.0D / 2.0D;
            double g = this.dragon.head.getBodyY(0.5D);
            double h = g;
            BlockPos.Mutable mutable = new BlockPos.Mutable(d, g, e);

            while(this.dragon.world.isAir(mutable)) {
                --h;
                if (h < 0.0D) {
                    h = g;
                    break;
                }

                mutable.set(d, h, e);
            }

            h = (double)(MathHelper.floor(h) + 1);
            this.dragonBreathEntity = new AreaEffectCloudEntity(this.dragon.world, d, h, e);
            this.dragonBreathEntity.setOwner(this.dragon);
            this.dragonBreathEntity.setRadius(5.0F);
            this.dragonBreathEntity.setDuration(200);
            this.dragonBreathEntity.setParticleType(ParticleTypes.DRAGON_BREATH);
            this.dragonBreathEntity.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE));
            this.dragon.world.spawnEntity(this.dragonBreathEntity);
        }

    }

    public void beginPhase() {
        this.ticks = 0;
        ++this.timesRun;
    }

    public void endPhase() {
        if (this.dragonBreathEntity != null) {
            this.dragonBreathEntity.discard();
            this.dragonBreathEntity = null;
        }

    }

    public ZombiePhaseType<SittingFlamingPhaseZombie> getType() {
        return ZombiePhaseType.SITTING_FLAMING;
    }

    public void reset() {
        this.timesRun = 0;
    }
}
