package gamma_02.zombiemobs.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.world.World;

public class ZombieSpider extends SpiderEntity
{
    public ZombieSpider(EntityType<? extends SpiderEntity> entityType, World world)
    {
        super(entityType, world);
    }


    public void onPlayerCollision(PlayerEntity player){
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 0, false, false, false), this);
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30, 1, false, false, false), this);
    }

}
