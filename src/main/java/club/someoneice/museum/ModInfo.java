package club.someoneice.museum;

import java.util.Map;

public class ModInfo {
    public static final String MODID = "museum";
    public static final String VERSION = "0.0.4";
    public static final String ANOTHER = "Someoneice";
    public static final int MuseumWorldID = Config.readConfig();
    public static boolean isAutoClearItem = false;
    public static boolean isAutoClearEntity = false;
    public static int AutoClearTime = 20 * 60 * 120;

    public ModInfo() {
        MuseumMain.Logger.info("Loading config and dim... dim id is:" + MuseumWorldID);
    }

    public static void getInfo(Map<String, Integer> map) {
        if (map.getOrDefault("isAutoClearItem", 0) != 0) ModInfo.isAutoClearItem = true;
        if (map.getOrDefault("isAutoClearEntity", 0) != 0) ModInfo.isAutoClearItem = true;
        ModInfo.AutoClearTime = map.getOrDefault("autoTime", 20 * 60 * 120);
    }
}
