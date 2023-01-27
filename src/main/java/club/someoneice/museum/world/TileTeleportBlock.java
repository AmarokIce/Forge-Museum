package club.someoneice.museum.world;

import club.someoneice.museum.util.TileEntityBase;
import net.minecraft.nbt.NBTTagCompound;

public class TileTeleportBlock extends TileEntityBase {
    public TileTeleportBlock() {}

    @Override
    public boolean canUpdate() {
        return false;
    }

    public void readNBT(NBTTagCompound nbt) {}

    @Override
    public void writeNBT(NBTTagCompound nbt) {}

    @Override
    public void update() {}
}
