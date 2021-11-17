package gamma_02.zombiemobs.mixin;

import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieDragonPart;
import gamma_02.zombiemobs.models.Container;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.EntityTrackingListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayerNetworkHandlerMixin implements EntityTrackingListener, ServerPlayPacketListener
{
    @Shadow public ServerPlayerEntity player;
    @Final @Shadow static Logger LOGGER;
    /**
     * @author gamma_02
     * @reason lul
     */
//    @Overwrite
//    public void onPlayerInteractEntity(PlayerInteractEntityC2SPacket packet) {
//        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
//        ServerWorld serverWorld = this.player.getServerWorld();
//        final Entity entity = ((Container) packet).getStored();
//        this.player.updateLastActionTime();
//        this.player.setSneaking(packet.isPlayerSneaking());
//        if (entity != null) {
//            double d = 36.0D;
//            if (this.player.squaredDistanceTo(entity) < 36.0D) {
//                packet.handle(new PlayerInteractEntityC2SPacket.Handler() {
//                    private void processInteract(Hand hand, ServerPlayNetworkHandler.Interaction action) {
//                        ItemStack itemStack = player.getStackInHand(hand).copy();
//                        ActionResult actionResult = action.run(player, entity, hand);
//                        if (actionResult.isAccepted()) {
//                            Criteria.PLAYER_INTERACTED_WITH_ENTITY.trigger(player, itemStack, entity);
//                            if (actionResult.shouldSwingHand()) {
//                                player.swingHand(hand, true);
//                            }
//                        }
//
//                    }
//
//                    public void interact(Hand hand) {
//                        this.processInteract(hand, PlayerEntity::interact);
//                    }
//
//                    public void interactAt(Hand hand, Vec3d pos) {
//                        this.processInteract(hand, (player, entityx, handx) -> {
//                            return entityx.interactAt(player, pos, handx);
//                        });
//                    }
//
//                    public void attack() {
//                        if (!(entity instanceof ItemEntity) && !(entity instanceof ExperienceOrbEntity) && !(entity instanceof PersistentProjectileEntity) && entity != player) {
//                            player.attack(entity);
//                        } else {
//                            player.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.invalid_entity_attacked"));
//                            LOGGER.warn("Player {} tried to attack an invalid entity", player.getName().getString());
//                        }
//                    }
//                });
//            }
//        }
//
//    }

}
