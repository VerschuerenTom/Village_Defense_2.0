package me.TomTheDeveloper.Kits;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.PremiumKit;
import me.TomTheDeveloper.User;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.ParticleEffect;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import me.TomTheDeveloper.YoutuberInvasion;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tom on 17/12/2015.
 */
public class BlockerKit extends PremiumKit implements Listener {

    private YoutuberInvasion plugin;
    private List<ZombieBarrier> zombiebarriers = new ArrayList<ZombieBarrier>();


    public BlockerKit(final YoutuberInvasion plugin) {
        this.plugin = plugin;
        setName(ChatManager.getFromLanguageConfig("The-Blocker-Kit-Name", ChatColor.AQUA + "Blocker"));
        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("Blocker-Kit-Description", "Hold the zombies back with your special barriers." +
                " These barriers last for 10 seconds"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                Iterator<ZombieBarrier> iterator = zombiebarriers.iterator();
                List<ZombieBarrier> removeAfter = new ArrayList<ZombieBarrier>();
                while (iterator.hasNext()) {

                    ZombieBarrier zombieBarrier = iterator.next();
                    zombieBarrier.decrementSeconds();
                    if (zombieBarrier.getSeconds() <= 0) {
                        zombieBarrier.getLocation().getBlock().setType(Material.AIR);
                        if (plugin.is1_12_R1()) {
                            ParticleEffect.FIREWORKS_SPARK.display(1, 1, 1, 0, 20, zombieBarrier.getLocation(), 255);
                        } else {
                            zombieBarrier.getLocation().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, zombieBarrier.getLocation(), 20, 1.0, 1.0, 1.0);
                        }
                        removeAfter.add(zombieBarrier);
                    }
                }
                zombiebarriers.removeAll(removeAfter);
            }
        }, 20L, 20L);
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return UserManager.getUser(player.getUniqueId()).isPremium() || player.hasPermission("villagedefense.kit.blockerkit") || player.hasPermission("villagedefense.kits.blockerkit");
    }

    @Override
    public void giveKitItems(Player player) {
        ArmorHelper.setColouredArmor(Color.RED, player);
        player.getInventory().addItem(WeaponHelper.getEnchanted(new ItemStack(Material.STONE_SWORD), new org.bukkit.enchantments.Enchantment[]{org.bukkit.enchantments.Enchantment.DURABILITY}, new int[]{10}));
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
        player.getInventory().addItem(Util.setItemNameAndLore(new ItemStack(Material.FENCE, 3),
                ChatManager.getFromLanguageConfig("Blocker-Fence-Item-Name", "Fence"),
                Util.splitString(ChatManager.getFromLanguageConfig("Blocker-Fence-Item-Lore", "Place this barrier to hold back zombies!" +
                        " These barriers last for 10 seconds"), 40).toArray(new String[Util.splitString(ChatManager.getFromLanguageConfig("Blocker-Fence-Item-Lore", "Place this barrier to hold back zombies!" +
                        " These barriers last for 10 seconds"), 40).size()])));
        player.getInventory().addItem(new ItemStack(Material.SADDLE));

    }

    @Override
    public Material getMaterial() {
        return plugin.is1_7_R4() ? Material.FENCE : Material.BARRIER;
    }

    @Override
    public void reStock(Player player) {
        PlayerInventory inventory = player.getInventory();
        player.getInventory().addItem(Util.setItemNameAndLore(new ItemStack(Material.FENCE, 3),
                ChatManager.getFromLanguageConfig("Blocker-Fence-Item-Name", "Fence"),
                Util.splitString(ChatManager.getFromLanguageConfig("Blocker-Fence-Item-Lore", "Place this barrier to hold back zombies!" +
                        " These barriers last for 10 seconds"), 40).toArray(new String[Util.splitString(ChatManager.getFromLanguageConfig("Blocker-Fence-Item-Lore", "Place this barrier to hold back zombies!" +
                        " These barriers last for 10 seconds"), 40).size()])));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBarrierPlace(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        Player player = event.getPlayer();
        if (player.getItemInHand() == null)
            return;
        if (plugin.getGameAPI().getGameInstanceManager().getGameInstance(player) == null)
            return;
        if (!player.getItemInHand().hasItemMeta())
            return;
        if (!player.getItemInHand().getItemMeta().hasDisplayName())
            return;
        if (!player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatManager.getSingleMessage("Blocker-Fence-Item-Name", "Fence")))
            return;
        Block block = null;
        for (Block blocks : player.getLastTwoTargetBlocks(null, 5)) {
            if (blocks.getType() == Material.AIR)
                block = blocks;
        }
        if (block == null) {
            event.getPlayer().sendMessage(ChatManager.getSingleMessage("Barrier-Can't-Be-Placed-Here", ChatColor.RED + "Unable to place barrier here"));
            return;
        }
        if (player.getItemInHand().getAmount() <= 1) {
            player.setItemInHand(new ItemStack(Material.AIR));
        } else {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        }
        User user = UserManager.getUser(event.getPlayer().getUniqueId());

        user.toPlayer().sendMessage(ChatManager.getSingleMessage("Barrier-Placed", ChatColor.GREEN + "Barrier placed!"));
        ZombieBarrier zombieBarrier = new ZombieBarrier();
        zombieBarrier.setUuid(user.getUuid());
        zombieBarrier.setLocation(block.getLocation());
        zombiebarriers.add(zombieBarrier);
        if (!plugin.is1_12_R1()) {
            ParticleEffect.FIREWORKS_SPARK.display(1, 1, 1, 0, 20, zombieBarrier.getLocation(), 255);
        } else {
            zombieBarrier.getLocation().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, zombieBarrier.getLocation(), 20, 1.0, 1.0, 1.0);
        }
        block.setType(Material.FENCE);
    }


    private class ZombieBarrier {

        private UUID uuid;
        private Location location;
        private int seconds;

        public ZombieBarrier() {
            seconds = 10;
        }

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        public void decrementSeconds() {
            this.seconds = seconds - 1;
        }
    }
}
