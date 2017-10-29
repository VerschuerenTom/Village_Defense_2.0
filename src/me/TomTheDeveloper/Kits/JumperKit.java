package me.TomTheDeveloper.Kits;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.PremiumKit;
import me.TomTheDeveloper.User;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;

/**
 * Created by Tom on 19/08/2014.
 */
public class JumperKit extends PremiumKit {

    public JumperKit() {
        setName(ChatManager.getFromLanguageConfig("The-Bunny-Kit-Name",ChatColor.AQUA + "The Bunny"));
        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("Jumper-Kit-Description", "Jumping is your passion. During the past," +
                "you've developed a way to double jump! Jumping on the roofs is easy! However you could use some extra training for shooting your special bow"), 40);
        this.setDescription(description.toArray(new String[description.size()]));

    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        if(UserManager.getUser(player.getUniqueId()).isPremium() || player.hasPermission("villagedefense.kit.jumper"))
            return true;
        return false;
    }

    @Override
    public void giveKitItems(Player player) {
        User user = UserManager.getUser(player.getUniqueId());
        user.setAllowDoubleJump(true);
        ArmorHelper.setColouredArmor(Color.AQUA, player);
        player.getInventory().addItem(WeaponHelper.getEnchantedBow(new Enchantment[] {
                Enchantment.DURABILITY, Enchantment.ARROW_DAMAGE, Enchantment.ARROW_KNOCKBACK },new int[]{10, 4,2} ));
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
