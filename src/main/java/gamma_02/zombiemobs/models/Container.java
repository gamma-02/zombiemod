package gamma_02.zombiemobs.models;

import gamma_02.zombiemobs.entities.ZombieDragonPart;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Container
{
    Int2ObjectMap<ZombieDragonPart> zombieDragonParts = new Int2ObjectOpenHashMap<>();
    Int2ObjectMap<Entity> dragonPartsMap = new Int2ObjectOpenHashMap<>();
    Entity access(int id);
    Entity getStored();
}
