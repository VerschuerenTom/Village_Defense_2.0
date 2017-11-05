package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.User;
import me.TomTheDeveloper.VillageDefense;
import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.LevelKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;

/**
 * Created by Tom on 21/07/2015.
 */
public class LooterKit extends LevelKit implements Listener {


    private VillageDefense plugin;

    public LooterKit(VillageDefense plugin) {
        this.plugin = plugin;
        setName(ChatManager.colorMessage("Kits.Looter.Kit-Name"));
        List<String> description = Util.splitString(ChatManager.colorMessage("Kits.Looter.Kit-Description"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
        setLevel(8);
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return UserManager.getUser(player.getUniqueId()).getInt("level") >= this.getLevel() || player.isOp() || player.hasPermission("villagefense.kit.looter");
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.STONE, 10));
        ArmorHelper.setColouredArmor(Color.ORANGE, player);
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));

    }

    @Override
    public Material getMaterial() {
        return Material.ROTTEN_FLESH;
    }

    @Override
    public void reStock(Player player) {

    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.ZOMBIE)
            return;
        if (event.getEntity().getKiller() == null)
            return;
        Player player = event.getEntity().getKiller();
        if (plugin.getGameAPI().getGameInstanceManager().getGameInstance(player) == null)
            return;
        User user = UserManager.getUser(player.getUniqueId());
        if (user.getKit() instanceof LooterKit) {
            player.getInventory().addItem(new ItemStack(Material.ROTTEN_FLESH, 1));
        }

    }
}
