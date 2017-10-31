package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.InvasionInstance;
import me.TomTheDeveloper.VillageDefense;
import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.PremiumKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.ParticleEffect;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;

/**
 * Created by Tom on 18/08/2014.
 */
public class CleanerKit extends PremiumKit implements Listener {

    private VillageDefense plugin;

    public CleanerKit(VillageDefense plugin) {
        this.plugin = plugin;
        setName(ChatManager.getFromLanguageConfig("Cleaner-Kit-Name", ChatManager.HIGHLIGHTED + "Cleaner"));
        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("Cleaner-Kit-Description", "" +
                "The cleaner has a special ability. With this ability he can make all the zombies disappear. However it takes a lot of effort to do this!"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return player.hasPermission("minigames.vip") || player.hasPermission("minigames.mvip") || player.hasPermission("minigames.elite") || player.hasPermission("villagedefense.kit.cleaner");
    }

    @Override
    public void giveKitItems(Player player) {
        ArmorHelper.setColouredArmor(Color.YELLOW, player);
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.WOOD, 10));
        ItemStack cleaneritem = new ItemStack(Material.BLAZE_ROD);
        List<String> cleanerWandLore = Util.splitString(ChatManager.getSingleMessage("Cleaner-Item-Lore", "Right click to kill all zombies!   " + "Cooldown: 1000 seconds"), 40);
        String[] cleanerWandLoreArray = cleanerWandLore.toArray(new String[cleanerWandLore.size()]);

        ItemStack itemStack = this.setItemNameAndLore(cleaneritem, ChatManager.getSingleMessage("Cleaner-Wand-Name", ChatColor.GOLD + "Cleaner Wand!"), cleanerWandLoreArray);
        player.getInventory().addItem(cleaneritem);
        player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
        player.getInventory().addItem(new ItemStack(Material.SADDLE));
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_POWDER;
    }

    @Override
    public void reStock(Player player) {

    }

    @EventHandler
    public void onClean(PlayerInteractEvent event) {
        if (!event.hasItem())
            return;
        if (event.getItem().getType() != Material.BLAZE_ROD)
            return;
        if (!(event.getItem().hasItemMeta()))
            return;
        if (!(event.getItem().getItemMeta().hasDisplayName()))
            return;
        if (!(event.getItem().getItemMeta().getDisplayName().contains(ChatManager.getSingleMessage("Cleaner-Wand-Name", "Cleaner Wand!"))))
            return;
        if (plugin.getGameAPI().getGameInstanceManager().getGameInstance(event.getPlayer()) == null)
            return;
        if (UserManager.getUser(event.getPlayer().getUniqueId()).isSpectator()) {
            ChatManager.getSingleMessage("You-Can't-Clean-You're-Spectator", ChatColor.RED + "You can't clean the map now! You are a spectator! You'll respawn at the start of the next wave!");
            return;
        }
        InvasionInstance invasionInstance = (InvasionInstance) plugin.getGameAPI().getGameInstanceManager().getGameInstance(event.getPlayer());

        if (UserManager.getUser(event.getPlayer().getUniqueId()).getCooldown("clean") > 0 && !UserManager.getUser(event.getPlayer().getUniqueId()).isSpectator()) {
            event.getPlayer().sendMessage(ChatManager.getMessage("Ability-Still-On-Cooldown", event.getPlayer(), "clean"));
            return;
        }
        if (invasionInstance.getZombies() != null) {
            for (Zombie zombie : invasionInstance.getZombies()) {
                if (!plugin.is1_12_R1()) {
                    ParticleEffect.LAVA.display(1, 1, 1, 1, 20, zombie.getLocation(), 100);
                    System.out.print("ITS SNOT");
                } else {
                    zombie.getWorld().spawnParticle(Particle.LAVA, zombie.getLocation(), 20, 1, 1, 1);
                }
                zombie.remove();

            }
            invasionInstance.getZombies().clear();
        } else {
            event.getPlayer().sendMessage(ChatManager.getSingleMessage("Map-is-already-empty", ChatColor.GREEN + "The map is already empty!"));
            return;
        }
        if (plugin.is1_9_R1()) {
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 1, 1);
        } else {
            // event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ZOMBIE_DEATH, 1, 1);
        }
        invasionInstance.getChatManager().broadcastMessage("Player-has-cleaned-the-map", ChatManager.HIGHLIGHTED + "%PLAYER%" + " has cleaned the map!", event.getPlayer());
        UserManager.getUser(event.getPlayer().getUniqueId()).setCooldown("clean", 1000);
    }
}
