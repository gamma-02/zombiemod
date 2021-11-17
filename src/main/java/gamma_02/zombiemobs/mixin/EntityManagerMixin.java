package gamma_02.zombiemobs.mixin;

import gamma_02.zombiemobs.TimeOut;
import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieDragonPart;
import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import gamma_02.zombiemobs.models.Container;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.entity.EntityIndex;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(targets = "net/minecraft/server/world/ServerWorld$ServerEntityHandler")
public class EntityManagerMixin<T extends EntityLike>
{


    @SuppressWarnings("ShadowTarget")
    @Shadow
    @Final
    private ServerWorld field_26936;

    @Final
    List<ServerPlayerEntity> players;

    public EntityManagerMixin()
    {
    }

    /**
     * @author gamma_02
     * @reason lul
     */
    @Overwrite
    public void startTracking(Entity entity) {
        ZombieMod.getServer().getWorld(entity.getEntityWorld().getRegistryKey()).getChunkManager().loadEntity(entity);
        if (entity instanceof ServerPlayerEntity) {
            ZombieMod.getServer().getWorld(entity.getEntityWorld().getRegistryKey()).players.add((ServerPlayerEntity)entity);
            ZombieMod.getServer().getWorld(entity.getEntityWorld().getRegistryKey()).updateSleepingPlayers();
        }

        if (entity instanceof MobEntity) {
            ZombieMod.getServer().getWorld(entity.getEntityWorld().getRegistryKey()).loadedMobs.add((MobEntity)entity);
        }
        if(entity instanceof ZombieEnderDragon){
            ZombieDragonPart[] var2 = ((ZombieEnderDragon)entity).getBodyParts();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                ZombieDragonPart enderDragonPart = var2[var4];
                ((Container)ZombieMod.server.getWorld((entity).getEntityWorld().getRegistryKey())).dragonPartsMap.put(enderDragonPart.getId(), enderDragonPart);
            }
        }
        if (entity instanceof EnderDragonEntity) {
            EnderDragonPart[] var2 = ((EnderDragonEntity)entity).getBodyParts();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                EnderDragonPart enderDragonPart = var2[var4];
                ((Container)ZombieMod.server.getWorld((entity).getEntityWorld().getRegistryKey())).dragonPartsMap.put(enderDragonPart.getId(), enderDragonPart);
            }
        }
    }

    /**
     * @author
     */
    @Overwrite
    public void stopTracking(Entity entity){
        ZombieMod.getServer().getWorld(entity.getEntityWorld().getRegistryKey()).getChunkManager().unloadEntity(entity);
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntityx = (ServerPlayerEntity)entity;
            ZombieMod.getServer().getWorld(entity.getEntityWorld().getRegistryKey()).players.remove(serverPlayerEntityx);
            ZombieMod.getServer().getWorld(entity.getEntityWorld().getRegistryKey()).updateSleepingPlayers();
        }

        if (entity instanceof MobEntity) {
            ZombieMod.getServer().getWorld(entity.getEntityWorld().getRegistryKey()).loadedMobs.remove((MobEntity)entity);
        }

        if(entity instanceof ZombieEnderDragon){
            ZombieDragonPart[] var2 = ((ZombieEnderDragon)entity).getBodyParts();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                ZombieDragonPart enderDragonPart = var2[var4];
                ((Container)ZombieMod.server.getWorld((entity).getEntityWorld().getRegistryKey())).dragonPartsMap.remove(enderDragonPart.getId(), enderDragonPart);
            }
        }
        if (entity instanceof EnderDragonEntity) {
            EnderDragonPart[] var2 = ((EnderDragonEntity)entity).getBodyParts();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                EnderDragonPart enderDragonPart = var2[var4];
                ((Container)ZombieMod.server.getWorld((entity).getEntityWorld().getRegistryKey())).dragonPartsMap.remove(enderDragonPart.getId(), enderDragonPart);
            }
        }
    }
}
