package me.TomTheDeveloper.Kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.TomTheDeveloper.InvasionInstance;
import me.TomTheDeveloper.VillageDefense;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.LevelKit;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import pl.Plajer.GameAPI.LanguageManager;

/**
 * Created by Tom on 21/07/2015.
 */
public class ZombieFinder extends LevelKit implements Listener {


    private VillageDefense plugin;

    public ZombieFinder(VillageDefense plugin) {
        this.plugin = plugin;
        setName(LanguageManager.getLanguageFile().get("Zombie-Teleporter-Kit-Name").toString());
        List<String> description = Util.splitString(LanguageManager.getLanguageFile().get("Zombie-Teleporter-Kit-Description").toString(), 40);
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
        ItemMeta im = zombieteleporter.getItemMeta();
        im.setDisplayName(LanguageManager.getLanguageFile().get("Zombie-Teleporter-Name").toString());
        im.setLore(Arrays.asList(LanguageManager.getLanguageFile().get("Zombie-Teleporter-Lore").toString()));
        zombieteleporter.setItemMeta(im);
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
        if (!(event.getItem().getItemMeta().getDisplayName().contains(LanguageManager.getLanguageFile().get("Zombie-Teleporter-Name").toString())))
        	return;
        if (plugin.getGameAPI().getGameInstanceManager().getGameInstance(event.getPlayer()) == null)
            return;
        if (UserManager.getUser(event.getPlayer().getUniqueId()).isSpectator()) {
            LanguageManager.getLanguageFile().get("You-Can't-Teleport-You're-Spectator").toString();
            return;
        }
        InvasionInstance invasionInstance = (InvasionInstance) plugin.getGameAPI().getGameInstanceManager().getGameInstance(event.getPlayer());

        if (UserManager.getUser(event.getPlayer().getUniqueId()).getCooldown("zombie") > 0 && !UserManager.getUser(event.getPlayer().getUniqueId()).isSpectator()) {
        	String msgstring = LanguageManager.getLanguageFile().get("Ability-Still-On-Cooldown").toString();
        	msgstring = msgstring.replaceFirst("%COOLDOWN%",Long.toString( UserManager.getUser(event.getPlayer().getUniqueId()).getCooldown("zombie")));
        	event.getPlayer().sendMessage(msgstring.replaceAll("(&([a-f0-9]))", "\u00A7$2"));
            return;
        }
        if (invasionInstance.getZombies() != null) {
            for (Zombie zombie : invasionInstance.getZombies()) {
                zombie.teleport(event.getPlayer());
                break;
            }
            //invasionInstance.getZombies().clear();
        } else {
            event.getPlayer().sendMessage(LanguageManager.getLanguageFile().get("Map-is-already-empty").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2"));
            return;
        }
        if (plugin.is1_9_R1() || plugin.is1_12_R1()) {
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 1, 1);
        } else {
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.valueOf("ZOMBIE_DEATH"), 1, 1);
        }
        UserManager.getUser(event.getPlayer().getUniqueId()).setCooldown("zombie", 30);
    }
}
