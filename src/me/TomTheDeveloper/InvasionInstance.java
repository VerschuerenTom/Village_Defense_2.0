package me.TomTheDeveloper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import me.TomTheDeveloper.Game.GameInstance;
import me.TomTheDeveloper.Game.GameState;
import me.TomTheDeveloper.Game.InstanceType;
import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.ConfigurationManager;
import me.TomTheDeveloper.Handlers.MessageHandler;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.Kits.GolemFriend;
import me.TomTheDeveloper.Utils.ArmorHelper;
//import me.confuser.barapi.BarAPI;
//import me.mgone.bossbarapi.BossbarAPI;
import me.TomTheDeveloper.chunks.ChunkManager;
import me.TomTheDeveloper.items.SpecialItemManager;

/**
 * Created by Tom on 12/08/2014.
 */
public abstract class InvasionInstance extends GameInstance implements Listener {

    protected List<Location> zombiespawns = new ArrayList<Location>();
    private List<Location> villagerspawns = new ArrayList<Location>();
    private List<Zombie> zombies = new ArrayList<Zombie>();
    private List<Wolf> wolfs = new ArrayList<Wolf>();
    private List<Villager> villagers = new ArrayList<Villager>();
    private List<IronGolem> irongolems = new ArrayList<IronGolem>();
    public LinkedHashMap<Location, Byte> doorblocks = new LinkedHashMap<Location, Byte>();
    private boolean FIGHTING;
    private int wave;
    protected int zombiestospawn;
    private int rottenflesh;
    private int rottenfleshlevel;
    private int zombiechecker = 0;
    private Random random;
    public static YoutuberInvasion youtuberInvasion;

    private List<Zombie> glitchedzombies = new ArrayList<Zombie>();

    private int spawncounter =0;
    private HashMap<Zombie, Location> zombiecheckerlocations = new HashMap<Zombie, Location>();


    public InvasionInstance(String ID) {
        super(ID);
        setType(InstanceType.VILLAGE_DEFENSE);
        random = new Random();
    }

    public HashMap<Location, Byte> getDoorLocations() {
        return doorblocks;
    }

    @Override
    public boolean needsPlayers() {
        if (getGameState() == GameState.STARTING || getGameState() == GameState.WAITING_FOR_PLAYERS)
            return true;
        else
            return true;
    }

    @SuppressWarnings({ "deprecation", "incomplete-switch" })
	@Override
    public void run() {

        User.handleCooldowns();
        updateScoreboard();
        if (plugin.isBarEnabled())
            updateBar();
        switch (getGameState()) {
            case WAITING_FOR_PLAYERS:
                if(plugin.isBungeeActivated())
                     plugin.getPlugin().getServer().setWhitelist(false);
                if (getPlayers().size() < getMIN_PLAYERS()) {

                    if (getTimer() <= 0) {
                        setTimer(15);
                        getChatManager().broadcastMessage("Waiting-For-Players","Waiting for players... We need at least " + ChatColor.AQUA + getMIN_PLAYERS() + ChatColor.GRAY  + " players to start.",getMIN_PLAYERS());
                        return;
                    }
                } else {
                    getChatManager().broadcastMessage("Enough-Players-To-Start","We now have enough players. The game is starting soon!");
                    setGameState(GameState.STARTING);
                    setTimer(YoutuberInvasion.STARTING_TIMER_TIME);
                    this.showPlayers();

                }
                setTimer(getTimer() - 1);
                break;

            case STARTING:
                if (getTimer() == 0) {
                    setGameState(GameState.INGAME);
                    setTimer(5);
                    teleportAllToStartLocation();
                    for (Player player : getPlayers()) {
                        player.getInventory().clear();
                        player.setGameMode(GameMode.SURVIVAL);
                        UserManager.getUser(player.getUniqueId()).setInt("orbs",20);
                        hidePlayersOutsideTheGame(player);
                        if (UserManager.getUser(player.getUniqueId()).getKit() != null) {
                            UserManager.getUser(player.getUniqueId()).getKit().giveKitItems(player);
                        } else {
                            plugin.getKitHandler().getDefaultKit().giveKitItems(player);
                        }
                        player.updateInventory();
                        addStat(player, "gamesplayed");
                        addStat(player, "xp", 10);
                        setTimer(25);


                    }
                    getChatManager().broadcastMessage("The-Game-Has-Started", "The game has started! Defend the village against waves of zombies!");
                    FIGHTING = false;


                }
                setTimer(getTimer() - 1);
                break;
            case INGAME:
                if(plugin.isBungeeActivated() && getMAX_PLAYERS() <= getPlayers().size()){
                    youtuberInvasion.getServer().setWhitelist(true);
                }else{
                    youtuberInvasion.getServer().setWhitelist(false);
                }
                zombiechecker++;
                if(zombiechecker >= 60){
                    List<Villager> remove = new ArrayList<Villager>();
                    for(Villager villager:getVillagers()){
                        if(villager.isDead())
                            remove.add(villager);
                    }
                    for(Villager villager:remove){
                        removeVillager(villager);
                    }
                    remove.clear();
                    zombiechecker = 0;
                    List<Zombie> removeaferloop = new ArrayList<Zombie>();
                    for(Zombie zombie:getZombies()) {
                        if(zombie.isDead()){
                            removeaferloop.add(zombie);
                            continue;
                        }
                        if(glitchedzombies.contains(zombie) && zombie.getLocation().distance(zombiecheckerlocations.get(zombie)) <=1){
                            removeaferloop.add(zombie);
                            zombiecheckerlocations.remove(zombie);
                            zombie.remove();
                        }
                        if (zombiecheckerlocations.get(zombie)== null){
                            zombiecheckerlocations.put(zombie,zombie.getLocation());
                        }else{
                            Location location = zombiecheckerlocations.get(zombie);

                            if(zombie.getLocation().distance(location) <=1){
                                zombie.teleport(zombiespawns.get(random.nextInt(zombiespawns.size()-1)));
                                zombiecheckerlocations.put(zombie, zombie.getLocation());
                                glitchedzombies.add(zombie);
                            }
                        }
                    }

                    for(Zombie zombie:removeaferloop){
                        removeZombie(zombie);
                    }
                    removeaferloop.clear();

                }
                if (getVillagers().size() <= 0) {
                    clearZombies();
                    this.stopGame();
                    this.setGameState(GameState.ENDING);
                    showPlayers();
                    this.setTimer(10);

                    return;
                }
                if (getPlayersLeft().size() <= 0) {
                    clearZombies();
                    this.stopGame();
                    this.setGameState(GameState.ENDING);
                    setTimer(5);
                    return;

                }
                if (FIGHTING) {

                    if (getZombiesLeft() <= 0) {
                        FIGHTING = false;
                        endWave();
                    }
                    if (zombiestospawn > 0) {
                        spawnZombies();
                        setTimer(500);
                    } else {
                        if (getTimer() == 0) {
                            if (getZombiesLeft() <= 5) {
                                //zombiestospawn = getZombiesLeft();
                                clearZombies();
                                zombiestospawn = 0;
                                getChatManager().broadcastMessage("Zombie-Got-Stuck-In-The-Map","It seems like the last zombie got stuck somewhere. No worries! The gods killed" +
                                        " him for you!");

                            } else {
                                int i = getZombiesLeft();
                                getZombies().clear();
                                for(i=getZombiesLeft();i>0;i++){
                                    spawnFastZombie(random);
                                }

                               // zombiestospawn = getZombiesLeft();
                                //zombiestospawn = 0;
                                //zombies.clear();
                                // getChatManager().broadcastMessage("There went something wrong internal. Hopefully it's fixed now but that problem spawned some zombies!");
                            }

                        }

                    }
                    if(zombiestospawn <0)
                        zombiestospawn=0;
                    setTimer(getTimer() - 1);

                } else {
                    if (getTimer() <= 0) {
                        FIGHTING = true;

                        startWave();

                    }
                }
                setTimer(getTimer() - 1);
                break;
            case ENDING:
                if(plugin.isBungeeActivated())
                    youtuberInvasion.getServer().setWhitelist(false);
                if (getTimer() <= 0) {
                    clearVillagers();
                    clearZombies();
                    clearGolems();
                    clearWolfs();


                    for (Player player : getPlayers()) {
                        UserManager.getUser(player.getUniqueId()).removeScoreboard();
                        player.setGameMode(GameMode.SURVIVAL);
                        for(Player players:Bukkit.getOnlinePlayers()){
                            player.showPlayer(players);
                            players.hidePlayer(player);
                        }
                        for (PotionEffect effect : player.getActivePotionEffects()) {
                            player.removePotionEffect(effect.getType());
                        }
                        player.setFlying(false);
                        player.setAllowFlight(false);
                        player.getInventory().clear();

                        ArmorHelper.clearArmor(player);
                        // if (plugin.isBungeeActivated())
                        //BossbarAPI.removeBar(player);
                        player.setMaxHealth(20.0);
                        for(Player players:youtuberInvasion.getServer().getOnlinePlayers()){
                            if(plugin.getGameInstanceManager().getGameInstance(players) != null)
                                players.showPlayer(player);
                            player.showPlayer(players);
                        }

                    }

                    teleportAllToEndLocation();

                    if(plugin.isInventoryManagerEnabled()) {
                        for (Player player : getPlayers()) {
                            plugin.getInventoryManager().loadInventory(player);
                        }
                    }

                    getChatManager().broadcastMessage("Teleported-To-The-Lobby","Teleported to the lobby!");

                    setGameState(GameState.RESTARTING);


                    for (User user : UserManager.getUsers(this)) {
                        user.setSpectator(false);
                        user.setInt("orbs", 0);
                        user.setAllowDoubleJump(false);
                        user.setFakeDead(false);
                    }
                    clearPlayers();
                    youtuberInvasion.getRewardsHandler().performEndGameRewards(this);
                if(plugin.isBungeeActivated()){
                    if(ConfigurationManager.getConfig("Bungee").getBoolean("ShutdownWhenGameEnds"))
                        plugin.getPlugin().getServer().shutdown();
                	}
                }
                setTimer(getTimer() - 1);
                break;
            case RESTARTING:
                clearVillagers();
                this.restoreMap();


                getPlayers().clear();
                plugin.getSignManager().addToQueue(this);

                setGameState(GameState.WAITING_FOR_PLAYERS);

                wave = 1;
                if(plugin.isBungeeActivated()){
                    for(Player player:youtuberInvasion.getServer().getOnlinePlayers()){
                        this.addPlayer(player);
                    }
                }

                break;
        }
    }

    private void updateLevelStat(Player player) {
        User user = UserManager.getUser(player.getUniqueId());

        if (Math.pow(50 * user.getInt("level"), 1.5) < user.getInt("xp")) {
            user.addInt("level", 1);
            player.sendMessage(getChatManager().getMessage("You-leveled-up", ChatColor.GREEN + "You leveled up! You're now level %NUMBER%! ", user.getInt("level")));
        }
    }

    @SuppressWarnings({ "unused", "incomplete-switch" })
	private void updateBar() {
        switch (getGameState()) {
            case WAITING_FOR_PLAYERS:
                for (Player player : getPlayers()) {
                    Random random = new Random();
                    ChatColor color = ChatColor.GREEN;
                    int i = random.nextInt(5);
                    if (i == 0) color = ChatColor.AQUA;
                    if (i == 1) color = ChatColor.YELLOW;
                    if (i == 2) color = ChatColor.LIGHT_PURPLE;
                    if (i == 3) color = ChatColor.RED;
                    //BossbarAPI.setMessage(color + "IP: HCSERVER.COM");
                }
                break;

            case STARTING:
                for (Player player : getPlayers()) {
                    float percentage = (float) Math.ceil(100 * getTimer() / 30);


                   // BossbarAPI.setMessage(player, ChatColor.GRAY + "Starting in: " + ChatManager.HIGHLIGHTED + getTimer(), percentage);


                }

                break;
            case INGAME:
                if (FIGHTING) {
                    for (Player player : getPlayers()) {
                        Random random = new Random();
                        ChatColor color = ChatColor.GREEN;
                        int i = random.nextInt(5);
                        if (i == 0) color = ChatColor.AQUA;
                        if (i == 1) color = ChatColor.YELLOW;
                        if (i == 2) color = ChatColor.LIGHT_PURPLE;
                        if (i == 3) color = ChatColor.RED;
                    //    BossbarAPI.setMessage(color + "IP: PLAY.EDGEVILLEMC.COM");
                    }
                } else {
                    for (Player player : getPlayers()) {
                        float percentage = (float) Math.ceil(100 * getTimer() / 40);
                      //  BossbarAPI.setMessage(player, ChatManager.PREFIX + "Next wave in " + ChatManager.HIGHLIGHTED + getTimer() + ChatManager.PREFIX + " seconds!", percentage);


                    }
                }

                break;
            case ENDING:

                break;
            case RESTARTING:
                break;
        }
    }

    public void setZombieAmount() {
        int playercount = getPlayers().size();
        zombiestospawn = (int) Math.ceil((playercount * 0.5) * (wave * wave) / 2);
    }

    public void resetRottenFlesh(){
        this.rottenflesh = 0;
        this.rottenfleshlevel = 0;
    }

    public void stopGame() {
        if (getPlayersLeft().size() > 0) {
            getChatManager().broadcastMessage("All-Villagers-Have-Died",ChatColor.RED + "All villagers have died! You lost the game!");
            getChatManager().broadcastMessage("Reached-Wave-X", "You have reached wave " + ChatColor.AQUA + "%NUMBER%" + ChatColor.GRAY  + "!", wave);
            getChatManager().broadcastMessage("Teleporting-To-Lobby-In-10-Seconds","You will be teleported to the lobby in " + ChatColor.AQUA  + 10 + ChatColor.GRAY + " seconds!");
        } else {
            getChatManager().broadcastMessage("All-Players-Have-Died",ChatColor.RED + "All players have died!");
            getChatManager().broadcastMessage("Reached-Wave-X", "You have reached wave " + ChatColor.AQUA  + wave + ChatColor.GRAY  + "!", wave);
            getChatManager().broadcastMessage("Teleporting-To-Lobby-In-10-Seconds","You will be teleported to the lobby in " +ChatColor.AQUA  + 10 + ChatColor.GRAY  + " seconds!");

        }
        for (Player player : getPlayers()) {
            //if (plugin.isBungeeActivated())
                //BossbarAPI.removeBar(player);
            setStat(player, "highestwave", wave);
            addStat(player, "xp", wave);

            UserManager.getUser(player.getUniqueId()).removeScoreboard();
        }
        this.resetRottenFlesh();
        this.restoreDoors();
        for (Zombie zombie : getZombies()) {
            zombie.remove();
        }
        zombies.clear();
        for (IronGolem ironGolem : getIronGolems()) {
            ironGolem.remove();
        }
        irongolems.clear();
        for (Villager villager : getVillagers()) {
            villager.remove();

        }
        villagers.clear();
        for (Wolf wolf : getWolfs()) {
            wolf.remove();
        }
        wolfs.clear();
        clearZombies();
        clearGolems();
        clearVillagers();
        clearWolfs();
        for(Entity entity:getStartLocation().getWorld().getEntities()){
            if(entity.getWorld().getName().equalsIgnoreCase(getStartLocation().getWorld().getName())
                &&entity.getLocation().distance(getStartLocation()) <300)
                if(entity.getType() != EntityType.PLAYER)
                entity.remove();
        }
    }

    public void restoreMap() {
        this.restoreDoors();
        for (Zombie zombie : getZombies()) {
            zombie.remove();
        }
        for (IronGolem ironGolem : getIronGolems()) {
            ironGolem.remove();
        }
        for(Villager villager:getVillagers()){
        villager.remove();
        }

        for (Wolf wolf : getWolfs()) {
            wolf.remove();
        }
        clearZombies();
        clearGolems();
        clearVillagers();
        clearWolfs();
        spawnVillagers();

    }

    public int getOrbs(Player player) {
        return UserManager.getUser(player.getUniqueId()).getInt("orbs");
    }


    public void spawnVillagers() {
        if (getVillagers().size() > 10) {
            return;
        }else if(getVillagerSpawns() == null || getVillagerSpawns().size() <=0){
            System.out.print("NO VILLAGERSPAWNS DEFINED FOR ARENA " + this.getID() + "! ARENA CAN'T RUN WITHOUT VILLAGER SPAWNS! PLEASE ADD VILLAGER SPAWNS!");
            return;
        } else {
            for (Location location : getVillagerSpawns()) {
                spawnVillager(location);
            }
            if(getVillagers().size()!=0) {
                spawnVillagers();
            }else{
                System.out.print("UNABLE TO SPAWN VILLAGERS! PLEASE CONTACT THE DEV TO SOLVE this PROBLEM!!");
            }
        }
    }


    public void start() {
        this.runTaskTimer(youtuberInvasion, 20L, 20L);
        this.setGameState(GameState.RESTARTING);
        for(Location location: villagerspawns){
            ChunkManager.getInstance().keepLoaded(location.getChunk());
        }
    }

    public List<Zombie> getZombies() {
        return zombies;
    }

    public void removeZombie(Zombie zombie) {
        if (zombies.contains(zombie))
            zombies.remove(zombie);
    }

    public List<Location> getVillagerSpawns() {
        return villagerspawns;
    }

    public void setVillagerSpawns(List<Location> villagerspawns) {
        this.villagerspawns = villagerspawns;
    }

    public void clearGolems() {
        for(IronGolem ironGolem:irongolems){
            ironGolem.remove();
        }
        this.irongolems.clear();
    }

    public void clearWolfs() {
        for(Wolf wolf:wolfs){
            wolf.remove();
        }
        this.wolfs.clear();
    }

    public void addVillagerSpawn(Location location) {
        this.villagerspawns.add(location);
    }

    public List<Location> getZombieSpawns() {
        return zombiespawns;
    }

    public void setZombieSpawns(List<Location> zombiespawns) {
        this.zombiespawns = zombiespawns;
    }

    public void addZombieSpawn(Location location) {
        zombiespawns.add(location);
    }


    public void clearZombies() {
        for(Zombie zombie: zombies){
            zombie.remove();

        }
        zombies.clear();
    }

    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    public void startWave() {
        setZombieAmount();
        if(!youtuberInvasion.getConfig().contains("RespawnAfterWave"))
            youtuberInvasion.getConfig().set("RespawnAfterWave", true);
        if(youtuberInvasion.getConfig().getBoolean("RespawnAfterWave"))
            this.bringDeathPlayersBack();
        for (User user : UserManager.getUsers(this)) {
            user.getKit().reStock(user.toPlayer());
        }

        getChatManager().broadcastMessage("Wave-Started", "Wave " + ChatManager.HIGHLIGHTED + "%NUMBER%" + ChatColor.GRAY  + " started!", wave);

    }



    @SuppressWarnings("deprecation")
	public void endWave() {
        youtuberInvasion.getRewardsHandler().performEndWaveRewards(this, wave);
        setTimer(25);
        zombiecheckerlocations.clear();
        wave = wave + 1;
        getChatManager().broadcastMessage("You-Feel-Refreshed",ChatColor.GREEN + "You feel refreshed!");
        getChatManager().broadcastMessage("Next-Wave-Starts-In","Next wave starts in " + ChatManager.HIGHLIGHTED + "%NUMBER%" + ChatColor.GRAY  + " seconds!",getTimer());
        for (Player player : getPlayers()) {
            if(!(plugin.is1_8_R3() || plugin.is1_7_R4())) {
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            }else{
                player.setHealth(player.getMaxHealth());
            }
            UserManager.getUser(player.getUniqueId()).addInt("orbs", wave * 10);
        }
        if(youtuberInvasion.getConfig().getBoolean("RespawnAfterWave"))
        this.bringDeathPlayersBack();
        for (Player player : getPlayersLeft()) {
            this.addStat(player, "xp", 5);
        }


    }

    public  boolean isInventoryEmpty(Player p) {
        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null)
                return false;

        }
        return true;
    }




    @SuppressWarnings("deprecation")
	@Override
    public void joinAttempt(Player p){
        System.out.print("Joining using Village Defense");
        if((getGameState() == GameState.INGAME || (getGameState() == GameState.STARTING && getTimer() <=3) || getGameState() == GameState.ENDING)){
            if(plugin.isInventoryManagerEnabled()) {
                p.setLevel(0);
                plugin.getInventoryManager().saveInventoryToFile(p);

            }
            this.teleportToStartLocation(p);
            p.sendMessage(getChatManager().getMessage("You-Are-Spectator", ChatColor.AQUA + "You are a spectator! You'll be respawned at the end of the next wave!"));
            p.getInventory().clear();
            for(PotionEffect potionEffect:p.getActivePotionEffects()){
                p.removePotionEffect(potionEffect.getType());

            }

            this.addPlayer(p);
            if(plugin.is1_8_R3()) {
                p.setHealth(p.getMaxHealth());
            }else{
                p.setHealth(p.getMaxHealth());
            }
            p.setFoodLevel(20);
            if(plugin.is1_8_R3()) {
                p.setGameMode(GameMode.SPECTATOR);
            }else{
                p.setGameMode(GameMode.SURVIVAL);
            }
            p.setAllowFlight(true);
            p.setFlying(true);
            User user = UserManager.getUser(p.getUniqueId());
            user.setSpectator(true);
            user.setFakeDead(true);
            user.setInt("orbs", 0);
            this.hidePlayer(p);
            user.getKit().giveKitItems(p);



            for(Player spectator:plugin.getGameInstanceManager().getGameInstances().get(0).getPlayers()){
                if(UserManager.getUser(spectator.getUniqueId()).isSpectator()){
                    p.hidePlayer(spectator);
                    spectator.hidePlayer(p);
                }else{
                    p.showPlayer(spectator);
                }
            }
            hidePlayersOutsideTheGame(p);
            return;
        }
        if(plugin.isInventoryManagerEnabled()) {
            p.setLevel(0);
            plugin.getInventoryManager().saveInventoryToFile(p);

        }
        teleportToLobby(p);
        this.addPlayer(p);
        if(plugin.is1_8_R3()) {
            p.setHealth(p.getMaxHealth());
        }else{
            p.setHealth(p.getMaxHealth());
        }
        p.setFoodLevel(20);
        p.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
        p.setFlying(false);
        p.setAllowFlight(false);
        p.getInventory().clear();
        showPlayers();
        if(!UserManager.getUser(p.getUniqueId()).isSpectator())
            getChatManager().broadcastJoinMessage(p);
        User user = UserManager.getUser(p.getUniqueId());
        user.setKit(plugin.getKitHandler().getDefaultKit());
        plugin.getKitMenuHandler().giveKitMenuItem(p);
        if(getGameState() == GameState.STARTING || getGameState() == GameState.WAITING_FOR_PLAYERS)
            p.getInventory().setItem(SpecialItemManager.getSpecialItem("Leave").getSlot(),SpecialItemManager.getSpecialItem("Leave").getItemStack());
        p.updateInventory();
        for(Player player:getPlayers()){
            showPlayer(player);
        }
        showPlayers();
    }


    public int getZombiesLeft() {
        return zombiestospawn + getZombies().size();

    }

    private void addStat(Player player, String stat, int i){
        User user = UserManager.getUser(player.getUniqueId());
        user.addInt(stat, i);
        if(stat.equalsIgnoreCase("xp")){
            if(user.isVIP()){
                user.addInt(stat, (int)Math.ceil(i/2));
            }
            if(user.isMVP()){
                user.addInt(stat, (int)Math.ceil(i/2));
            }
            if(user.isELITE()){
                user.addInt(stat, (int)Math.ceil(i/2));
            }
        }
        this.updateLevelStat(player);
    }

    private void addStat(Player player, String stat){
        User user = UserManager.getUser(player.getUniqueId());
        user.addInt(stat, 1);
        this.updateLevelStat(player);
    }

    private void setStat(Player player, String stat, int i){
        User user = UserManager.getUser(player.getUniqueId());
        if(user.getInt(stat) <= i){
            user.setInt(stat, i);
        }
    }

    public void spawnZombies() {
        Random random = new Random();
        /*if (zombiestospawn < 5) {
            spawnFastZombie(random);
            return;
        }
        if (random.nextInt(8) == 0 && getIronGolems().size() > 0) {
            for (int i = 0; i < 6; i++) {
                spawnGolemBuster(random);

            }
            return;
        }
        if (random.nextInt(8) == 0 && wave > 10) {
            for (int i = 0; i < 6; i++) {
                spawnPlayerBuster(random);
            }
        }

        if (random.nextInt(15) == 0) {
            if (random.nextInt(4) == 0) {
                if (random.nextInt(2) == 1) {
                    spawnHardZombie(random);
                    spawnHardZombie(random);

                    if(wave > 5)
                        spawnHardZombie(random);

                    if(wave>10)
                        spawnHardZombie(random);
                        return;
                } else {
                    for(int i = 0;i<=wave;i++) {
                        spawnBabyZombie(random);
                    }
                    return;
                }
            } else {
                for (int i = 0; i <= wave; i++) {
                    spawnFastZombie(random);

                }
                return;
            }

        } */
        if(getZombies() == null || getZombies().size() <=0){
            for (int i = 0; i <= wave; i++) {
                if(zombiestospawn >0) {
                    spawnFastZombie(random);
                }

            }
        }
        spawncounter++;
        if(spawncounter == 20)
            spawncounter =0;

        if(zombiestospawn<5){
            if(zombiestospawn>0)
            spawnFastZombie(random);
            return;
        }
        if(spawncounter == 5){
            if(random.nextInt(3)!=2) {
                for (int i = 0; i <= wave; i++) {
                    if(zombiestospawn >0) {
                        if(wave>7){
                            if(random.nextInt(2) ==1)
                            spawnSoftHardZombie(random);
                        }else if(wave>14){
                            if(random.nextInt(2)==1)
                                spawnHardZombie(random);
                        }else if(wave>20){
                            if(random.nextInt(3)==1)
                                spawnKnockbackResistantZombies(random);
                        }else {
                            spawnFastZombie(random);
                        }
                    }
                }
            }else{
                for(int i = 0;i<=wave;i++) {
                    if(zombiestospawn >0)
                    spawnBabyZombie(random);
                }
            }
        }
        if(spawncounter == 15 && wave >4){
            if(wave>8) {
                for (int i = 0; i < (wave - 7); i++) {
                    if (zombiestospawn > 0)
                        spawnHardZombie(random);
                }
            }else{
                for (int i = 0; i < (wave - 3); i++) {
                    if (zombiestospawn > 0)
                        spawnSoftHardZombie(random);
                }
            }

        }

        if (random.nextInt(8) == 0 && wave > 10) {
            for (int i = 0; i < (wave-8); i++) {
                if(zombiestospawn >0)
                spawnPlayerBuster(random);
            }
        }
        if (random.nextInt(8) == 0 && wave > 7) {
            for (int i = 0; i < (wave-5); i++) {
                if(zombiestospawn >0)
                    spawnHalfInvisibleZombie(random);
            }
        }
        if (random.nextInt(8) == 0 && wave > 15) {
            for (int i = 0; i < (wave-13); i++) {
                if(zombiestospawn >0)
                    spawnHalfInvisibleZombie(random);
            }
        }
        if (random.nextInt(8) == 0 && getIronGolems().size() > 0 && wave>=6) {
            for (int i = 0; i < (wave-4); i++) {
                if(zombiestospawn >0)
                spawnGolemBuster(random);

            }

        }



    }

    public void setWave(int i){
        wave = i;
    }

    public int getWave(){
        return wave;
    }

   public abstract void spawnVillager(Location location);
    public abstract void spawnWolf(Location location,Player player);
    public abstract void spawnGolem(Location location,Player player);

    public abstract void spawnFastZombie(Random random);
    public abstract void spawnBabyZombie(Random random);
    public abstract void spawnHardZombie(Random random);
    public abstract void spawnPlayerBuster(Random random);
    public abstract void spawnGolemBuster(Random random);
    public abstract void spawnSoftHardZombie(Random random);
    public abstract void spawnHalfInvisibleZombie(Random random);
    public abstract void spawnKnockbackResistantZombies(Random random);

    public void addWolf(Wolf wolf) {
        wolfs.add(wolf);
    }

    public void removeWolf(Wolf wolf) {
        wolfs.remove(wolf);
    }

    public List<Wolf> getWolfs() {
        return wolfs;
    }

    public List<IronGolem> getIronGolems() {
        return irongolems;
    }

    public List<Villager> getVillagers() {
        return villagers;
    }

    public void addVillager(Villager villager) {
        villagers.add(villager);

    }

    public void removeVillager(Villager villager) {
        if (villagers.contains(villager)) {

            villager.remove();

            villager.setHealth(0);

            villagers.remove(villager);

        }
    }


    public void clearVillagers() {
        for(Villager villager:villagers){
            villager.remove();

        }
        villagers.clear();
    }

    public void addIronGolem(IronGolem ironGolem) {
        irongolems.add(ironGolem);
    }

    public void removeIronGolem(IronGolem ironGolem) {
        if (irongolems.contains(ironGolem))
            irongolems.remove(ironGolem);
    }

    public void addDoor(Location location, byte data) {
        this.doorblocks.put(location, data);
    }

    @SuppressWarnings({ "deprecation", "unused" })
	public void restoreDoors() {
        for (Location location : doorblocks.keySet()) {


            byte raw = 8;
            Block block = location.getBlock();
            Byte doordata = doorblocks.get(location);
            int id = Material.WOODEN_DOOR.getId();
            block.setTypeIdAndData(id, doordata, false);


        }
    }

    public void updateScoreboard() {
        if (getPlayers().size() == 0)
            return;
        for (Player p : getPlayers()) {
            User user = UserManager.getUser(p.getUniqueId());
            if (user.getScoreboard().getObjective("waiting") == null) {
                user.getScoreboard().registerNewObjective("waiting", "dummy");
                user.getScoreboard().registerNewObjective("starting", "dummy");
                user.getScoreboard().registerNewObjective("ingame", "dummy");
                user.getScoreboard().registerNewObjective("ingame2", "dummy");

            }
            switch (getGameState()) {
                case WAITING_FOR_PLAYERS:

                case STARTING:
                    Objective startingobj = user.getScoreboard().getObjective("starting");
                    startingobj.setDisplayName( getChatManager().getMessage("SCOREBOARD-Header", ChatManager.PREFIX + "Village Defense"));
                    startingobj.setDisplaySlot(DisplaySlot.SIDEBAR);
                    if (!plugin.isBarEnabled() && getGameState()==GameState.STARTING) {
                        Score timerscore = startingobj.getScore(getChatManager().getMessage("SCOREBOARD-Starting-In"));
                        timerscore.setScore(getTimer());
                    }
                    Score playerscore = startingobj.getScore(getChatManager().getMessage("SCOREBOARD-Players"));
                    playerscore.setScore(getPlayers().size());
                    Score minplayerscore = startingobj.getScore(getChatManager().getMessage("SCOREBOARD-Min-Players"));
                    minplayerscore.setScore(getMIN_PLAYERS());


                    break;
                case INGAME:


                    if (FIGHTING) {
                        Objective ingameobj = user.getScoreboard().getObjective("ingame");
                        ingameobj.setDisplayName(getChatManager().getMessage("SCOREBOARD-Header", ChatManager.PREFIX + "Village Defense"));
                        ingameobj.setDisplaySlot(DisplaySlot.SIDEBAR);
                        Score playerleftscore = ingameobj.getScore(getChatManager().getMessage("SCOREBOARD-Players-Left"));
                        playerleftscore.setScore(this.getPlayersLeft().size());

                        Score villagersscore = ingameobj.getScore(getChatManager().getMessage("SCOREBOARD-Villagers"));
                        villagersscore.setScore(getVillagers().size());
                        Score orbsscore = ingameobj.getScore(getChatManager().getMessage("SCOREBOARD-Orbs"));
                        orbsscore.setScore(user.getInt("orbs"));
                        Score zombiesscore = ingameobj.getScore(getChatManager().getMessage("SCOREBOARD-Zombies"));
                        zombiesscore.setScore(getZombiesLeft());
                        Score rottenfleshscore = ingameobj.getScore(this.getChatManager().getMessage("SCOREBOARD-Rotten-Flesh"));
                        rottenfleshscore.setScore(getRottenFlesh());

                    } else {

                        Objective ingame2obj = user.getScoreboard().getObjective("ingame2");
                        ingame2obj.setDisplayName(getChatManager().getMessage("SCOREBOARD-Header", ChatManager.PREFIX + "Village Defense"));
                        ingame2obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                        Score playerleftscore = ingame2obj.getScore(getChatManager().getMessage("SCOREBOARD-Players-Left"));
                        playerleftscore.setScore(this.getPlayersLeft().size());

                        Score villagersscore = ingame2obj.getScore(getChatManager().getMessage("SCOREBOARD-Villagers"));
                        villagersscore.setScore(getVillagers().size());
                        Score orbsscore = ingame2obj.getScore(getChatManager().getMessage("SCOREBOARD-Orbs"));
                        orbsscore.setScore(user.getInt("orbs"));
                        if (!plugin.isBarEnabled()) {
                            Score nextwavescore = ingame2obj.getScore(getChatManager().getMessage("SCOREBOARD-Next-Wave-In"));
                            nextwavescore.setScore(getTimer());
                        }
                        Score rottenfleshscore = ingame2obj.getScore(getChatManager().getMessage("SCOREBOARD-Rotten-Flesh"));
                        rottenfleshscore.setScore(getRottenFlesh());


                    }

                    break;
                case ENDING:
                    user.removeScoreboard();
                    break;
                case RESTARTING:

                    break;
                default:
                    setGameState(GameState.WAITING_FOR_PLAYERS);
            }
            user.setScoreboard(user.getScoreboard());
        }


    }

    @EventHandler
    public void onDieEntity(EntityDeathEvent event) {

        if (event.getEntity().getType() == EntityType.ZOMBIE) {
            if (getZombies().contains(event.getEntity()))
                removeZombie((Zombie) event.getEntity());
            if(event.getEntity().getKiller() != null){
                if(plugin.getGameInstanceManager().getGameInstance(event.getEntity().getKiller()) !=null) {
                    addStat(event.getEntity().getKiller(), "kills");
                    addStat(event.getEntity().getKiller(), "xp", 2);
                }
            }
        }
        if (event.getEntity().getType() == EntityType.VILLAGER) {
            if (getVillagers().contains(event.getEntity())) {
                getStartLocation().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                removeVillager((Villager) event.getEntity());
                getChatManager().broadcastMessage("A-Villager-Has-Died",ChatColor.RED + "A villager has died!");

            }
        }

    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerDie(PlayerDeathEvent event){
        if(!getPlayers().contains(event.getEntity()))
            return;
        if (getPlayers().contains(event.getEntity()))
            this.onDeath(event.getEntity());
        if(event.getEntity().isDead())
            if(plugin.is1_8_R3()) {
                event.getEntity().setHealth(event.getEntity().getMaxHealth());
            }else{
                event.getEntity().setHealth(event.getEntity().getMaxHealth());
            }
        event.setDeathMessage("");
        this.onDeath(event.getEntity());
    }




    public void onDeath(final Player player) {
        if(getGameState() == GameState.STARTING){
            player.teleport(this.getStartLocation());
            return;
        }
        if(getGameState() == GameState.ENDING || getGameState() == GameState.RESTARTING){
            player.getInventory().clear();
            player.setFlying(false);
            player.setAllowFlight(false);
            User user = UserManager.getUser(player.getUniqueId());
            user.setAllowDoubleJump(false);
            user.setInt("orbs",0);
            player.teleport(this.getEndLocation());
            return;
        }
        User user = UserManager.getUser(player.getUniqueId());
        addStat(player, "deaths");

        if (user.isFakeDead()) {
            player.setAllowFlight(true);
            if(plugin.is1_8_R3()) {
                player.setGameMode(GameMode.SPECTATOR);
            }else{
                player.setGameMode(GameMode.SURVIVAL);
            }
            teleportToStartLocation(player);

         //   player.setFlying(true);

        } else {
            teleportToStartLocation(player);
            user.setSpectator(true);
            if(plugin.is1_8_R3()) {
                player.setGameMode(GameMode.SPECTATOR);
            }else{
                player.setGameMode(GameMode.SURVIVAL);
            }
            user.setFakeDead(true);
            user.setInt("orbs", 0);
            hidePlayer(player);
            player.setAllowFlight(true);
            if(plugin.is1_8_R3()){
                MessageHandler.sendTitleMessage(player, getChatManager().getMessage("DEAD-SCREEN"));
                MessageHandler.sendActionBarMessage(player, getChatManager().getMessage("Died-Respawn-In-Next-Wave"));

            }else {
                player.sendMessage(getChatManager().getMessage("You-Are-Spectator", ChatColor.RED + "You're now a spectator! You can fly now!"));
            }
            getChatManager().broadcastDeathMessage(player);

                    teleportToStartLocation(player);

                    player.setAllowFlight(true);
                    player.setFlying(true);



        }

    }

    public void hidePlayersOutsideTheGame(Player player){
        for(Player players:youtuberInvasion.getServer().getOnlinePlayers()){
            if(getPlayers().contains(players))
                continue;
            player.hidePlayer(players);
            players.hidePlayer(player);
        }
    }

    @SuppressWarnings("deprecation")
	@Override
    public void leaveAttempt(Player p){

        User user = UserManager.getUser(p.getUniqueId());
        user.setInt("orbs",0);
        p.getInventory().clear();
        ArmorHelper.clearArmor(p);


        this.removePlayer(p);
        if(!user.isSpectator()) {
            getChatManager().broadcastLeaveMessage(p);
        }
        user.setFakeDead(false);
        user.setAllowDoubleJump(false);
        user.setSpectator(false);
        user.removeScoreboard();
        if(user.getKit() instanceof GolemFriend){
            for(IronGolem ironGolem:getIronGolems()){
                if(ironGolem.getCustomName().contains(user.toPlayer().getName()))
                    ironGolem.remove();
            }
        }
      //  if(plugin.isBarEnabled())
        //    BossbarAPI.removeBar(p);

        p.setMaxHealth(20.0);
        p.setFoodLevel(20);
        p.setFlying(false);
        p.setAllowFlight(false);
        for(PotionEffect effect :p.getActivePotionEffects()){
            p.removePotionEffect(effect.getType());
        }
        p.setFireTicks(0);
        if(getPlayers().size() ==0){
            this.setGameState(GameState.RESTARTING);
        }


        p.setGameMode(GameMode.SURVIVAL);
        for(Player players:youtuberInvasion.getServer().getOnlinePlayers()){
            if(plugin.getGameInstanceManager().getGameInstance(players) != null)
                players.showPlayer(p);
                p.showPlayer(players);
        }

        this.teleportToEndLocation(p);
        if(!plugin.isBungeeActivated() && plugin.isInventoryManagerEnabled()) {
            plugin.getInventoryManager().loadInventory(p);

        }




    }

    public void onRespawn(Player player) {
        User user = UserManager.getUser(player.getUniqueId());
        if (user.isFakeDead()) {
            teleportToStartLocation(player);
            player.setAllowFlight(true);
            player.setFlying(true);

        } else {
            teleportToStartLocation(player);
            user.setSpectator(true);
            player.setGameMode(GameMode.SURVIVAL);
            user.setFakeDead(true);
            player.setAllowFlight(true);
            player.setFlying(true);
            user.setInt("orbs", 0);

        }

    }

    public void addRottenFlesh(int i){
        rottenflesh = rottenflesh + i;
    }

    public int getRottenFlesh(){
        return rottenflesh;
    }

    public void removeRottenFlesh(int i){
        rottenflesh = rottenflesh-i;
        if(rottenflesh<0){
            rottenflesh=0;
        }
    }

    public boolean checkLevelUpRottenFlesh(){
        if(rottenfleshlevel == 0 && rottenflesh >50){
            rottenfleshlevel = 1;
            return true;
        }
        if(rottenfleshlevel*10*getPlayers().size()+10<rottenflesh){
            rottenfleshlevel++;
            return true;
        }
        return false;
    }



    @EventHandler
    public void onrespawnEvent(PlayerRespawnEvent event) {
        if (getPlayers().contains(event.getPlayer())) {
            this.onRespawn(event.getPlayer());
            event.setRespawnLocation(this.getStartLocation());
        }
    }

    public void bringDeathPlayersBack() {
        for (Player player : getPlayers()) {
            if (!getPlayersLeft().contains(player)) {

                User user = UserManager.getUser(player.getUniqueId());
                user.setFakeDead(false);
                user.setSpectator(false);


                teleportToStartLocation(player);
                player.setFlying(false);
                player.setAllowFlight(false);
                player.setGameMode(GameMode.SURVIVAL);
                this.showPlayers();
                player.sendMessage(getChatManager().getMessage("You're-Back-In-Game",ChatColor.GREEN + "You're not a spectator anymore! You're back in the game!"));
            }

        }
    }


}
