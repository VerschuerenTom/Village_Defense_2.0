package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.KitAPI.BaseKits.PremiumKit;
import me.TomTheDeveloper.Permissions.PermissionsManager;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;

/**
 * Created by Tom on 19/08/2014.
 */
public class HeavyTankKit extends PremiumKit {

    public HeavyTankKit() {
        setName(ChatManager.colorMessage("Heavy-Tank-Kit-Name"));
        List<String> description = Util.splitString(ChatManager.colorMessage("Heavy-Tank-Kit-Description"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return player.hasPermission(PermissionsManager.getVIP()) || player.hasPermission(PermissionsManager.getMVP()) || player.hasPermission(PermissionsManager.getELITE()) || player.hasPermission("villagedefense.kit.heavytank");
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getEnchanted(new ItemStack(Material.STICK), new Enchantment[]{Enchantment.DURABILITY, Enchantment.DAMAGE_ALL}, new int[]{10, 2}));
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));
        player.setMaxHealth(40.0);
        player.setHealth(40.0);
        ArmorHelper.setArmor(player, ArmorHelper.ArmorType.IRON);
        player.getInventory().addItem(new ItemStack(Material.SADDLE));
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_CHESTPLATE;
    }

    @Override
    public void reStock(Player player) {

    }
}
