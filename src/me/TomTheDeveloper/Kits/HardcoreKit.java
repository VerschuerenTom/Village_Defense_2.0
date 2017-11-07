package me.TomTheDeveloper.kits;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import me.TomTheDeveloper.KitAPI.basekits.LevelKit;
import me.TomTheDeveloper.handlers.ChatManager;
import me.TomTheDeveloper.handlers.UserManager;
import me.TomTheDeveloper.utils.ArmorHelper;
import me.TomTheDeveloper.utils.Items;
import me.TomTheDeveloper.utils.Util;
import me.TomTheDeveloper.utils.WeaponHelper;

/**
 * Created by Tom on 28/07/2015.
 */
public class HardcoreKit extends LevelKit {


    public HardcoreKit() {
        setName(ChatManager.colorMessage("Kits.Hardcore.Kit-Name"));
        List<String> description = Util.splitString(ChatManager.colorMessage("Kits.Hardcore.Kit-Description"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
        setLevel(100);
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return UserManager.getUser(player.getUniqueId()).getInt("level") >= this.getLevel() || player.isOp() || player.hasPermission("villagefense.kit.hardcore");
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.WOOD, 10));
        ArmorHelper.setColouredArmor(Color.WHITE, player);
        player.getInventory().addItem(Items.getPotion(PotionType.INSTANT_HEAL, 2, true, 1));
        player.getInventory().addItem(new ItemStack(Material.COOKIE, 10));
        player.setMaxHealth(10.0);

    }

    @Override
    public Material getMaterial() {
        return Material.SKULL_ITEM;
    }

    @Override
    public void reStock(Player player) {
        player.getInventory().addItem(Items.getPotion(PotionType.INSTANT_HEAL, 2, true, 1));


    }
}
