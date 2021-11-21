/*    */ package gamma_02.zombiemobs.mixin;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.block.Blocks;

/*    */ import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(EnderDragonFight.class)
/*    */ public abstract class DragonFightMizin
/*    */ {
    /*    */
    @Shadow @Final private ServerBossBar bossBar;

    @Shadow protected abstract void generateEndPortal(boolean previouslyKilled);

    @Shadow private boolean previouslyKilled;

    @Shadow private boolean dragonKilled;

    @Shadow @Final private ServerWorld world;

    @Shadow private UUID dragonUuid;

    /**
     * @author gamma_02
     */
    @Overwrite
    public void dragonKilled(EnderDragonEntity dragon) {
        if (dragon.getUuid().equals(this.dragonUuid)) {
            this.bossBar.setPercent(0.0F);
            this.bossBar.setVisible(false);


            if (!this.previouslyKilled) {
                this.world.setBlockState(this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN), Blocks.DRAGON_EGG.getDefaultState());
            }

            this.previouslyKilled = true;
            this.dragonKilled = true;
        }
    }
}


/* Location:              C:\Users\gamma\Downloads\zombiemobs-1.1.jar!\gamma_02\zombiemobs\mixin\DragonFightMizin.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */