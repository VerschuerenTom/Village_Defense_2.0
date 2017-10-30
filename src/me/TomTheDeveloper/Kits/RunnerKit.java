package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.LevelKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;

/**
 * Created by Tom on 18/08/2014.
 */
public class RunnerKit extends LevelKit {

    public RunnerKit() {
        setLevel(10);
        setName(ChatManager.getFromLanguageConfig("Runner-Kit-Name", ChatManager.PREFIX + "Runner"));
        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("Runner-Kit-Description", "Parkour is your passion" +
                "! You are able to run faster than everybody else. You have also the ability to jump higher."), 40);
        this.setDescription(description.toArray(new String[description.size()]));
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return UserManager.getUser(player.getUniqueId()).getInt("level") >= this.getLevel() || player.isOp() || player.hasPermission("villagefense.kit.runner");
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getEnchanted(new ItemStack(Material.STICK), new Enchantment[]{
                Enchantment.KNOCKBACK, Enchantment.DAMAGE_UNDEAD, Enchantment.DURABILITY
        }, new int[]{
                2, 1, 10
        }));
        ArmorHelper.setColouredArmor(Color.BLUE, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2));
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));
    }

    @Override
    public Material getMaterial() {
        return Material.FIREWORK;
    }

    @Override
    public void reStock(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2));
    }
}
