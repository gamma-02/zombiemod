package gamma_02.zombiemobs.entities;

import com.google.common.collect.Lists;
import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.dragon.ZombieDragonFight;
import gamma_02.zombiemobs.dragon.ZombieDragonPhaseManager;
import gamma_02.zombiemobs.dragon.ZombiePhase;
import gamma_02.zombiemobs.dragon.ZombiePhaseType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathMinHeap;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseManager;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.MobSpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class ZombieEnderDragon extends HostileEntity implements Monster
{

    private static final Logger LOGGER = LogManager.getLogger();
    public static final TrackedData<Integer> PHASE_TYPE;
    private static final TargetPredicate CLOSE_PLAYER_PREDICATE;
    private static final int MAX_HEALTH = 200;
    private static final int field_30429 = 400;
    /**
     * The damage the dragon can take before it takes off, represented as a ratio to the full health.
     */
    private static final float TAKEOFF_THRESHOLD = 0.25F;
    private static final String DRAGON_DEATH_TIME_KEY = "DragonDeathTime";
    private static final String DRAGON_PHASE_KEY = "DragonPhase";
    /**
     * (yaw, y, ?)
     */
    public final double[][] segmentCircularBuffer = new double[64][3];
    public int latestSegment = -1;
    private final ZombieDragonPart[] parts;
    public final ZombieDragonPart head = new ZombieDragonPart(this, "head", 1.0F, 1.0F);
    private final ZombieDragonPart neck = new ZombieDragonPart(this, "neck", 3.0F, 3.0F);
    private final ZombieDragonPart body = new ZombieDragonPart(this, "body", 5.0F, 3.0F);
    private final ZombieDragonPart tail1 = new ZombieDragonPart(this, "tail", 2.0F, 2.0F);
    private final ZombieDragonPart tail2 = new ZombieDragonPart(this, "tail", 2.0F, 2.0F);
    private final ZombieDragonPart tail3 = new ZombieDragonPart(this, "tail", 2.0F, 2.0F);
    private final ZombieDragonPart rightWing = new ZombieDragonPart(this, "wing", 4.0F, 2.0F);
    private final ZombieDragonPart leftWing = new ZombieDragonPart(this, "wing", 4.0F, 2.0F);
    public float prevWingPosition;
    public float wingPosition;
    public boolean slowedDownByBlock;
    public int ticksSinceDeath;
    public float yawAcceleration;
    @Nullable
    public EndCrystalEntity connectedCrystal;
    @Nullable
    private ZombieDragonFight fight;
    private final ZombieDragonPhaseManager phaseManager;
    private int ticksUntilNextGrowl = 100;
    private int damageDuringSitting;
    /**
     * The first 12 path nodes are used for end crystals; the others are not tied to them.
     */
    private final PathNode[] pathNodes = new PathNode[24];
    /**
     * An array of 24 bitflags, where node #i leads to #j if and only if
     * {@code (pathNodeConnections[i] & (1 << j)) != 0}.
     */
    private final int[] pathNodeConnections = new int[24];
    private final PathMinHeap pathHeap = new PathMinHeap();

    public ZombieEnderDragon(EntityType<? extends ZombieEnderDragon> entityType, World world) {
        super(ZombieMod.ZOMBIE_ENDER_DRAGON, world);
        this.parts = new ZombieDragonPart[]{this.head, this.neck, this.body, this.tail1, this.tail2, this.tail3, this.rightWing, this.leftWing};
        this.setHealth(this.getMaxHealth());
        this.noClip = true;
        this.ignoreCameraFrustum = true;
        if (world instanceof ServerWorld) {
            this.fight = ZombieMod.getZombieDragonFight();
        } else {
            this.fight = null;
        }

        this.phaseManager = new ZombieDragonPhaseManager(this);
    }



    public static DefaultAttributeContainer.Builder createEnderDragonAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0D);
    }

    public void setDragonFight(ZombieDragonFight newFight){
        this.fight = newFight;
    }

    public boolean hasWings() {
        float f = MathHelper.cos(this.wingPosition * 6.2831855F);
        float g = MathHelper.cos(this.prevWingPosition * 6.2831855F);
        return g <= -0.3F && f >= -0.3F;
    }

    public void addFlapEffects() {
        if (this.world.isClient && !this.isSilent()) {
            this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_FLAP, this.getSoundCategory(), 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
        }

    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(PHASE_TYPE, PhaseType.HOVER.getTypeId());
    }

    public double[] getSegmentProperties(int segmentNumber, float tickDelta) {
        if (this.isDead()) {
            tickDelta = 0.0F;
        }

        tickDelta = 1.0F - tickDelta;
        int i = this.latestSegment - segmentNumber & 63;
        int j = this.latestSegment - segmentNumber - 1 & 63;
        double[] ds = new double[3];
        double d = this.segmentCircularBuffer[i][0];
        double e = MathHelper.wrapDegrees(this.segmentCircularBuffer[j][0] - d);
        ds[0] = d + e * (double)tickDelta;
        d = this.segmentCircularBuffer[i][1];
        e = this.segmentCircularBuffer[j][1] - d;
        ds[1] = d + e * (double)tickDelta;
        ds[2] = MathHelper.lerp((double)tickDelta, this.segmentCircularBuffer[i][2], this.segmentCircularBuffer[j][2]);
        return ds;
    }

    public void tickMovement() {
        this.addAirTravelEffects();
        if (this.world.isClient) {
            this.setHealth(this.getHealth());
            if (!this.isSilent() && !this.phaseManager.getCurrent().isSittingOrHovering() && --this.ticksUntilNextGrowl < 0) {
                this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, this.getSoundCategory(), 2.5F, 0.8F + this.random.nextFloat() * 0.3F, false);
                this.ticksUntilNextGrowl = 200 + this.random.nextInt(200);
            }
        }

        this.prevWingPosition = this.wingPosition;
        float g;
        if (this.isDead()) {
            float f = (this.random.nextFloat() - 0.5F) * 8.0F;
            g = (this.random.nextFloat() - 0.5F) * 4.0F;
            float h = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.world.addParticle(ParticleTypes.EXPLOSION, this.getX() + (double)f, this.getY() + 2.0D + (double)g, this.getZ() + (double)h, 0.0D, 0.0D, 0.0D);
        } else {
            this.tickWithEndCrystals();
            Vec3d f = this.getVelocity();
            g = 0.2F / ((float)f.horizontalLength() * 10.0F + 1.0F);
            g *= (float)Math.pow(2.0D, f.y);
            if (this.phaseManager.getCurrent().isSittingOrHovering()) {
                this.wingPosition += 0.1F;
            } else if (this.slowedDownByBlock) {
                this.wingPosition += g * 0.5F;
            } else {
                this.wingPosition += g;
            }

            this.setYaw(MathHelper.wrapDegrees(this.getYaw()));
            if (this.isAiDisabled()) {
                this.wingPosition = 0.5F;
            } else {
                if (this.latestSegment < 0) {
                    for(int h = 0; h < this.segmentCircularBuffer.length; ++h) {
                        this.segmentCircularBuffer[h][0] = (double)this.getYaw();
                        this.segmentCircularBuffer[h][1] = this.getY();
                    }
                }

                if (++this.latestSegment == this.segmentCircularBuffer.length) {
                    this.latestSegment = 0;
                }

                this.segmentCircularBuffer[this.latestSegment][0] = (double)this.getYaw();
                this.segmentCircularBuffer[this.latestSegment][1] = this.getY();
                double d;
                double e;
                double i;
                float m;
                float o;
                float n;
                if (this.world.isClient) {
                    if (this.bodyTrackingIncrements > 0) {
                        double h = this.getX() + (this.serverX - this.getX()) / (double)this.bodyTrackingIncrements;
                        d = this.getY() + (this.serverY - this.getY()) / (double)this.bodyTrackingIncrements;
                        e = this.getZ() + (this.serverZ - this.getZ()) / (double)this.bodyTrackingIncrements;
                        i = MathHelper.wrapDegrees(this.serverYaw - (double)this.getYaw());
                        this.setYaw(this.getYaw() + (float)i / (float)this.bodyTrackingIncrements);
                        this.setPitch(this.getPitch() + (float)(this.serverPitch - (double)this.getPitch()) / (float)this.bodyTrackingIncrements);
                        --this.bodyTrackingIncrements;
                        this.setPosition(h, d, e);
                        this.setRotation(this.getYaw(), this.getPitch());
                    }

                    this.phaseManager.getCurrent().clientTick();
                } else {
                    ZombiePhase h = this.phaseManager.getCurrent();
                    h.serverTick();
                    if (this.phaseManager.getCurrent() != h) {
                        h = this.phaseManager.getCurrent();
                        h.serverTick();
                    }

                    Vec3d vec3d = h.getPathTarget();
                    if (vec3d != null) {
                        d = vec3d.x - this.getX();
                        e = vec3d.y - this.getY();
                        i = vec3d.z - this.getZ();
                        double j = d * d + e * e + i * i;
                        float k = h.getMaxYAcceleration();
                        double l = Math.sqrt(d * d + i * i);
                        if (l > 0.0D) {
                            e = MathHelper.clamp(e / l, (double)(-k), (double)k);
                        }

                        this.setVelocity(this.getVelocity().add(0.0D, e * 0.01D, 0.0D));
                        this.setYaw(MathHelper.wrapDegrees(this.getYaw()));
                        Vec3d vec3d2 = vec3d.subtract(this.getX(), this.getY(), this.getZ()).normalize();
                        Vec3d vec3d3 = (new Vec3d((double)MathHelper.sin(this.getYaw() * 0.017453292F), this.getVelocity().y, (double)(-MathHelper.cos(this.getYaw() * 0.017453292F)))).normalize();
                        m = Math.max(((float)vec3d3.dotProduct(vec3d2) + 0.5F) / 1.5F, 0.0F);
                        if (Math.abs(d) > 9.999999747378752E-6D || Math.abs(i) > 9.999999747378752E-6D) {
                            double n1 = MathHelper.clamp(MathHelper.wrapDegrees(180.0D - MathHelper.atan2(d, i) * 57.2957763671875D - (double)this.getYaw()), -50.0D, 50.0D);
                            this.yawAcceleration *= 0.8F;
                            this.yawAcceleration = (float)((double)this.yawAcceleration + n1 * (double)h.getYawAcceleration());
                            this.setYaw(this.getYaw() + this.yawAcceleration * 0.1F);
                        }

                        n = (float)(2.0D / (j + 1.0D));
                        o = 0.06F;
                        this.updateVelocity(0.06F * (m * n + (1.0F - n)), new Vec3d(0.0D, 0.0D, -1.0D));
                        if (this.slowedDownByBlock) {
                            this.move(MovementType.SELF, this.getVelocity().multiply(0.800000011920929D));
                        } else {
                            this.move(MovementType.SELF, this.getVelocity().multiply(2));
                        }

                        Vec3d vec3d4 = this.getVelocity().normalize();
                        double p = 0.8D + 0.15D * (vec3d4.dotProduct(vec3d3) + 1.0D) / 2.0D;
                        this.setVelocity(this.getVelocity().multiply(p, 0.9100000262260437D, p));
                    }
                }

                this.bodyYaw = this.getYaw();
                Vec3d[] h = new Vec3d[this.parts.length];

                for(int vec3d = 0; vec3d < this.parts.length; ++vec3d) {
                    h[vec3d] = new Vec3d(this.parts[vec3d].getX(), this.parts[vec3d].getY(), this.parts[vec3d].getZ());
                }

                float vec3d = (float)(this.getSegmentProperties(5, 1.0F)[1] - this.getSegmentProperties(10, 1.0F)[1]) * 10.0F * 0.017453292F;
                float d1 = MathHelper.cos(vec3d);
                float q = MathHelper.sin(vec3d);
                float e1 = this.getYaw() * 0.017453292F;
                float r = MathHelper.sin(e1);
                float i1 = MathHelper.cos(e1);
                this.movePart(this.body, (double)(r * 0.5F), 0.0D, (double)(-i1 * 0.5F));
                this.movePart(this.rightWing, (double)(i1 * 4.5F), 2.0D, (double)(r * 4.5F));
                this.movePart(this.leftWing, (double)(i1 * -4.5F), 2.0D, (double)(r * -4.5F));
                if (!this.world.isClient && this.hurtTime == 0) {
                    this.launchLivingEntities(this.world.getOtherEntities(this, this.rightWing.getBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR));
                    this.launchLivingEntities(this.world.getOtherEntities(this, this.leftWing.getBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR));
                    this.damageLivingEntities(this.world.getOtherEntities(this, this.head.getBoundingBox().expand(1.0D), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR));
                    this.damageLivingEntities(this.world.getOtherEntities(this, this.neck.getBoundingBox().expand(1.0D), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR));
                }

                float s = MathHelper.sin(this.getYaw() * 0.017453292F - this.yawAcceleration * 0.01F);
                float j = MathHelper.cos(this.getYaw() * 0.017453292F - this.yawAcceleration * 0.01F);
                float t = this.getHeadVerticalMovement();
                this.movePart(this.head, (double)(s * 6.5F * d1), (double)(t + q * 6.5F), (double)(-j * 6.5F * d1));
                this.movePart(this.neck, (double)(s * 5.5F * d1), (double)(t + q * 5.5F), (double)(-j * 5.5F * d1));
                double[] k = this.getSegmentProperties(5, 1.0F);

                int l;
                for(l = 0; l < 3; ++l) {
                    ZombieDragonPart ZombieDragonPart = null;
                    if (l == 0) {
                        ZombieDragonPart = this.tail1;
                    }

                    if (l == 1) {
                        ZombieDragonPart = this.tail2;
                    }

                    if (l == 2) {
                        ZombieDragonPart = this.tail3;
                    }

                    double[] vec3d2 = this.getSegmentProperties(12 + l * 2, 1.0F);
                    float vec3d3 = this.getYaw() * 0.017453292F + this.wrapYawChange(vec3d2[0] - k[0]) * 0.017453292F;
                    m = MathHelper.sin(vec3d3);
                    n = MathHelper.cos(vec3d3);
                    o = 1.5F;
                    float vec3d4 = (float)(l + 1) * 2.0F;
                    this.movePart(ZombieDragonPart, (double)(-(r * 1.5F + m * vec3d4) * d1), vec3d2[1] - k[1] - (double)((vec3d4 + 1.5F) * q) + 1.5D, (double)((i1 * 1.5F + n * vec3d4) * d1));
                }

                if (!this.world.isClient) {
                    this.slowedDownByBlock = this.destroyBlocks(this.head.getBoundingBox()) | this.destroyBlocks(this.neck.getBoundingBox()) | this.destroyBlocks(this.body.getBoundingBox());
                    if (this.fight != null) {
                        this.fight.updateFight(this);
                    }
                }

                for(l = 0; l < this.parts.length; ++l) {
                    this.parts[l].prevX = h[l].x;
                    this.parts[l].prevY = h[l].y;
                    this.parts[l].prevZ = h[l].z;
                    this.parts[l].lastRenderX = h[l].x;
                    this.parts[l].lastRenderY = h[l].y;
                    this.parts[l].lastRenderZ = h[l].z;
                }

            }
        }
    }

    private void movePart(ZombieDragonPart ZombieDragonPart, double dx, double dy, double dz) {
        ZombieDragonPart.setPosition(this.getX() + dx, this.getY() + dy, this.getZ() + dz);
    }

    private float getHeadVerticalMovement() {
        if (this.phaseManager.getCurrent().isSittingOrHovering()) {
            return -1.0F;
        } else {
            double[] ds = this.getSegmentProperties(5, 1.0F);
            double[] es = this.getSegmentProperties(0, 1.0F);
            return (float)(ds[1] - es[1]);
        }
    }

    /**
     * Things to do every tick related to end crystals. The Ender Dragon:
     *
     * * Disconnects from its crystal if it is removed
     * * If it is connected to a crystal, then heals every 10 ticks
     * * With a 1 in 10 chance each tick, searches for the nearest crystal and connects to it if present
     */
    private void tickWithEndCrystals() {
        if (this.connectedCrystal != null) {
            if (this.connectedCrystal.isRemoved()) {
                this.connectedCrystal = null;
            } else if (this.age % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.setHealth(this.getHealth() + 1.0F);
            }
        }

        if (this.random.nextInt(10) == 0) {
            List<EndCrystalEntity> list = this.world.getNonSpectatingEntities(EndCrystalEntity.class, this.getBoundingBox().expand(32.0D));
            EndCrystalEntity endCrystalEntity = null;
            double d = Double.MAX_VALUE;
            Iterator var5 = list.iterator();

            while(var5.hasNext()) {
                EndCrystalEntity endCrystalEntity2 = (EndCrystalEntity)var5.next();
                double e = endCrystalEntity2.squaredDistanceTo(this);
                if (e < d) {
                    d = e;
                    endCrystalEntity = endCrystalEntity2;
                }
            }

            this.connectedCrystal = endCrystalEntity;
        }

    }

    private void launchLivingEntities(List<Entity> entities) {
        double d = (this.body.getBoundingBox().minX + this.body.getBoundingBox().maxX) / 2.0D;
        double e = (this.body.getBoundingBox().minZ + this.body.getBoundingBox().maxZ) / 2.0D;
        Iterator var6 = entities.iterator();

        while(var6.hasNext()) {
            Entity entity = (Entity)var6.next();
            if (entity instanceof LivingEntity) {
                double f = entity.getX() - d;
                double g = entity.getZ() - e;
                double h = Math.max(f * f + g * g, 0.1D);
                entity.addVelocity(f / h * 4.0D, 0.20000000298023224D, g / h * 4.0D);
                if (!this.phaseManager.getCurrent().isSittingOrHovering() && ((LivingEntity)entity).getLastAttackedTime() < entity.age - 2) {
                    entity.damage(DamageSource.mob(this), 5.0F);
                    this.applyDamageEffects(this, entity);
                }
            }
        }

    }

    private void damageLivingEntities(List<Entity> entities) {
        Iterator var2 = entities.iterator();

        while(var2.hasNext()) {
            Entity entity = (Entity)var2.next();
            if (entity instanceof LivingEntity) {
                entity.damage(DamageSource.mob(this), 10.0F);
                this.applyDamageEffects(this, entity);
            }
        }

    }

    private float wrapYawChange(double yawDegrees) {
        return (float)MathHelper.wrapDegrees(yawDegrees);
    }

    private boolean destroyBlocks(Box box) {
        int i = MathHelper.floor(box.minX);
        int j = MathHelper.floor(box.minY);
        int k = MathHelper.floor(box.minZ);
        int l = MathHelper.floor(box.maxX);
        int m = MathHelper.floor(box.maxY);
        int n = MathHelper.floor(box.maxZ);
        boolean bl = false;
        boolean bl2 = false;

        for(int o = i; o <= l; ++o) {
            for(int p = j; p <= m; ++p) {
                for(int q = k; q <= n; ++q) {
                    BlockPos blockPos = new BlockPos(o, p, q);
                    BlockState blockState = this.world.getBlockState(blockPos);
                    if (!blockState.isAir() && blockState.getMaterial() != Material.FIRE) {
                        if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && !blockState.isIn(BlockTags.DRAGON_IMMUNE)) {
                            bl2 = this.world.removeBlock(blockPos, false) || bl2;
                        } else {
                            bl = true;
                        }
                    }
                }
            }
        }

        if (bl2) {
            BlockPos o = new BlockPos(i + this.random.nextInt(l - i + 1), j + this.random.nextInt(m - j + 1), k + this.random.nextInt(n - k + 1));
            this.world.syncWorldEvent(WorldEvents.ENDER_DRAGON_BREAKS_BLOCK, o, 0);
        }

        return bl;
    }

    public boolean damagePart(ZombieDragonPart part, DamageSource source, float amount) {
        if (this.phaseManager.getCurrent().getType() == ZombiePhaseType.DYING) {
            return false;
        } else {
            amount = this.phaseManager.getCurrent().modifyDamageTaken(source, amount);
            if (part != this.head) {
                amount = amount / 4.0F + Math.min(amount, 1.0F);
            }

            if (amount < 0.01F) {
                return false;
            } else {
                if (source.getAttacker() instanceof PlayerEntity || source.isExplosive()) {
                    float f = this.getHealth();
                    this.parentDamage(source, amount);
                    if (this.isDead() && !this.phaseManager.getCurrent().isSittingOrHovering()) {
                        this.setHealth(1.0F);
                        this.phaseManager.setPhase(ZombiePhaseType.DYING);
                    }

                    if (this.phaseManager.getCurrent().isSittingOrHovering()) {
                        this.damageDuringSitting = (int)((float)this.damageDuringSitting + (f - this.getHealth()));
                        if ((float)this.damageDuringSitting > 0.25F * this.getMaxHealth()) {
                            this.damageDuringSitting = 0;
                            this.phaseManager.setPhase(ZombiePhaseType.TAKEOFF);
                        }
                    }
                }

                return true;
            }
        }
    }

    public boolean damage(DamageSource source, float amount) {
        if (source instanceof EntityDamageSource) {
            this.damagePart(this.body, source, amount);
        }

        return false;
    }

    protected boolean parentDamage(DamageSource source, float amount) {
        return super.damage(source, amount);
    }

    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        if (this.fight != null) {
            this.fight.updateFight(this);
            this.fight.dragonKilled(this);
        }

    }

    protected void updatePostDeath() {
        if (this.fight != null) {
            this.fight.updateFight(this);
        }

        ++this.ticksSinceDeath;
        if (this.ticksSinceDeath >= 180 && this.ticksSinceDeath <= 200) {
            float f = (this.random.nextFloat() - 0.5F) * 8.0F;
            float g = (this.random.nextFloat() - 0.5F) * 4.0F;
            float h = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX() + (double)f, this.getY() + 2.0D + (double)g, this.getZ() + (double)h, 0.0D, 0.0D, 0.0D);
        }

        boolean f = this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT);
        int g = 500;
        if (this.fight != null && !this.fight.hasPreviouslyKilled()) {
            g = 12000;
        }

        if (this.world instanceof ServerWorld) {
            if (this.ticksSinceDeath > 150 && this.ticksSinceDeath % 5 == 0 && f) {
                ExperienceOrbEntity.spawn((ServerWorld)this.world, this.getPos(), MathHelper.floor((float)g * 0.08F));
            }

            if (this.ticksSinceDeath == 1 && !this.isSilent()) {
                this.world.syncGlobalEvent(WorldEvents.ENDER_DRAGON_DIES, this.getBlockPos(), 0);
            }
        }

        this.move(MovementType.SELF, new Vec3d(0.0D, 0.10000000149011612D, 0.0D));
        this.setYaw(this.getYaw() + 20.0F);
        this.bodyYaw = this.getYaw();
        if (this.ticksSinceDeath == 200 && this.world instanceof ServerWorld) {
            if (f) {
                ExperienceOrbEntity.spawn((ServerWorld)this.world, this.getPos(), MathHelper.floor((float)g * 0.2F));
            }

            if (this.fight != null) {
                this.fight.dragonKilled(this);
            }

            this.remove(Entity.RemovalReason.KILLED);
        }

    }

    public int getNearestPathNodeIndex() {
        if (this.pathNodes[0] == null) {
            for(int i = 0; i < 24; ++i) {
                int j = 5;
                int l;
                int m;
                if (i < 12) {
                    l = MathHelper.floor(60.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.2617994F * (float)i)));
                    m = MathHelper.floor(60.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.2617994F * (float)i)));
                } else {
                    int k;
                    if (i < 20) {
                        k = i - 12;
                        l = MathHelper.floor(40.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.3926991F * (float)k)));
                        m = MathHelper.floor(40.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.3926991F * (float)k)));
                        j += 10;
                    } else {
                        k = i - 20;
                        l = MathHelper.floor(20.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.7853982F * (float)k)));
                        m = MathHelper.floor(20.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.7853982F * (float)k)));
                    }
                }

                int n = Math.max(this.world.getSeaLevel() + 10, this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(l, 0, m)).getY() + j);
                this.pathNodes[i] = new PathNode(l, n, m);
            }

            this.pathNodeConnections[0] = 6146;
            this.pathNodeConnections[1] = 8197;
            this.pathNodeConnections[2] = 8202;
            this.pathNodeConnections[3] = 16404;
            this.pathNodeConnections[4] = 32808;
            this.pathNodeConnections[5] = 32848;
            this.pathNodeConnections[6] = 65696;
            this.pathNodeConnections[7] = 131392;
            this.pathNodeConnections[8] = 131712;
            this.pathNodeConnections[9] = 263424;
            this.pathNodeConnections[10] = 526848;
            this.pathNodeConnections[11] = 525313;
            this.pathNodeConnections[12] = 1581057;
            this.pathNodeConnections[13] = 3166214;
            this.pathNodeConnections[14] = 2138120;
            this.pathNodeConnections[15] = 6373424;
            this.pathNodeConnections[16] = 4358208;
            this.pathNodeConnections[17] = 12910976;
            this.pathNodeConnections[18] = 9044480;
            this.pathNodeConnections[19] = 9706496;
            this.pathNodeConnections[20] = 15216640;
            this.pathNodeConnections[21] = 13688832;
            this.pathNodeConnections[22] = 11763712;
            this.pathNodeConnections[23] = 8257536;
        }

        return this.getNearestPathNodeIndex(this.getX(), this.getY(), this.getZ());
    }

    public int getNearestPathNodeIndex(double x, double y, double z) {
        float f = 10000.0F;
        int i = 0;
        PathNode pathNode = new PathNode(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
        int j = 0;
        if (this.fight == null || this.fight.getAliveEndCrystals() == 0) {
            j = 12;
        }

        for(int k = j; k < 24; ++k) {
            if (this.pathNodes[k] != null) {
                float g = this.pathNodes[k].getSquaredDistance(pathNode);
                if (g < f) {
                    f = g;
                    i = k;
                }
            }
        }

        return i;
    }

    @Nullable
    public Path findPath(int from, int to, @Nullable PathNode pathNode) {
        PathNode pathNode2;
        for(int i = 0; i < 24; ++i) {
            pathNode2 = this.pathNodes[i];
            pathNode2.visited = false;
            pathNode2.heapWeight = 0.0F;
            pathNode2.penalizedPathLength = 0.0F;
            pathNode2.distanceToNearestTarget = 0.0F;
            pathNode2.previous = null;
            pathNode2.heapIndex = -1;
        }

        PathNode i = this.pathNodes[from];
        pathNode2 = this.pathNodes[to];
        i.penalizedPathLength = 0.0F;
        i.distanceToNearestTarget = i.getDistance(pathNode2);
        i.heapWeight = i.distanceToNearestTarget;
        this.pathHeap.clear();
        this.pathHeap.push(i);
        PathNode pathNode3 = i;
        int j = 0;
        if (this.fight == null || this.fight.getAliveEndCrystals() == 0) {
            j = 12;
        }

        while(!this.pathHeap.isEmpty()) {
            PathNode pathNode4 = this.pathHeap.pop();
            if (pathNode4.equals(pathNode2)) {
                if (pathNode != null) {
                    pathNode.previous = pathNode2;
                    pathNode2 = pathNode;
                }

                return this.getPathOfAllPredecessors(i, pathNode2);
            }

            if (pathNode4.getDistance(pathNode2) < pathNode3.getDistance(pathNode2)) {
                pathNode3 = pathNode4;
            }

            pathNode4.visited = true;
            int k = 0;

            int l;
            for(l = 0; l < 24; ++l) {
                if (this.pathNodes[l] == pathNode4) {
                    k = l;
                    break;
                }
            }

            for(l = j; l < 24; ++l) {
                if ((this.pathNodeConnections[k] & 1 << l) > 0) {
                    PathNode pathNode5 = this.pathNodes[l];
                    if (!pathNode5.visited) {
                        float f = pathNode4.penalizedPathLength + pathNode4.getDistance(pathNode5);
                        if (!pathNode5.isInHeap() || f < pathNode5.penalizedPathLength) {
                            pathNode5.previous = pathNode4;
                            pathNode5.penalizedPathLength = f;
                            pathNode5.distanceToNearestTarget = pathNode5.getDistance(pathNode2);
                            if (pathNode5.isInHeap()) {
                                this.pathHeap.setNodeWeight(pathNode5, pathNode5.penalizedPathLength + pathNode5.distanceToNearestTarget);
                            } else {
                                pathNode5.heapWeight = pathNode5.penalizedPathLength + pathNode5.distanceToNearestTarget;
                                this.pathHeap.push(pathNode5);
                            }
                        }
                    }
                }
            }
        }

        if (pathNode3 == i) {
            return null;
        } else {
            LOGGER.debug((String)"Failed to find path from {} to {}", (Object)from, (Object)to);
            if (pathNode != null) {
                pathNode.previous = pathNode3;
                pathNode3 = pathNode;
            }

            return this.getPathOfAllPredecessors(i, pathNode3);
        }
    }

    @Contract("_, _ -> new") private @NotNull Path getPathOfAllPredecessors(PathNode unused, PathNode node) {
        List<PathNode> list = Lists.newArrayList();
        PathNode pathNode = node;
        list.add(0, node);

        while(pathNode.previous != null) {
            pathNode = pathNode.previous;
            list.add(0, pathNode);
        }

        return new Path(list, new BlockPos(node.x, node.y, node.z), true);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("DragonPhase", this.phaseManager.getCurrent().getType().getTypeId());
        nbt.putInt("DragonDeathTime", this.ticksSinceDeath);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("DragonPhase")) {
            this.phaseManager.setPhase(ZombiePhaseType.getFromId(nbt.getInt("DragonPhase")));
        }

        if (nbt.contains("DragonDeathTime")) {
            this.ticksSinceDeath = nbt.getInt("DragonDeathTime");
        }

    }

    public void checkDespawn() {
        if(hasPlayerRider()){
            this.remove(RemovalReason.UNLOADED_TO_CHUNK);
        }
    }

    public ZombieDragonPart[] getBodyParts() {
        return this.parts;
    }

    public boolean collides() {
        return true;
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ENDER_DRAGON_HURT;
    }

    protected float getSoundVolume() {
        return 5.0F;
    }

    public float getChangeInNeckPitch(int segmentOffset, double[] segment1, double[] segment2) {
        ZombiePhase phase = this.phaseManager.getCurrent();
        ZombiePhaseType<? extends ZombiePhase> phaseType = phase.getType();
        double e;
        if (phaseType != ZombiePhaseType.LANDING && phaseType != ZombiePhaseType.TAKEOFF) {
            if (phase.isSittingOrHovering()) {
                e = (double)segmentOffset;
            } else if (segmentOffset == 6) {
                e = 0.0D;
            } else {
                e = segment2[1] - segment1[1];
            }
        } else {
            BlockPos blockPos = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN);
            double d = Math.max(Math.sqrt(blockPos.getSquaredDistance(this.getPos(), true)) / 4.0D, 1.0D);
            e = (double)segmentOffset / d;
        }

        return (float)e;
    }

    public Vec3d getRotationVectorFromPhase(float tickDelta) {
        ZombiePhase phase = this.phaseManager.getCurrent();
        ZombiePhaseType<? extends ZombiePhase> phaseType = phase.getType();
        Vec3d vec3d;
        float f;
        if (phaseType != ZombiePhaseType.LANDING && phaseType != ZombiePhaseType.TAKEOFF) {
            if (phase.isSittingOrHovering()) {
                float blockPos = this.getPitch();
                f = 1.5F;
                this.setPitch(-45.0F);
                vec3d = this.getRotationVec(tickDelta);
                this.setPitch(blockPos);
            } else {
                vec3d = this.getRotationVec(tickDelta);
            }
        } else {
            BlockPos blockPos = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN);
            f = Math.max((float)Math.sqrt(blockPos.getSquaredDistance(this.getPos(), true)) / 4.0F, 1.0F);
            float g = 6.0F / f;
            float h = this.getPitch();
            float i = 1.5F;
            this.setPitch(-g * 1.5F * 5.0F);
            vec3d = this.getRotationVec(tickDelta);
            this.setPitch(h);
        }

        return vec3d;
    }

    public void crystalDestroyed(EndCrystalEntity crystal, BlockPos pos, DamageSource source) {
        PlayerEntity playerEntity;
        if (source.getAttacker() instanceof PlayerEntity) {
            playerEntity = (PlayerEntity)source.getAttacker();
        } else {
            playerEntity = this.world.getClosestPlayer(CLOSE_PLAYER_PREDICATE, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
        }

        if (crystal == this.connectedCrystal) {
            this.damagePart(this.head, DamageSource.explosion((LivingEntity)playerEntity), 10.0F);
        }

        this.phaseManager.getCurrent().crystalDestroyed(crystal, pos, source, playerEntity);
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (PHASE_TYPE.equals(data) && this.world.isClient) {
            this.phaseManager.setPhase(ZombiePhaseType.getFromId((Integer)this.getDataTracker().get(PHASE_TYPE)));
        }

        super.onTrackedDataSet(data);
    }

    public ZombieDragonPhaseManager getPhaseManager() {
        return this.phaseManager;
    }

    @Nullable
    public ZombieDragonFight getFight() {
        return this.fight;
    }

    public boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source) {
        return false;
    }

    protected boolean canStartRiding(Entity entity) {
        return false;
    }

    public boolean canUsePortals() {
        return false;
    }

    public void readFromPacket(MobSpawnS2CPacket packet) {
        super.readFromPacket(packet);
        ZombieDragonPart[] ZombieDragonParts = this.getBodyParts();

        for(int i = 0; i < ZombieDragonParts.length; ++i) {
            ZombieDragonParts[i].setId(i + packet.getId());
        }

    }

    public boolean canTarget(LivingEntity target) {
        return target.canTakeDamage();
    }

    static {
        PHASE_TYPE = DataTracker.registerData(ZombieEnderDragon.class, TrackedDataHandlerRegistry.INTEGER);
        CLOSE_PLAYER_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(64.0D);
    }


}
