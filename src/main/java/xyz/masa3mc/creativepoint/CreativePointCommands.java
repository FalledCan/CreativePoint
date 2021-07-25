package xyz.masa3mc.creativepoint;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CreativePointCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.translateAlternateColorCodes('&', CreativePoint.cf2.getString("prefix"));


        //正しいコマンドが送信されなかった場合
        if(args.length != 2){

                sender.sendMessage(prefix + ChatColor.RED + "/cp [player] [amount]");

        }else {

            //ポイントを与えるプレイヤー情報を取得
            Player target = Bukkit.getPlayerExact(args[0]);

            if(target == null){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',CreativePoint.cf2.getString("offline").replace("%prefix%", prefix).replace("%player%", args[0])));
                return true;
            }

            try {
                Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',CreativePoint.cf2.getString("Not-a-number").replace("%prefix%", prefix)));
                return true;
            }


            int amount = Integer.parseInt(args[1]);


            if(amount < 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',CreativePoint.cf2.getString("Not-a-number").replace("%prefix%", prefix)));
                return true;
            }
            //プレイヤーがのポイントが存在しない場合新しく作成
            if(CreativePoint.cf.get("Point." + target.getUniqueId()) == null)
                CreativePoint.cf.set("Point." + target.getUniqueId(), 0);

            //もともとのポイントを取得
            int oldPoint = CreativePoint.cf.getInt("Point." + target.getUniqueId());

            //加算
            CreativePoint.cf.set("Point." + target.getUniqueId(), oldPoint + amount);
            try {
                CreativePoint.cf.save(CreativePoint.file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String am = String.valueOf(amount);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',CreativePoint.cf2.getString("give-point-sender").replace("%prefix%", prefix).replace("%player%", target.getName()).replace("%cp%", CreativePoint.cf.getString("Point." + target.getUniqueId())).replace("%amount%", am)));
            target.sendMessage(ChatColor.translateAlternateColorCodes('&',CreativePoint.cf2.getString("give-point-target").replace("%prefix%", prefix).replace("%player%", target.getName()).replace("%cp%", CreativePoint.cf.getString("Point." + target.getUniqueId())).replace("%amount%", am)));

            Give give = new Give();
            give.onRun(target);
        }

        return true;
    }
}
