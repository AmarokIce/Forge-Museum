package club.someoneice.museum;

import club.someoneice.museum.sync.MuseumNBTCore;
import club.someoneice.museum.util.PlayerVec;
import club.someoneice.museum.util.Util;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;

import java.io.IOException;

public class WorldEvent {
    private static int time = ModInfo.AutoClearTime;
    private static boolean call = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void worldTickEvent(TickEvent.WorldTickEvent event) {
        int item = 0, entity = 0;
        if (ModInfo.isAutoClearItem || ModInfo.isAutoClearEntity) {
            if (time > 0) time--;
            if (time < 20 * 60 * 60 && !call) {
                for (Object obj : event.world.playerEntities) {
                    if (obj instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) obj;
                        player.addChatMessage(new ChatComponentText("It will clean the entity in 1 min."));
                        call = true;
                    }
                }
            } else {
                if (ModInfo.isAutoClearEntity) item = Util.instance.clearWorldEntity(event.world);
                if (ModInfo.isAutoClearItem) entity = Util.instance.clearWorldItem(event.world);
                for (Object obj : event.world.playerEntities) {
                    if (obj instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) obj;
                        player.addChatMessage(new ChatComponentText("This time clean all the item (or entity) :" + (item + entity)));
                    }
                }
                call = false;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        try {
            Vec3 playerPos = Vec3.createVectorHelper(event.player.posX, event.player.posY, event.player.posZ);
            if (MuseumNBTCore.readFromNBT(event.player, new PlayerVec(playerPos, event.player.dimension)))
                event.player.addChatMessage(new ChatComponentText("Now load the nbt!"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        try {
            MuseumMain.Logger.info(event.player.getDisplayName() + " is levels the server. Now save the data... ");
            MuseumNBTCore.writeToNBT(event.player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
