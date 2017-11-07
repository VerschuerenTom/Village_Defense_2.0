package me.tomthedeveloper.events;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.tomthedeveloper.GameAPI;
import me.tomthedeveloper.InvasionInstance;
import me.tomthedeveloper.User;
import me.tomthedeveloper.VillageDefense;
import me.tomthedeveloper.bungee.Bungee;
import me.tomthedeveloper.game.GameInstance;
import me.tomthedeveloper.game.GameState;
import me.tomthedeveloper.game.InstanceType;
import me.tomthedeveloper.handlers.ChatManager;
import me.tomthedeveloper.handlers.UserManager;
import me.tomthedeveloper.items.SpecialItemManager;
import me.tomthedeveloper.permissions.PermissionsManager;
import me.tomthedeveloper.shop.Shop;
import me.tomthedeveloper.stats.MySQLDatabase;
import me.tomthedeveloper.utils.Util;

/**
 * Created by Tom on 16/08/2014.
 */
public class Events implements Listener {

    private final List<EntityType> VILLAGE_ENTITIES = Arrays.asList(EntityType.PLAYER, EntityType.WOLF, EntityType.IRON_GOLEM, EntityType.VILLAGER);
    private VillageDefense plugin;
    private GameAPI gameAPI;

    public Events(VillageDefense plugin) {
        this.plugin = plugin;
        this.gameAPI = plugin.getGameAPI();
    }

    @EventHandler
    public void onItemPickup(PlayerExpChangeEvent event) {

        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
        if (gameInstance == null)
            return;
        if (gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        User user = UserManager.getUser(event.getPlayer().getUniqueId());
        event.setAmount((int) Math.ceil(event.getAmount() * 1.6));
        if (user.isFakeDead()) {
            event.setAmount(0);
            return;
        }

        if(event.getPlayer().hasPermission(PermissionsManager.getELITE())){
        	user.addInt("orbs", (int) Math.ceil(event.getAmount() * 1.5));
        	return;
        } else if(event.getPlayer().hasPermission(PermissionsManager.getMVP())) {
        	user.addInt("orbs", (int) Math.ceil(event.getAmount() * 1.0));
        	return;
        } else if(event.getPlayer().hasPermission(PermissionsManager.getVIP())) {
        	user.addInt("orbs", (int) Math.ceil(event.getAmount() * 0.5));
        	return;
        } else {
        	user.addInt("orbs", event.getAmount());
        }
    }


    @Deprecated
    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) {
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        if (UserManager.getUser(event.getPlayer().getUniqueId()).isFakeDead()) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onKitMenuItemClick(InventoryClickEvent event) {
        ItemStack inv = event.getCurrentItem();
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance((Player) event.getWhoClicked());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        if (inv == null || !inv.hasItemMeta() || !inv.getItemMeta().hasDisplayName() || inv.getType() != gameAPI.getKitMenuHandler().getMaterial() || !inv.getItemMeta().getDisplayName().equalsIgnoreCase(gameAPI.getKitMenuHandler().getItemName()))
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void KitMenuItemClick(InventoryClickEvent event) {
        ItemStack inv = event.getCursor();
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance((Player) event.getWhoClicked());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        if (inv == null || !inv.hasItemMeta() || !inv.getItemMeta().hasDisplayName() || inv.getType() != gameAPI.getKitMenuHandler().getMaterial() || !inv.getItemMeta().getDisplayName().equalsIgnoreCase(gameAPI.getKitMenuHandler().getItemName()))
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        if (UserManager.getUser(event.getPlayer().getUniqueId()).isFakeDead()) {
            event.setCancelled(true);
            return;
        }
        if (event.getItemDrop().getItemStack().getType() == Material.SADDLE) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void ExplosionCancel(EntityExplodeEvent event) {
        for (GameInstance gameInstance : gameAPI.getGameInstanceManager().getGameInstances()) {
            if (gameInstance.getStartLocation().getWorld().getName().equals(event.getLocation().getWorld().getName()) && gameInstance.getStartLocation().distance(event.getLocation()) < 300)
                event.blockList().clear();
        }
    }

    @EventHandler
    public void chunkload(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            for (GameInstance gameInstance : gameAPI.getGameInstanceManager().getGameInstances()) {
                if (entity.getWorld().getName().equals(gameInstance.getStartLocation().getWorld().getName()) && entity.getLocation().distance(gameInstance.getStartLocation()) < 300) {
                    if (VILLAGE_ENTITIES.contains(entity.getType()) || entity.getType().equals(EntityType.ZOMBIE))
                        entity.remove();
                    if (gameInstance instanceof InvasionInstance && gameInstance.getGameState() != GameState.STARTING) {
                        //((InvasionInstance) gameInstance).restoreMap();

                    }
                }
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
        if (gameInstance != null && UserManager.getUser(event.getPlayer().getUniqueId()).isSpectator())
            event.setCancelled(true);
    }


    @EventHandler
    public void onSetupClick(SetupInventoryClickEvent event) {
        String name = event.getItemStack().getItemMeta().getDisplayName();
        if (name.contains("Add villager")) {
            event.setCancelled(true);
            event.getPlayer().performCommand(event.getGameInstance().getPlugin().getGameName() + " " + event.getGameInstance().getID() + " addspawn villager");
            event.getPlayer().closeInventory();
            return;

        }
        if (name.contains("Add zombie")) {
            event.setCancelled(true);
            event.getPlayer().performCommand(event.getGameInstance().getPlugin().getGameName() + " " + event.getGameInstance().getID() + " addspawn zombie");
            event.getPlayer().closeInventory();
        }
        if (name.contains("Add doors")) {
            event.setCancelled(true);
            event.getPlayer().performCommand(event.getGameInstance().getPlugin().getGameName() + " " + event.getGameInstance().getID() + " add doors");
            event.getPlayer().closeInventory();
            return;

        }
        if (name.contains("Set the chest shop")) {
            event.setCancelled(true);
            Block targetblock;
            targetblock = event.getPlayer().getTargetBlock(null, 100);
            if (targetblock == null || targetblock.getType() != Material.CHEST) {
                event.getPlayer().sendMessage(ChatColor.RED + "Look at the chest! You are targetting something else!");
                return;
            }
            gameAPI.saveLoc("shop.location", targetblock.getLocation());
            event.getPlayer().sendMessage(ChatColor.GREEN + "Shop for chest set!");
            return;
        }
    }

    @EventHandler
    public void onEntityInteractEntity(PlayerInteractEntityEvent event) {
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        User user = UserManager.getUser(event.getPlayer().getUniqueId());
        if (user.isFakeDead()) {
            event.setCancelled(true);
            return;
        }
        if (event.getRightClicked().getType() == EntityType.VILLAGER)
            event.setCancelled(true);
        if (event.getPlayer().getItemInHand() == null) {
            Shop.openShop(event.getPlayer());
            return;
        }
        if (event.getPlayer().getItemInHand().getType() != Material.WOOD_SWORD
                && event.getPlayer().getItemInHand().getType() != Material.STONE_SWORD
                && event.getPlayer().getItemInHand().getType() != Material.IRON_SWORD
                && event.getPlayer().getItemInHand().getType() != Material.GOLD_SWORD
                && event.getPlayer().getItemInHand().getType() != Material.DIAMOND_SWORD
                && event.getPlayer().getItemInHand().getType() != Material.WOOD_AXE
                && event.getPlayer().getItemInHand().getType() != Material.STONE_AXE
                && event.getPlayer().getItemInHand().getType() != Material.IRON_AXE
                && event.getPlayer().getItemInHand().getType() != Material.GOLD_AXE
                && event.getPlayer().getItemInHand().getType() != Material.DIAMOND_AXE
                && event.getPlayer().getItemInHand().getType() != Material.SADDLE
                && event.getPlayer().getItemInHand().getType() != Material.BOW
                && event.getPlayer().getItemInHand().getType() != Material.DIAMOND_AXE
                && event.getPlayer().getItemInHand().getType() != Material.DIAMOND_SPADE
                && event.getPlayer().getItemInHand().getType() != Material.STICK
                && event.getRightClicked().getType() == EntityType.VILLAGER) {
            Shop.openShop(event.getPlayer());
            return;
        } else if (event.getPlayer().getItemInHand().getType() == Material.SADDLE) {
            if (event.getRightClicked().getType() == EntityType.IRON_GOLEM || event.getRightClicked().getType() == EntityType.VILLAGER) {
                event.getRightClicked().setPassenger(event.getPlayer());
                return;
            }
        } else if (event.getPlayer().getItemInHand().getType() != Material.WOOD_SWORD
                && event.getPlayer().getItemInHand().getType() != Material.STONE_SWORD
                && event.getPlayer().getItemInHand().getType() != Material.IRON_SWORD
                && event.getPlayer().getItemInHand().getType() != Material.GOLD_SWORD
                && event.getPlayer().getItemInHand().getType() != Material.DIAMOND_SWORD
                && event.getPlayer().getItemInHand().getType() != Material.WOOD_AXE
                && event.getPlayer().getItemInHand().getType() != Material.STONE_AXE
                && event.getPlayer().getItemInHand().getType() != Material.IRON_AXE
                && event.getPlayer().getItemInHand().getType() != Material.GOLD_AXE
                && event.getPlayer().getItemInHand().getType() != Material.DIAMOND_AXE
                && event.getPlayer().getItemInHand().getType() != Material.SADDLE
                && event.getPlayer().getItemInHand().getType() != Material.BOW
                && event.getPlayer().getItemInHand().getType() != Material.DIAMOND_AXE
                && event.getPlayer().getItemInHand().getType() != Material.DIAMOND_SPADE
                && event.getPlayer().getItemInHand().getType() != Material.STICK
                && event.getRightClicked().getType() == EntityType.IRON_GOLEM) {
            IronGolem ironGolem = (IronGolem) event.getRightClicked();
            if (ironGolem.getCustomName().contains(event.getPlayer().getName())) {
                event.getRightClicked().setPassenger(event.getPlayer());
                return;
            } else {
                event.getPlayer().sendMessage(ChatManager.PLUGINPREFIX + ChatManager.colorMessage("In-game.Messages.Cant-Ride-Others-Golem"));
            }
        } else {
            if (event.getRightClicked().getType() == EntityType.VILLAGER || event.getRightClicked().getType() == EntityType.IRON_GOLEM)
                event.getPlayer().sendMessage(ChatManager.colorMessage("In-game.Messages.Shop-Messages.Rude-Message"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void disableCommands(PlayerCommandPreprocessEvent event) {
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        if (event.getMessage().contains("leave") || event.getMessage().contains("stats")) {
            return;
        }
        if (event.getPlayer().isOp() || event.getPlayer().hasPermission(PermissionsManager.getEditGames()))
            return;
        event.setCancelled(true);
        event.getPlayer().sendMessage(ChatManager.PLUGINPREFIX + ChatManager.colorMessage("In-game.Only-Command-Ingame-Is-Leave"));
    }

    @EventHandler
    public void onDoorDrop(ItemSpawnEvent event) {
        if (event.getEntity().getItemStack().getType() == Material.WOOD_DOOR) {
            for (Entity entity : Util.getNearbyEntities(event.getLocation(), 20)) {
                if (entity.getType() == EntityType.PLAYER) {
                    if (gameAPI.getGameInstanceManager().getGameInstance((Player) entity) != null) {
                        event.getEntity().remove();
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLeave(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
            return;
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        ItemStack itemStack = event.getPlayer().getItemInHand();
        if (itemStack == null || itemStack.getItemMeta() == null || itemStack.getItemMeta().getDisplayName() == null)
            return;
        String key = SpecialItemManager.getRelatedSpecialItem(itemStack);
        if (key == null)
            return;
        if (SpecialItemManager.getRelatedSpecialItem(itemStack).equalsIgnoreCase("Leave")) {
            event.setCancelled(true);
            if (gameAPI.isBungeeActivated()) {
                Bungee.connectToHub(event.getPlayer());
            } else {
                gameInstance.leaveAttempt(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onZombieDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.ZOMBIE) {
            for (GameInstance gameInstance : gameAPI.getGameInstanceManager().getGameInstances()) {
                if (gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
                    return;
                InvasionInstance instance = (InvasionInstance) gameInstance;
                Zombie zombie = (Zombie) event.getEntity();
                if (instance.getZombies().contains(zombie)) {
                    instance.removeZombie(zombie);
                    if (event.getEntity().getKiller() == null)
                        return;
                    if (event.getEntity().getKiller().getType() == EntityType.PLAYER) {
                        Player player = event.getEntity().getKiller();

                        if (gameAPI.getGameInstanceManager().getGameInstance(player) != null)
                            plugin.getRewardsHandler().performZombieKillReward(player);
                    }
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFriendHurt(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player))
            return;
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance((Player) event.getDamager());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        User user = UserManager.getUser(event.getDamager().getUniqueId());
        if (user.isFakeDead()) {
            event.setCancelled(true);
            return;
        }
        if (!VILLAGE_ENTITIES.contains(event.getEntityType()))
            return;
        event.setCancelled(true);
    }


    @EventHandler
    public void onLobbyHurt(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER)
            return;
        Player player = (Player) event.getEntity();
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(player);
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE || gameInstance.getGameState() == GameState.INGAME)
            return;
        event.setCancelled(true);
        player.setHealth(player.getMaxHealth());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSecond(EntityDamageByEntityEvent event) {
        User user = UserManager.getUser((event.getDamager().getUniqueId()));
        if (user.isFakeDead() || user.isSpectator()) {
            event.setCancelled(true);
            return;
        }
        if (!(event.getDamager() instanceof Arrow))
            return;
        Arrow arrow = (Arrow) event.getDamager();
        if (arrow.getShooter() == null)
            return;
        if (!(arrow.getShooter() instanceof Player))
            return;
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance((Player) arrow.getShooter());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        if (user.isFakeDead() || user.isSpectator()) {
            event.setCancelled(true);
            return;
        }
        if (!VILLAGE_ENTITIES.contains(event.getEntityType()))
            return;
        event.setCancelled(true);


    }


    @EventHandler
    @Deprecated
    public void onSpectate(PlayerPickupItemEvent event) {
        if (UserManager.getUser(event.getPlayer().getUniqueId()).isSpectator())
            event.setCancelled(true);
    }


    @EventHandler
    public void onSpectate(PlayerDropItemEvent event) {
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        if (gameInstance.getGameState() != GameState.INGAME)
            event.setCancelled(true);
        if (UserManager.getUser(event.getPlayer().getUniqueId()).isFakeDead())
            event.setCancelled(true);
    }

    @EventHandler
    public void entityLeashEvent(PlayerLeashEntityEvent event) {
        if (event.getEntity() instanceof Villager) {
            ((Villager) event.getEntity()).setLeashHolder(event.getPlayer());
        }
    }

    @EventHandler
    public void onFoodLevelCHange(FoodLevelChangeEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER)
            return;
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance((Player) event.getEntity());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        if (gameInstance.getGameState() == GameState.STARTING || gameInstance.getGameState() == GameState.WAITING_FOR_PLAYERS || gameInstance.getGameState() == GameState.ENDING) {
            event.setFoodLevel(20);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onShop(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance((Player) event.getWhoClicked());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        User user = UserManager.getUser(player.getUniqueId());
        if (user.isFakeDead()) {
            event.setCancelled(true);
            return;
        }
        if (inv.getName() == null || !inv.getName().equalsIgnoreCase("Shop"))
            return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta() || !event.getCurrentItem().getItemMeta().hasLore())
            return;
        String string = event.getCurrentItem().getItemMeta().getLore().get(0);
        string = ChatColor.stripColor(string);
        if (!(string.contains(ChatManager.colorMessage("In-game.Messages.Shop-Messages.Currency-In-Shop")) || string.contains("orbs"))) {
            boolean b = false;
            for (String s : event.getCurrentItem().getItemMeta().getLore()) {
                if (string.contains(ChatManager.colorMessage("In-game.Messages.Shop-Messages.Currency-In-Shop")) || string.contains("orbs")) {
                    string = s;
                    b = true;
                    continue;
                }
            }
            if (b = false)
                return;
        }
        int price = Integer.parseInt(string.split(" ")[0]);
        if (price > UserManager.getUser(player.getUniqueId()).getInt("orbs")) {
            player.sendMessage(ChatManager.PLUGINPREFIX + ChatManager.colorMessage("In-game.Messages.Shop-Messages.Not-Enough-Orbs"));
            return;
        }
        if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains(ChatManager.colorMessage("In-game.Messages.Shop-Messages.Golem-Item-Name"))) {
                ((InvasionInstance) gameInstance).spawnGolem(gameInstance.getStartLocation(), player);
                player.sendMessage(ChatManager.PLUGINPREFIX + ChatManager.colorMessage("In-game.Messages.Golem-Spawned"));
                UserManager.getUser(player.getUniqueId()).setInt("orbs", UserManager.getUser(player.getUniqueId()).getInt("orbs") - price);
                return;

            }
            /*
             * TODO
             * Add translatable message for 'Spawn Wolf' item
             */
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Spawn Wolf")) {
                ((InvasionInstance) gameInstance).spawnWolf(gameInstance.getStartLocation(), player);
                player.sendMessage(ChatManager.PLUGINPREFIX + ChatManager.colorMessage("In-game.Messages.Wolf-Spawned"));
                UserManager.getUser(player.getUniqueId()).setInt("orbs", UserManager.getUser(player.getUniqueId()).getInt("orbs") - price);
                return;
            }
        }

        ItemStack itemStack = event.getCurrentItem().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<String>();
        Iterator iterator = lore.iterator();
        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            if (s.contains(ChatManager.colorMessage("In-game.Messages.Shop-Messages.Currency-In-Shop"))) {
                lore.remove(s);
            }
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        player.getInventory().addItem(itemStack);
        UserManager.getUser(player.getUniqueId()).setInt("orbs", UserManager.getUser(player.getUniqueId()).getInt("orbs") - price);

    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        if (gameAPI.isBungeeActivated())
            gameAPI.getGameInstanceManager().getGameInstances().get(0).teleportToLobby(event.getPlayer());
        if (event.getPlayer().getWorld().getName().contains("VD")) {
            gameAPI.getInventoryManager().loadInventory(event.getPlayer());
            event.getPlayer().teleport(gameAPI.getGameInstanceManager().getGameInstances().get(0).getEndLocation());
        }
        if (!plugin.isDatabaseActivated()) {
            List<String> temp = new ArrayList<String>();
            temp.add("gamesplayed");
            temp.add("kills");
            temp.add("deaths");
            temp.add("highestwave");
            temp.add("xp");
            temp.add("level");
            temp.add("orbs");
            for (String s : temp) {
                plugin.getFileStats().loadStat(event.getPlayer(), s);
            }
            return;
        }
        User user = UserManager.getUser(event.getPlayer().getUniqueId());

		/*        if (plugin.getMyDatabase().getSingle(new BasicDBObject().append("UUID", event.getPlayer().getUniqueId().toString())) == null) {
            plugin.getMyDatabase().insertDocument(new String[]{"UUID", "gamesplayed", "kills", "deaths", "highestwave", "exp", "level", "orbs"},
                    new Object[]{event.getPlayer().getUniqueId().toString(), 0, 0, 0, 0, 0, 0, 0});
        }

        List<String> temp = new ArrayList<String>();
        temp.add("gamesplayed");
        temp.add("kills");
        temp.add("deaths");
        temp.add("highestwave");
        temp.add("exp");
        temp.add("level");
        temp.add("orbs");
        for (String s : temp) {
            user.setInt(s, (Integer) plugin.getMyDatabase().getSingle(new BasicDBObject("UUID", event.getPlayer().getUniqueId().toString())).get(s));
        } */
        final String playername = event.getPlayer().getUniqueId().toString();
        final Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {


            @Override
            public void run() {
                boolean b = false;
                MySQLDatabase database = plugin.getMySQLDatabase();
                ResultSet resultSet = database.executeQuery("SELECT UUID from playerstats WHERE UUID='" + playername + "'");
                try {
                    if (!resultSet.next()) {
                        database.insertPlayer(playername);
                        b = true;
                    }

                    int gamesplayed = 0;
                    int zombiekills = 0;
                    int highestwave = 0;
                    int deaths = 0;
                    int xp = 0;
                    int level = 0;
                    int orbs = 0;
                    gamesplayed = database.getStat(player.getUniqueId().toString(), "gamesplayed");
                    zombiekills = database.getStat(player.getUniqueId().toString(), "kills");
                    highestwave = database.getStat(player.getUniqueId().toString(), "highestwave");
                    deaths = database.getStat(player.getUniqueId().toString(), "deaths");
                    xp = database.getStat(player.getUniqueId().toString(), "xp");
                    level = database.getStat(player.getUniqueId().toString(), "level");
                    orbs = database.getStat(player.getUniqueId().toString(), "orbs");
                    User user = UserManager.getUser(player.getUniqueId());
                    user.setInt("gamesplayed", gamesplayed);
                    user.setInt("kills", zombiekills);
                    user.setInt("highestwave", highestwave);
                    user.setInt("deaths", deaths);
                    user.setInt("xp", xp);
                    user.setInt("level", level);
                    user.setInt("orbs", orbs);
                    b = true;
                } catch (SQLException e1) {
                    System.out.print("CONNECTION FAILED FOR PLAYER " + event.getPlayer().getName());
                    //e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                if (b = false) {
                    try {
                        if (!resultSet.next()) {
                            database.insertPlayer(playername);
                            b = true;
                        }

                        int gamesplayed = 0;
                        int zombiekills = 0;
                        int highestwave = 0;
                        int deaths = 0;
                        int xp = 0;
                        int level = 0;
                        int orbs = 0;
                        gamesplayed = database.getStat(player.getUniqueId().toString(), "gamesplayed");
                        zombiekills = database.getStat(player.getUniqueId().toString(), "kills");
                        highestwave = database.getStat(player.getUniqueId().toString(), "highestwave");
                        deaths = database.getStat(player.getUniqueId().toString(), "deaths");
                        xp = database.getStat(player.getUniqueId().toString(), "xp");
                        level = database.getStat(player.getUniqueId().toString(), "level");
                        orbs = database.getStat(player.getUniqueId().toString(), "orbs");
                        User user = UserManager.getUser(player.getUniqueId());
                        user.setInt("gamesplayed", gamesplayed);
                        user.setInt("kills", zombiekills);
                        user.setInt("highestwave", highestwave);
                        user.setInt("deaths", deaths);
                        user.setInt("xp", xp);
                        user.setInt("level", level);
                        user.setInt("orbs", orbs);
                        b = true;
                    } catch (SQLException e1) {
                        System.out.print("CONNECTION FAILED TWICE FOR PLAYER " + event.getPlayer().getName());
                        //e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        });


    }


    @EventHandler
    public void onQuitSaveStats(PlayerQuitEvent event) {
        if (gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer()) != null) {
            gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer()).leaveAttempt(event.getPlayer());
        }
        final User user = UserManager.getUser(event.getPlayer().getUniqueId());

		/* List<String> temp = new ArrayList<String>();
        temp.add("gamesplayed");
        temp.add("kills");
        temp.add("deaths");
        temp.add("highestwave");
        temp.add("exp");
        temp.add("level");
        temp.add("orbs");
        for (String s : temp) {
            plugin.getMyDatabase().updateDocument(new BasicDBObject("UUID", event.getPlayer().getUniqueId().toString()), new BasicDBObject(s, user.getInt(s)));
            System.out.println("");
        }
		 */
        final Player player = event.getPlayer();

        if (plugin.isDatabaseActivated()) {

            Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    List<String> temp = new ArrayList<String>();
                    temp.add("gamesplayed");
                    temp.add("kills");
                    temp.add("deaths");
                    temp.add("highestwave");
                    temp.add("xp");
                    temp.add("level");
                    temp.add("orbs");

                    for (final String s : temp) {
                        int i;
                        try {
                            i = plugin.getMySQLDatabase().getStat(player.getUniqueId().toString(), s);
                        } catch (NullPointerException npe) {
                            i = 0;
                            System.out.print("COULDN'T GET STATS FROM PLAYER: " + player.getName());
                        }

                        if (i > user.getInt(s)) {
                            plugin.getMySQLDatabase().setStat(player.getUniqueId().toString(), s, user.getInt(s) + i);
                        } else {
                            plugin.getMySQLDatabase().setStat(player.getUniqueId().toString(), s, user.getInt(s));
                        }
                    }
                }
            });

        } else {
            List<String> temp = new ArrayList<String>();
            temp.add("gamesplayed");
            temp.add("kills");
            temp.add("deaths");
            temp.add("highestwave");
            temp.add("xp");
            temp.add("level");
            temp.add("orbs");

            for (String s : temp) {
                plugin.getFileStats().saveStat(player, s);
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onDoorPlace(BlockPlaceEvent event) {
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
        if (gameInstance == null || gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        User user = UserManager.getUser(event.getPlayer().getUniqueId());
        if (user.isSpectator()) {
            event.setCancelled(true);
            return;
        }
        if (event.getPlayer().getItemInHand() == null) {
            event.setCancelled(true);
            return;
        }
        if (!(event.getPlayer().getItemInHand().getType() == Material.WOOD_DOOR || event.getPlayer().getItemInHand().getType() == Material.WOODEN_DOOR)) {
            event.setCancelled(true);
            return;
        }
        InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());

        if (!invasionInstance.getDoorLocations().containsKey(event.getBlock().getLocation())) {
            event.setCancelled(true);
            return;
        }
        event.getPlayer().sendMessage(ChatManager.colorMessage("Kits.Worker.game-Item-Place-Message"));
    }


    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
        if (gameInstance == null)
            return;
        if (gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        User user = UserManager.getUser(event.getPlayer().getUniqueId());
        if (user.isFakeDead()) {
            event.setCancelled(true);
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onCraft(PlayerInteractEvent event) {
        GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
        if (gameInstance == null)
            return;
        if (gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
            return;
        if (event.getPlayer().getTargetBlock(null, 7).getType() == Material.WORKBENCH)
            event.setCancelled(true);
    }


    @EventHandler
    public void onRottenFleshDrop(InventoryPickupItemEvent event) {
        if (event.getInventory().getType() != InventoryType.HOPPER)
            return;
        for (Entity entity : Util.getNearbyEntities(event.getItem().getLocation(), 20)) {
            if (entity.getType() == EntityType.PLAYER) {
                if (gameAPI.getGameInstanceManager().getGameInstance((Player) entity) != null) {
                    if (event.getItem().getItemStack().getType() != Material.ROTTEN_FLESH) {
                        continue;

                    }
                    GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance((Player) entity);
                    if (gameInstance == null)
                        continue;
                    if (gameInstance.getType() != InstanceType.VILLAGE_DEFENSE)
                        continue;
                    InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(((Player) entity));
                    int start = invasionInstance.getRottenFlesh();
                    invasionInstance.addRottenFlesh(event.getItem().getItemStack().getAmount());
                    event.getItem().remove();
                    event.getInventory().clear();
                    event.getItem().getLocation().getWorld().spigot().playEffect(event.getItem().getLocation(), Effect.CLOUD, 0, 0, 2, 2, 2, 1, 50, 100);
                    int end = invasionInstance.getRottenFlesh();
                    if (invasionInstance.checkLevelUpRottenFlesh()) {
                        for (Player player : invasionInstance.getPlayers()) {
                            player.setMaxHealth(player.getMaxHealth() + 2.0);
                        }
                        for(Player player1 : gameInstance.getPlayers()) {
                        	String message = ChatManager.formatMessage(ChatManager.colorMessage("In-game.Rotten-Flesh-Level-Up"), player1);
                            player1.sendMessage(ChatManager.PLUGINPREFIX + message);
                        }
                    }

                }

            }
        }
        return;

    }


    @EventHandler
    public void onChatIngame(AsyncPlayerChatEvent event) {
        if (gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer()) == null) {
            for (GameInstance gameInstance : gameAPI.getGameInstanceManager().getGameInstances()) {
                for (Player player : gameInstance.getPlayers()) {
                    if (event.getRecipients().contains(player)) {
                        if (!plugin.isSpyChatEnabled(player))
                            event.getRecipients().remove(player);
                    }
                }
            }
            return;
        }
        if (plugin.isChatFormatEnabled()) {
            Iterator<Player> iterator = event.getRecipients().iterator();
            List<Player> remove = new ArrayList<Player>();
            while (iterator.hasNext()) {
                Player player = iterator.next();
                if (!plugin.isSpyChatEnabled(player))
                    remove.add(player);
            }
            for (Player player : remove) {
                event.getRecipients().remove(player);
            }
            remove.clear();


            GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
            for (Player player : gameInstance.getPlayers()) {
                if (!UserManager.getUser(player.getUniqueId()).isFakeDead()) {
                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + UserManager.getUser(event.getPlayer().getUniqueId()).getInt("level") +
                            ChatColor.GRAY + "]" + ChatColor.GRAY + "[" + UserManager.getUser(event.getPlayer().getUniqueId()).getKit().getName() + ChatColor.GRAY + "]" +
                            ChatColor.GRAY + " " + event.getPlayer().getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
                    System.out.print(ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + UserManager.getUser(event.getPlayer().getUniqueId()).getInt("level") +
                            ChatColor.GRAY + "]" + ChatColor.GRAY + "[" + UserManager.getUser(event.getPlayer().getUniqueId()).getKit().getName() + ChatColor.GRAY + "]" +
                            ChatColor.GRAY + " " + event.getPlayer().getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
                } else {
                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + UserManager.getUser(event.getPlayer().getUniqueId()).getInt("level") +
                            ChatColor.GRAY + "]" + ChatColor.GRAY + "[" + ChatManager.formatMessage(ChatManager.colorMessage("In-game.Dead-Tag-On-Death")) + ChatColor.GRAY + "]" +
                            ChatColor.GRAY + " " + event.getPlayer().getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
                    System.out.print(ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + UserManager.getUser(event.getPlayer().getUniqueId()).getInt("level") +
                            ChatColor.GRAY + "]" + ChatColor.GRAY + "[" + ChatManager.formatMessage(ChatManager.colorMessage("In-game.Dead-Tag-On-Death")) + ChatColor.GRAY + "]" +
                            ChatColor.GRAY + " " + event.getPlayer().getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
                }
            }
        } else {
            GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(event.getPlayer());
            event.getRecipients().clear();
            event.getRecipients().addAll(new ArrayList<Player>(gameInstance.getPlayers()));
            event.setMessage(event.getMessage().replaceAll("%KIT%", UserManager.getUser(event.getPlayer().getUniqueId()).getKit().getName()));
        }


    }

    @EventHandler
    public void onInteractEntityInteract(PlayerInteractEntityEvent event) {
        User user = UserManager.getUser(event.getPlayer().getUniqueId());
        if (user.isFakeDead() || user.isSpectator()) {
            event.setCancelled(true);
            return;
        }
    }

}
