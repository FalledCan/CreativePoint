package xyz.masa3mc.creativepoint;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreativePointCheck implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.translateAlternateColorCodes('&', CreativePoint.cf2.getString("prefix"));

        if(sender instanceof Player){

            String s1 = CreativePoint.cf2.getString("points-you-have").replace("%prefix%",prefix);
            String s2 = s1.replace("%cp%", CreativePoint.cf.getString("Point." + ((Player) sender).getPlayer().getUniqueId()));
            String s3 = s2.replace("%player%", ((Player) sender).getPlayer().getName());
            String s4 = ChatColor.translateAlternateColorCodes('&', s3);
            sender.sendMessage(s4);
            return true;
        }

        return false;
    }
}
