package club.someoneice.museum.sync;

import club.someoneice.museum.MuseumMain;
import club.someoneice.museum.util.PlayerVec;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MuseumNBTCore {
    private static final String path = PartBeans.path;

    public static void writeToNBT(EntityPlayer player) throws IOException {
        MuseumMain.Logger.debug(new File(path, player.getDisplayName() + ".gzip").toPath().toString());
        if (deleteEeveeFile(player)) {
            NBTTagCompound tag = new NBTTagCompound();
            player.writeToNBT(tag);
            File output = new File(path, player.getDisplayName() + ".gzip");
            if (!output.exists() && !output.getParentFile().isDirectory()) output.getParentFile().mkdirs();
            if (!output.isFile()) output.createNewFile();

            CompressedStreamTools.writeCompressed(tag, Files.newOutputStream(output.toPath()));
            MuseumMain.Logger.info("Success save player" + player.getDisplayName() + "NBT data!");

            if (output.isFile()) {
                MuseumMain.Logger.info(output.toPath().toString());
            } else {
                MuseumMain.Logger.warn("Error! Path cannot be find!");
            }
        } else MuseumMain.Logger.warn("Cannot find the player " + player.getDisplayName() + " Eevee File, can you sure the player join to server with a event?");
    }

    public static boolean readFromNBT(EntityPlayer player, PlayerVec pos) throws IOException {
        File input = new File(path, player.getDisplayName() + ".gzip");
        MuseumMain.Logger.debug(input.toPath().toString());
        if (!input.exists() || !input.isFile()) return false;
        createNewEeveeFile(player);
        NBTTagCompound tag = CompressedStreamTools.readCompressed(Files.newInputStream(input.toPath()));
        tag.setInteger("Dimension", pos.DIMId);
        tag.setTag("Pos", newDoubleNBTList(pos.playerPos.xCoord, pos.playerPos.yCoord, pos.playerPos.zCoord));
        tag.setTag("Rotation", newFloatNBTList(player.rotationYaw, player.rotationPitch));

        player.readFromNBT(tag);
        return true;
    }

    public static void createNewEeveeFile(EntityPlayer player) throws IOException {
        File eevee = new File(path, player.getDisplayName() + ".eevee");
        if (eevee.isFile())
            ((EntityPlayerMP) player).playerNetServerHandler.kickPlayerFromServer("This time, another you in another server right?");
        else {
            eevee.createNewFile();
            eevee.deleteOnExit();
            if (eevee.isFile()) MuseumMain.Logger.info("Eevee file for player " + player.getDisplayName() + " is success!");
            else MuseumMain.Logger.error("Eevee file for player " + player.getDisplayName() + " is fail!");
        }
    }

    public static boolean deleteEeveeFile(EntityPlayer player) {
        File eevee = new File(path, player.getDisplayName() + ".eevee");
        if (!eevee.isFile())
            return false;
        else {
            eevee.delete();
            return true;
        }
    }

    private static NBTTagList newDoubleNBTList(double ... args) {
        NBTTagList nbttaglist = new NBTTagList();
        for (double d1 : args) nbttaglist.appendTag(new NBTTagDouble(d1));

        return nbttaglist;
    }

    private static NBTTagList newFloatNBTList(float ... args) {
        NBTTagList nbttaglist = new NBTTagList();
        for (float f1 : args) nbttaglist.appendTag(new NBTTagFloat(f1));

        return nbttaglist;
    }
}
