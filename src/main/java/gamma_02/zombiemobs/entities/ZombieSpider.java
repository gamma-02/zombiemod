package gamma_02.zombiemobs.entities;

import gamma_02.zombiemobs.ZombieMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
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
    public void onDeath(DamageSource source){

        ZombieMod.getTimeout.add(this);

    }
    public DefaultAttributeContainer.Builder buildSpiderAttributes(){

        return SpiderEntity.createSpiderAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3D);

    }

}
