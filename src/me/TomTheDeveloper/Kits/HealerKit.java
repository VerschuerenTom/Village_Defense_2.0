package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.LevelKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Items;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;

/**
 * Created by Tom on 18/08/2014.
 */
public class HealerKit extends LevelKit {

    public HealerKit() {
        setName(ChatManager.colorMessage("Kits.Healer.Kit-Name"));
        List<String> description = Util.splitString(ChatManager.colorMessage("Kits.Healer.Kit-Description"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
        setLevel(6);
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return UserManager.getUser(player.getUniqueId()).getInt("level") >= this.getLevel() || player.isOp() || player.hasPermission("villagefense.kit.healer");
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.WOOD, 10));
        ArmorHelper.setColouredArmor(Color.WHITE, player);
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));
        player.getInventory().addItem(Items.getPotion(PotionType.INSTANT_HEAL, 2, true, 1));
        player.getInventory().addItem(Items.getPotion(PotionType.REGEN, 1, true, 1));

    }

    @Override
    public Material getMaterial() {
        return Material.RED_ROSE;
    }

    @Override
    public void reStock(Player player) {
        for (int i = 0; i < 2; i++) {
            player.getInventory().addItem(Items.getPotion(PotionType.INSTANT_HEAL, 2, true, 1));
        }
        for (int i = 0; i < 2; i++) {
            player.getInventory().addItem(Items.getPotion(PotionType.REGEN, 1, true, 1));
        }
    }
}
