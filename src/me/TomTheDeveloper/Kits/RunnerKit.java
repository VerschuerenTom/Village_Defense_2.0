package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.LevelKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import pl.Plajer.GameAPI.LanguageManager;

/**
 * Created by Tom on 18/08/2014.
 */
public class RunnerKit extends LevelKit {

    public RunnerKit() {
        setLevel(10);
        setName(LanguageManager.getLanguageFile().get("Runner-Kit-Name").toString());
        List<String> description = Util.splitString(LanguageManager.getLanguageFile().get("Runner-Kit-Description").toString(), 40);
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
