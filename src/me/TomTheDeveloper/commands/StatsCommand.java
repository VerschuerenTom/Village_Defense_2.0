package me.TomTheDeveloper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.TomTheDeveloper.User;
import me.TomTheDeveloper.Handlers.UserManager;
import pl.Plajer.GameAPI.LanguageManager;

/**
 * Created by Tom on 18/08/2014.
 */
public class StatsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
    	if(cmd.getName().equalsIgnoreCase("stats")) {
    		if(sender instanceof ConsoleCommandSender) {
    			return true;
    		}
    		Player player = (Player) sender;
            User user = UserManager.getUser(player.getUniqueId());
            player.sendMessage(LanguageManager.getLanguageFile().get("STATS-AboveLine").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2"));
            player.sendMessage(LanguageManager.getLanguageFile().get("STATS-Kills").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2") + user.getInt("kills"));
            player.sendMessage(LanguageManager.getLanguageFile().get("STATS-Deaths").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2") + user.getInt("deaths"));
            player.sendMessage(LanguageManager.getLanguageFile().get("STATS-Games-Played").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2") + user.getInt("gamesplayed"));
            player.sendMessage(LanguageManager.getLanguageFile().get("STATS-Hihgest-Wave").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2") + user.getInt("highestwave"));
            player.sendMessage(LanguageManager.getLanguageFile().get("STATS-Level").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2") + user.getInt("level"));
            player.sendMessage(LanguageManager.getLanguageFile().get("STATS-Exp").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2") + user.getInt("xp"));
            player.sendMessage(LanguageManager.getLanguageFile().get("STATS-Next-Level-Exp").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2") + Math.ceil(Math.pow(50 * user.getInt("level"), 1.5)));
            player.sendMessage(LanguageManager.getLanguageFile().get("STATS-UnderLinen").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2"));
            return true;
    	}
    	return false;
    }
}
