package club.someoneice.museum.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import project.studio.manametalmod.api.IMultipleHPBar;
import project.studio.manametalmod.feeddragon.EntityDragonEgg;
import project.studio.manametalmod.feeddragon.MobDragonElements;
import project.studio.manametalmod.mob.MobAlcis;
import project.studio.manametalmod.mob.boss.BossSnakeWhite;
import project.studio.manametalmod.npc.EntityNpc;

public class Util {
    public static Util instance = new Util();
    public Util() {}

    public int clearWorldItem(World worldObj) {
        int entity = 0;
        for (int i = 0; i < worldObj.loadedEntityList.size(); i ++) {
            if (worldObj.loadedEntityList.get(i) instanceof EntityItem) {
                ((EntityItem) worldObj.loadedEntityList.get(i)).setDead();
                entity ++;
            }
        }

        return entity;
    }

    public int clearWorldEntity(World worldObj) {
        int entity = 0;
        for (int i = 0; i < worldObj.loadedEntityList.size(); i ++) {
            if (
                    !(worldObj.loadedEntityList.get(i) instanceof EntityPlayer)         &&
                    !(worldObj.loadedEntityList.get(i) instanceof IMultipleHPBar)       &&
                    !(worldObj.loadedEntityList.get(i) instanceof EntityNpc)            &&
                    !(worldObj.loadedEntityList.get(i) instanceof EntityWolf)           &&
                    !(worldObj.loadedEntityList.get(i) instanceof EntityOcelot)         &&
                    !(worldObj.loadedEntityList.get(i) instanceof EntityVillager)       &&
                    !(worldObj.loadedEntityList.get(i) instanceof EntityDragonEgg)      &&
                    !(worldObj.loadedEntityList.get(i) instanceof MobDragonElements)    &&
                    !(worldObj.loadedEntityList.get(i) instanceof MobAlcis)             &&
                    !(worldObj.loadedEntityList.get(i) instanceof BossSnakeWhite)
            ) {
                ((Entity) worldObj.loadedEntityList.get(i)).setDead();
                entity ++;
            }
        }

        return entity;
    }

    public int booleanToInt(boolean obj) {
        if (obj) return 1;
        else return 0;
    }
}
