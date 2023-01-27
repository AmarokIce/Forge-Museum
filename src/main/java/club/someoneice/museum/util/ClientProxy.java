package club.someoneice.museum.util;

import club.someoneice.museum.world.RenderHelper;
import club.someoneice.museum.world.TileTeleportBlock;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {
    public static int RenderID;

    @Override
    @SideOnly(Side.CLIENT)
    public void render() {
        RenderID = RenderingRegistry.getNextAvailableRenderId();
        ClientRegistry.bindTileEntitySpecialRenderer(TileTeleportBlock.class, new RenderHelper());
    }
}
