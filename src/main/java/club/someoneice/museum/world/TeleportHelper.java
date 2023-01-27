package club.someoneice.museum.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleportHelper extends Teleporter {
    public TeleportHelper(WorldServer world) {
        super(world);
    }

    @Override
    public void placeInPortal(Entity entity, double x, double y, double z, float rot) {

    }

    @Override
    public void removeStalePortalLocations(long l) {

    }
}
