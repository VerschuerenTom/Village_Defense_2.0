package me.TomTheDeveloper.Kits;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.PremiumKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;

/**
 * Created by Tom on 18/08/2014.
 */
public class SuperArcherKit extends PremiumKit implements Listener {

    public SuperArcherKit() {
    	setName(ChatManager.colorMessage("SuperArcher-Kit-Name"));
        List<String> description = Util.splitString(ChatManager.colorMessage("SuperArcher-Kit-Description"), 40);
        this.setDescription(description.toArray(new String[description.size()]));

    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return player.hasPermission("minigames.vip") || player.hasPermission("minigames.mvip") || player.hasPermission("minigames.elite") || player.hasPermission("villagedefense.kit.superarcher");
    }

    @Override
    public void giveKitItems(Player player) {
        ArmorHelper.setColouredArmor(Color.GREEN, player);
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.WOOD, 10));
        player.getInventory().addItem(WeaponHelper.getEnchantedBow(new Enchantment[]{Enchantment.DURABILITY, Enchantment.ARROW_KNOCKBACK}, new int[]{10, 1}));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
        player.getInventory().addItem(new ItemStack(Material.SADDLE));
    }

    @Override
    public Material getMaterial() {
        return Material.ARROW;
    }

    @Override
    public void reStock(Player player) {

    }

    @EventHandler
    public void ShootArrow(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getPlayer().getItemInHand() != null) {
                if (e.getPlayer().getItemInHand().getType() == Material.BOW) {
                    if (UserManager.getUser(e.getPlayer().getUniqueId()).getKit() instanceof SuperArcherKit && !UserManager.getUser(e.getPlayer().getUniqueId()).isSpectator()) {
                        if (e.getPlayer().getInventory().contains(Material.ARROW)) {
                            Projectile pr = e.getPlayer().launchProjectile(Arrow.class);
                            pr.setVelocity(e.getPlayer().getLocation().getDirection().multiply(3));
                            e.setCancelled(true);

                            e.getPlayer().getInventory().removeItem(new ItemStack(Material.ARROW, 1));
                        }
                    }
                }
            }
        }
    }
}
