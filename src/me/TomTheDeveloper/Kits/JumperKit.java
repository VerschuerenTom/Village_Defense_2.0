package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.User;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.PremiumKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import pl.Plajer.GameAPI.LanguageManager;

/**
 * Created by Tom on 19/08/2014.
 */
public class JumperKit extends PremiumKit {

    public JumperKit() {
        setName(LanguageManager.getLanguageFile().get("The-Bunny-Kit-Name").toString());
        List<String> description = Util.splitString(LanguageManager.getLanguageFile().get("Jumper-Kit-Description").toString(), 40);
        this.setDescription(description.toArray(new String[description.size()]));

    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return player.hasPermission("minigames.vip") || player.hasPermission("minigames.mvip") || player.hasPermission("minigames.elite") || player.hasPermission("villagedefense.kit.jumper");
    }

    @Override
    public void giveKitItems(Player player) {
        User user = UserManager.getUser(player.getUniqueId());
        user.setAllowDoubleJump(true);
        ArmorHelper.setColouredArmor(Color.AQUA, player);
        player.getInventory().addItem(WeaponHelper.getEnchantedBow(new Enchantment[]{
                Enchantment.DURABILITY, Enchantment.ARROW_DAMAGE, Enchantment.ARROW_KNOCKBACK}, new int[]{10, 4, 2}));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));
        player.getInventory().addItem(new ItemStack(Material.SADDLE));
    }

    @Override
    public Material getMaterial() {
        return Material.CARROT_ITEM;
    }

    @Override
    public void reStock(Player player) {
        player.getInventory().addItem(new ItemStack(Material.ARROW, 16));
    }
}
