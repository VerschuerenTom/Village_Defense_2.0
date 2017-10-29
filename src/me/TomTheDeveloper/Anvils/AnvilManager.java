package me.TomTheDeveloper.Anvils;

import me.TomTheDeveloper.YoutuberInvasion;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tom on 10/02/2016.
 */
public class AnvilManager implements Listener {

    private YoutuberInvasion plugin;

    public AnvilManager(YoutuberInvasion plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAnvilCloseIngame(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        if (inventory.getType() != InventoryType.ANVIL)
            return;

        if (plugin.getGameAPI().getGameInstanceManager().getGameInstance(player) == null)
            return;

    }
    
}
