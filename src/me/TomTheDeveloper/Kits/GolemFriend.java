package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.InvasionInstance;
import me.TomTheDeveloper.VillageDefense;
import me.TomTheDeveloper.Game.GameInstance;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.LevelKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import me.TomTheDeveloper.versions.InvasionInstance1_12_R1;
import me.TomTheDeveloper.versions.InvasionInstance1_7_10;
import me.TomTheDeveloper.versions.InvasionInstance1_8_R3;
import pl.Plajer.GameAPI.LanguageManager;

/**
 * Created by Tom on 21/07/2015.
 */
public class GolemFriend extends LevelKit {

    private VillageDefense plugin;


    public GolemFriend(VillageDefense plugin) {
        this.plugin = plugin;
        setName(LanguageManager.getLanguageFile().get("Golem-Friend-Kit-Name").toString());
        List<String> description = Util.splitString(LanguageManager.getLanguageFile().get("Golem-Friend-Kit-Description").toString(), 40);
        this.setDescription(description.toArray(new String[description.size()]));
        setLevel(18);
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return UserManager.getUser(player.getUniqueId()).getInt("level") >= this.getLevel() || player.isOp() || player.hasPermission("villagefense.kit.golemfriend");
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.STONE, 10));
        ArmorHelper.setColouredArmor(Color.WHITE, player);
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));
        GameInstance gameInstance = plugin.getGameAPI().getGameInstanceManager().getGameInstance(player);
        if (gameInstance == null)
            return;
        if (!(gameInstance instanceof InvasionInstance)) {
            return;
        }
        if (plugin.is1_8_R3()) {
            InvasionInstance1_8_R3 invasionInstance1_8_r3 = (InvasionInstance1_8_R3) gameInstance;
            invasionInstance1_8_r3.spawnGolem(invasionInstance1_8_r3.getStartLocation(), player);
        }
        if (plugin.is1_7_R4()) {
            InvasionInstance1_7_10 invasionInstance1_8_r3 = (InvasionInstance1_7_10) gameInstance;
            invasionInstance1_8_r3.spawnGolem(invasionInstance1_8_r3.getStartLocation(), player);

        }
        if (plugin.is1_12_R1()) {
            InvasionInstance1_12_R1 invasionInstance1_8_r3 = (InvasionInstance1_12_R1) gameInstance;
            invasionInstance1_8_r3.spawnGolem(invasionInstance1_8_r3.getStartLocation(), player);

        }

    }

    @Override
    public Material getMaterial() {
        return Material.IRON_INGOT;
    }

    @Override
    public void reStock(Player player) {

    }
}
