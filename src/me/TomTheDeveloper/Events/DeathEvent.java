package me.TomTheDeveloper.Events;

import me.TomTheDeveloper.YoutuberInvasion;
import org.bukkit.event.Listener;

/**
 * Created by Tom on 15/08/2014.
 */
public class DeathEvent implements Listener {

    private YoutuberInvasion plugin;


    public DeathEvent(YoutuberInvasion plugin) {
        this.plugin = plugin;
    }

   /* @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(plugin.getGameInstanceManager().getGameInstance(event.getEntity()) == null)
            return;
        if(event.getEntity().isDead())
         plugin.getGameInstanceManager().getGameInstance(event.getEntity()).teleportToStartLocation(event.getEntity());
        ((InvasionInstance) plugin.getGameInstanceManager().getGameInstance(event.getEntity())).onDeath(event);
    }

    @EventHandler
    public void onREspawn(PlayerRespawnEvent event){
        if(plugin.getGameInstanceManager().getGameInstance(event.getPlayer()) == null) {
            System.out.print("onRespawn fakes");
            return;
        }
        event.setRespawnLocation(plugin.getGameInstanceManager().getGameInstance(event.getPlayer()).getStartLocation());
    }
    */
}
