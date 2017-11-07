package me.TomTheDeveloper.kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.InvasionInstance;
import me.TomTheDeveloper.VillageDefense;
import me.TomTheDeveloper.KitAPI.basekits.PremiumKit;
import me.TomTheDeveloper.game.GameInstance;
import me.TomTheDeveloper.handlers.ChatManager;
import me.TomTheDeveloper.permissions.PermissionsManager;
import me.TomTheDeveloper.utils.ArmorHelper;
import me.TomTheDeveloper.utils.Util;
import me.TomTheDeveloper.utils.WeaponHelper;
import me.TomTheDeveloper.versions.InvasionInstance1_12_R1;
import me.TomTheDeveloper.versions.InvasionInstance1_8_R3;

/**
 * Created by Tom on 18/07/2015.
 */
public class DogFriendKit extends PremiumKit {

    private VillageDefense plugin;

    public DogFriendKit(VillageDefense invasion) {
        this.plugin = invasion;
        this.setName(ChatManager.colorMessage("Kits.Dog-Friend.Kit-Name"));
        List<String> description = Util.splitString(ChatManager.colorMessage("Kits.Dog-Friend.Kit-Description"), 40);
        this.setDescription(description.toArray(new String[description.size()]));

    }


    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return player.hasPermission(PermissionsManager.getVIP()) || player.hasPermission(PermissionsManager.getMVP()) || player.hasPermission(PermissionsManager.getELITE()) || player.hasPermission("villagedefense.kit.dogfriend");
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
