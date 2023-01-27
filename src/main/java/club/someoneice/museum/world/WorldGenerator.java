package club.someoneice.museum.world;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.List;

public class WorldGenerator implements IChunkProvider {
    private static final Block[] blockArray = new Block[32768];
    private static final byte biomeId = (byte) BiomeGenBase.plains.biomeID;
    private final World worldObj;

    public WorldGenerator(World world) {
        this.worldObj = world;
        short offset;
        for (byte y = 0; y < 64; y = (byte) (y + 1)) {
            for (byte x = 0; x < 16; x = (byte) (x + 1)) {
                for (byte z = 0; z < 16; z = (byte) (z + 1)) {
                    offset = (short) ((x << 11) + (z << 7) + y);
                    if (y < 10) blockArray[offset] = Blocks.quartz_block;
                }
            }
        }
    }

    @Override
    public boolean chunkExists(int x, int y) {
        return true;
    }

    @Override
    public Chunk provideChunk(int x, int y) {
        Chunk chunk = new Chunk(this.worldObj, blockArray, x, y);
        byte[] biomes = chunk.getBiomeArray();
        int len = biomes.length;
        for (int i = 0; i < len; i++)
            biomes[i] = biomeId;
        return chunk;
    }

    @Override
    public Chunk loadChunk(int x, int y) {
        return null;
    }

    @Override
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {

    }

    @Override
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {
        return true;
    }

    @Override
    public boolean unloadQueuedChunks() {
        return true;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "TestWorld";
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {
        return null;
    }

    @Override
    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int p_82695_1_, int p_82695_2_) {

    }

    @Override
    public void saveExtraData() {

    }
}
