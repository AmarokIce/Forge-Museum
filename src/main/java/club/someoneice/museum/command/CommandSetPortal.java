package club.someoneice.museum.command;

import club.someoneice.museum.Config;
import club.someoneice.museum.ModInfo;
import club.someoneice.museum.util.Util;
import club.someoneice.museum.world.MuseumVec3;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.io.IOException;
import java.util.Objects;

public class CommandSetPortal extends CommandBase {
    @Override
    public String getCommandName() {
        return "museum";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/museum [x] [z]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (Objects.equals(args[0], "set")) {
            if (Objects.equals(args[1], "item")) {
                ModInfo.isAutoClearItem = !ModInfo.isAutoClearItem;
                sender.addChatMessage(new ChatComponentText("Now item cleaner is " + ModInfo.isAutoClearItem));
            } else if (Objects.equals(args[1], "entity")) {
                // ModInfo.isAutoClearEntity = !ModInfo.isAutoClearEntity;
                // sender.addChatMessage(new ChatComponentText("Now item cleaner is " + ModInfo.isAutoClearEntity));
            } else if (Objects.equals(args[1], "time")) {
                int time = Integer.parseInt(args[2]);
                ModInfo.AutoClearTime = time;
                sender.addChatMessage(new ChatComponentText("Success to set time :" + time));
            }
        } else if (Objects.equals(args[0], "clean")) {
            if (Objects.equals(args[1], "item")) {
                int i = Util.instance.clearWorldItem(sender.getEntityWorld());
                sender.addChatMessage(new ChatComponentText("This time clean " + i + " item."));
            }
            else if (Objects.equals(args[1], "entity")) {
                int e = Util.instance.clearWorldEntity(sender.getEntityWorld());
                sender.addChatMessage(new ChatComponentText("This time clean " + e + " entity."));
            }
            else {
                int i = Util.instance.clearWorldEntity(sender.getEntityWorld());
                // int e = Util.instance.clearWorldItem(sender.getEntityWorld());
                sender.addChatMessage(new ChatComponentText("This time clean " + i + " item."));
                // sender.addChatMessage(new ChatComponentText("This time clean " + e + " entity."));
            }
        } else {
            try {
                int x, z;
                if (Objects.equals(args[0], "~")) x = sender.getPlayerCoordinates().posX;
                else x = Integer.parseInt(args[0]);

                if (Objects.equals(args[1], "~")) z = sender.getPlayerCoordinates().posZ;
                else z = Integer.parseInt(args[1]);

                MuseumVec3.museumVec3 = new MuseumVec3(x, z);
                sender.addChatMessage(new ChatComponentText("Set successful!"));
            } catch (Exception e) {
                sender.addChatMessage(new ChatComponentText("/museum [x] [z]"));
            }
        }

        try {
            Config.writeToJson();
        } catch (IOException e) {
            throw new RuntimeException("Cannot write to config!");
        }
    }
}
