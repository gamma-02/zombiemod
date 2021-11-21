package gamma_02.zombiemobs.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(EnderDragonFight.class)
public abstract class DragonFightMizin {

    @Shadow private BlockPos exitPortalLocation;

    @Shadow @Final private ServerWorld world;

    @Shadow private boolean dragonKilled;

    @Shadow private boolean previouslyKilled;

    @Shadow private UUID dragonUuid;

    @Shadow @Final private ServerBossBar bossBar;

    @Shadow protected abstract void generateEndPortal(boolean previouslyKilled);

    /**
     * @author gamma_02
     */
    @Overwrite
    public void dragonKilled(EnderDragonEntity dragon) {
        if (dragon.getUuid().equals(this.dragonUuid)) {
            this.bossBar.setPercent(0.0f);
            this.bossBar.setVisible(false);
            this.generateEndPortal(false);
            this.previouslyKilled = true;
            this.dragonKilled = true;
        }
    }


}

