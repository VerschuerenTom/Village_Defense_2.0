package me.TomTheDeveloper.Kits;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.InvasionInstance;
import me.TomTheDeveloper.KitAPI.BaseKits.LevelKit;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import me.TomTheDeveloper.YoutuberInvasion;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Tom on 21/07/2015.
 */
public class ZombieFinder extends LevelKit implements Listener {


    private YoutuberInvasion plugin;

    public ZombieFinder(YoutuberInvasion plugin) {
        this.plugin = plugin;
        this.setName(ChatManager.getFromLanguageConfig("Zombie-Teleporter-Kit-Name", ChatColor.DARK_GREEN + "Zombie Teleporter"));
        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("Zombie-Teleporter-Kit-Description", "Teleport those zombies to you!" +
                "Many people think this is the worst kit! I must admit they they are totally wrong!"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
        this.setLevel(1);

    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return true;
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.WOOD, 10));
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));
        ItemStack zombieteleporter = WeaponHelper.getEnchanted(new ItemStack(Material.BOOK), new Enchantment[]{Enchantment.DAMAGE_ALL}, new int[]{1});
        Util.setItemNameAndLore(zombieteleporter, ChatManager.getFromLanguageConfig("Zombie-Teleporter-Name", "ZombieTeleporter"), new String[]{ChatManager.getFromLanguageConfig("Zombie-Teleporter-Lore", "Teleport zombies to you!")});
        player.getInventory().addItem(zombieteleporter);
    }

    @Override
    public Material getMaterial() {
        return Material.FISHING_ROD;
    }

    @Override
    public void reStock(Player player) {

    }

    @EventHandler
    public void onClean(PlayerInteractEvent event) {
        if (!event.hasItem())
            return;
        if (event.getItem().getType() != Material.BOOK)
            return;
        if (!(event.getItem().hasItemMeta()))
            return;
        if (!(event.getItem().getItemMeta().hasDisplayName()))
            return;
        if (!(event.getItem().getItemMeta().getDisplayName().contains(ChatManager.getFromLanguageConfig("Zombie-Teleporter-Name", "ZombieTeleporter"))))
            return;
        if (plugin.getGameAPI().getGameInstanceManager().getGameInstance(event.getPlayer()) == null)
            return;
        if (UserManager.getUser(event.getPlayer().getUniqueId()).isSpectator()) {
            ChatManager.getFromLanguageConfig("You-Can't-Teleport-You're-Spectator", ChatColor.RED + "You can't teleport zombies to u now! You are a spectator! You'll respawn at the start of the next wave!");
            return;
        }
        InvasionInstance invasionInstance = (InvasionInstance) plugin.getGameAPI().getGameInstanceManager().getGameInstance(event.getPlayer());

        if (UserManager.getUser(event.getPlayer().getUniqueId()).getCooldown("zombie") > 0 && !UserManager.getUser(event.getPlayer().getUniqueId()).isSpectator()) {
            event.getPlayer().sendMessage(ChatManager.getMessage("Ability-Still-On-Cooldown", event.getPlayer(), "zombie"));
            return;
        }
        if (invasionInstance.getZombies() != null) {
            for (Zombie zombie : invasionInstance.getZombies()) {
                zombie.teleport(event.getPlayer());
                break;
            }
            //invasionInstance.getZombies().clear();
        } else {
            event.getPlayer().sendMessage(ChatManager.getSingleMessage("Map-is-already-empty", ChatColor.GREEN + "The map is already empty!"));
            return;
        }
        if (plugin.is1_9_R1()) {
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 1, 1);
        } else {
            //event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ZOMBIE_DEATH, 1, 1);
        }
        UserManager.getUser(event.getPlayer().getUniqueId()).setCooldown("zombie", 30);
    }
}
