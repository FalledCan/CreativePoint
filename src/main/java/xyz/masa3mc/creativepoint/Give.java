package xyz.masa3mc.creativepoint;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;


public class Give {

    private CreativePoint cp = CreativePoint.getPlugin(CreativePoint.class);

    public void onRun(Player target) {

        FileConfiguration config = cp.getConfig();

        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        File file = new File(cp.getDataFolder().getAbsolutePath(), "/PlayerData/" + target.getUniqueId() + ".yml");
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            fc.options().copyDefaults(true);
            fc.set("unchi", 0);
            try {
                fc.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < config.getConfigurationSection("Config.Command-execution").getKeys(false).size(); i++) {

            if ((i + 1) <= fc.getInt("unchi")) {
                //take a unchi
            } else {

                if (config.getInt("Config.Command-execution." + (i + 1) + ".point") <= CreativePoint.cf.getInt("Point." + target.getUniqueId())) {

                    fc.set("unchi", fc.getInt("unchi") + 1);
                    try {
                        fc.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (String command : config.getStringList("Config.Command-execution." + (i + 1) + ".run")) {

                        Bukkit.dispatchCommand(console, command.replace("%player%", target.getName()));

                    }

                } else {
                    break;
                }
            }


        }
    }
}

