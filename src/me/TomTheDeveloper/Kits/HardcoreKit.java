package me.TomTheDeveloper.Kits;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.LevelKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Items;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.List;

/**
 * Created by Tom on 28/07/2015.
 */
public class HardcoreKit extends LevelKit {



    public HardcoreKit() {
        setName(ChatManager.getFromLanguageConfig("Hardcore-Kit-Name", ChatManager.PREFIX + "Hardcore"));
        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("Hardcore-Kit-Description", "You'll see yourself why this is hardcore"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
        setLevel(100);
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        if(UserManager.getUser(player.getUniqueId()).getInt("level") >= this.getLevel() || player.isOp()|| player.hasPermission("villagefense.kit.hardcore"))
            return true;
        return false;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.WOOD, 10));
        ArmorHelper.setColouredArmor(Color.WHITE, player);
        player.getInventory().addItem(Items.getPotion(PotionType.INSTANT_HEAL, 2, true, 1));
        player.getInventory().addItem(new ItemStack(Material.COOKIE,10));
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
