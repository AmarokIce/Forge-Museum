package club.someoneice.museum;

import club.someoneice.museum.util.Util;
import club.someoneice.museum.world.MuseumVec3;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private static final String path = System.getProperty("user.dir") + java.io.File.separator + "config";
    private static final File FileDir = new File(path);
    private static final File File = new File(path + java.io.File.separator + "Museum.json");

    public static int readConfig() {
        Gson gson = new Gson();
        try {
            if (!File.isFile()) {
                writeToJson();
                return 11;
            }

            BufferedReader reader = Files.newBufferedReader(File.toPath());
            StringBuilder buffer = new StringBuilder();
            String s;
            do {
                s = reader.readLine();
                buffer.append(s);
            } while (s != null);
            reader.close();

            Map<String, Integer> map = gson.fromJson(buffer.toString(), new TypeToken<Map>() {}.getType());
            MuseumVec3.putMapToInfo(map);
            ModInfo.getInfo(map);
            return map.getOrDefault("worldID", 11);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 11;
    }

    public static void writeToJson() throws IOException {
        Gson gson = new GsonBuilder().create();
        if (!FileDir.isDirectory()) FileDir.mkdirs();

        BufferedWriter writer = Files.newBufferedWriter(File.toPath());
        Map<String, Integer> map = new HashMap<>(MuseumVec3.getInfoInMap());

        map.put("world", MuseumMain.dimensionId);
        map.put("isAutoClearItem", Util.instance.booleanToInt(ModInfo.isAutoClearItem));
        map.put("isAutoClearEntity", Util.instance.booleanToInt(ModInfo.isAutoClearEntity));
        map.put("autoTime", ModInfo.AutoClearTime);

        writer.write(gson.toJson(map));
        writer.close();
    }


}
