package club.someoneice.museum.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public abstract class TileEntityBase extends TileEntity {
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox();
    }

    abstract public void readNBT(NBTTagCompound nbt);
    abstract public void writeNBT(NBTTagCompound nbt);

    abstract public void update();

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {
        this.update();
    }

    @Override
    public void markDirty() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeNBT(nbt);
    }


}
