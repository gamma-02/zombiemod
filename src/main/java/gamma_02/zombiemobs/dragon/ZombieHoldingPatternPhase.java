package gamma_02.zombiemobs.dragon;

import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.boss.dragon.phase.StrafePlayerPhase;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.jetbrains.annotations.Nullable;

public class ZombieHoldingPatternPhase extends AbstractZombiePhase
{
    private static final TargetPredicate PLAYERS_IN_RANGE_PREDICATE = TargetPredicate.createAttackable().ignoreVisibility();
    private Path path;
    private Vec3d pathTarget;
    private boolean shouldFindNewPath;

    public ZombieHoldingPatternPhase(ZombieEnderDragon enderDragonEntity) {
        super(enderDragonEntity);
    }

    public ZombiePhaseType<ZombieHoldingPatternPhase> getType() {
        return ZombiePhaseType.HOLDING_PATTERN;
    }

    public void serverTick() {
        double d = this.pathTarget == null ? 0.0D : this.pathTarget.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
        if (d < 100.0D || d > 22500.0D || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
            this.tickInRange();
        }

    }

    public void beginPhase() {
        this.path = null;
        this.pathTarget = null;
    }

    @Nullable
    public Vec3d getPathTarget() {
        return this.pathTarget;
    }

    private void tickInRange() {
        int i;
        if (this.path != null && this.path.isFinished()) {
            BlockPos blockPos = this.dragon.world.getTopPosition(
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPortalFeature.ORIGIN));
            i = this.dragon.getFight() == null ? 0 : this.dragon.getFight().getAliveEndCrystals();
            if (this.dragon.getRandom().nextInt(i + 3) == 0) {
                this.dragon.getPhaseManager().setPhase(ZombiePhaseType.LANDING_APPROACH);
                return;
            }

            double d = 64.0D;
            PlayerEntity playerEntity = this.dragon.world.getClosestPlayer(PLAYERS_IN_RANGE_PREDICATE, this.dragon, (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
            if (playerEntity != null) {
                d = blockPos.getSquaredDistance(playerEntity.getPos(), true) / 512.0D;
            }

            if (playerEntity != null && (this.dragon.getRandom().nextInt(MathHelper.abs((int)d) + 2) == 0 || this.dragon.getRandom().nextInt(i + 2) == 0)) {
                this.strafePlayer(playerEntity);
                return;
            }
        }

        if (this.path == null || this.path.isFinished()) {
            int blockPos = this.dragon.getNearestPathNodeIndex();
            i = blockPos;
            if (this.dragon.getRandom().nextInt(8) == 0) {
                this.shouldFindNewPath = !this.shouldFindNewPath;
                i = blockPos + 6;
            }

            if (this.shouldFindNewPath) {
                ++i;
            } else {
                --i;
            }

            if (this.dragon.getFight() != null && this.dragon.getFight().getAliveEndCrystals() >= 0) {
                i %= 12;
                if (i < 0) {
                    i += 12;
                }
            } else {
                i -= 12;
                i &= 7;
                i += 12;
            }

            this.path = this.dragon.findPath(blockPos, i, (PathNode)null);
            if (this.path != null) {
                this.path.next();
            }
        }

        this.followPath();
    }

    private void strafePlayer(PlayerEntity player) {
        this.dragon.getPhaseManager().setPhase(ZombiePhaseType.STRAFE_PLAYER);
        (this.dragon.getPhaseManager().create(ZombiePhaseType.STRAFE_PLAYER)).setTargetEntity(player);
    }

    private void followPath() {
        if (this.path != null && !this.path.isFinished()) {
            Vec3i vec3i = this.path.getCurrentNodePos();
            this.path.next();
            double d = (double)vec3i.getX();
            double e = (double)vec3i.getZ();

            double f;
            do {
                f = (double)((float)vec3i.getY() + this.dragon.getRandom().nextFloat() * 20.0F);
            } while(f < (double)vec3i.getY());

            this.pathTarget = new Vec3d(d, f, e);
        }

    }

    public void crystalDestroyed(EndCrystalEntity crystal, BlockPos pos, DamageSource source, @Nullable PlayerEntity player) {
        if (player != null && this.dragon.canTarget(player)) {
            this.strafePlayer(player);
        }

    }
}

