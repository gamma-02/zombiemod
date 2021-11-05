package gamma_02.zombiemobs.dragon;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public enum ZombieDragonSpawnState {
    START {
        public void run(ServerWorld world, ZombieDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos pos) {
            BlockPos blockPos = new BlockPos(0, 128, 0);
            Iterator var7 = crystals.iterator();

            while(var7.hasNext()) {
                EndCrystalEntity endCrystalEntity = (EndCrystalEntity)var7.next();
                endCrystalEntity.setBeamTarget(blockPos);
            }

            fight.setSpawnState(PREPARING_TO_SUMMON_PILLARS);
        }
    },
    PREPARING_TO_SUMMON_PILLARS {
        public void run(ServerWorld world, ZombieDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos pos) {
            if (i < 100) {
                if (i == 0 || i == 50 || i == 51 || i == 52 || i >= 95) {
                    world.syncWorldEvent(WorldEvents.ENDER_DRAGON_RESURRECTED, new BlockPos(0, 128, 0), 0);
                }
            } else {
                fight.setSpawnState(SUMMONING_PILLARS);
            }

        }
    },
    SUMMONING_PILLARS {
        public void run(ServerWorld world, ZombieDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos pos) {

            boolean bl = i % 40 == 0;
            boolean bl2 = i % 40 == 39;
            if (bl || bl2) {
                List<EndSpikeFeature.Spike> list = EndSpikeFeature.getSpikes(world);
                int k = i / 40;
                if (k < list.size()) {
                    EndSpikeFeature.Spike spike = (EndSpikeFeature.Spike)list.get(k);
                    if (bl) {
                        Iterator var12 = crystals.iterator();

                        while(var12.hasNext()) {
                            EndCrystalEntity endCrystalEntity = (EndCrystalEntity)var12.next();
                            endCrystalEntity.setBeamTarget(new BlockPos(spike.getCenterX(), spike.getHeight() + 1, spike.getCenterZ()));
                        }
                    } else {

                        Iterator var16 = BlockPos.iterate(new BlockPos(spike.getCenterX() - 10, spike.getHeight() - 10, spike.getCenterZ() - 10), new BlockPos(spike.getCenterX() + 10, spike.getHeight() + 10, spike.getCenterZ() + 10)).iterator();

                        while(var16.hasNext()) {
                            BlockPos blockPos = (BlockPos)var16.next();
                            world.removeBlock(blockPos, false);
                        }

                        world.createExplosion((Entity)null, (double)((float)spike.getCenterX() + 0.5F), (double)spike.getHeight(), (double)((float)spike.getCenterZ() + 0.5F), 5.0F, Explosion.DestructionType.DESTROY);
                        EndSpikeFeatureConfig endCrystalEntityx = new EndSpikeFeatureConfig(true, ImmutableList.of(spike), new BlockPos(0, 128, 0));
                        Feature.END_SPIKE.configure(endCrystalEntityx).generate(world, world.getChunkManager().getChunkGenerator(), new Random(), new BlockPos(spike.getCenterX(), 45, spike.getCenterZ()));
                    }
                } else if (bl) {
                    fight.setSpawnState(SUMMONING_DRAGON);
                }
            }

        }
    },
    SUMMONING_DRAGON {
        public void run(ServerWorld world, ZombieDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos pos) {
            Iterator var6;
            EndCrystalEntity endCrystalEntity;
            if (i >= 100) {
                fight.setSpawnState(END);
                fight.resetEndCrystals();
                var6 = crystals.iterator();

                while(var6.hasNext()) {
                    endCrystalEntity = (EndCrystalEntity)var6.next();
                    endCrystalEntity.setBeamTarget((BlockPos)null);
                    world.createExplosion(endCrystalEntity, endCrystalEntity.getX(), endCrystalEntity.getY(), endCrystalEntity.getZ(), 6.0F, Explosion.DestructionType.NONE);
                    endCrystalEntity.discard();
                }
            } else if (i >= 80) {
                world.syncWorldEvent(WorldEvents.ENDER_DRAGON_RESURRECTED, new BlockPos(0, 128, 0), 0);
            } else if (i == 0) {
                var6 = crystals.iterator();

                while(var6.hasNext()) {
                    endCrystalEntity = (EndCrystalEntity)var6.next();
                    endCrystalEntity.setBeamTarget(new BlockPos(0, 128, 0));
                }
            } else if (i < 5) {
                world.syncWorldEvent(WorldEvents.ENDER_DRAGON_RESURRECTED, new BlockPos(0, 128, 0), 0);
            }

        }
    },
    END {
        public void run(ServerWorld world, ZombieDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos pos) {
        }
    };

    public abstract void run(ServerWorld world, ZombieDragonFight fight, List<EndCrystalEntity> crystals, int i, BlockPos pos);
}
