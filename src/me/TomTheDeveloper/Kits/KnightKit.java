package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.KitAPI.BaseKits.FreeKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import pl.Plajer.GameAPI.LanguageManager;

/**
 * Created by Tom on 14/08/2014.
 */
public class KnightKit extends FreeKit {

    public KnightKit() {
        this.setName(LanguageManager.getLanguageFile().get("Knight-Kit-Name").toString());
        List<String> description = Util.splitString(LanguageManager.getLanguageFile().get("Knight-Kit-Description").toString(), 40);
        this.setDescription(description.toArray(new String[description.size()]));

    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return true;
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.WOOD, 10));
        ArmorHelper.setArmor(player, ArmorHelper.ArmorType.LEATHER);
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));

    }

    @Override
    public Material getMaterial() {
        return Material.WOOD_SWORD;
    }

    @Override
    public void reStock(Player player) {

    }
}
