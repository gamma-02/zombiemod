package gamma_02.zombiemobs.mixin;

import gamma_02.zombiemobs.entities.ZombieBat;
import gamma_02.zombiemobs.entities.ZombieDragonPart;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(MinecraftClient.class)
public class ClientMixin
{

    @Shadow protected int attackCooldown;

    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow @Nullable public ClientWorld world;

    @Shadow @Nullable public ClientPlayerInteractionManager interactionManager;

    @Inject(method = "doAttack", at = @At("HEAD"))
    public void attackMixin(CallbackInfo ci){
        if (this.attackCooldown <=0 ){
            Optional<Entity> target = DebugRenderer.getTargetedEntity(this.player, 5);
            if(!target.isEmpty()){
                if(target.get() instanceof ZombieDragonPart){
                    this.interactionManager.attackEntity(this.player, target.get());
                }
            }

        }
    }
}
