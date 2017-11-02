package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.InvasionInstance;
import me.TomTheDeveloper.VillageDefense;
import me.TomTheDeveloper.Game.GameInstance;
import me.TomTheDeveloper.KitAPI.BaseKits.PremiumKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import me.TomTheDeveloper.versions.InvasionInstance1_12_R1;
import me.TomTheDeveloper.versions.InvasionInstance1_8_R3;
import pl.Plajer.GameAPI.LanguageManager;

/**
 * Created by Tom on 18/07/2015.
 */
public class DogFriendKit extends PremiumKit {

    private VillageDefense plugin;

    public DogFriendKit(VillageDefense invasion) {
        this.plugin = invasion;
        this.setName(LanguageManager.getLanguageFile().get("Dog-Friend-Kit-Name").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2"));
        List<String> description = Util.splitString(LanguageManager.getLanguageFile().get("Dog-Friend-Kit-Description").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2"), 40);
        this.setDescription(description.toArray(new String[description.size()]));

    }


    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return player.hasPermission("minigames.vip") || player.hasPermission("minigames.mvip") || player.hasPermission("minigames.elite") || player.hasPermission("villagedefense.kit.dogfriend");
    }

    @Override
    public void giveKitItems(Player player) {

        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.STONE, 10));
        ArmorHelper.setArmor(player, ArmorHelper.ArmorType.LEATHER);
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));
        player.getInventory().addItem(new ItemStack(Material.SADDLE));
        GameInstance gameInstance = plugin.getGameAPI().getGameInstanceManager().getGameInstance(player);
        if (gameInstance == null)
            return;
        if (!(gameInstance instanceof InvasionInstance)) {
            return;
        }
        if (plugin.is1_8_R3()) {
            InvasionInstance1_8_R3 invasionInstance1_8_r3 = (InvasionInstance1_8_R3) gameInstance;
            invasionInstance1_8_r3.spawnWolf(invasionInstance1_8_r3.getStartLocation(), player);
            invasionInstance1_8_r3.spawnWolf(invasionInstance1_8_r3.getStartLocation(), player);
            invasionInstance1_8_r3.spawnWolf(invasionInstance1_8_r3.getStartLocation(), player);
        }
        if (plugin.is1_12_R1()) {
            InvasionInstance1_12_R1 invasionInstance1_8_r3 = (InvasionInstance1_12_R1) gameInstance;
            invasionInstance1_8_r3.spawnWolf(invasionInstance1_8_r3.getStartLocation(), player);
            invasionInstance1_8_r3.spawnWolf(invasionInstance1_8_r3.getStartLocation(), player);
            invasionInstance1_8_r3.spawnWolf(invasionInstance1_8_r3.getStartLocation(), player);
        }
    }

    @Override
    public Material getMaterial() {
        return Material.BONE;
    }

    @Override
    public void reStock(Player player) {
        GameInstance gameInstance = plugin.getGameAPI().getGameInstanceManager().getGameInstance(player);
        if (gameInstance == null)
            return;
        if (!(gameInstance instanceof InvasionInstance)) {
            return;
        }
        if (plugin.is1_8_R3()) {
            InvasionInstance1_8_R3 invasionInstance1_8_r3 = (InvasionInstance1_8_R3) gameInstance;
            invasionInstance1_8_r3.spawnWolf(invasionInstance1_8_r3.getStartLocation(), player);
        }
        if (plugin.is1_12_R1()) {
            InvasionInstance1_12_R1 invasionInstance1_12_R1 = (InvasionInstance1_12_R1) gameInstance;
            invasionInstance1_12_R1.spawnWolf(invasionInstance1_12_R1.getStartLocation(), player);
        }
    }
}
