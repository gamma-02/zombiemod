package gamma_02.zombiemobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class util
{
    public static Optional<Entity> getTargetedEntity(@Nullable Entity entity, int maxDistance) {
        if (entity == null) {
            return Optional.empty();
        } else {
            Vec3d vec3d = entity.getEyePos();
            Vec3d vec3d2 = entity.getRotationVec(1.0F).multiply(maxDistance);
            Vec3d vec3d3 = vec3d.add(vec3d2);
            Box box = entity.getBoundingBox().stretch(vec3d2).expand(1.0D);
            int i = maxDistance * maxDistance;
            Predicate<Entity> predicate = (entityx) -> {
                return !entityx.isSpectator();
            };
            EntityHitResult entityHitResult = ProjectileUtil.raycast(entity, vec3d, vec3d3, box, predicate, i);
            if (entityHitResult == null) {
                return Optional.empty();
            } else {
                return vec3d.squaredDistanceTo(entityHitResult.getPos()) > (double)i ? Optional.empty() : Optional.of(entityHitResult.getEntity());
            }
        }
    }
}
