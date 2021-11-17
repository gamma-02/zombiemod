package gamma_02.zombiemobs.mixin;

import gamma_02.zombiemobs.entities.ZombieDragonPart;
import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin
{
    /**
     * @author gamma_02
     * @reason no other way? maybe? private static void idk lol java confusing
     */
    @Overwrite
    private static void renderHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta) {
        Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());
        WorldRenderer.drawBox(matrices, vertices, box, 1.0F, 1.0F, 1.0F, 1.0F);
        if (entity instanceof EnderDragonEntity) {
            double d = -MathHelper.lerp((double)tickDelta, entity.lastRenderX, entity.getX());
            double e = -MathHelper.lerp((double)tickDelta, entity.lastRenderY, entity.getY());
            double f = -MathHelper.lerp((double)tickDelta, entity.lastRenderZ, entity.getZ());
            EnderDragonPart[] var11 = ((EnderDragonEntity)entity).getBodyParts();
            int var12 = var11.length;

            for(int var13 = 0; var13 < var12; ++var13) {
                EnderDragonPart enderDragonPart = var11[var13];
                matrices.push();
                double g = d + MathHelper.lerp((double)tickDelta, enderDragonPart.lastRenderX, enderDragonPart.getX());
                double h = e + MathHelper.lerp((double)tickDelta, enderDragonPart.lastRenderY, enderDragonPart.getY());
                double i = f + MathHelper.lerp((double)tickDelta, enderDragonPart.lastRenderZ, enderDragonPart.getZ());
                matrices.translate(g, h, i);
                WorldRenderer.drawBox(matrices, vertices, enderDragonPart.getBoundingBox().offset(-enderDragonPart.getX(), -enderDragonPart.getY(), -enderDragonPart.getZ()), 0.25F, 1.0F, 0.0F, 1.0F);
                matrices.pop();
            }
        }else if (entity instanceof ZombieEnderDragon) {
            double d = -MathHelper.lerp((double)tickDelta, entity.lastRenderX, entity.getX());
            double e = -MathHelper.lerp((double)tickDelta, entity.lastRenderY, entity.getY());
            double f = -MathHelper.lerp((double)tickDelta, entity.lastRenderZ, entity.getZ());
            ZombieDragonPart[] var11 = ((ZombieEnderDragon)entity).getBodyParts();
            int var12 = var11.length;

            for(int var13 = 0; var13 < var12; ++var13) {
                ZombieDragonPart enderDragonPart = var11[var13];
                matrices.push();
                double g = d + MathHelper.lerp((double)tickDelta, enderDragonPart.lastRenderX, enderDragonPart.getX());
                double h = e + MathHelper.lerp((double)tickDelta, enderDragonPart.lastRenderY, enderDragonPart.getY());
                double i = f + MathHelper.lerp((double)tickDelta, enderDragonPart.lastRenderZ, enderDragonPart.getZ());
                matrices.translate(g, h, i);
                WorldRenderer.drawBox(matrices, vertices, enderDragonPart.getBoundingBox().offset(-enderDragonPart.getX(), -enderDragonPart.getY(), -enderDragonPart.getZ()), 0.25F, 1.0F, 0.0F, 1.0F);
                matrices.pop();
            }
        }

        if (entity instanceof LivingEntity) {
            float d = 0.01F;
            WorldRenderer.drawBox(matrices, vertices, box.minX, (double)(entity.getStandingEyeHeight() - 0.01F), box.minZ, box.maxX, (double)(entity.getStandingEyeHeight() + 0.01F), box.maxZ, 1.0F, 0.0F, 0.0F, 1.0F);
        }

        Vec3d d = entity.getRotationVec(tickDelta);
        Matrix4f matrix4f = matrices.peek().getModel();
        Matrix3f e = matrices.peek().getNormal();
        vertices.vertex(matrix4f, 0.0F, entity.getStandingEyeHeight(), 0.0F).color(0, 0, 255, 255).normal(e, (float)d.x, (float)d.y, (float)d.z).next();
        vertices.vertex(matrix4f, (float)(d.x * 2.0D), (float)((double)entity.getStandingEyeHeight() + d.y * 2.0D), (float)(d.z * 2.0D)).color(0, 0, 255, 255).normal(e, (float)d.x, (float)d.y, (float)d.z).next();
    }
}
