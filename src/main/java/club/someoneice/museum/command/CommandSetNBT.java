package club.someoneice.museum.command;

import club.someoneice.museum.sync.MuseumNBTCore;
import club.someoneice.museum.util.PlayerVec;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.Vec3;

import java.io.IOException;
import java.util.Objects;


public class CommandSetNBT extends CommandBase {
    @Override
    public String getCommandName() {
        return "museumNBT";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/museumNBT";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            if (Objects.equals(args[0], "set"))
                MuseumNBTCore.writeToNBT(getPlayer(sender, args[1]));
            else if (Objects.equals(args[0], "read"))
                MuseumNBTCore.readFromNBT(getPlayer(sender, args[1]), new PlayerVec(Vec3.createVectorHelper(sender.getPlayerCoordinates().posX, sender.getPlayerCoordinates().posY, sender.getPlayerCoordinates().posZ), sender.getEntityWorld().provider.dimensionId));
            else if (Objects.equals(args[0], "setself"))
                MuseumNBTCore.writeToNBT(getCommandSenderAsPlayer(sender));
            else if (Objects.equals(args[0], "readself"))
                MuseumNBTCore.readFromNBT(getCommandSenderAsPlayer(sender), new PlayerVec(Vec3.createVectorHelper(sender.getPlayerCoordinates().posX, sender.getPlayerCoordinates().posY, sender.getPlayerCoordinates().posZ), sender.getEntityWorld().provider.dimensionId));
        } catch (IOException e) {
            throw new RuntimeException("无法找到目标或目标的NBT文件. ");
        }
    }
}
