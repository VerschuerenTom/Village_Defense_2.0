package me.TomTheDeveloper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.TomTheDeveloper.User;
import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;

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
            player.sendMessage(ChatManager.colorMessage("STATS-AboveLine"));
            player.sendMessage(ChatManager.colorMessage("STATS-Kills"));
            player.sendMessage(ChatManager.colorMessage("STATS-Deaths"));
            player.sendMessage(ChatManager.colorMessage("STATS-Games-Played"));
            player.sendMessage(ChatManager.colorMessage("STATS-Hihgest-Wave") + user.getInt("highestwave"));
            player.sendMessage(ChatManager.colorMessage("STATS-Level") + user.getInt("level"));
            player.sendMessage(ChatManager.colorMessage("STATS-Exp") + user.getInt("xp"));
            player.sendMessage(ChatManager.colorMessage("STATS-Next-Level-Exp") + Math.ceil(Math.pow(50 * user.getInt("level"), 1.5)));
            player.sendMessage(ChatManager.colorMessage("STATS-UnderLinen"));
            return true;
    	}
    	return false;
    }
}
