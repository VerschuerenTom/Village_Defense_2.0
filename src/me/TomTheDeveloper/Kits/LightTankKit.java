package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.KitAPI.BaseKits.FreeKit;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import pl.Plajer.GameAPI.LanguageManager;

/**
 * Created by Tom on 18/08/2014.
 */
public class LightTankKit extends FreeKit {

    public LightTankKit() {
        setName(LanguageManager.getLanguageFile().get("Light-Tank-Kit-Name").toString());
        List<String> description = Util.splitString(LanguageManager.getLanguageFile().get("Light-Tank-Kit-Description").toString(), 40);
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
