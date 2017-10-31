package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.KitAPI.BaseKits.PremiumKit;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;

/**
 * Created by Tom on 28/07/2015.
 */
public class PremiumHardcoreKit extends PremiumKit {

    public PremiumHardcoreKit() {
        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("PremiumHardcore-Kit-Description", "One hit most zombies with your OP sword!" +
                " However be carefull. this kit is only for the pro's! Do not use it if u aren't a pro!"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
        setName(ChatManager.getFromLanguageConfig("PremiumHardcore-Kit-Name", ChatManager.HIGHLIGHTED + "Premium Hardcore Master"));

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
