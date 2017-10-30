package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.LevelKit;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;

/**
 * Created by Tom on 19/08/2014.
 */
public class MediumTankKit extends LevelKit {


    public MediumTankKit() {
        setName(ChatManager.getFromLanguageConfig("Medium-Tank-Kit", ChatManager.PREFIX + " Medium Tank"));

        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("Medium-Tank-Kit-Description", "Start off with 6 more hearts! " +
                "Don't be afraid! You have plenty hearts left to lose!"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
        setLevel(12);

    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return UserManager.getUser(player.getUniqueId()).getInt("level") >= this.getLevel() || player.isOp() || player.hasPermission("villagefense.kit.mediumtank");
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.WOOD, 10));
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));
        player.setMaxHealth(32.0);
        player.setHealth(32.0);

    }

    @Override
    public Material getMaterial() {
        return Material.IRON_CHESTPLATE;
    }

    @Override
    public void reStock(Player player) {

    }
}
