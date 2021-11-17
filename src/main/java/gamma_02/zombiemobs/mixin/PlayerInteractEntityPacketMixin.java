package gamma_02.zombiemobs.mixin;

import gamma_02.zombiemobs.models.Container;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInteractEntityC2SPacket.class)
public abstract class PlayerInteractEntityPacketMixin implements Container
{
//    @Shadow @Final private int entityId;
//    public Entity zombieDragonPart;
//    @Inject(method = "getEntity", at = @At("HEAD"), cancellable = true)
//    public void storeLocals(ServerWorld world, CallbackInfoReturnable<Entity> cir){
//        this.zombieDragonPart = ((Container)world).access(this.entityId);
//    }



//    @Override
//    public Entity getStored()
//    {
//        return zombieDragonPart;
//    }





}
