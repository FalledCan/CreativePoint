package xyz.masa3mc.creativepoint;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class CreativePoint extends JavaPlugin {

    private static CreativePoint plugin;
    public static File file;
    public static FileConfiguration cf;
    public static File file2;
    public static FileConfiguration cf2;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("有効");

        DBcontrol dBcontrol = new DBcontrol();
        dBcontrol.loadDB();

        plugin = this;
        //.yml load
        loadyml();
        saveDefaultConfig();


        getCommand("cpadd").setExecutor(new CreativePointCommands());
        getCommand("cp").setExecutor(new CreativePointCheck());

        Bukkit.getPluginManager().registerEvents(new Event(),this);



    }

    private void loadyml(){

        file = new File(plugin.getDataFolder().getAbsolutePath(), "/Point/Point.yml");
        cf = YamlConfiguration.loadConfiguration(file);

        if(!file.exists()){
            cf.options().copyDefaults(true);
            try {
                cf.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        file2 = new File(plugin.getDataFolder().getAbsolutePath(), "message.yml");
        cf2 = YamlConfiguration.loadConfiguration(file2);

        if(!file2.exists()){

            Reader defConfigStream = null;
            try {
                defConfigStream = new InputStreamReader(getResource("message.yml"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                cf2.setDefaults(defConfig);
            }
            cf2.options().copyDefaults(true);
            try {
                cf2.save(file2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static CreativePoint getPlugin(){
        return plugin;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("無効");
    }
}
