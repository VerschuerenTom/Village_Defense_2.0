package me.TomTheDeveloper.Kits;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.KitAPI.BaseKits.FreeKit;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Tom on 18/08/2014.
 */
public class LightTankKit extends FreeKit {

    public LightTankKit() {
        setName(ChatManager.getFromLanguageConfig("Light-Tank-Kit-Name",ChatColor.DARK_GREEN + " Light Tank"));
        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("Light-Tank-Kit-Description", "You are tank Junior!"), 40);
        this.setDescription(description.toArray(new String[description.size()]));

    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return true;
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.WOOD, 10));
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));
        player.setMaxHealth(26.0);
        player.setHealth(26.0);
    }

    @Override
    public Material getMaterial() {
        return Material.LEATHER_CHESTPLATE;
    }

    @Override
    public void reStock(Player player) {

    }
}
