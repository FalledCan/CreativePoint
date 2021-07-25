package xyz.masa3mc.creativepoint;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Event implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(CreativePoint.cf.get("Point." + player.getUniqueId()) == null){
            CreativePoint.cf.set("Point." + player.getUniqueId(), 0);
        }

    }

}
