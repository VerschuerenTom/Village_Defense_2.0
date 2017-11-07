package me.TomTheDeveloper.kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.KitAPI.basekits.FreeKit;
import me.TomTheDeveloper.handlers.ChatManager;
import me.TomTheDeveloper.utils.ArmorHelper;
import me.TomTheDeveloper.utils.Util;
import me.TomTheDeveloper.utils.WeaponHelper;

/**
 * Created by Tom on 14/08/2014.
 */
public class KnightKit extends FreeKit {

    public KnightKit() {
        this.setName(ChatManager.colorMessage("Kits.Knight.Kit-Name"));
        List<String> description = Util.splitString(ChatManager.colorMessage("Kits.Knight.Kit-Description"), 40);
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
