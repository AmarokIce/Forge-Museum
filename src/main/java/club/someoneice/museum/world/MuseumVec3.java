package club.someoneice.museum.world;

import java.util.HashMap;
import java.util.Map;

public class MuseumVec3 {
    public static MuseumVec3 museumVec3 = new MuseumVec3(0, 0);
    public int x, z;

    public MuseumVec3(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public static Map<String, Integer> getInfoInMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("x", MuseumVec3.museumVec3.x);
        map.put("z", MuseumVec3.museumVec3.z);

        return map;
    }

    public static void putMapToInfo(Map<String, Integer> map) {
        int x = map.getOrDefault("x", 0);
        int z = map.getOrDefault("z", 0);

        MuseumVec3.museumVec3 = new MuseumVec3(x, z);
    }
}
