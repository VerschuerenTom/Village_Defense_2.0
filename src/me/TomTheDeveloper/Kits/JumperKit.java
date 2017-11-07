package me.TomTheDeveloper.kits;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.User;
import me.TomTheDeveloper.KitAPI.basekits.PremiumKit;
import me.TomTheDeveloper.handlers.ChatManager;
import me.TomTheDeveloper.handlers.UserManager;
import me.TomTheDeveloper.permissions.PermissionsManager;
import me.TomTheDeveloper.utils.ArmorHelper;
import me.TomTheDeveloper.utils.Util;
import me.TomTheDeveloper.utils.WeaponHelper;

/**
 * Created by Tom on 19/08/2014.
 */
public class JumperKit extends PremiumKit {

    public JumperKit() {
        setName(ChatManager.colorMessage("Kits.Bunny.Kit-Name"));
        List<String> description = Util.splitString(ChatManager.colorMessage("Kits.Bunny.Kit-Description"), 40);
        this.setDescription(description.toArray(new String[description.size()]));

    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return player.hasPermission(PermissionsManager.getVIP()) || player.hasPermission(PermissionsManager.getMVP()) || player.hasPermission(PermissionsManager.getELITE()) || player.hasPermission("villagedefense.kit.jumper");
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
