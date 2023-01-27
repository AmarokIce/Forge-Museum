package club.someoneice.museum.world;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldMuseum extends WorldProvider {
    @Override
    public IChunkProvider createChunkGenerator() {
        return new WorldGenerator(worldObj);
    }

    @Override
    public String getDimensionName() {
        return "Museum";
    }

    @Override
    public long getWorldTime() {
        return 6000L;
    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }
}
