package gamma_02.zombiemobs.dragon;

import com.google.common.collect.*;
import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonSpawnState;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.predicate.block.BlockPredicate;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.Unit;
import net.minecraft.util.math.*;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.EndPortalFeature;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class ZombieDragonFight
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int CHECK_DRAGON_SEEN_INTERVAL = 1200;
    private static final int CRYSTAL_COUNTING_INTERVAL = 100;
    private static final int field_31445 = 20;
    private static final int field_31446 = 8;
    public static final int field_31441 = 9;
    private static final int field_31447 = 20;
    private static final int field_31448 = 96;
    public static final int field_31442 = 128;
    private static final Predicate<Entity> VALID_ENTITY;
    private final ServerBossBar bossBar;
    private final ServerWorld world;
    private final List<Integer> gateways;
    private final BlockPattern endPortalPattern;
    private int dragonSeenTimer;
    private int endCrystalsAlive;
    private int crystalCountTimer;
    private int playerUpdateTimer;
    private boolean dragonKilled;
    private boolean previouslyKilled;
    private UUID dragonUuid;
    private boolean doLegacyCheck;
    private BlockPos exitPortalLocation;
    private ZombieDragonSpawnState dragonSpawnState;
    private int spawnStateTimer;
    private List<EndCrystalEntity> crystals;

    public ZombieDragonFight(ServerWorld serverWorld, long l, NbtCompound nbtCompound) {
        this.bossBar = (ServerBossBar)(new ServerBossBar(new TranslatableText("entity.minecraft.ender_dragon"), BossBar.Color.PINK, BossBar.Style.PROGRESS)).setDragonMusic(true).setThickenFog(true);
        this.gateways = Lists.newArrayList();
        this.doLegacyCheck = true;
        this.world = serverWorld;
        if (nbtCompound.contains("NeedsStateScanning")) {
            this.doLegacyCheck = nbtCompound.getBoolean("NeedsStateScanning");
        }

        if (nbtCompound.contains("DragonKilled", 99)) {
            if (nbtCompound.containsUuid("Dragon")) {
                this.dragonUuid = nbtCompound.getUuid("Dragon");
            }

            this.dragonKilled = nbtCompound.getBoolean("DragonKilled");
            this.previouslyKilled = nbtCompound.getBoolean("PreviouslyKilled");
            if (nbtCompound.getBoolean("IsRespawning")) {
                this.dragonSpawnState = ZombieDragonSpawnState.START;
            }

            if (nbtCompound.contains("ExitPortalLocation", 10)) {
                this.exitPortalLocation = NbtHelper.toBlockPos(nbtCompound.getCompound("ExitPortalLocation"));
            }
        } else {
            this.dragonKilled = true;
            this.previouslyKilled = true;
        }

        if (nbtCompound.contains("Gateways", 9)) {
            NbtList nbtList = nbtCompound.getList("Gateways", 3);

            for(int i = 0; i < nbtList.size(); ++i) {
                this.gateways.add(nbtList.getInt(i));
            }
        } else {
            this.gateways.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
            Collections.shuffle(this.gateways, new Random(l));
        }

        this.endPortalPattern = BlockPatternBuilder.start().aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ").aisle("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ").where('#', CachedBlockPosition.matchesBlockState(
                BlockPredicate.make(Blocks.BEDROCK))).build();

    }

    public NbtCompound toNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putBoolean("NeedsStateScanning", this.doLegacyCheck);
        if (this.dragonUuid != null) {
            nbtCompound.putUuid("Dragon", this.dragonUuid);
        }

        nbtCompound.putBoolean("DragonKilled", this.dragonKilled);
        nbtCompound.putBoolean("PreviouslyKilled", this.previouslyKilled);
        if (this.exitPortalLocation != null) {
            nbtCompound.put("ExitPortalLocation", NbtHelper.fromBlockPos(this.exitPortalLocation));
        }

        NbtList nbtList = new NbtList();
        Iterator var3 = this.gateways.iterator();

        while(var3.hasNext()) {
            int i = (Integer)var3.next();
            nbtList.add(NbtInt.of(i));
        }

        nbtCompound.put("Gateways", nbtList);
        return nbtCompound;
    }

    public void tick() {
        this.bossBar.setVisible(!this.dragonKilled);
        if (++this.playerUpdateTimer >= 20) {
            this.updatePlayers();
            this.playerUpdateTimer = 0;
        }

        if (!this.bossBar.getPlayers().isEmpty()) {
            this.world.getChunkManager().addTicket(ChunkTicketType.DRAGON, new ChunkPos(0, 0), 9, Unit.INSTANCE);
            boolean bl = this.loadChunks();
            if (this.doLegacyCheck && bl) {
                this.convertFromLegacy();
                this.doLegacyCheck = false;
            }

            if (this.dragonSpawnState != null) {
                if (this.crystals == null && bl) {
                    this.dragonSpawnState = null;
                    this.respawnDragon();
                }

                this.dragonSpawnState.run(this.world, this, this.crystals, this.spawnStateTimer++, this.exitPortalLocation);
            }

            if (!this.dragonKilled) {
                if ((this.dragonUuid == null || ++this.dragonSeenTimer >= 1200) && bl) {
                    this.checkDragonSeen();
                    this.dragonSeenTimer = 0;
                }

                if (++this.crystalCountTimer >= 100 && bl) {
                    this.countAliveCrystals();
                    this.crystalCountTimer = 0;
                }
            }
        } else {
            this.world.getChunkManager().removeTicket(ChunkTicketType.DRAGON, new ChunkPos(0, 0), 9, Unit.INSTANCE);
        }

    }

    private void convertFromLegacy() {
        LOGGER.info("Scanning for legacy world dragon fight...");
        boolean bl = this.worldContainsEndPortal();
        if (bl) {
            LOGGER.info("Found that the dragon has been killed in this world already.");
            this.previouslyKilled = true;
        } else {
            LOGGER.info("Found that the dragon has not yet been killed in this world.");
            this.previouslyKilled = false;
            if (this.findEndPortal() == null) {
                this.generateEndPortal(false);
            }
        }

        List<? extends EnderDragonEntity> list = this.world.getAliveEnderDragons();
        if (list.isEmpty()) {
            this.dragonKilled = true;
        } else {
            EnderDragonEntity enderDragonEntity = (EnderDragonEntity)list.get(0);
            this.dragonUuid = enderDragonEntity.getUuid();
            LOGGER.info((String)"Found that there's a dragon still alive ({})", (Object)enderDragonEntity);
            this.dragonKilled = false;
            if (!bl) {
                LOGGER.info("But we didn't have a portal, let's remove it.");
                enderDragonEntity.discard();
                this.dragonUuid = null;
            }
        }

        if (!this.previouslyKilled && this.dragonKilled) {
            this.dragonKilled = false;
        }

    }

    public void checkDragonSeen() {
        List<? extends ZombieEnderDragon> list = this.world.getEntitiesByType(TypeFilter.instanceOf(ZombieEnderDragon.class), EntityPredicates.VALID_ENTITY);
        if (list.isEmpty()) {
            LOGGER.debug("Haven't seen the dragon, respawning it");
            this.createDragon();
        } else {
            LOGGER.debug("Haven't seen our dragon, but found another one to use.");
            this.dragonUuid = (list.get(0)).getUuid();
        }

    }



    public void setSpawnState(ZombieDragonSpawnState spawnState) {
        if (this.dragonSpawnState == null) {
            throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
        } else {
            this.spawnStateTimer = 0;
            if (spawnState == ZombieDragonSpawnState.END) {
                this.dragonSpawnState = null;
                this.dragonKilled = false;
                ZombieEnderDragon enderDragonEntity = this.createDragon();
                Iterator var3 = this.bossBar.getPlayers().iterator();

                while(var3.hasNext()) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)var3.next();
                    Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, enderDragonEntity);
                }
            } else {
                this.dragonSpawnState = spawnState;
            }

        }
    }

    private boolean worldContainsEndPortal() {
        for(int i = -8; i <= 8; ++i) {
            for(int j = -8; j <= 8; ++j) {
                WorldChunk worldChunk = this.world.getChunk(i, j);
                Iterator var4 = worldChunk.getBlockEntities().values().iterator();

                while(var4.hasNext()) {
                    BlockEntity blockEntity = (BlockEntity)var4.next();
                    if (blockEntity instanceof EndPortalBlockEntity) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Nullable
    private BlockPattern.Result findEndPortal() {
        int i;
        int j;
        for(i = -8; i <= 8; ++i) {
            for(j = -8; j <= 8; ++j) {
                WorldChunk worldChunk = this.world.getChunk(i, j);
                Iterator var4 = worldChunk.getBlockEntities().values().iterator();

                while(var4.hasNext()) {
                    BlockEntity blockEntity = (BlockEntity)var4.next();
                    if (blockEntity instanceof EndPortalBlockEntity) {
                        BlockPattern.Result result = this.endPortalPattern.searchAround(this.world, blockEntity.getPos());
                        if (result != null) {
                            BlockPos blockPos = result.translate(3, 3, 3).getBlockPos();
                            if (this.exitPortalLocation == null) {
                                this.exitPortalLocation = blockPos;
                            }

                            return result;
                        }
                    }
                }
            }
        }

        i = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN).getY();

        for(j = i; j >= this.world.getBottomY(); --j) {
            BlockPattern.Result worldChunk = this.endPortalPattern.searchAround(this.world, new BlockPos(EndPortalFeature.ORIGIN.getX(), j, EndPortalFeature.ORIGIN.getZ()));
            if (worldChunk != null) {
                if (this.exitPortalLocation == null) {
                    this.exitPortalLocation = worldChunk.translate(3, 3, 3).getBlockPos();
                }

                return worldChunk;
            }
        }

        return null;
    }

    private boolean loadChunks() {
        for(int i = -8; i <= 8; ++i) {
            for(int j = 8; j <= 8; ++j) {
                Chunk chunk = this.world.getChunk(i, j, ChunkStatus.FULL, false);
                if (!(chunk instanceof WorldChunk)) {
                    return false;
                }

                ChunkHolder.LevelType levelType = ((WorldChunk)chunk).getLevelType();
                if (!levelType.isAfter(ChunkHolder.LevelType.TICKING)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void updatePlayers() {
        Set<ServerPlayerEntity> set = Sets.newHashSet();
        Iterator var2 = this.world.getPlayers(VALID_ENTITY).iterator();

        while(var2.hasNext()) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)var2.next();
            this.bossBar.addPlayer(serverPlayerEntity);
            set.add(serverPlayerEntity);
        }

        Set<ServerPlayerEntity> set2 = Sets.newHashSet((Iterable)this.bossBar.getPlayers());
        set2.removeAll(set);
        Iterator var6 = set2.iterator();

        while(var6.hasNext()) {
            ServerPlayerEntity serverPlayerEntity2 = (ServerPlayerEntity)var6.next();
            this.bossBar.removePlayer(serverPlayerEntity2);
        }

    }

    private void countAliveCrystals() {
        this.crystalCountTimer = 0;
        this.endCrystalsAlive = 0;

        EndSpikeFeature.Spike spike;
        for(Iterator var1 = EndSpikeFeature.getSpikes(this.world).iterator(); var1.hasNext(); this.endCrystalsAlive += this.world.getNonSpectatingEntities(EndCrystalEntity.class, spike.getBoundingBox()).size()) {
            spike = (EndSpikeFeature.Spike)var1.next();
        }

        LOGGER.debug((String)"Found {} end crystals still alive", (Object)this.endCrystalsAlive);
    }

    public void dragonKilled(ZombieEnderDragon dragon) {
        if (dragon.getUuid().equals(this.dragonUuid)) {
            this.bossBar.setPercent(0.0F);
            this.bossBar.setVisible(false);
            this.generateEndPortal(true);
            this.generateNewEndGateway();
            if (!this.previouslyKilled) {
                this.world.setBlockState(this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN), Blocks.DRAGON_EGG.getDefaultState());
            }

            this.previouslyKilled = true;
            this.dragonKilled = true;
        }

    }

    private void generateNewEndGateway() {
        if (!this.gateways.isEmpty()) {
            int i = (Integer)this.gateways.remove(this.gateways.size() - 1);
            int j = MathHelper.floor(96.0D * Math.cos(2.0D * (-3.141592653589793D + 0.15707963267948966D * (double)i)));
            int k = MathHelper.floor(96.0D * Math.sin(2.0D * (-3.141592653589793D + 0.15707963267948966D * (double)i)));
            this.generateEndGateway(new BlockPos(j, 75, k));
        }
    }

    private void generateEndGateway(BlockPos pos) {
        this.world.syncWorldEvent(WorldEvents.END_GATEWAY_SPAWNS, pos, 0);
        ConfiguredFeatures.END_GATEWAY_DELAYED.generate(this.world, this.world.getChunkManager().getChunkGenerator(), new Random(), pos);
    }

    private void generateEndPortal(boolean previouslyKilled) {
        EndPortalFeature endPortalFeature = new EndPortalFeature(previouslyKilled);
        if (this.exitPortalLocation == null) {
            for(this.exitPortalLocation = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN).down(); this.world.getBlockState(this.exitPortalLocation).isOf(Blocks.BEDROCK) && this.exitPortalLocation.getY() > this.world.getSeaLevel(); this.exitPortalLocation = this.exitPortalLocation.down()) {
            }
        }

        endPortalFeature.configure(FeatureConfig.DEFAULT).generate(this.world, this.world.getChunkManager().getChunkGenerator(), new Random(), this.exitPortalLocation);
    }

    public ZombieEnderDragon createDragon() {
        this.world.getWorldChunk(new BlockPos(0, 128, 0));
        ZombieEnderDragon enderDragonEntity = ZombieMod.ZOMBIE_ENDER_DRAGON.create(this.world);
        enderDragonEntity.getPhaseManager().setPhase(ZombiePhaseType.HOLDING_PATTERN);
        enderDragonEntity.refreshPositionAndAngles(0.0D, 128.0D, 0.0D, this.world.random.nextFloat() * 360.0F, 0.0F);
        this.world.spawnEntity(enderDragonEntity);
        this.dragonUuid = enderDragonEntity.getUuid();



        return enderDragonEntity;
    }

    public void updateFight(ZombieEnderDragon dragon) {
        if (dragon.getUuid().equals(this.dragonUuid)) {
            this.bossBar.setPercent(dragon.getHealth() / dragon.getMaxHealth());
            this.dragonSeenTimer = 0;
            if (dragon.hasCustomName()) {
                this.bossBar.setName(dragon.getDisplayName());
            }
        }

    }

    public int getAliveEndCrystals() {
        return this.endCrystalsAlive;
    }

    public void crystalDestroyed(EndCrystalEntity enderCrystal, DamageSource source) {
        if (this.dragonSpawnState != null && this.crystals.contains(enderCrystal)) {
            LOGGER.debug("Aborting respawn sequence");
            this.dragonSpawnState = null;
            this.spawnStateTimer = 0;
            this.resetEndCrystals();
            this.generateEndPortal(true);
        } else {
            this.countAliveCrystals();
            Entity entity = this.world.getEntity(this.dragonUuid);
            if (entity instanceof EnderDragonEntity) {
                ((EnderDragonEntity)entity).crystalDestroyed(enderCrystal, enderCrystal.getBlockPos(), source);
            }
        }

    }

    public boolean hasPreviouslyKilled() {
        return this.previouslyKilled;
    }

    public void respawnDragon() {
        if (this.dragonKilled && this.dragonSpawnState == null) {
            BlockPos blockPos = this.exitPortalLocation;
            if (blockPos == null) {
                LOGGER.debug("Tried to respawn, but need to find the portal first.");
                BlockPattern.Result result = this.findEndPortal();
                if (result == null) {
                    LOGGER.debug("Couldn't find a portal, so we made one.");
                    this.generateEndPortal(true);
                } else {
                    LOGGER.debug("Found the exit portal & saved its location for next time.");
                }

                blockPos = this.exitPortalLocation;
            }

            List<EndCrystalEntity> result = Lists.newArrayList();
            BlockPos blockPos2 = blockPos.up(1);
            Iterator var4 = Direction.Type.HORIZONTAL.iterator();

            while(var4.hasNext()) {
                Direction direction = (Direction)var4.next();
                List<EndCrystalEntity> list = this.world.getNonSpectatingEntities(EndCrystalEntity.class, new Box(blockPos2.offset((Direction)direction, 2)));
                if (list.isEmpty()) {
                    return;
                }

                result.addAll(list);
            }

            LOGGER.debug("Found all crystals, respawning dragon.");
            this.respawnDragon(result);
        }

    }

    private void respawnDragon(List<EndCrystalEntity> crystals) {
        if (this.dragonKilled && this.dragonSpawnState == null) {
            for(BlockPattern.Result result = this.findEndPortal(); result != null; result = this.findEndPortal()) {
                for(int i = 0; i < this.endPortalPattern.getWidth(); ++i) {
                    for(int j = 0; j < this.endPortalPattern.getHeight(); ++j) {
                        for(int k = 0; k < this.endPortalPattern.getDepth(); ++k) {
                            CachedBlockPosition cachedBlockPosition = result.translate(i, j, k);
                            if (cachedBlockPosition.getBlockState().isOf(Blocks.BEDROCK) || cachedBlockPosition.getBlockState().isOf(Blocks.END_PORTAL)) {
                                this.world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.END_STONE.getDefaultState());
                            }
                        }
                    }
                }
            }

            this.dragonSpawnState = ZombieDragonSpawnState.START;
            this.spawnStateTimer = 0;
            this.generateEndPortal(false);
            this.crystals = crystals;
        }

    }

    public void resetEndCrystals() {
        Iterator var1 = EndSpikeFeature.getSpikes(this.world).iterator();

        while(var1.hasNext()) {
            EndSpikeFeature.Spike spike = (EndSpikeFeature.Spike)var1.next();
            List<EndCrystalEntity> list = this.world.getNonSpectatingEntities(EndCrystalEntity.class, spike.getBoundingBox());
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                EndCrystalEntity endCrystalEntity = (EndCrystalEntity)var4.next();
                endCrystalEntity.setInvulnerable(false);
                endCrystalEntity.setBeamTarget((BlockPos)null);
            }
        }

    }

    static {
        VALID_ENTITY = EntityPredicates.VALID_ENTITY.and(EntityPredicates.maxDistance(0.0D, 128.0D, 0.0D, 192.0D));
    }
}
