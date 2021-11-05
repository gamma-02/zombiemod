package gamma_02.zombiemobs.dragon;

import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.boss.dragon.phase.SittingFlamingPhase;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ZombieLandingPhase extends AbstractZombiePhase
{
    private Vec3d target;

    public ZombieLandingPhase(ZombieEnderDragon enderDragonEntity) {
        super(enderDragonEntity);
    }

    public void clientTick() {
        Vec3d vec3d = this.dragon.getRotationVectorFromPhase(1.0F).normalize();
        vec3d.rotateY(-0.7853982F);
        double d = this.dragon.head.getX();
        double e = this.dragon.head.getBodyY(0.5D);
        double f = this.dragon.head.getZ();

        for(int i = 0; i < 8; ++i) {
            Random random = this.dragon.getRandom();
            double g = d + random.nextGaussian() / 2.0D;
            double h = e + random.nextGaussian() / 2.0D;
            double j = f + random.nextGaussian() / 2.0D;
            Vec3d vec3d2 = this.dragon.getVelocity();
            this.dragon.world.addParticle(ParticleTypes.DRAGON_BREATH, g, h, j, -vec3d.x * 0.07999999821186066D + vec3d2.x, -vec3d.y * 0.30000001192092896D + vec3d2.y, -vec3d.z * 0.07999999821186066D + vec3d2.z);
            vec3d.rotateY(0.19634955F);
        }

    }

    public void serverTick() {
        if (this.target == null) {
            this.target = Vec3d.ofBottomCenter(this.dragon.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN));
        }

        if (this.target.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ()) < 1.0D) {
            (this.dragon.getPhaseManager().create(ZombiePhaseType.SITTING_FLAMING)).reset();
            this.dragon.getPhaseManager().setPhase(ZombiePhaseType.SITTING_SCANNING);
        }

    }

    public float getMaxYAcceleration() {
        return 1.5F;
    }

    public float getYawAcceleration() {
        float f = (float)this.dragon.getVelocity().horizontalLength() + 1.0F;
        float g = Math.min(f, 40.0F);
        return g / f;
    }

    public void beginPhase() {
        this.target = null;
    }

    @Nullable
    public Vec3d getPathTarget() {
        return this.target;
    }

    public ZombiePhaseType<ZombieLandingPhase> getType() {
        return ZombiePhaseType.LANDING;
    }
}

