package me.TomTheDeveloper.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.TomTheDeveloper.GameAPI;

/**
 * Created by Tom on 10/07/2015.
 */
public class onJoin implements Listener {


    private GameAPI plugin;

    public onJoin(GameAPI plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(plugin.isBungeeActivated())
            return;
        for(Player player:plugin.getPlugin().getServer().getOnlinePlayers()){
            if(plugin.getGameInstanceManager().getGameInstance(player)==null)
                continue;
            player.hidePlayer(event.getPlayer());
            event.getPlayer().hidePlayer(player);
        }
    }
}
