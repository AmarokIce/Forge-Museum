package club.someoneice.museum.util;

import net.minecraft.util.Vec3;

public class PlayerVec {
    public Vec3 playerPos;
    public int DIMId;

    public PlayerVec(Vec3 vec3, int DIMId) {
        this.playerPos = vec3;
        this.DIMId = DIMId;
    }
}
