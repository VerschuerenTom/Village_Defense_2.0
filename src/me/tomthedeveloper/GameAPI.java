package me.tomthedeveloper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import me.tomthedeveloper.kitapi.KitHandler;
import me.tomthedeveloper.kitapi.KitMenuHandler;
import me.tomthedeveloper.bungee.Bungee;
import me.tomthedeveloper.commands.InstanceCommands;
import me.tomthedeveloper.commands.SignCommands;
import me.tomthedeveloper.commands.leave;
import me.tomthedeveloper.commands.onStopCommand;
import me.tomthedeveloper.events.SetupInventoryEvents;
import me.tomthedeveloper.events.onBuild;
import me.tomthedeveloper.events.onJoin;
import me.tomthedeveloper.events.onQuit;
import me.tomthedeveloper.events.onSpectate;
import me.tomthedeveloper.game.GameInstance;
import me.tomthedeveloper.handlers.ChatManager;
import me.tomthedeveloper.handlers.ConfigurationManager;
import me.tomthedeveloper.handlers.GameInstanceManager;
import me.tomthedeveloper.handlers.InventoryManager;
import me.tomthedeveloper.handlers.SignManager;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.MinecraftKey;

/**
 * Created by Tom on 25/07/2014.
 */
public class GameAPI {

	private KitHandler kitHandler;
	private GameInstanceManager gameInstanceManager;
	private KitMenuHandler kitMenuHandler;
	private String name;
	private String abreviation;
	private boolean kitsenabled = false;
	private InventoryManager inventoryManager;
	private boolean bar = false;
	private boolean bungee;
	private boolean inventorymanagerEnabled = false;
	private  String version;
	private JavaPlugin plugin;

	public JavaPlugin getPlugin(){
		return plugin;
	}

	private  boolean needsMapRestore = false;
	private boolean allowBuilding = false;
	private static boolean restart = false;

	public static void setRestart(){
		restart = true;
	}

	public static boolean getRestart(){
		return restart;
	}

	public boolean isBarEnabled(){
		return bar;
	}

	public boolean isBungeeActivated(){
		return bungee;
	}

	public String getVersion(){
		return version;
	}

	public boolean getAllowBuilding(){
		return allowBuilding;
	}

	public void setAllowBuilding(boolean b){
		this.allowBuilding = b;
	}

	public boolean needsMapRestore(){
		return needsMapRestore;
	}

	public void setNeedsMapRestore(boolean b){
		needsMapRestore = b;
	}

	public  void onSetup(JavaPlugin plugin,CommandsInterface commandsInterface){
		this.plugin = plugin;

		version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
		onPreStart();
		ConfigurationManager.plugin = plugin;
		inventoryManager = new InventoryManager(plugin);
		signManager = new SignManager(this);
		gameInstanceManager = new GameInstanceManager();

		if(!plugin.getConfig().contains("bar"))
			plugin.getConfig().set("bar", false);
		if(!plugin.getConfig().contains("BungeeActivated"))
			plugin.getConfig().set("BungeeActivated", false);
		bungee = plugin.getConfig().getBoolean("BungeeActivated");
		if(!plugin.getConfig().contains("InventoryManager")){
			plugin.getConfig().set("InventoryManager", false);
			plugin.saveConfig();
		}
		inventorymanagerEnabled = plugin.getConfig().getBoolean("InventoryManager");
		bar = plugin.getConfig().getBoolean("bar");

		/* if(bar){
            BossBar.plugin = this;
        } */

		ConfigurationManager.plugin = plugin;
		GameInstance.plugin = this;
		/*  this.getServer().getPluginManager().registerEvents(this.getSignManager(), this);
        this.getServer().getPluginManager().registerEvents(new onBuild(this), this);
        this.getServer().getPluginManager().registerEvents(new onQuit(this), this);
        this.getServer().getPluginManager().registerEvents(new onSpectate(this), this);
        this.getServer().getPluginManager().registerEvents(new onDoubleJump(this), this);
        this.getServer().getPluginManager().registerEvents(new onChatEvent(this), this);*/

		User.plugin = this;

		this.kitHandler = new KitHandler();

		this.kitMenuHandler = new KitMenuHandler(this);
		plugin.getServer().getPluginManager().registerEvents(this.kitMenuHandler, plugin);

		plugin.getServer().getPluginManager().registerEvents(new onSpectate(this), plugin);
		// plugin.getServer().getPluginManager().registerEvents(new onDoubleJump(this), plugin);
		if(!this.getAllowBuilding()) {
			plugin.getServer().getPluginManager().registerEvents(new onBuild(this), plugin);
		}
		plugin.getServer().getPluginManager().registerEvents(new onQuit(this), plugin);
		plugin.getServer().getPluginManager().registerEvents(new SetupInventoryEvents(this), plugin);
		plugin.getServer().getPluginManager().registerEvents(new onJoin(this),plugin);

		loadSigns();

		plugin.saveConfig();
		if(plugin.getConfig().getBoolean("BungeeActivated")){

			plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");

			setupBungee();
			me.tomthedeveloper.bungee.Bungee.plugin = this;
			plugin.getServer().getPluginManager().registerEvents(new Bungee(), plugin);
		}

		if(! plugin.getConfig().contains("Disable-Leave-Command")){
			plugin.getConfig().set("Disable-Leave-Command", false);
			plugin.saveConfig();
		}
		if(! plugin.getConfig().getBoolean("Disable-Leave-Command")) {
			plugin.getCommand("leave").setExecutor(new leave(this));
		}
		plugin.getCommand(this.getGameName()).setExecutor(new InstanceCommands(this,commandsInterface));
		plugin.getCommand("addsigns").setExecutor(new SignCommands(this));
		//      this.getCommand("smartreload").setExecutor(new onReloadCommand(this));
		plugin.getCommand("smartstop").setExecutor(new onStopCommand(this));
	}





	public void registerEntity(String name, int id, Class<? extends net.minecraft.server.v1_8_R3.EntityInsentient> customClass) {
		try {

			List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
			for (Field f : net.minecraft.server.v1_8_R3.EntityTypes.class.getDeclaredFields()) {
				if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
					f.setAccessible(true);
					dataMaps.add((Map<?, ?>) f.get(null));
				}
			}

			((Map<Class<? extends net.minecraft.server.v1_8_R3.EntityInsentient>, String>) dataMaps.get(1)).put(customClass, name);
			((Map<Class<? extends net.minecraft.server.v1_8_R3.EntityInsentient>, Integer>) dataMaps.get(3)).put(customClass, id);

		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(ChatManager.ERRORPREFIX);
            Bukkit.getConsoleSender().sendMessage("§c-------------------------------------");
            Bukkit.getConsoleSender().sendMessage("§cIt seems that you've occured an error with entity registering!");
            Bukkit.getConsoleSender().sendMessage("§cDon't panic! Try to do this steps:");
            Bukkit.getConsoleSender().sendMessage("§c- check if your server version is 1.8.8 if not try to update it to 1.9 or 1.12");
            Bukkit.getConsoleSender().sendMessage("§c- contact the developer");
		}
	}

	public void register1_9_R1_Entity(String name, int id, Class<? extends net.minecraft.server.v1_9_R1.EntityInsentient> customClass) {
		try {

			List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
			for (Field f : net.minecraft.server.v1_9_R1.EntityTypes.class.getDeclaredFields()) {
				if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
					f.setAccessible(true);
					dataMaps.add((Map<?, ?>) f.get(null));
				}
			}

			((Map<Class<? extends net.minecraft.server.v1_9_R1.EntityInsentient>, String>) dataMaps.get(1)).put(customClass, name);
			((Map<Class<? extends net.minecraft.server.v1_9_R1.EntityInsentient>, Integer>) dataMaps.get(3)).put(customClass, id);

		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(ChatManager.ERRORPREFIX);
            Bukkit.getConsoleSender().sendMessage("§c-------------------------------------");
            Bukkit.getConsoleSender().sendMessage("§cIt seems that you've occured an error with entity registering!");
            Bukkit.getConsoleSender().sendMessage("§cDon't panic! Try to do this steps:");
            Bukkit.getConsoleSender().sendMessage("§c- check if your server version is 1.9 if not try to update it to 1.12");
            Bukkit.getConsoleSender().sendMessage("§c- contact the developer");
		}
	}

	@SuppressWarnings("unchecked")
	public void register1_12_R1_Entity(String name, int id, Class<? extends net.minecraft.server.v1_12_R1.EntityInsentient> customClass) {

		MinecraftKey minecraftKey = new MinecraftKey(name);
		EntityTypes.b.a(id, minecraftKey, customClass);
	}

	public static void addCustomEntity(int entityId, String entityName, Class<? extends Entity> entityClass) {

	}

	public void loadInstances(){

	}


	public void registerEntity1_7_10(String name, int id, Class<? extends net.minecraft.server.v1_7_R4.EntityInsentient> customClass) {
		try {

			List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
			for (Field f : net.minecraft.server.v1_7_R4.EntityTypes.class.getDeclaredFields()) {
				if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
					f.setAccessible(true);
					dataMaps.add((Map<?, ?>) f.get(null));
				}
			}

			((Map<Class<? extends net.minecraft.server.v1_7_R4.EntityInsentient>, String>) dataMaps.get(1)).put(customClass, name);
			((Map<Class<? extends net.minecraft.server.v1_7_R4.EntityInsentient>, Integer>) dataMaps.get(3)).put(customClass, id);

		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(ChatManager.ERRORPREFIX);
            Bukkit.getConsoleSender().sendMessage("§c-------------------------------------");
            Bukkit.getConsoleSender().sendMessage("§cIt seems that you've occured an error with entity registering!");
            Bukkit.getConsoleSender().sendMessage("§cDon't panic! Try to do this steps:");
            Bukkit.getConsoleSender().sendMessage("§c- check if your server version is 1.7.10 if not try to update it to 1.8.8 or 1.9 or 1.12");
            Bukkit.getConsoleSender().sendMessage("§c- contact the developer");
		}
	}

	public void onPreStart(){

	}
	public void enableKits(){
		kitsenabled = true;
	}

	public void disalbeKits(){
		kitsenabled = false;
	}

	public boolean areKitsEnabled(){
		return kitsenabled;
	}

	public void addExtraItemsToSetupInventory(GameInstance gameInstance, Inventory inventory){};

	public String getGameName() {
		return name;
	}

	public void setGameName(String newName) {
		name = newName;
	}


	public KitHandler getKitHandler(){
		return kitHandler;
	}


	public GameInstanceManager getGameInstanceManager() {
		return gameInstanceManager;
	}




	public KitMenuHandler getKitMenuHandler() {
		return kitMenuHandler;
	}

	private SignManager signManager;

	public SignManager getSignManager() {
		return signManager;
	}

	public InventoryManager getInventoryManager(){
		return inventoryManager;
	}

	public boolean isInventoryManagerEnabled(){
		return inventorymanagerEnabled;
	}

	private void loadSigns(){
		if(!plugin.getConfig().contains("signs")){
			saveLoc("signs.example", Bukkit.getWorlds().get(0).getSpawnLocation());
		}

		for(String path:plugin.getConfig().getConfigurationSection("signs").getKeys(false)){
			if(path.contains("example"))
				continue;
			path = "signs." + path;

			Location loc = getLocation(path);
			if(loc == null)
				System.out.print("LOCATION IS NNNNUUUUULLLL!!");
			if(loc.getBlock().getState() instanceof Sign){
				getSignManager().registerSign((Sign) loc.getBlock().getState());
			}else {
				System.out.println("Block at given location " + path + " isn't a sign!");
			}
		}
	}

	public void saveLoc(String path, Location loc) {
		String location = loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
		plugin.getConfig().set(path, location);
		plugin.saveConfig();
	}



	public Location getLocation(String path) {
		String[] loc = plugin.getConfig().getString(path).split("\\,");
		plugin.getServer().createWorld(new WorldCreator(loc[0]));
		World w = plugin.getServer().getWorld(loc[0]);
		Double x = Double.parseDouble(loc[1]);
		Double y = Double.parseDouble(loc[2]);
		Double z = Double.parseDouble(loc[3]);
		float yaw = Float.parseFloat(loc[4]);
		float pitch = Float.parseFloat(loc[5]);
		Location location = new Location(w, x, y, z, yaw, pitch);
		return location;
	}



	public WorldEditPlugin getWorldEditPlugin(){
		Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if(p instanceof WorldEditPlugin)
			return (WorldEditPlugin) p;
		return null;
	}

	public void setAbreviation(String abreviation){
		this.abreviation = abreviation;
	}

	public String getAbreviation(){
		return abreviation;
	}


	public void setupBungee() {
		Bungee.plugin = this;
		FileConfiguration fileConfiguration = ConfigurationManager.getConfig("bungee");

		if (!fileConfiguration.contains("Hub")) {
			fileConfiguration.set("Hub", "Hub");
			try {
				fileConfiguration.save(ConfigurationManager.getFile("bungee"));
			} catch (IOException e) {
				e.printStackTrace();
				Bukkit.getConsoleSender().sendMessage(ChatManager.ERRORPREFIX);
                Bukkit.getConsoleSender().sendMessage("§c-------------------------------------");
                Bukkit.getConsoleSender().sendMessage("§cIt seems that you've occured an error with bungee.yml file save!");
                Bukkit.getConsoleSender().sendMessage("§cDon't panic! Try to do this steps:");
                Bukkit.getConsoleSender().sendMessage("§c- create blank file named bungee.yml if it doesn't exists");
                Bukkit.getConsoleSender().sendMessage("§c- disable bungee option in config (Bungeecord support will not work)");
                Bukkit.getConsoleSender().sendMessage("§c- contact the developer");
			}
		}

	}








}
