package me.TomTheDeveloper.Kits;

import me.TomTheDeveloper.Game.GameInstance;
import me.TomTheDeveloper.GameAPI;
import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.InvasionInstance;
import me.TomTheDeveloper.KitAPI.BaseKits.PremiumKit;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.ParticleEffect;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import me.TomTheDeveloper.YoutuberInvasion;
import net.minecraft.server.v1_8_R3.Village;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tom on 18/08/2014.
 */
public class TeleporterKit extends PremiumKit implements Listener {

    private YoutuberInvasion plugin;
    private GameAPI gameAPI;

    public TeleporterKit(YoutuberInvasion plugin) {
        this.plugin = plugin;
        gameAPI = plugin.getGameAPI();
        this.setName(ChatManager.getFromLanguageConfig("Teleporter-Kit-Name", ChatManager.HIGHLIGHTED + "Teleporter"));
        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("Teleporter-Kit-Description", "" +
                "Everybody is asthonished about your teleportion. Nobody knows how to do it except you! Due to this you are able" +
                " to teleport to villagers that need help in notime!"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        if(UserManager.getUser(player.getUniqueId()).isPremium() || player.hasPermission("villagedefense.kit.teleporter"))
            return true;
        return false;
    }

    @Override
    public void giveKitItems(Player player) {
        ArmorHelper.setArmor(player, ArmorHelper.ArmorType.GOLD);
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.STONE, 10));

        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
        player.getInventory().addItem(new ItemStack(Material.SADDLE));
        ItemStack enderpealteleporter = new ItemStack(Material.ENDER_PEARL);
        List<String> teleporationlore =Util.splitString(ChatManager.getSingleMessage("Teleportion-Item-Lore","" +
                ChatColor.GRAY + "Right click to open teleportation menu!"),40);
        this.setItemNameAndLore(enderpealteleporter, ChatManager.getSingleMessage("Teleportion-Item-Name", "Teleportation Menu"), teleporationlore.toArray(new String[teleporationlore.size()]));
        player.getInventory().addItem(enderpealteleporter);
    }

    @Override
    public Material getMaterial() {
        return Material.ENDER_PEARL;
    }

    @Override
    public void reStock(Player player) {

    }

    public void OpenAndCreateTeleportationMenu(World world, Player p){
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(p);
        Inventory inventory = plugin.getServer().createInventory(null, 18, ChatManager.getSingleMessage("Teleportation-Menu-Name","Teleportation Menu"))  ;
        for(Player player: world.getPlayers() ){
            if(gameAPI.getGameInstanceManager().getGameInstance(player) != null && !UserManager.getUser(player.getUniqueId()).isFakeDead()){
                ItemStack skull = new ItemStack(397, 1, (short) 3);

                SkullMeta meta = (SkullMeta) skull.getItemMeta();
                meta.setOwner(player.getName());
                meta.setDisplayName(player.getName());
                meta.setLore(Arrays.asList(new String[]{""}));
                skull.setItemMeta(meta);
                inventory.addItem(skull);
            }
        }
        for (Villager villager :((InvasionInstance)gameInstance).getVillagers()){


                ItemStack villageritem = new ItemStack(Material.EMERALD);
                this.setItemNameAndLore(villageritem, villager.getCustomName(), new String[]{villager.getUniqueId().toString()});

                inventory.addItem(villageritem);

        }
        p.openInventory(inventory);
    }


    @EventHandler
    public void OpenInventoryRightClickEnderPearl(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(gameAPI.getGameInstanceManager().getGameInstance(e.getPlayer()) == null)
                return;
            if(!(e.getPlayer().getItemInHand() == null)){
                if(e.getPlayer().getItemInHand().hasItemMeta()){
                    if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == null)
                        return;

                    if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatManager.getSingleMessage("Teleportion-Item-Name","Teleportation Menu"))){
                        OpenAndCreateTeleportationMenu(e.getPlayer().getWorld(), e.getPlayer());
                    }
                }
            }
            if(e.getPlayer().getItemInHand().getType() == Material.ENDER_PEARL){
                e.setCancelled(true);
            }

        }
    }



    @EventHandler
    public void PlayerClickToTeleport(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (gameAPI.getGameInstanceManager().getGameInstance(p) == null)
            return;
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(p);
        if (e.getCurrentItem() == null)
            return;
        if (!e.getCurrentItem().hasItemMeta())
            return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        if (!e.getCurrentItem().getItemMeta().hasLore())
            return;
        if (e.getCurrentItem().hasItemMeta()) {
            if (e.getInventory().getName().equalsIgnoreCase(ChatManager.getSingleMessage("Teleportation-Menu-Name","Teleportation Menu"))) {
                e.setCancelled(true);
                if ((e.isLeftClick() || e.isRightClick())) {
                    if (e.getCurrentItem().getType() == Material.EMERALD) {
                        boolean villagerfound = false;
                        for (Villager  villager: ((InvasionInstance) gameInstance).getVillagers()) {
                            if (villager.getCustomName() == null) {
                                villager.remove();
                            }
                            if (villager.getCustomName().equalsIgnoreCase(e.getCurrentItem().getItemMeta().getDisplayName()) && villager.getUniqueId().toString().equalsIgnoreCase(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0)))) {
                                e.getWhoClicked().teleport(villager.getLocation());
                                if(plugin.is1_9_R1()){
                                    p.getWorld().playSound(p.getLocation(),Sound.ENTITY_ENDERMEN_TELEPORT,1,1);
                                }else {
                                   // p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                }
                                if(!plugin.is1_12_R1())
                                    ParticleEffect.PORTAL.display(1, 1, 1, 10, 30, p.getLocation(), 100);
                                else{
                                    p.getWorld().spawnParticle(Particle.PORTAL,p.getLocation(),30,1,1,1);
                                }                                villagerfound = true;
                                p.sendMessage(gameInstance.getChatManager().getMessage("Teleported-To-Villager",ChatColor.GREEN + "Teleported!"));
                                break;


                            }
                        }
                        if (!villagerfound) {

                            p.sendMessage(ChatManager.getSingleMessage("Didn't-Found-The-Villager",ChatColor.DARK_RED + "Village defense didn't found that villager! That villager is probably already dead!"));
                        }
                        villagerfound = false;
                        e.setCancelled(true);
                    } else { /*if(e.getCurrentItem().getType() == Material.SKULL_ITEM || e.getCurrentItem().getType() == Material.SKULL)*/

                            ItemMeta meta = e.getCurrentItem().getItemMeta();
                            for (Player player : gameInstance.getPlayers()) {
                                if (player.getName().equalsIgnoreCase(meta.getDisplayName()) || ChatColor.stripColor(meta.getDisplayName()).contains(player.getName())) {
                                    p.sendMessage(gameInstance.getChatManager().getMessage("Teleported-To-Player",ChatColor.GREEN + "Teleported to %PLAYER%", player));
                                    p.teleport(player);
                                    if(plugin.is1_9_R1()){
                                        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_ENDERMEN_TELEPORT,1,1);
                                    }else {
                                       // p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                    }
                                    if(!plugin.is1_12_R1())
                                        ParticleEffect.PORTAL.display(1, 1, 1, 10, 30, p.getLocation(), 100);
                                    else{
                                        p.getWorld().spawnParticle(Particle.PORTAL,p.getLocation(),30,1,1,1);
                                    }
                                    p.closeInventory();
                                    e.setCancelled(true);
                                    return;

                                }
                            }
                            p.sendMessage(ChatManager.getSingleMessage("Player-Not-Found",ChatColor.RED + "Player not found! Try again!"));


                        }

                        e.setCancelled(true);
                    }
                }
            }
        }

}
