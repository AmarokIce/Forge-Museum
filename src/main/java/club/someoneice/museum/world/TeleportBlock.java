package club.someoneice.museum.world;

import club.someoneice.museum.MuseumMain;
import club.someoneice.museum.util.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TeleportBlock extends Block implements ITileEntityProvider {
    public TeleportBlock() {
        super(Material.portal);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setBlockUnbreakable();
        this.setBlockName("museum_world_block");
        this.setBlockTextureName("museum:museum_world_block");
        this.setBlockBounds(-0.3F, 0.0F, -0.3F, 1.3F, 1.3F, 1.3F);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (!(entity instanceof EntityPlayerMP)) return;
        EntityPlayerMP player = (EntityPlayerMP) entity;
        int PosY = (int) player.posY;
        if (player.worldObj.provider.dimensionId != MuseumMain.dimensionId) {
            WorldServer sworld = player.mcServer.worldServerForDimension(MuseumMain.dimensionId);
            while (sworld.getBlock(0, PosY, 0) != Blocks.air) PosY += 2;
            while (sworld.getBlock(0, PosY - 2, 0) == Blocks.air) PosY -= 1;
            player.mcServer.getConfigurationManager().transferPlayerToDimension(player, MuseumMain.dimensionId, new TeleportHelper(sworld));

            player.playerNetServerHandler.setPlayerLocation(0, PosY, 0, player.rotationPitch, player.rotationYawHead);
        } else {
            WorldServer sworld = player.mcServer.worldServerForDimension(0);
            player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0, new TeleportHelper(sworld));

            while (sworld.getBlock(x, PosY, z) != Blocks.air) PosY += 2;
            while (sworld.getBlock(x, PosY - 2, z) == Blocks.air) PosY -= 1;
            player.playerNetServerHandler.setPlayerLocation(MuseumVec3.museumVec3.x, PosY, MuseumVec3.museumVec3.z, player.rotationPitch, player.rotationYawHead);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random radnom) {
        for (int l = 0; l < 4; ++l) {
            double d0 = (float)x + radnom.nextFloat();
            double d1 = (float)y + radnom.nextFloat();
            double d2 = (float)z + radnom.nextFloat();
            double d3, d4, d5;

            int i1 = radnom.nextInt(2) * 2 - 1;
            d3 = d4 = d5 = ((double)radnom.nextFloat() - 0.5D) * 0.5D;

            world.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isBlockNormalCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTeleportBlock();
    }

    @Override
    public int getRenderType() {
        return ClientProxy.RenderID;
    }
}
