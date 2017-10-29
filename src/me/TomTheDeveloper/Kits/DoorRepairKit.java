package me.TomTheDeveloper.Kits;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.LevelKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Tom on 19/07/2015.
 */
public class DoorRepairKit extends LevelKit {

    public DoorRepairKit() {
        this.setLevel(15);
        this.setName(ChatManager.getFromLanguageConfig("Worker-Kit-Name", ChatManager.PREFIX + "Worker"));
        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("Worker-Kit-Description", "Get each round a door and start of with 2 additional doors. This way u can replace the doors!"), 40);
        this.setDescription(description.toArray(new String[description.size()]));


    }


    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return UserManager.getUser(player.getUniqueId()).getInt("level") >= this.getLevel() || player.hasPermission("villagefense.kit.door");


    }

    @Override
    public void giveKitItems(Player player) {
        ArmorHelper.setColouredArmor(Color.PURPLE, player);
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.WOOD, 10));
        player.getInventory().addItem(WeaponHelper.getEnchantedBow(Enchantment.DURABILITY, 10));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
        player.getInventory().addItem(new ItemStack(Material.WOOD_DOOR, 2));
    }

    @Override
    public Material getMaterial() {
        return Material.WOOD_DOOR;
    }

    @Override
    public void reStock(Player player) {
        player.getInventory().addItem(new ItemStack(Material.WOOD_DOOR));
    }
}
