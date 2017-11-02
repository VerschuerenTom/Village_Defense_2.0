package me.TomTheDeveloper.Handlers;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.TomTheDeveloper.Game.GameInstance;
import me.TomTheDeveloper.KitAPI.BaseKits.Kit;
import me.TomTheDeveloper.Utils.Util;

/**
 * Created by Tom on 27/07/2014.
 */
public class ChatManager {

	/*
	 * TODO, FIXME
	 * Remove this useless code
	 */
	
    public static ChatColor PREFIX = ChatColor.GOLD;
    public static ChatColor NORMAL = ChatColor.GRAY;
    public static ChatColor HIGHLIGHTED = ChatColor.AQUA;
    public static final String PLUGINPREFIX = "§a[VillageDefense] ";

    private static HashMap<String, String> messages = new HashMap<String, String>();

    private static GameInstance gameInstance;

    public ChatManager(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
    }

    public void broadcastJoinMessage(Player p){
        if(messages.containsKey("JoinMessage")){
            gameInstance.getChatManager().broadcastMessage("JoinMessage", p);
        } else if(messages.containsKey("Join")){
            gameInstance.getChatManager().broadcastMessage("Join", p);
        } else{
        	String message = formatMessage("§b" + p.getName() + "§7 joined the Game! (" + gameInstance.getPlayers().size() + "/" + gameInstance.getMAX_PLAYERS() + ")");
        	for(Player player:gameInstance.getPlayers()) {
        		player.sendMessage(PLUGINPREFIX + message);
        	}
        }
    }

    public void broadcastLeaveMessage(Player p){
        if(messages.containsKey("LeaveMessage")){
            gameInstance.getChatManager().broadcastMessage("LeaveMessage", p);
        }else if(messages.containsKey("Leave")){
            gameInstance.getChatManager().broadcastMessage("Leave", p);
        }else {
        	String message = formatMessage("§b" + p.getName() + "§7 left the Game! (" + gameInstance.getPlayers().size() + "/" + gameInstance.getMAX_PLAYERS() + ")");
        	for(Player player:gameInstance.getPlayers()) {
        		player.sendMessage(PLUGINPREFIX + message);
        	}
        }
    }

    public void broadcastDeathMessage(Player player){
        if(messages.containsKey("DeathMessage")){
            gameInstance.getChatManager().broadcastMessage("DeathMessage", player);
        } else if(messages.containsKey("Death")) {
            gameInstance.getChatManager().broadcastMessage("Death", player);
        } else{
        	String message = formatMessage("§b" + player.getName() + "§7 died!");
        	for(Player p : gameInstance.getPlayers()) {
        		p.sendMessage(PLUGINPREFIX + message);
        	}
        }
    }

    public void broadcastMessage(String messageID, OfflinePlayer player){
        String message = formatMessage(messages.get(messageID), player);
        for(Player player1:gameInstance.getPlayers()) {
            player1.sendMessage(PLUGINPREFIX + message);
        }
    }

    public static String formatMessage(String message, Player[] players){
        String returnstring = message;
        for(Player player:players){
           returnstring = returnstring.replaceFirst("%PLAYER%", player.getName());
        }
         returnstring = returnstring.replaceAll("%TIME%", Integer.toString(gameInstance.getTimer()));
        returnstring = returnstring.replaceAll("%FORMATTEDTIME%", Util.formatIntoMMSS((gameInstance.getTimer())));
        returnstring = returnstring.replaceAll("(&([a-f0-9]))", "\u00A7$2");
        returnstring =  returnstring.replaceAll("%PLAYERSIZE%", Integer.toString(gameInstance.getPlayers().size()));
        returnstring =  returnstring.replaceAll("%MAXPLAYERS%", Integer.toString(gameInstance.getMAX_PLAYERS()));
        returnstring = returnstring.replaceAll("%MINPLAYERS%", Integer.toString(gameInstance.getMIN_PLAYERS()));

        returnstring = returnstring.replaceAll("&l", ChatColor.BOLD.toString());
        returnstring = returnstring.replaceAll("&n", ChatColor.UNDERLINE.toString());
        returnstring = returnstring.replaceAll("&m", ChatColor.STRIKETHROUGH.toString());
        returnstring = returnstring.replaceAll("&r", ChatColor.RESET.toString());
        returnstring = returnstring.replaceAll("&k", ChatColor.MAGIC.toString());
        return returnstring;
    }

    public static String formatMessage(String message, int integer){
        String returnstring = message;
         returnstring = returnstring.replaceAll("%NUMBER%",Integer.toString(integer));

        returnstring = returnstring.replaceAll("%TIME%", Integer.toString(gameInstance.getTimer()));
        returnstring = returnstring.replaceAll("%FORMATTEDTIME%", Util.formatIntoMMSS((gameInstance.getTimer())));
        returnstring = returnstring.replaceAll("(&([a-f0-9]))", "\u00A7$2");
        returnstring =  returnstring.replaceAll("%PLAYERSIZE%", Integer.toString(gameInstance.getPlayers().size()));
        returnstring =  returnstring.replaceAll("%MAXPLAYERS%", Integer.toString(gameInstance.getMAX_PLAYERS()));
        returnstring = returnstring.replaceAll("%MINPLAYERS%", Integer.toString(gameInstance.getMIN_PLAYERS()));

        returnstring = returnstring.replaceAll("&l", ChatColor.BOLD.toString());
        returnstring = returnstring.replaceAll("&n", ChatColor.UNDERLINE.toString());
        returnstring = returnstring.replaceAll("&m", ChatColor.STRIKETHROUGH.toString());
        returnstring = returnstring.replaceAll("&r", ChatColor.RESET.toString());
        returnstring = returnstring.replaceAll("&k", ChatColor.MAGIC.toString());
        return returnstring;
    }

    public static String formatMessage(String message){
        String returnstring = message;

        returnstring = returnstring.replaceAll("%TIME%", Integer.toString(gameInstance.getTimer()));
        returnstring = returnstring.replaceAll("%FORMATTEDTIME%", Util.formatIntoMMSS((gameInstance.getTimer())));
        returnstring = returnstring.replaceAll("(&([a-f0-9]))", "\u00A7$2");
        returnstring =  returnstring.replaceAll("%PLAYERSIZE%", Integer.toString(gameInstance.getPlayers().size()));
        returnstring =  returnstring.replaceAll("%MAXPLAYERS%", Integer.toString(gameInstance.getMAX_PLAYERS()));
        returnstring = returnstring.replaceAll("%MINPLAYERS%", Integer.toString(gameInstance.getMIN_PLAYERS()));
        returnstring = returnstring.replaceAll("&l", ChatColor.BOLD.toString());
        returnstring = returnstring.replaceAll("&n", ChatColor.UNDERLINE.toString());
        returnstring = returnstring.replaceAll("&m", ChatColor.STRIKETHROUGH.toString());
        returnstring = returnstring.replaceAll("&r", ChatColor.RESET.toString());
        returnstring = returnstring.replaceAll("&k", ChatColor.MAGIC.toString());
        return returnstring;
    }

    public static String formatMessage(String message, Player player){
        String returnstring = message;
        returnstring = returnstring.replaceAll("%PLAYER%", player.getName());
        returnstring = returnstring.replaceAll("%TIME%", Integer.toString(gameInstance.getTimer()));
        returnstring = returnstring.replaceAll("%FORMATTEDTIME%", Util.formatIntoMMSS((gameInstance.getTimer())));
        returnstring = returnstring.replaceAll("(&([a-f0-9]))", "\u00A7$2");
        returnstring =  returnstring.replaceAll("%PLAYERSIZE%", Integer.toString(gameInstance.getPlayers().size()));
        returnstring =  returnstring.replaceAll("%MAXPLAYERS%", Integer.toString(gameInstance.getMAX_PLAYERS()));
        returnstring = returnstring.replaceAll("%MINPLAYERS%", Integer.toString(gameInstance.getMIN_PLAYERS()));
        returnstring = returnstring.replaceAll("&l", ChatColor.BOLD.toString());
        returnstring = returnstring.replaceAll("&n", ChatColor.UNDERLINE.toString());
        returnstring = returnstring.replaceAll("&m", ChatColor.STRIKETHROUGH.toString());
        returnstring = returnstring.replaceAll("&r", ChatColor.RESET.toString());
        returnstring = returnstring.replaceAll("&k", ChatColor.MAGIC.toString());
        return returnstring;
    }

    public String formatMessage(String message, OfflinePlayer player){
        String returnstring = message;
        returnstring = returnstring.replaceAll("%PLAYER%", player.getName());
        returnstring = returnstring.replaceAll("%TIME%", Integer.toString(gameInstance.getTimer()));
        returnstring = returnstring.replaceAll("%FORMATTEDTIME%", Util.formatIntoMMSS((gameInstance.getTimer())));
        returnstring = returnstring.replaceAll("(&([a-f0-9]))", "\u00A7$2");
        returnstring =  returnstring.replaceAll("%PLAYERSIZE%", Integer.toString(gameInstance.getPlayers().size()));
        returnstring =  returnstring.replaceAll("%MAXPLAYERS%", Integer.toString(gameInstance.getMAX_PLAYERS()));
        returnstring = returnstring.replaceAll("%MINPLAYERS%", Integer.toString(gameInstance.getMIN_PLAYERS()));
        returnstring = returnstring.replaceAll("&l", ChatColor.BOLD.toString());
        returnstring = returnstring.replaceAll("&n", ChatColor.UNDERLINE.toString());
        returnstring = returnstring.replaceAll("&m", ChatColor.STRIKETHROUGH.toString());
        returnstring = returnstring.replaceAll("&r", ChatColor.RESET.toString());
        returnstring = returnstring.replaceAll("&k", ChatColor.MAGIC.toString());
        return returnstring;
    }

    public String formatMessage(String message, String playername){
        String returnstring = message;
        returnstring = returnstring.replaceAll("%PLAYER%", playername);
        returnstring = returnstring.replaceAll("%TIME%", Integer.toString(gameInstance.getTimer()));
        returnstring = returnstring.replaceAll("%FORMATTEDTIME%", Util.formatIntoMMSS((gameInstance.getTimer())));
        returnstring = returnstring.replaceAll("(&([a-f0-9]))", "\u00A7$2");
        returnstring =  returnstring.replaceAll("%PLAYERSIZE%", Integer.toString(gameInstance.getPlayers().size()));
        returnstring =  returnstring.replaceAll("%MAXPLAYERS%", Integer.toString(gameInstance.getMAX_PLAYERS()));
        returnstring = returnstring.replaceAll("%MINPLAYERS%", Integer.toString(gameInstance.getMIN_PLAYERS()));
        returnstring = returnstring.replaceAll("&l", ChatColor.BOLD.toString());
        returnstring = returnstring.replaceAll("&n", ChatColor.UNDERLINE.toString());
        returnstring = returnstring.replaceAll("&m", ChatColor.STRIKETHROUGH.toString());
        returnstring = returnstring.replaceAll("&r", ChatColor.RESET.toString());
        returnstring = returnstring.replaceAll("&k", ChatColor.MAGIC.toString());
        return returnstring;
    }

    public static String formatMessage(String message, Kit kit){
        String returnstring = message;
        returnstring = returnstring.replaceFirst("%KIT%", kit.getName());
        return returnstring.replaceAll("(&([a-f0-9]))", "\u00A7$2");
    }
    
}

