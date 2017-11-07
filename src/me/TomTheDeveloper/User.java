package me.TomTheDeveloper;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.TomTheDeveloper.KitAPI.basekits.Kit;
import me.TomTheDeveloper.game.GameInstance;

/**
 * Created by Tom on 27/07/2014.
 */
public class User {

    private ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    private Scoreboard scoreboard;
    private static long COOLDOWNCOUNTER = 0;
    private UUID uuid;
    private UUID lasthitted;
    private int power;
    private int exp;
    private boolean fakedead = false;
    private boolean spectator = false;
    private boolean doublejump = false;
    private boolean hasdoublejumped = false;
    public static GameAPI plugin;
    private Kit kit;
    private HashMap<String, Integer> ints = new HashMap<String, Integer>();
    private HashMap<String, Long> cooldowns = new HashMap<String, Long>();
    private HashMap<String, Object> objects = new HashMap<String, Object>();





    public User(UUID uuid){
        scoreboard = scoreboardManager.getNewScoreboard();
        this.uuid = uuid;

        kit = plugin.getKitHandler().getDefaultKit();
    }

    public void setScoreboard(Scoreboard scoreboard){
        Bukkit.getPlayer(uuid).setScoreboard(scoreboard);
    }

    public Kit getKit(){
        if(kit == null){
            throw new NullPointerException("User has no kit!");
        }else
            return kit;
    }

    public Object getObject(String s){
        if(objects.containsKey(s))
            return objects.get(s);
        return null;
    }

    public void setObject(Object object, String s){
        objects.put(s,object);
    }

    public void setKit(Kit kit){
        this.kit = kit;
    }

    public Scoreboard getScoreboard(){
        return scoreboard;
    }

    public boolean isInInstance(){
        if(plugin.getGameInstanceManager().isInGameInstance(Bukkit.getPlayer(uuid)))
            return true;
        else
            return false;
    }

    public GameInstance getGameInstance(){
        return plugin.getGameInstanceManager().getGameInstance(Bukkit.getPlayer(uuid));
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getPower(){
        return power;
    }

    public void setPower(int power){
        this.power = power;
    }

    public void addPower(){
        setPower(getPower() + 1);
    }

    public void addPower(int i){
        setPower(getPower() + i);
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void addExp(int exp){
        setExp(getExp() + exp);
    }

    public void addExp(){
        setExp(getExp() + 1);
    }

    public void setFakeDead(boolean b){
        fakedead = b;
    }

    public boolean isFakeDead(){
        return fakedead;
    }

    public Player toPlayer(){
        return Bukkit.getServer().getPlayer(uuid);
    }

    public void removePower(int i){
        setPower(getPower() -i);
    }

    public void setSpectator(boolean b){
        spectator = b;
    }

    public boolean isSpectator(){
        return spectator;
    }

    public int getInt(String s){
        if(!ints.containsKey(s)) {
            ints.put(s, 0);
            return 0;
        } else if(ints.get(s) == null){
            return 0;
        }
        return ints.get(s);
    }

    public void removeScoreboard(){
        this.toPlayer().setScoreboard(scoreboardManager.getNewScoreboard());

    }

    public void setInt(String s, int i){
        ints.put(s, i);

    }

    public void addInt(String s, int i){
        ints.put(s, getInt(s)+i);
    }

    private enum Rank{
        VIP, MVP, ELITE;
    }

    public void setAllowDoubleJump(boolean b){
        doublejump = b;
    }

    public static void handleCooldowns(){
       COOLDOWNCOUNTER++;
    }

    public void setCooldown(String s, int seconds){
       cooldowns.put(s, seconds + COOLDOWNCOUNTER);
    }


    public long getCooldown(String s){
        if(!cooldowns.containsKey(s))
            return 0;
        if(cooldowns.get(s) <=COOLDOWNCOUNTER)
            return 0;
        return cooldowns.get(s)-COOLDOWNCOUNTER;
    }

    public void removeInt(String string, int i){
        if(ints.containsKey(string)) {
            ints.put(string, ints.get(string) - i);
        }
    }

    public Player getLastHitted(){
        if(lasthitted == null)
            return null;
        return Bukkit.getPlayer(lasthitted);
    }

    public void setLastHitted(Player player){
        if(player == null){
            lasthitted = null;
            return;
        }
        lasthitted = player.getUniqueId();
    }





}
