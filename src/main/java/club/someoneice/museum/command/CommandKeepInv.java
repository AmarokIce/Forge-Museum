package club.someoneice.museum.command;

import club.someoneice.museum.sync.MuseumNBTCore;
import club.someoneice.museum.util.PlayerVec;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.io.IOException;
import java.util.Objects;

public class CommandKeepInv extends CommandBase {
    @Override
    public String getCommandName() {
        return "museumKeepInv";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/museumKeepInv";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        for (int i = 0; i < 256; i++) {
            WorldServer worldServer = getPlayer(sender, sender.getCommandSenderName()).mcServer.worldServers[i];
            worldServer.getGameRules().setOrCreateGameRule("keepInventory", "true");
            sender.addChatMessage(new ChatComponentText(worldServer.getWorldInfo().getWorldName() + " set keep inventory success！"));
        }
    }
}
