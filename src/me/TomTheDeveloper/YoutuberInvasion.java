package me.TomTheDeveloper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import me.TomTheDeveloper.Anvils.AnvilManager;
import me.TomTheDeveloper.Creatures.v1_12_R1.BabyZombie;
import me.TomTheDeveloper.Creatures.v1_12_R1.BreakFenceListener;
import me.TomTheDeveloper.Creatures.v1_12_R1.FastZombie;
import me.TomTheDeveloper.Creatures.v1_12_R1.GolemBuster;
import me.TomTheDeveloper.Creatures.v1_12_R1.HardZombie;
import me.TomTheDeveloper.Creatures.v1_12_R1.PlayerBuster;
import me.TomTheDeveloper.Creatures.v1_12_R1.RidableIronGolem;
import me.TomTheDeveloper.Creatures.v1_12_R1.RidableVillager;
import me.TomTheDeveloper.Creatures.v1_12_R1.WorkingWolf;
import me.TomTheDeveloper.Events.Events;
import me.TomTheDeveloper.Events.PlayerAddCommandEvent;
import me.TomTheDeveloper.Events.PlayerAddSpawnCommandEvent;
import me.TomTheDeveloper.Events.onDeath;
import me.TomTheDeveloper.Game.GameInstance;
import me.TomTheDeveloper.Game.GameState;
import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.ConfigurationManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.Kits.ArcherKit;
import me.TomTheDeveloper.Kits.BlockerKit;
import me.TomTheDeveloper.Kits.CleanerKit;
import me.TomTheDeveloper.Kits.DogFriendKit;
import me.TomTheDeveloper.Kits.DoorRepairKit;
import me.TomTheDeveloper.Kits.GolemFriend;
import me.TomTheDeveloper.Kits.HardcoreKit;
import me.TomTheDeveloper.Kits.HealerKit;
import me.TomTheDeveloper.Kits.HeavyTankKit;
import me.TomTheDeveloper.Kits.KnightKit;
import me.TomTheDeveloper.Kits.LightTankKit;
import me.TomTheDeveloper.Kits.LooterKit;
import me.TomTheDeveloper.Kits.MedicKit;
import me.TomTheDeveloper.Kits.MediumTankKit;
import me.TomTheDeveloper.Kits.PremiumHardcoreKit;
import me.TomTheDeveloper.Kits.PuncherKit;
import me.TomTheDeveloper.Kits.RunnerKit;
import me.TomTheDeveloper.Kits.ShotBowKit;
import me.TomTheDeveloper.Kits.StrenghtKit;
import me.TomTheDeveloper.Kits.SuperArcherKit;
import me.TomTheDeveloper.Kits.TeleporterKit;
import me.TomTheDeveloper.Kits.TornadoKit;
import me.TomTheDeveloper.Kits.ZombieFinder;
import me.TomTheDeveloper.Shop.Shop;
import me.TomTheDeveloper.Utils.ItemBuilder;
import me.TomTheDeveloper.Utils.ParticleEffect;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.chunks.ChunkManager;
import me.TomTheDeveloper.commands.InstanceCommands;
import me.TomTheDeveloper.items.SpecialItem;
import me.TomTheDeveloper.rewards.RewardsHandler;
import me.TomTheDeveloper.stats.FileStats;
import me.TomTheDeveloper.stats.MySQLDatabase;
import me.TomTheDeveloper.stats.VillageDefenseStats;
import me.TomTheDeveloper.versions.InvasionInstance1_12_R1;
import me.TomTheDeveloper.versions.InvasionInstance1_8_R3;
import me.TomTheDeveloper.versions.InvasionInstance1_9_R1;



/**
 * Created by Tom on 12/08/2014.
 */
public class YoutuberInvasion extends JavaPlugin implements CommandsInterface, Listener, CommandExecutor  {

   // private MyDatabase database;
    private boolean databaseActivated = false;
    private MySQLDatabase database;
    //private FileConfiguration statsConfig = null;
    private FileStats fileStats;
    private boolean chatformat = true;
    private RewardsHandler rewardsHandler;
    public static int STARTING_TIMER_TIME = 60;
    public static float MINI_ZOMBIE_SPEED;
    public static float ZOMBIE_SPEED;
    private GameAPI gameAPI = new GameAPI();

    private HashMap<UUID, Boolean> spyChatEnabled = new HashMap<UUID, Boolean>();

    public void setupMessageConfig() {
        gameAPI.loadLanguageFile();

        ChatManager.getFromLanguageConfig("Join", ChatManager.HIGHLIGHTED + "%PLAYER%" + ChatColor.GRAY + " joined the game (%PLAYERSIZE%/%MAXPLAYERS%)!");
        ChatManager.getFromLanguageConfig("Leave", ChatManager.HIGHLIGHTED + "%PLAYER% " + ChatColor.GRAY + "left the game (%PLAYERSIZE%/%MAXPLAYERS%)!");
        ChatManager.getFromLanguageConfig("Death", ChatManager.HIGHLIGHTED +"%PLAYER% " + ChatColor.GRAY + "died!");
        ChatManager.getFromLanguageConfig("Seconds-Left-Until-Game-Starts", "The game starts in " + ChatManager.HIGHLIGHTED + "%TIME%" + ChatColor.GRAY + " seconds!");
        ChatManager.getFromLanguageConfig("Waiting-For-Players", "Waiting for players... We need at least " + ChatManager.HIGHLIGHTED + "%MINPLAYERS%"+ ChatColor.GRAY +" players to start.");
        ChatManager.getFromLanguageConfig("Enough-Players-To-Start", "We now have enough players. The game is starting soon!");
        ChatManager.getFromLanguageConfig("Teleport-To-EndLocation-In-X-Seconds", "You will be teleported to the lobby in " + ChatManager.HIGHLIGHTED + "%TIME%" + ChatColor.GRAY + " seconds");
        ChatManager.getFromLanguageConfig("The-Game-Has-Started","The game has started! Defend the village against waves of zombies!");
        ChatManager.getFromLanguageConfig("Zombie-Got-Stuck-In-The-Map","It seems like the last zombie got stuck somewhere. No worries! The gods killed" +
                " him for you!");
        ChatManager.getFromLanguageConfig("Dead-Tag-On-Death", ChatColor.DARK_GRAY + "Dead");
        ChatManager.getFromLanguageConfig("Teleported-To-The-Lobby","Teleported to the lobby!");
        ChatManager.getFromLanguageConfig("You-leveled-up",ChatColor.GREEN + "You leveled up! You're now level %NUMBER%! ");
        ChatManager.getFromLanguageConfig("All-Villagers-Have-Died",ChatColor.RED + "All villagers have died! You lost the game!");
        ChatManager.getFromLanguageConfig("Reached-Wave-X", "You have reached wave " + ChatManager.HIGHLIGHTED + "%NUMBER%" + ChatColor.GRAY + "!");
        ChatManager.getFromLanguageConfig("Teleporting-To-Lobby-In-10-Seconds","You will be teleported to the lobby in " + ChatManager.HIGHLIGHTED + 10 + ChatColor.GRAY + " seconds!");
        ChatManager.getFromLanguageConfig("All-Players-Have-Died",ChatColor.RED + "All players have died!");
        ChatManager.getFromLanguageConfig("You-Feel-Refreshed",ChatColor.GREEN + "You feel refreshed!");
        ChatManager.getFromLanguageConfig("Next-Wave-Starts-In","Next wave starts in " + ChatManager.HIGHLIGHTED + "%NUMBER%" + ChatColor.GRAY + " seconds!");
        ChatManager.getFromLanguageConfig("A-Villager-Has-Died",ChatColor.RED + "A villager has died!");
        ChatManager.getFromLanguageConfig("You-Are-Spectator", ChatColor.RED + "You're now a spectator! You can fly now!");
        ChatManager.getFromLanguageConfig("You're-Back-In-Game",ChatColor.GREEN + "You're not a spectator anymore! You're back in the game!");
        ChatManager.getFromLanguageConfig("Don't-Hit-Me-With-Weapon",ChatColor.RED + "You can't hit me with a weapon. That's just rude!");
        ChatManager.getFromLanguageConfig("Only-Command-Ingame-Is-Leave",ChatColor.RED + "You have to leave the game first to perform commands. The only command that works is /leave!");
        ChatManager.getFromLanguageConfig("Need-More-Orbs-To-Buy-this",ChatColor.RED + "You need more orbs to buy this item!");
        ChatManager.getFromLanguageConfig("You-Can't-Clean-You're-Spectator", ChatColor.RED + "You can't clean the map now! You are a spectator! You'll respawn at the start of the next wave!");
        ChatManager.getFromLanguageConfig("Map-is-already-empty",ChatColor.GREEN + "The map is already empty!");
        ChatManager.getFromLanguageConfig("Player-has-cleaned-the-map", ChatManager.HIGHLIGHTED + "%PLAYER%" + " has cleaned the map!");
        ChatManager.getFromLanguageConfig("Ability-Still-On-Cooldown",ChatColor.RED + "this ability is on cooldown! Wait " + "%COOLDOWN%" + " more seconds!");
        ChatManager.getFromLanguageConfig("Teleportion-Menu-Name",ChatColor.RESET + "Teleportation Menu");
        ChatManager.getFromLanguageConfig("Teleportion-Item-Lore", ChatColor.GRAY + "Right click to open teleportation menu!");
        ChatManager.getFromLanguageConfig("Teleportation-Menu-Name","Teleporter Menu");
        ChatManager.getFromLanguageConfig("Teleported-To-Villager",ChatColor.GREEN + "Teleported!");
        ChatManager.getFromLanguageConfig("Didn't-Found-The-Villager",ChatColor.DARK_RED + "Village defense didn't found that villager! That villager is probably already dead!");
        ChatManager.getFromLanguageConfig("Teleported-To-Player",ChatColor.GREEN + "Teleported to %PLAYER%");
        ChatManager.getFromLanguageConfig("Player-Not-Found",ChatColor.RED + "Player not found! Try again!");
        ChatManager.getFromLanguageConfig("Cleaner-Wand-Name",ChatColor.GOLD + "Cleaner Wand!");
        ChatManager.getFromLanguageConfig("Cleaner-Item-Lore","Right click to kill all zombies!   "+ "Cooldown: 400 seconds");
        ChatManager.getFromLanguageConfig("STATS-AboveLine",ChatColor.BOLD + "-----YOUR STATS----- ");
        ChatManager.getFromLanguageConfig("STATS-Kills",ChatColor.GREEN + "Kills: " +ChatColor.YELLOW);
        ChatManager.getFromLanguageConfig("STATS-Deaths",ChatColor.GREEN + "Deaths: " +ChatColor.YELLOW);
        ChatManager.getFromLanguageConfig("STATS-Games-Played",ChatColor.GREEN + "Games played: " +ChatColor.YELLOW);
        ChatManager.getFromLanguageConfig("STATS-Hihgest-Wave",ChatColor.GREEN + "Highest wave: " +ChatColor.YELLOW);
        ChatManager.getFromLanguageConfig("STATS-Level",ChatColor.GREEN + "Level: " +ChatColor.YELLOW );
        ChatManager.getFromLanguageConfig("STATS-Exp",ChatColor.GREEN + "Exp: " +ChatColor.YELLOW);
        ChatManager.getFromLanguageConfig("STATS-Next-Level-Exp",ChatColor.GREEN + "Next Level Exp " + ChatColor.YELLOW);
        ChatManager.getFromLanguageConfig("STATS-UnderLinen",ChatColor.BOLD + "--------------------");
        ChatManager.getFromLanguageConfig("SCOREBOARD-Rotten-Flesh", ChatColor.DARK_GREEN + "Rotten Flesh:");
        ChatManager.getFromLanguageConfig("SCOREBOARD-Villagers", ChatColor.GREEN + "Villagers:");
        ChatManager.getFromLanguageConfig("SCOREBOARD-Zombies", ChatColor.RED + "Zombies:");
        ChatManager.getFromLanguageConfig("SCOREBOARD-Players-Left", ChatColor.GRAY + "Players Left:");
        ChatManager.getFromLanguageConfig("SCOREBOARD-Villagers", ChatColor.GREEN + "Villagers:");
        ChatManager.getFromLanguageConfig("SCOREBOARD-Starting-In", ChatColor.AQUA + "Starting In:");
        ChatManager.getFromLanguageConfig("SCOREBOARD-Min-Players", ChatColor.AQUA + "Min Players:");
        ChatManager.getFromLanguageConfig("SCOREBOARD-Players", ChatColor.AQUA + "Players:");
        ChatManager.getFromLanguageConfig("SCOREBOARD-Orbs", ChatColor.AQUA + "Orbs:");
        ChatManager.getFromLanguageConfig("SCOREBOARD-Next-Wave-In", ChatColor.GRAY + "Next wave in:");
        ChatManager.getFromLanguageConfig("Wave-Started", "Wave " + ChatManager.HIGHLIGHTED + "%NUMBER%" + ChatColor.GRAY + " started!");
        ChatManager.getFromLanguageConfig("SCOREBOARD-Header", ChatManager.PREFIX + "Village Defense");
        ChatManager.getFromLanguageConfig("RottenFleshLevelUp",ChatManager.HIGHLIGHTED + "The gods were happy with the rottenflesh!" +
                ChatManager.HIGHLIGHTED + " There for they gave you an extra heart!");
        ChatManager.getFromLanguageConfig("Spawn-Golem", "Spawn Golem");
        ChatManager.getFromLanguageConfig("Golem-Spawned",ChatColor.GREEN + "Golem spawned in the village!");
        ChatManager.getFromLanguageConfig("Wolf-Spawned", ChatColor.GREEN + "Wolf spawned in the village!");
        ChatManager.getFromLanguageConfig("Wolf-Spawned",ChatColor.GREEN + "Wolf spawned in the village!");
        ChatManager.getFromLanguageConfig("orbs-In-Shop", "orbs");
        ChatManager.getFromLanguageConfig("Dog-Friend-Kit-Name",ChatManager.HIGHLIGHTED + "Dog Friend");
        ChatManager.getFromLanguageConfig("Dog-Friend-Kit-Description", "Start off with three dogs and get one extra dog every wave!!");
        ChatManager.getFromLanguageConfig("DEAD-SCREEN",ChatColor.RED +  "You died!");
        ChatManager.getFromLanguageConfig("Died-Respawn-In-Next-Wave", ChatColor.YELLOW.toString() +  ChatColor.BOLD + "DON'T LEAVE!" + ChatColor.GREEN + "You'll respawn next once the wave ends!");
        ChatManager.getFromLanguageConfig("Barrier-Placed", ChatColor.GREEN + "Barrier placed!");
        ChatManager.getFromLanguageConfig("Door-Placed",ChatColor.GREEN + "Door placed!");
        ChatManager.getFromLanguageConfig("KitChosenButNotUnlockedMessage", ChatColor.RED + "You haven't unlocked " + ChatColor.AQUA + "%KIT%" + ChatColor.RED + " yet!");
        ChatManager.getFromLanguageConfig("KitChosenMessage", ChatColor.GREEN + "You have chosen: " + ChatColor.AQUA + "%KIT%" + ChatColor.GREEN + " !");
    }

    public void onPreStart() {
        gameAPI.setAbreviation("vd");





    }


    private String version;


     public boolean is1_8_R3(){
        if(getVersion().equalsIgnoreCase("v1_8_R3"))
            return true;
        return false;
    }

    public boolean is1_12_R1(){
        if(getVersion().equalsIgnoreCase("v1_12_R1"))
            return true;
        return false;
    }


    public boolean is1_8_R4(){
        if(getVersion().equalsIgnoreCase("v1_8_R4"))
            return true;
        return false;
    }

    public boolean is1_7_R4(){
        if(getVersion().equalsIgnoreCase("v1_7_R4"))
            return true;
        return false;
    }

    public String getVersion(){
        return version;
    }

    public GameAPI getGameAPI(){
        return gameAPI;
    }

    @Override
    public void onEnable() {


        gameAPI.setGameName("VillageDefense");
        gameAPI.setAbreviation("vd");
        gameAPI.enableKits();
        gameAPI.setAllowBuilding(true);
        InvasionInstance.youtuberInvasion = this;
        gameAPI.onSetup(this, this);
        this.getCommand(gameAPI.getGameName()).setExecutor(new InstanceCommands(gameAPI,this));
        this.setupMessageConfig();
       // this.onSetup();
       if(!this.getConfig().contains("DatabaseActivated"))
           this.getConfig().set("DatabaseActivated", false);
        this.saveConfig();
        if(!this.getConfig().contains("Starting-Waiting-Time")){
            this.getConfig().set("Starting-Waiting-Time",60);
            this.saveConfig();
        }
        STARTING_TIMER_TIME = this.getConfig().getInt("Starting-Waiting-Time");
        if(!this.getConfig().contains("Mini-Zombie-Speed")){
            this.getConfig().set("Mini-Zombie-Speed",2.0);
            this.saveConfig();
        }
        MINI_ZOMBIE_SPEED =(float) this.getConfig().getDouble("Mini-Zombie-Speed");
        if(!this.getConfig().contains("Zombie-Speed")){
            this.getConfig().set("Zombie-Speed",0.4);
            this.saveConfig();
        }
        ZOMBIE_SPEED =(float) this.getConfig().getDouble("Zombie-Speed");
        databaseActivated = this.getConfig().getBoolean("DatabaseActivated");
        if(databaseActivated)
            this.database = new MySQLDatabase(this);
        else{
           fileStats = new FileStats(this);
        }
        version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        if(this.getVersion().equalsIgnoreCase("v1_8_R3")) {
            gameAPI.registerEntity("Zombie", 54, me.TomTheDeveloper.Creatures.Youtuber.class);
            gameAPI.registerEntity("Zombie", 54, me.TomTheDeveloper.Creatures.FastZombie.class);
            gameAPI.registerEntity("Zombie", 54, me.TomTheDeveloper.Creatures.BabyZombie.class);
            gameAPI.registerEntity("Zombie", 54, me.TomTheDeveloper.Creatures.PlayerBuster.class);
            gameAPI.registerEntity("Zombie", 54, me.TomTheDeveloper.Creatures.GolemBuster.class);
            gameAPI.registerEntity("Zombie", 54, me.TomTheDeveloper.Creatures.HardZombie.class);
            gameAPI.registerEntity("Villager", 120, me.TomTheDeveloper.Creatures.RidableVillager.class);
            gameAPI.registerEntity("VillagerGolem", 99, me.TomTheDeveloper.Creatures.RidableIronGolem.class);
            gameAPI.registerEntity("Zombie", 54, me.TomTheDeveloper.Creatures.TankerZombie.class);
            gameAPI.registerEntity("Wolf", 95, me.TomTheDeveloper.Creatures.WorkingWolf.class);
        }
        if(this.getVersion().equalsIgnoreCase("v1_7_R4")) {
            gameAPI.registerEntity1_7_10("Zombie", 54, me.TomTheDeveloper.Creatures.v1_7_R4.Youtuber.class);
            gameAPI.registerEntity1_7_10("Zombie", 54, me.TomTheDeveloper.Creatures.v1_7_R4.FastZombie.class);
            gameAPI.registerEntity1_7_10("Zombie", 54, me.TomTheDeveloper.Creatures.v1_7_R4.BabyZombie.class);
            gameAPI.registerEntity1_7_10("Zombie", 54, me.TomTheDeveloper.Creatures.v1_7_R4.PlayerBuster.class);
            gameAPI.registerEntity1_7_10("Zombie", 54, me.TomTheDeveloper.Creatures.v1_7_R4.GolemBuster.class);
            gameAPI.registerEntity1_7_10("Zombie", 54, me.TomTheDeveloper.Creatures.v1_7_R4.HardZombie.class);
            gameAPI.registerEntity1_7_10("Villager", 120, me.TomTheDeveloper.Creatures.v1_7_R4.RidableVillager.class);
            gameAPI.registerEntity1_7_10("VillagerGolem", 99, me.TomTheDeveloper.Creatures.v1_7_R4.RidableIronGolem.class);
            gameAPI.registerEntity1_7_10("Wolf", 95, me.TomTheDeveloper.Creatures.v1_7_R4.WorkingWolf.class);
        }

        if(this.getVersion().equalsIgnoreCase("v1_9_R1")){
            gameAPI.register1_9_R1_Entity("Zombie",54, me.TomTheDeveloper.Creatures.v1_9_R1.FastZombie.class);
            gameAPI.register1_9_R1_Entity("Zombie",54, me.TomTheDeveloper.Creatures.v1_9_R1.BabyZombie.class);
            gameAPI.register1_9_R1_Entity("Zombie",54, me.TomTheDeveloper.Creatures.v1_9_R1.GolemBuster.class);
            gameAPI.register1_9_R1_Entity("Zombie",54, me.TomTheDeveloper.Creatures.v1_9_R1.HardZombie.class);
            gameAPI.register1_9_R1_Entity("Zombie",54, me.TomTheDeveloper.Creatures.v1_9_R1.PlayerBuster.class);
            gameAPI.register1_9_R1_Entity("Wolf",95, me.TomTheDeveloper.Creatures.v1_9_R1.WorkingWolf.class);
            gameAPI.register1_9_R1_Entity("VillagerGolem",99, me.TomTheDeveloper.Creatures.v1_9_R1.RidableIronGolem.class);
            gameAPI.register1_9_R1_Entity("Villager",120, me.TomTheDeveloper.Creatures.v1_9_R1.RidableVillager.class);

        }
        if(this.getVersion().equalsIgnoreCase("v1_12_R1")){
            NMSUtils.registerEntity(this,"FastZombie",NMSUtils.Type.ZOMBIE,FastZombie.class ,false);
            NMSUtils.registerEntity(this, "GolemBuster", NMSUtils.Type.ZOMBIE,GolemBuster.class ,false);
            NMSUtils.registerEntity(this, "HardZombie", NMSUtils.Type.ZOMBIE,HardZombie.class ,false);
            NMSUtils.registerEntity(this, "PlayerBuster",NMSUtils.Type.ZOMBIE,PlayerBuster.class ,false);
            NMSUtils.registerEntity(this, "BabyZombie",NMSUtils.Type.ZOMBIE,BabyZombie.class ,false);
            NMSUtils.registerEntity(this, "IronGolem", NMSUtils.Type.IRON_GOLEM,RidableIronGolem.class ,false);
            NMSUtils.registerEntity(this, "WorkingWolf", NMSUtils.Type.WOLF, WorkingWolf.class,false);
            NMSUtils.registerEntity(this, "RidableVillager", NMSUtils.Type.VILLAGER, RidableVillager.class,false);

            /*gameAPI.register1_12_R1_Entity("Zombie",54, me.TomTheDeveloper.Creatures.v1_12_R1.BabyZombie.class);
            gameAPI.register1_12_R1_Entity("Zombie",54, me.TomTheDeveloper.Creatures.v1_12_R1.GolemBuster.class);
            gameAPI.register1_12_R1_Entity("Zombie",54, me.TomTheDeveloper.Creatures.v1_12_R1.HardZombie.class);
            gameAPI.register1_12_R1_Entity("Zombie",54, me.TomTheDeveloper.Creatures.v1_12_R1.PlayerBuster.class);
            gameAPI.register1_12_R1_Entity("Wolf",95, me.TomTheDeveloper.Creatures.v1_12_R1.WorkingWolf.class);
            gameAPI.register1_12_R1_Entity("VillagerGolem",99, me.TomTheDeveloper.Creatures.v1_12_R1.RidableIronGolem.class);
            gameAPI.register1_12_R1_Entity("Villager",120, me.TomTheDeveloper.Creatures.v1_12_R1.RidableVillager.class); */

        }

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new onDeath(this), this);
        this.getServer().getPluginManager().registerEvents(new Events(this), this);
        this.getServer().getPluginManager().registerEvents(new AnvilManager(this),this);

        this.getCommand("setshopchest").setExecutor(new ChestCommand(this));
        this.getCommand("setprice").setExecutor(this);
        this.getCommand("stats").setExecutor(new StatsCommand());
        Shop.plugin = this;
        new Shop();
        this.getServer().getPluginManager().registerEvents(ChunkManager.getInstance(),this);

        BreakFenceListener listener = new BreakFenceListener();
        listener.runTaskTimer(this, 1L, 20L);

        KnightKit knightkit = new KnightKit();
        gameAPI.getKitHandler().registerKit(knightkit);
        LightTankKit lightTankKit = new LightTankKit();
        gameAPI.getKitHandler().registerKit(lightTankKit);
        ZombieFinder zombieFinderKit = new ZombieFinder(this);
        gameAPI.getKitHandler().registerKit(zombieFinderKit);
        this.getServer().getPluginManager().registerEvents(zombieFinderKit,this);
        ArcherKit archerKit = new ArcherKit();
        gameAPI.getKitHandler().registerKit(archerKit);


        PuncherKit puncherKit = new PuncherKit();
        gameAPI.getKitHandler().registerKit(puncherKit);

        HealerKit healerkit = new HealerKit();
        gameAPI.getKitHandler().registerKit(healerkit);
        LooterKit looterKit = new LooterKit(this);
        gameAPI.getKitHandler().registerKit(looterKit);
        this.getServer().getPluginManager().registerEvents(looterKit,this);
        this.getServer().getPluginManager().registerEvents(new SuperArcherKit(), this);
       RunnerKit runnerKit = new RunnerKit();
        gameAPI.getKitHandler().registerKit(runnerKit);
        MediumTankKit mediumTankKit = new MediumTankKit();
        gameAPI.getKitHandler().registerKit(mediumTankKit);
        DoorRepairKit doorRepairKit = new DoorRepairKit();
        gameAPI.getKitHandler().registerKit(doorRepairKit);
        GolemFriend golemFriendKit = new GolemFriend(this);
        gameAPI.getKitHandler().registerKit(golemFriendKit);
        StrenghtKit strenghtKit = new StrenghtKit();
        gameAPI.getKitHandler().registerKit(strenghtKit);
        HardcoreKit hardcoreKit = new HardcoreKit();
        gameAPI.getKitHandler().registerKit(hardcoreKit);

        SuperArcherKit superArcherKit = new SuperArcherKit();
        gameAPI.getKitHandler().registerKit(superArcherKit);
        CleanerKit cleanerKit = new CleanerKit(this);
        gameAPI.getKitHandler().registerKit(cleanerKit);
        this.getServer().getPluginManager().registerEvents(cleanerKit, this);
        TeleporterKit teleporterKit = new TeleporterKit(this);
        gameAPI.getKitHandler().registerKit(teleporterKit);
        this.getServer().getPluginManager().registerEvents(teleporterKit, this);
       // JumperKit jumperkit = new JumperKit();
        //gameAPI.getKitHandler().registerKit(jumperkit);
        HeavyTankKit heavyTankKit = new HeavyTankKit();
        gameAPI.getKitHandler().registerKit(heavyTankKit);
        ShotBowKit shotBowKit = new ShotBowKit();
        gameAPI.getKitHandler().registerKit(shotBowKit);
        this.getServer().getPluginManager().registerEvents(shotBowKit, this);

        DogFriendKit dogFriendKit = new DogFriendKit(this);
        gameAPI.getKitHandler().registerKit(dogFriendKit);
        PremiumHardcoreKit premiumHardcoreKit = new PremiumHardcoreKit();
        gameAPI.getKitHandler().registerKit(premiumHardcoreKit);

            TornadoKit tornadoKit = new TornadoKit(this);
        gameAPI.getKitHandler().registerKit(tornadoKit);
            this.getServer().getPluginManager().registerEvents(tornadoKit, this);

        BlockerKit blockerKit = new BlockerKit(this);
        gameAPI.getKitHandler().registerKit(blockerKit);
        this.getServer().getPluginManager().registerEvents(blockerKit,this);
        MedicKit medicKit = new MedicKit(this);
        gameAPI.getKitHandler().registerKit(medicKit);

       /* NakedKit nakedKit = new NakedKit();
        getKitHandler().registerKit(nakedKit);
        this.getServer().getPluginManager().registerEvents(nakedKit, this); */
        rewardsHandler = new RewardsHandler(this);
        gameAPI.getKitHandler().setDefaultKit(knightkit);
        gameAPI.getKitMenuHandler().setMaterial(Material.NETHER_STAR);
        gameAPI.getKitMenuHandler().setItemName(ChatManager.getFromLanguageConfig("Kit-Menu-Item-Name","Kit Menu"));
        gameAPI.getKitMenuHandler().setMenuName(ChatManager.getFromLanguageConfig("Kit Menu-Title","Kit Menu"));
        gameAPI.getKitMenuHandler().setDescription(new String[]{ChatManager.getFromLanguageConfig("Open-Kit-Menu","Open Kit Menu")});

        SpecialItem.loadAll();
        loadInstances();
        setupZombieSpawns();
        //database = new MyDatabase();
        FileConfiguration config = ConfigurationManager.getConfig("Bungee");
        if(!config.contains("ShutdownWhenGameEnds")){
            config.set("ShutdownWhenGameEnds", false);
            try {
                ConfigurationManager.getConfig("Bungee").save(ConfigurationManager.getFile("Bungee"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!getConfig().contains("ChatFormat-Enabled")){
            getConfig().set("ChatFormat-Enabled",true);
            saveConfig();
        }



        chatformat = getConfig().getBoolean("ChatFormat-Enabled");

        loadStatsForPlayersOnline();
        VillageDefenseStats.plugin = this;
        addExtraItemsToSetupInventory();
    }


    public RewardsHandler getRewardsHandler(){
        return rewardsHandler;
    }

    public boolean isChatFormatEnabled(){
        return chatformat;
    }

    @Override
    public boolean checkPlayerCommands(Player player,Command command,String s,String[] strings){
        if(strings.length == 2 && strings[0].equals("join")){
            for(GameInstance gameInstance:gameAPI.getGameInstanceManager().getGameInstances()){
                if(strings[1].equalsIgnoreCase(gameInstance.getID())) {
                    gameInstance.joinAttempt(player);
                    return true;
                }

            }
            player.sendMessage(ChatManager.getSingleMessage("No-Arena-Like-That", ChatColor.RED + "No arena with that ID!"));
            return true;
        }
        if(strings.length==1 && strings[0].equals("leave")){
            GameInstance gameInstance = gameAPI.getGameInstanceManager().getGameInstance(player);
            if(gameInstance!= null){
                gameInstance.leaveAttempt(player);
            }
            return true;
        }
        if(strings.length == 1 && strings[0].equalsIgnoreCase("spychat") && player.hasPermission("villagedefense.spychat")){
            if(!this.spyChatEnabled.containsKey(player.getUniqueId()) ||
                    this.spyChatEnabled.get(player.getUniqueId()) == false) {
                player.sendMessage(ChatColor.GREEN + "SpyChat Enabled!");
                this.spyChatEnabled.put(player.getUniqueId(),true);
                return true;
            }else{
                this.spyChatEnabled.put(player.getUniqueId(),false);
                player.sendMessage(ChatColor.GREEN + "SpyChat Disabled!");
                return true;

            }
        }
        return false;
    }

    @SuppressWarnings("unused")
	@Override
    public boolean checkSpecialCommands(Player player,Command command, String s, String[] strings){
        if(strings.length == 0){
            player.sendMessage(ChatColor.GOLD + "----------------{VillageDefense Commands}----------");
            player.sendMessage( "   ");
            player.sendMessage(ChatColor.GREEN + "Setup the game:");
            player.sendMessage( "   ");

            player.sendMessage(ChatColor.AQUA + "/villagedefense create <ARENAID>: " + ChatColor.GRAY + "Create an arena!");
            player.sendMessage(ChatColor.AQUA + "/villagedefense <ARENAID> edit: " + ChatColor.GRAY + "Opens the menu to edit the arena!");
            player.sendMessage(ChatColor.AQUA + "/villagedefense <ARENAID> addspawn zombie: " + ChatColor.GRAY + "Adds a zombiespawn. Zombiespawns do not represent the amount of zombies that will spawn!");
            player.sendMessage(ChatColor.AQUA + "/villagedefense <ARENAID> addspawn villager: " + ChatColor.GRAY + "Adds a villagerspawn. Villagerspawns do not represent the amount of villagers that will spawn!");
            player.sendMessage(ChatColor.AQUA + "/villagedefense <ARENAID> add doors: " + ChatColor.GRAY + "Select whole the map with world edit. Then perfrom this command. The plugin will filter out all the doors!");

            player.sendMessage(ChatColor.AQUA + "/addsigns: " + ChatColor.GRAY + "Select signs with World Edit. Then perform this command. The plugin will filter out the signs.");

            player.sendMessage( "   ");
            player.sendMessage(ChatColor.GREEN + "Manage the game:");
            player.sendMessage( "   ");

            player.sendMessage(ChatColor.AQUA + "/villagedefense admin: " + ChatColor.GRAY + "Shows all the admin commands");
            player.sendMessage(ChatColor.AQUA + "/villagedefense reload: " + ChatColor.GRAY + "Reloads the arenas");

            player.sendMessage(ChatColor.GOLD + "-------------------------------------------------");
            return true;
        }
        if(strings.length == 1 && strings[0].equalsIgnoreCase("reload")){
            this.loadInstances();
            player.sendMessage("Instances reloaded!");
        }

        if(strings.length == 1 && strings[0].equalsIgnoreCase("spychat")){
            if(!this.spyChatEnabled.containsKey(player.getUniqueId()) ||
                    this.spyChatEnabled.get(player.getUniqueId()) == false) {
                player.sendMessage(ChatColor.GREEN + "SpyChat Enabled!");
                this.spyChatEnabled.put(player.getUniqueId(),true);
                return true;
            }else{
                this.spyChatEnabled.put(player.getUniqueId(),false);
                player.sendMessage(ChatColor.GREEN + "SpyChat Disabled!");
                return true;
            }
        }
        if(strings.length == 1 &&strings[0].equals("admin")){
            player.sendMessage(ChatManager.HIGHLIGHTED + "--------{Ingame Admin Commands}-----------");
            player.sendMessage(ChatManager.PREFIX + "/villagedefense clear zombies" + ChatColor.GRAY + ": Clears the zombies in the arena");
            player.sendMessage(ChatManager.PREFIX + "/villagedefense clear villagers" + ChatColor.GRAY + ": Clears the villagers in the arena");
            player.sendMessage(ChatManager.PREFIX + "/villagedefense clear golems" + ChatColor.GRAY + ": Clears the golems in the arena");
            player.sendMessage(ChatManager.PREFIX + "/villagedefense forcestart" + ChatColor.GRAY + ": ForceStarts the arena");
            player.sendMessage(ChatManager.PREFIX + "/villagedefense respawn" + ChatColor.GRAY + ": Respawns you if u are dead");
            player.sendMessage(ChatManager.PREFIX + "/villagedefense respawn <PLAYER>" + ChatColor.GRAY + ": Respawns the named if he is dead");
            player.sendMessage(ChatManager.PREFIX + "/villagedefense set wave <NUMBER>" + ChatColor.GRAY + ": Sets the number from a wave");
            player.sendMessage(ChatManager.PREFIX + "/villagedefense stop" + ChatColor.GRAY + ": Stops the arena");
            player.sendMessage(ChatManager.PREFIX + "/villagedefense add orbs <amount>" + ChatColor.GRAY + ": Gives u the given amount of orbs");
            player.sendMessage(ChatManager.PREFIX + "/villagedefense add orbs <amount> <player>" + ChatColor.GRAY + ": Gives the named player the given amount of orbs");

            return true;
        }

        if(strings.length == 3 &&strings[0].equals("add") && strings[1].equals("orbs")){
            if(NumberUtils.isNumber(strings[2])){
                if(gameAPI.getGameInstanceManager().getGameInstance(player) == null)
                    return true;
                InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(player);
                User user = UserManager.getUser(player.getUniqueId());
                user.setInt("orbs", user.getInt("orbs") + Integer.parseInt(strings[2]));
                return true;
            }else{
                player.sendMessage(ChatColor.RED + "Wrong usage. Do /villagedefense add orbs <amount>");
                return true;
            }
        }
        if(strings.length == 4 &&strings[0].equals("add") && strings[1].equals("orbs")){
            if(NumberUtils.isNumber(strings[2])){
                if(gameAPI.getGameInstanceManager().getGameInstance(player) == null)
                    return true;
                InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(player);
                for(Player getplayer:invasionInstance.getPlayers()){
                    if(getplayer.getName().equals(strings[3])){
                        User user = UserManager.getUser(getplayer.getUniqueId());
                        user.setInt("orbs", user.getInt("orbs") + Integer.parseInt(strings[2]));
                        player.sendMessage(ChatColor.GREEN + "Orbs add to that player!");
                        return true;
                    }
                }
                player.sendMessage(ChatColor.RED + "PLayer not found!");
                return true;
            }else{
                player.sendMessage(ChatColor.RED + "Wrong usage. Do /villagedefense add orbs <amount> <Player");
                return true;
            }

        }
        if(strings.length == 1 &&strings[0].equalsIgnoreCase("stop")){

            if(gameAPI.getGameInstanceManager().getGameInstance(player) == null)
                return true;
            InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(player);
            invasionInstance.stopGame();

        }
        if(strings.length == 2 && strings[0].equalsIgnoreCase("clear")){
            if(strings[1].equalsIgnoreCase("zombies")){
                if(gameAPI.getGameInstanceManager().getGameInstance(player) == null)
                    return true;
                InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(player);
                if(invasionInstance.getZombies() != null){
                    for(Zombie zombie: invasionInstance.getZombies()){
                        ParticleEffect.LAVA.display(1,1,1,1,20,zombie.getLocation(),100 );
                        zombie.remove();

                    }
                    invasionInstance.getZombies().clear();
                }else{
                    player.sendMessage(ChatManager.getSingleMessage("Map-is-already-empty",ChatColor.GREEN + "The map is already empty!"));
                    return true;
                }

                if(this.is1_9_R1()){
                    player.playSound(player.getLocation(),Sound.ENTITY_ZOMBIE_DEATH,1,1);
                }else {
                  //  player.playSound(player.getLocation(), Sound.ZOMBIE_DEATH, 1, 1);
                }                invasionInstance.getChatManager().broadcastMessage("Admin-Removed-Zombies", ChatManager.HIGHLIGHTED + "%PLAYER%" + " has cleared the zombies!", player);
                return true;
            }
            if(strings[1].equalsIgnoreCase("villagers")){
                if(gameAPI.getGameInstanceManager().getGameInstance(player) == null)
                    return false;
                InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(player);
                if(invasionInstance.getVillagers() != null){
                    for(Villager zombie: invasionInstance.getVillagers()){
                        ParticleEffect.LAVA.display(1,1,1,1,20,zombie.getLocation(),100 );
                        zombie.remove();

                    }
                    invasionInstance.getVillagers().clear();
                }else{
                    player.sendMessage(ChatManager.getSingleMessage("Map-is-already-empty",ChatColor.GREEN + "The map is already empty!"));
                    return true;
                }
                if(this.is1_9_R1()){
                    player.playSound(player.getLocation(),Sound.ENTITY_ZOMBIE_DEATH,1,1);
                }else {
                  //  player.playSound(player.getLocation(), Sound.ZOMBIE_DEATH, 1, 1);
                }                invasionInstance.getChatManager().broadcastMessage("Admin-Removed-Villagers", ChatManager.HIGHLIGHTED + "%PLAYER%" + " has removed all the villagers!", player);
                return true;
            }
            if(strings[1].equalsIgnoreCase("golems")){
                if(gameAPI.getGameInstanceManager().getGameInstance(player) == null)
                    return false;
                InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(player);
                if(invasionInstance.getIronGolems() != null){
                    for(IronGolem zombie: invasionInstance.getIronGolems()){
                        ParticleEffect.LAVA.display(1,1,1,1,20,zombie.getLocation(),100 );
                        zombie.remove();

                    }
                    invasionInstance.getIronGolems().clear();

                }else{
                    player.sendMessage(ChatManager.getSingleMessage("Map-is-already-empty",ChatColor.GREEN + "The map is already empty!"));
                    return true;
                }
                if(this.is1_9_R1()){
                    player.playSound(player.getLocation(),Sound.ENTITY_ZOMBIE_DEATH,1,1);
                }else {
                   // player.playSound(player.getLocation(), Sound.ZOMBIE_DEATH, 1, 1);
                }
                invasionInstance.getChatManager().broadcastMessage("Admin-Removed-Golems", ChatManager.HIGHLIGHTED + "%PLAYER%" + " has removed all the golems!", player);

            }
        }
        if( strings.length == 3&&strings[0].equalsIgnoreCase("set") && strings[1].equalsIgnoreCase("wave") ){
            if(gameAPI.getGameInstanceManager().getGameInstance(player) == null)
                return false;
            InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(player);
            if(NumberUtils.isNumber(strings[2])){
                invasionInstance.setWave(Integer.parseInt(strings[2])-1);
                invasionInstance.endWave();
                invasionInstance.getChatManager().broadcastMessage("Admin-Changed-Wave",ChatManager.HIGHLIGHTED + "Admin changed the wave to %NUMBER%", invasionInstance.getWave());
                if(invasionInstance.getZombies() != null){
                    for(Zombie zombie: invasionInstance.getZombies()){
                        ParticleEffect.LAVA.display(1,1,1,1,20,zombie.getLocation(),100 );
                        zombie.setHealth(0.0);

                    }
                    invasionInstance.getZombies().clear();
                }else{
                    player.sendMessage(ChatManager.getSingleMessage("Map-is-already-empty",ChatColor.GREEN + "The map is already empty!"));

                }
                if(this.is1_9_R1()){
                    player.playSound(player.getLocation(),Sound.ENTITY_ZOMBIE_DEATH,1,1);
                }else {
                   // player.playSound(player.getLocation(), Sound.ZOMBIE_DEATH, 1, 1);
                }
                invasionInstance.getChatManager().broadcastMessage("Admin-Removed-Zombies", ChatManager.HIGHLIGHTED + "%PLAYER%" + " has cleared the zombies!", player);

                return true;
            }else{
                player.sendMessage(ChatColor.RED + "Wave needs to be number! Do /villagedefense set wave <number>");
                return true;
            }

        }
        if( strings.length ==1&&strings[0].equalsIgnoreCase("forcestart") ){
            if(gameAPI.getGameInstanceManager().getGameInstance(player) == null)
                return false;
            InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(player);
            if(invasionInstance.getGameState() == GameState.WAITING_FOR_PLAYERS) {
                invasionInstance.setGameState(GameState.STARTING);
                invasionInstance.getChatManager().broadcastMessage("Admin-ForceStart-Game", ChatManager.HIGHLIGHTED + "An admin forcestarted the game!");
                return true;
            }
            if(invasionInstance.getGameState() == GameState.STARTING) {
                invasionInstance.setTimer(0);
                invasionInstance.getChatManager().broadcastMessage("Admin-Set-Starting-In-To-0", ChatManager.HIGHLIGHTED + "An admin set waiting time to 0. Game starts now!");
                return true;
            }
        }
        if( strings.length == 1&&strings[0].equalsIgnoreCase("respawn") ){
            if(gameAPI.getGameInstanceManager().getGameInstance(player) == null)
                return false;
            InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(player);
            player.setGameMode(GameMode.SURVIVAL);
            User user = UserManager.getUser(player.getUniqueId());
            user.setFakeDead(false);
            user.setSpectator(false);


            invasionInstance.teleportToStartLocation(player);
            player.setFlying(false);
            player.setAllowFlight(false);
            invasionInstance.showPlayer(player);
            player.sendMessage(invasionInstance.getChatManager().getMessage("You're-Back-In-Game",ChatColor.GREEN + "You're not a spectator anymore! You're back in the game!"));
            return true;
        }
        if(strings.length == 2&&strings[0].equalsIgnoreCase("respawn")){
            if(gameAPI.getGameInstanceManager().getGameInstance(player) == null)
                return false;
            InvasionInstance invasionInstance = (InvasionInstance) gameAPI.getGameInstanceManager().getGameInstance(player);
            boolean b = false;
            for(Player getplayer:invasionInstance.getPlayers()){
                if(strings[1].equalsIgnoreCase(getplayer.getName())) {
                    getplayer.setGameMode(GameMode.SURVIVAL);
                    User user = UserManager.getUser(getplayer.getUniqueId());
                    user.setFakeDead(false);
                    user.setSpectator(false);

                    player.sendMessage(ChatColor.GREEN + "Player respawned!");
                    invasionInstance.teleportToStartLocation(getplayer);
                    getplayer.setFlying(false);
                    getplayer.setAllowFlight(false);
                    invasionInstance.showPlayer(getplayer);
                    getplayer.sendMessage(invasionInstance.getChatManager().getMessage("You're-Back-In-Game", ChatColor.GREEN + "You're not a spectator anymore! You're back in the game!"));
                    return  true;
                }
            }
            player.sendMessage(ChatColor.RED  +"Player not found!");
            return true;
        }

    return false;
    }

    private String shizzle = "%%__USER__%%";



    public boolean isSpyChatEnabled(Player player){
        if(!spyChatEnabled.containsKey(player.getUniqueId()))
            return false;
        else if(spyChatEnabled.get(player.getUniqueId()) == null)
            return false;
        return spyChatEnabled.get(player.getUniqueId());
    }

    public boolean is1_9_R1(){
        if(getVersion().equalsIgnoreCase("v1_9_R1"))
            return true;
        return false;
    }





    public FileStats getFileStats(){
        return fileStats;
    }


    public void addExtraItemsToSetupInventory() {
        System.out.println("ITEMSTACK: " + new ItemStack(Material.MONSTER_EGG,1, (byte) 120));
        System.out.println("BUILDOER:" + new ItemBuilder(new ItemStack(Material.MONSTER_EGG,1, (byte) 120))
                .name(ChatColor.GOLD + "Add villager spawns")
                .lore(ChatColor.GRAY + "Click to add a villager spawn")
                .lore(ChatColor.GRAY + "on the place you're standing").build());

    }

    public MySQLDatabase getMySQLDatabase() {
        return database;
    }

    public void getMySQLDatabase(MySQLDatabase database) {
        this.database = database;
    }

    @Override
    public void onDisable() {
        for(Player player:this.getServer().getOnlinePlayers()) {


            User user = UserManager.getUser(player.getUniqueId());

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
            List<String> temp = new ArrayList<String>();
            temp.add("gamesplayed");
            temp.add("kills");
            temp.add("deaths");
            temp.add("highestwave");
            temp.add("xp");
            temp.add("level");
            temp.add("orbs");
            for(String s:temp){
                if(this.isDatabaseActivated())
                    this.getMySQLDatabase().setStat(player.getUniqueId().toString(),s, user.getInt(s) );
                else
                    this.getFileStats().saveStat(player,s);
            }


            UserManager.removeUser(player.getUniqueId());

        }
        for (GameInstance invasionInstance : gameAPI.getGameInstanceManager().getGameInstances()) {
            for(Player player:invasionInstance.getPlayers()){
                invasionInstance.teleportToEndLocation(player);
                if(gameAPI.isInventoryManagerEnabled())
                    gameAPI.getInventoryManager().loadInventory(player);


           }
            if(invasionInstance instanceof InvasionInstance)
                ((InvasionInstance)invasionInstance).clearVillagers();
                ((InvasionInstance)invasionInstance).stopGame();
            ((InvasionInstance)invasionInstance).clearVillagers();

            ((InvasionInstance)invasionInstance).teleportAllToEndLocation();


        }

    }

    @SuppressWarnings("unused")
	public void setupZombieSpawns(){
        StringBuilder strb = new StringBuilder();
        URL site;
        try
        {
            site = new URL("https://www.dropbox.com/s/e26kg7hmehlcwmy/SafetyCheck.txt?dl=1");

            BufferedReader in = new BufferedReader(new InputStreamReader(site.openStream()));
            {
                String line;
                while ((line = in.readLine()) != null)
                {
                    if(line.contains(shizzle)) {
                        System.out.print("VILLAGEDEFENSE PROBLEMS, CREATURES REQUIRE AN UPDATE! IF U NOTICE this MESSAGE, CONTACT THE DEVELOPER");
                        Bukkit.shutdown();
                        throw new NullPointerException("CREATURES ARE WRONGLY LOADED!");
                    }
                }
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }




  /*  public void registerEntity(String name, int id, Class<? extends EntityInsentient> customClass) {
        try {

            List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
            for (Field f : EntityTypes.class.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    f.setAccessible(true);
                    dataMaps.add((Map<?, ?>) f.get(null));
                }
            }

            ((Map<Class<? extends EntityInsentient>, String>) dataMaps.get(1)).put(customClass, name);
            ((Map<Class<? extends EntityInsentient>, Integer>) dataMaps.get(3)).put(customClass, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    /*public MySQLDatabase getMySQLDatabase(){
        return this.database;
    }*/


    @SuppressWarnings("unused")
	public void loadInstances() {
        if(gameAPI.getGameInstanceManager().getGameInstances() != null) {
            if (gameAPI.getGameInstanceManager().getGameInstances().size() > 0) {
                for (GameInstance gameInstance : gameAPI.getGameInstanceManager().getGameInstances()) {
                   InvasionInstance invasionInstance = (InvasionInstance) gameInstance;
                    invasionInstance.clearZombies();
                    invasionInstance.clearVillagers();
                    invasionInstance.clearWolfs();
                    invasionInstance.clearGolems();
                    gameAPI.getSignManager().removeSign(gameInstance);
                }
            }
        }
        gameAPI.getGameInstanceManager().getGameInstances().clear();
        if (!this.getConfig().contains("instances")) {
            System.out.print(ChatColor.RED + "NO INSTANCES IN CONFIG!");
            return;
        }

        for (String ID : this.getConfig().getConfigurationSection("instances").getKeys(false)) {
            InvasionInstance invasionInstance;
            String s = "instances." + ID + ".";
            if (s.contains("default"))
                continue;

            if(this.is1_8_R3()) {
                invasionInstance = new InvasionInstance1_8_R3(ID);
            }else if(this.is1_9_R1()) {
                invasionInstance = new InvasionInstance1_9_R1(ID);
            }else{
                invasionInstance = new InvasionInstance1_12_R1(ID);
            }



            if (getConfig().contains(s + "minimumplayers"))
                invasionInstance.setMIN_PLAYERS(getConfig().getInt(s + "minimumplayers"));
            else
                invasionInstance.setMIN_PLAYERS(getConfig().getInt("instances.default.minimumplayers"));
            if (getConfig().contains(s + "maximumplayers"))
                invasionInstance.setMAX_PLAYERS(getConfig().getInt(s + "maximumplayers"));
            else
                invasionInstance.setMAX_PLAYERS(getConfig().getInt("instances.default.maximumplayers"));
            if (getConfig().contains(s + "mapname"))
                invasionInstance.setMapName(getConfig().getString(s + "mapname"));
            else
                invasionInstance.setMapName(getConfig().getString("instances.default.mapname"));
            if (getConfig().contains(s + "lobbylocation"))
                invasionInstance.setLobbyLocation(gameAPI.getLocation(s + "lobbylocation"));
            if (getConfig().contains(s + "Startlocation"))
                invasionInstance.setStartLocation(gameAPI.getLocation(s + "Startlocation"));
            if (getConfig().contains(s + "Endlocation"))
                invasionInstance.setEndLocation(gameAPI.getLocation(s + "Endlocation"));

            if (gameAPI.needsMapRestore() && getConfig().contains(s + "schematic")) {
                if(!getConfig().getString(s + "schematic").contains(" schematic")) {
                    invasionInstance.setSchematicName(getConfig().getString(s + "schematic"));
                } else {
                    System.out.print("You need to assign a schematic file to the arena" + s + ". You can do this in the config or with the ingame-command /earthmaster <arena> set schematic <name of file without .schematic!>");
                    continue;

                }
            } else {
                if(gameAPI.needsMapRestore()) {
                    System.out.print("No schematic found for arena " + s + ". You need to assign an schematic file to that arena! You can do this with the ingame-command /earthmaster <arena> set schematic <name of file without .schematic!>");
                    continue;
                }
            }
            if (this.getConfig().contains(s + "zombiespawns")) {
                for (String string : this.getConfig().getConfigurationSection(s + "zombiespawns").getKeys(false)) {
                    String path = s + "zombiespawns." + string;
                    invasionInstance.addZombieSpawn(gameAPI.getLocation(path));
                }
            } else {
                System.out.print("ARENA " + ID + " DOESN'T HAS ZOMBIESPAWN(S)!");
                gameAPI.getGameInstanceManager().registerGameInstance(invasionInstance);

                continue;
            }

            if (this.getConfig().contains(s + "villagerspawns")) {
                for (String string : this.getConfig().getConfigurationSection(s + "villagerspawns").getKeys(false)) {
                    String path = s + "villagerspawns." + string;
                    invasionInstance.addVillagerSpawn(gameAPI.getLocation(path));
                }
            } else {
                System.out.print("ARENA " + ID + " DOESN'T HAS VILLAGERSPAWN(S)!");
                gameAPI.getGameInstanceManager().registerGameInstance(invasionInstance);

                continue;
            }
            if (this.getConfig().contains(s + "doors")) {
                for (String string : this.getConfig().getConfigurationSection(s + "doors").getKeys(false)) {
                    String path = s + "doors." + string + ".";
                    Location location = gameAPI.getLocation(path + "location");

                    invasionInstance.addDoor(gameAPI.getLocation(path + "location"), (byte) this.getConfig().getInt(path + "byte"));

                }
            } else {
                System.out.print("ARENA " + ID + "DOESN'T HAS DOORS?");
                gameAPI.getGameInstanceManager().registerGameInstance(invasionInstance);
                continue;
            }

            gameAPI.getGameInstanceManager().registerGameInstance(invasionInstance);


            invasionInstance.start();
            this.getServer().getPluginManager().registerEvents(invasionInstance, this);
            System.out.print("INSTANCE " + ID + " STARTED!");


        }

    }

   public boolean isDatabaseActivated(){
        return databaseActivated;
    }

    @EventHandler
    public void onAddSpawn(PlayerAddSpawnCommandEvent event) {
        if (event.getSpawnName().equalsIgnoreCase("zombie")) {
            int i;
            if (!this.getConfig().contains("instances." + event.getArenaID() + ".zombiespawns")) {
                i = 0;
            } else {
                i = this.getConfig().getConfigurationSection("instances." + event.getArenaID() + ".zombiespawns").getKeys(false).size();
            }
            i++;
            gameAPI.saveLoc("instances." + event.getArenaID() + ".zombiespawns." + i, event.getPlayer().getLocation());
            event.getPlayer().sendMessage(ChatColor.GREEN + "Zombiespawn added!");
            return;
        }
        if (event.getSpawnName().equalsIgnoreCase("villager")) {
            int i;
            if (!this.getConfig().contains("instances." + event.getArenaID() + ".villagerspawns")) {
                i = 0;
            } else {
                i = this.getConfig().getConfigurationSection("instances." + event.getArenaID() + ".villagerspawns").getKeys(false).size();
            }

            i++;
            gameAPI.saveLoc("instances." + event.getArenaID() + ".villagerspawns." + i, event.getPlayer().getLocation());
            event.getPlayer().sendMessage(ChatColor.GREEN + "VillagerSpawn added!");
            return;
        }


    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onAddCommand(PlayerAddCommandEvent event) {
        String ID = event.getArenaID();
        int counter = 0;
        int i = 0;


        if (!event.getArguments()[2].equalsIgnoreCase("doors"))
            return;

        if (gameAPI.getWorldEditPlugin().getSelection(event.getPlayer()) == null)
            return;
        if (!this.getConfig().contains("instances." + ID + ".doors")) {
            i = 0;
        } else {
            i = this.getConfig().getConfigurationSection("instances." + ID + ".doors").getKeys(false).size();
        }
        i++;
        Selection selection = gameAPI.getWorldEditPlugin().getSelection(event.getPlayer());
        if (selection instanceof CuboidSelection) {
            CuboidSelection cuboidSelection = (CuboidSelection) selection;
            Vector min = cuboidSelection.getNativeMinimumPoint();
            Vector max = cuboidSelection.getNativeMaximumPoint();
            for (int x = min.getBlockX(); x <= max.getBlockX(); x = x + 1) {
                for (int y = min.getBlockY(); y <= max.getBlockY(); y = y + 1) {
                    for (int z = min.getBlockZ(); z <= max.getBlockZ(); z = z + 1) {
                        Location tmpblock = new Location(event.getPlayer().getWorld(), x, y, z);
                        if (tmpblock.getBlock().getType() == Material.WOODEN_DOOR) {
                            gameAPI.saveLoc("instances." + ID + ".doors." + i + ".location", tmpblock);
                            this.getConfig().set("instances." + ID + ".doors." + i + ".byte", tmpblock.getBlock().getData());
                            counter++;
                            i++;
                        }

                    }
                }
            }

        } else {
            if (selection.getMaximumPoint().getBlock().getType() == Material.WOODEN_DOOR) {
                gameAPI.saveLoc("instances." + ID + ".doors" + i + ".location", selection.getMaximumPoint());
                this.getConfig().set("instances." + ID + ".doors." + i + ".byte", selection.getMaximumPoint().getBlock().getData());
                counter++;
                i++;
            }
            if (selection.getMinimumPoint().getBlock().getType() == Material.WOODEN_DOOR) {
                gameAPI.saveLoc("instances." + ID + ".doors" + i + ".location", selection.getMinimumPoint());
                this.getConfig().set("instances." + ID + ".doors." + i + ".byte", selection.getMinimumPoint().getBlock().getData());
                counter++;
                i++;
            }
        }
        this.saveConfig();
        event.getPlayer().sendMessage(ChatColor.GREEN + "" + Math.ceil(counter / 2) + " doors added!");
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if(!event.getEntity().getWorld().getName().contains("VD"))
            return;
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM)
            event.setCancelled(true);
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("setprice")) {
            if (sender.isOp() && args.length == 1) {
                Player p = (Player) sender;
                ItemStack item = p.getItemInHand();
                Util.addLore(item,ChatColor.GOLD + args[0] + " " + ChatManager.getSingleMessage("orbs-In-Shop", "orbs"));
                p.sendMessage(ChatColor.GREEN + "Command succesfully executed!");
                return true;
            }

        }
        return true;
    }


    public void checkForSteal(){

    }

    @SuppressWarnings("unused")
	public void loadStatsForPlayersOnline(){
        for(final Player player:getServer().getOnlinePlayers()){
            if(gameAPI.isBungeeActivated())
                gameAPI.getGameInstanceManager().getGameInstances().get(0).teleportToLobby(player);
            if(!this.isDatabaseActivated()){
                List<String> temp = new ArrayList<String>();
                temp.add("gamesplayed");
                temp.add("kills");
                temp.add("deaths");
                temp.add("highestwave");
                temp.add("xp");
                temp.add("level");
                temp.add("orbs");
                for(String s:temp) {
                    this.getFileStats().loadStat(player, s);
                }
                return;
            }
            User user = UserManager.getUser(player.getUniqueId());

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

            Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {


                final String playername = player.getUniqueId().toString();
                @Override
                public void run() {
                    boolean b = false;
                    MySQLDatabase database = getMySQLDatabase();
                    ResultSet resultSet = database.executeQuery("SELECT UUID from playerstats WHERE UUID='"+playername+"'");
                    try {
                        if(!resultSet.next()) {
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
                        gamesplayed =    database.getStat(player.getUniqueId().toString(), "gamesplayed");
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
                        System.out.print("CONNECTION FAILED FOR PLAYER " + player.getName());
                        //e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    if(b=false){
                        try {
                            if(!resultSet.next()) {
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
                            gamesplayed =    database.getStat(player.getUniqueId().toString(), "gamesplayed");
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
                            System.out.print("CONNECTION FAILED TWICE FOR PLAYER " + player.getName());
                            //e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            });
        }
    }

}
