package me.TomTheDeveloper.Anvils;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import me.TomTheDeveloper.YoutuberInvasion;

/**
 * Created by Tom on 10/02/2016.
 */
public class AnvilManager implements Listener {

    private YoutuberInvasion plugin;

    public AnvilManager(YoutuberInvasion plugin){
        this.plugin= plugin;
    }

    @EventHandler
    public void onAnvilCloseIngame(InventoryCloseEvent event){
        Inventory inventory = event.getInventory();
        Player player = (Player)event.getPlayer();
        if (inventory.getType() != InventoryType.ANVIL)
            return;

        if(plugin.getGameAPI().getGameInstanceManager().getGameInstance(player) == null)
            return;
        for(Location locations : sphere(player.getLocation(), 5, false)){
            if(locations.getBlock().getType() == Material.ANVIL)
                locations.getBlock().setType(Material.ANVIL);
        }
    }

    public Set<Location> sphere(Location location, int radius, boolean hollow){
    	Set<Location> blocks = new HashSet<Location>();
    	World world = location.getWorld();
    	int X = location.getBlockX();
    	int Y = location.getBlockY();
    	int Z = location.getBlockZ();
    	int radiusSquared = radius * radius;
    	for (int x = X - radius; x <= X + radius; x++) {
    		for (int y = Y - radius; y <= Y + radius; y++) {
    			for (int z = Z - radius; z <= Z + radius; z++) {
    				if ((X - x) * (X - x) + (Y - y) * (Y - y) + (Z - z) * (Z - z) <= radiusSquared) {
    					Location block = new Location(world, x, y, z);
    					blocks.add(block);
    				}
    			}
    		}
    	}
    	return blocks;
    }
}
