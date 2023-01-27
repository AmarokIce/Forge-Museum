package club.someoneice.museum.world;

import club.someoneice.museum.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHelper extends TileEntitySpecialRenderer {
    public static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID, "textures/blocks/MuseumDoor.png");

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean blending = GL11.glIsEnabled(3042);
        GL11.glEnable(3042);
        GL11.glTranslatef((float)x + 0.5F, (float)y + 1.0F, (float)z + 0.5F);
        GL11.glBlendFunc(1, 1);
        GL11.glScalef(2.5F, 2.5F, 2.5F);
        long t = System.currentTimeMillis() % 6L;
        renderBillboardQuad(0.6000000238418579D, (float)t * 0.16666667F, 0.16666667F);
        GL11.glPopMatrix();
        if (!blending)
            GL11.glDisable(3042);
    }

    public static void renderBillboardQuad(double scale, float vAdd1, float vAdd2) {
        GL11.glPushMatrix();
        rotateToPlayer();
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.9F);
        tessellator.addVertexWithUV(-scale, -scale, 0.0D, 0.0D, (0.0F + vAdd1));
        tessellator.addVertexWithUV(-scale, scale, 0.0D, 0.0D, (0.0F + vAdd1 + vAdd2));
        tessellator.addVertexWithUV(scale, scale, 0.0D, 1.0D, (0.0F + vAdd1 + vAdd2));
        tessellator.addVertexWithUV(scale, -scale, 0.0D, 1.0D, (0.0F + vAdd1));
        tessellator.draw();
        GL11.glPopMatrix();
    }

    public static void rotateToPlayer() {
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
    }
}
