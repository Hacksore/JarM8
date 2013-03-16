package org.hacksore.jarm8;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;



public class Config {
    public Map < String, String > jars;
    public String conf;
    public String confpath;
    public Map < String, Object > map;
    public String minecraft = "";
    public String mcjar;


    public Config(JarM8 m) {
        mcjar = System.getenv("APPDATA") + File.separator + ".minecraft" + File.separator + "bin" + File.separator + "minecraft.jar";
        File folder = new File(System.getenv("APPDATA") + File.separator + ".jarm8");
        File jars = new File(System.getenv("APPDATA") + File.separator + ".jarm8" + File.separator + "jars");
        this.conf = (System.getenv("APPDATA") + File.separator + ".jarm8" + File.separator + "config.json");

        if (!folder.isDirectory()) {
            folder.mkdir();
        }

        if (!jars.isDirectory()) {
            jars.mkdir();
        }

        File f = new File(this.conf);
        if (!f.isFile()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("default_config.json"), Charset.forName("UTF-8")));
            try {
                String buffer = "";
                char[] buf = new char[1024];
                int numRead = 0;
                while ((numRead = bufferedreader.read(buf)) != -1) {
                    String readData = String.valueOf(buf, 0, numRead);
                    buffer = buffer + readData;
                    buf = new char[1024];
                }
                bufferedreader.close();

                Util.file_put_contents(this.conf, buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        loadConfig();
    }

    @SuppressWarnings("unchecked")
	public void loadConfig() {
        Gson g = new Gson();
        String json = "";

        json = Util.file_get_contents(this.conf);

        this.map = ((Map < String, Object > ) g.fromJson(json, Object.class));
        this.jars = (Map < String, String > ) this.map.get("jars");
        this.minecraft = (String) this.map.get("minecraft");

        System.out.println("[JarM8] Config loaded!");
    }

    public void saveConfig() {
        Gson g = new GsonBuilder().setPrettyPrinting().create();

        this.map.put("jars", this.jars);
        this.map.put("minecraft", this.minecraft);

        String json = g.toJson(this.map);

        Util.file_put_contents(this.conf, json);
    }
}