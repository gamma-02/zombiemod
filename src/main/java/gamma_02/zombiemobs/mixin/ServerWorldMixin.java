package gamma_02.zombiemobs.mixin;

import gamma_02.zombiemobs.TimeOut;
import gamma_02.zombiemobs.ZombieMod;
import gamma_02.zombiemobs.dragon.ZombieDragonFight;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World
{

    @Shadow private boolean inBlockTick;
    @Shadow @Final private MinecraftServer server;
    private ZombieDragonFight zombieDragonFight;
    private TimeOut timeOut = ZombieMod.getTimeout;

    protected ServerWorldMixin(MutableWorldProperties mutableWorldProperties, RegistryKey<World> registryKey,
            DimensionType dimensionType, Supplier<Profiler> supplier, boolean bl, boolean bl2, long l)
    {
        super(mutableWorldProperties, registryKey, dimensionType, supplier, bl, bl2, l);

    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(BooleanSupplier shouldKeepTicking, CallbackInfo ci){

        Profiler profiler = this.getProfiler();
        this.inBlockTick = true;
        this.zombieDragonFight = ZombieMod.getZombieDragonFight();

        if (this.zombieDragonFight != null) {
            this.zombieDragonFight.tick();
        }

        if(ZombieMod.started && this.timeOut!=null){
            timeOut.setWorld(this.server.getWorld(this.getRegistryKey()));
            timeOut.tick();
        }
        this.timeOut = ZombieMod.getTimeout;




    }

}



