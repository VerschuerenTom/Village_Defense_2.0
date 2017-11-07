package me.tomthedeveloper.shop;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.tomthedeveloper.VillageDefense;
import me.tomthedeveloper.menuapi.IconMenu;

/**
 * Created by Tom on 16/08/2014.
 */
public class Shop {

    public static VillageDefense plugin;
    private static IconMenu iconMenu;
    private Chest chest;

    public Shop() {
        setup();
    }

    public static void openShop(Player player) {
        if (iconMenu == null) {
            System.out.print("Set up the shop or the shopchest first please!");
            return;
        }

        iconMenu.open(player);
    }

    public static void closeShop(Player player) {
        player.closeInventory();
    }

    private void setup() {
        if (!plugin.getConfig().contains("shop.location")) {
            System.out.print("NO SHOP FOUND FOR THE GAME!!!!");
            return;
        }
        Location location = plugin.getGameAPI().getLocation("shop.location");
        if (!(location.getBlock().getState() instanceof Chest)) {
            System.out.print("Location for shop isn't a chest!");
            return;
        }

        chest = (Chest) location.getBlock().getState();
        int i = 0;
        for (ItemStack itemStack : chest.getInventory().getContents()) {
            //if (itemStack != null)
            i++;
        }
        iconMenu = new IconMenu("shop", i);
        i = 0;
        for (ItemStack itemStack : chest.getInventory().getContents()) {

            if (itemStack != null && itemStack.getType() != Material.REDSTONE_BLOCK)
                iconMenu.addOption(itemStack, i);
            i++;
        }

    }


}
