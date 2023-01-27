package club.someoneice.museum;

import club.someoneice.museum.command.CommandKeepInv;
import club.someoneice.museum.command.CommandSetNBT;
import club.someoneice.museum.command.CommandSetPortal;
import club.someoneice.museum.sync.PartBeans;
import club.someoneice.museum.util.CommonProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkHandshakeEstablished;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


@Mod(modid = ModInfo.MODID , version = ModInfo.VERSION, acceptableRemoteVersions = "*")
public class MuseumMain {
    public static SimpleNetworkWrapper channel;
    public static Logger Logger = LogManager.getLogger("[Museum]");

    @SidedProxy(clientSide = "club.someoneice.museum.util.ClientProxy", serverSide = "club.someoneice.museum.util.CommonProxy")
    public static CommonProxy proxy = new CommonProxy();

    public static int dimensionId = ModInfo.MuseumWorldID;

    @Mod.EventHandler
    public void ModPerInitEvent(FMLPreInitializationEvent event) {
        Logger.info("Now read the config.");
        Logger = event.getModLog();
        new ModInfo();


        channel = NetworkRegistry.INSTANCE.newSimpleChannel("Museum");
        // FMLEventChannel eventChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel("Museum_EventChannel");
        // eventChannel.register(this);
    }

    @Mod.EventHandler
    public void ModInitEvent(FMLInitializationEvent event) {
        PartBeans.init();
        Logger.info("Now read the world block and render it.");
        // GameRegistry.registerBlock(new TeleportBlock(), "museum_world_block");
        // GameRegistry.registerTileEntity(TileTeleportBlock.class, "tile_museum_world_block");
        registryEvent(new WorldEvent());
        registryEvent(this);
        // proxy.render();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        Logger.info("[Server] Now registry the new world.");
        event.registerServerCommand(new CommandSetPortal());
        event.registerServerCommand(new CommandSetNBT());
        event.registerServerCommand(new CommandKeepInv());

        /*
        File root = DimensionManager.getCurrentSaveRootDirectory();
        if (null != root) {
            for (File file : Objects.requireNonNull(root.listFiles())) {
                if (file.isDirectory()) {
                    DimensionManager.registerProviderType(dimensionId, WorldMuseum.class, false);
                    if (!DimensionManager.isDimensionRegistered(dimensionId)) {
                        DimensionManager.registerDimension(dimensionId, dimensionId);
                        DimensionManager.initDimension(dimensionId);
                    }
                }
            }
        }

         */
    }

    @SubscribeEvent
    public void networkEvent(FMLNetworkEvent.CustomNetworkEvent event) {
        if (event.wrappedEvent instanceof NetworkHandshakeEstablished) {
            NetworkHandshakeEstablished handshake = (NetworkHandshakeEstablished)event.wrappedEvent;
            if (handshake.netHandler instanceof NetHandlerPlayServer) {
                NetHandlerPlayServer handler = (NetHandlerPlayServer)handshake.netHandler;
                MuseumMain.WorldMessagePackage MuPackage = new MuseumMain.WorldMessagePackage();
                handler.sendPacket(channel.getPacketFrom(MuPackage));
            }
        }
    }

    private void registryEvent(Object eventObj) {
        MinecraftForge.EVENT_BUS.register(eventObj);
        FMLCommonHandler.instance().bus().register(eventObj);
    }

    public class WorldMessagePackage implements IMessage {
        Map<Integer, Integer> dimensions = new HashMap<Integer, Integer>();

        @Override
        public void fromBytes(ByteBuf buf) {
            this.dimensions.clear();
            int numDimensions = buf.readInt();
            for (int i = 0; i < numDimensions; i++) this.dimensions.put(buf.readInt(), buf.readInt());
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(1);
            buf.writeInt(dimensionId);
            buf.writeInt(0);
        }
    }
}
