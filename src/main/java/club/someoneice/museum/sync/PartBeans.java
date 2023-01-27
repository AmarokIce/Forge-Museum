package club.someoneice.museum.sync;

import club.someoneice.museum.MuseumMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;

public class PartBeans {
    public static String path = System.getProperty("user.dir") + File.separator +"museum";

    public static void init() {
        setPath();
    }

    private static void setPath() {
        File file = new File(System.getProperty("user.dir") + File.separator +"config");
        try {
            if (!file.isDirectory()) throw new FileNotFoundException("无法找到NBT目录指定！");
            else MuseumMain.Logger.info("The path is: " + file.getPath());

            MuseumMain.Logger.info("Now read the Sync path...");

            BufferedReader reader = Files.newBufferedReader(new File(System.getProperty("user.dir") + File.separator +"config" + File.separator + "MuseumSync.txt").toPath());
            path = reader.readLine();
            reader.close();

            if (path == null)
                path = System.getProperty("user.dir") + File.separator +"museum";


            MuseumMain.Logger.info("The path is:" + path);

        } catch (Exception e) {
            MuseumMain.Logger.error("Cannot set path!");
            e.printStackTrace();
        }
    }
}
