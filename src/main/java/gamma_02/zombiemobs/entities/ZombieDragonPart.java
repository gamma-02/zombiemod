package gamma_02.zombiemobs.entities;

import gamma_02.zombiemobs.ZombieMod;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;

public class ZombieDragonPart extends Entity
{


        public final ZombieEnderDragon owner;
        public final String name;
        private final EntityDimensions partDimensions;

        public ZombieDragonPart(ZombieEnderDragon enderDragonEntity, String string, float f, float g) {
            super(enderDragonEntity.getType(), enderDragonEntity.world);
            System.out.println("w h y");
            this.partDimensions = EntityDimensions.changing(f, g);
            this.calculateDimensions();
            this.owner = enderDragonEntity;
            this.name = string;
        }

        protected void initDataTracker() {
        }

        public void readCustomDataFromNbt(NbtCompound nbt) {
        }

        public void writeCustomDataToNbt(NbtCompound nbt) {
        }

        public boolean collides() {
            return true;
        }

        public boolean damage(DamageSource source, float amount) {
            return this.isInvulnerableTo(source) ? false : this.owner.damagePart(this, source, amount);
        }

        public boolean isPartOf(Entity entity) {
            return this == entity || this.owner == entity;
        }

        public Packet<?> createSpawnPacket() {
            throw new UnsupportedOperationException();
        }



        public EntityDimensions getDimensions(EntityPose pose) {
            return this.partDimensions;
        }

        public boolean shouldSave() {
            return false;
        }

    }

