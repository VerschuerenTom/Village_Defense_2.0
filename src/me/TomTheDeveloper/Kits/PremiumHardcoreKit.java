package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.KitAPI.BaseKits.PremiumKit;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import pl.Plajer.GameAPI.LanguageManager;

/**
 * Created by Tom on 28/07/2015.
 */
public class PremiumHardcoreKit extends PremiumKit {

    public PremiumHardcoreKit() {
        setName(LanguageManager.getLanguageFile().get("PremiumHardcore-Kit-Name").toString());
        List<String> description = Util.splitString(LanguageManager.getLanguageFile().get("PremiumHardcore-Kit-Description").toString(), 40);
        this.setDescription(description.toArray(new String[description.size()]));

    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return player.hasPermission("minigames.vip") || player.hasPermission("minigames.mvip") || player.hasPermission("minigames.elite") || player.hasPermission("villagedefense.kit.premiumhardcore");
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getEnchanted(new ItemStack(Material.DIAMOND_SWORD), new Enchantment[]{Enchantment.DAMAGE_ALL}, new int[]{11}));
        player.setMaxHealth(6);
        player.getInventory().addItem(new ItemStack(Material.SADDLE));

    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public void reStock(Player player) {

    }


}
