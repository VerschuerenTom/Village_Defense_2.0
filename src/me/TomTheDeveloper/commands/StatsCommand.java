package me.TomTheDeveloper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.TomTheDeveloper.User;
import me.TomTheDeveloper.handlers.ChatManager;
import me.TomTheDeveloper.handlers.UserManager;

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
            player.sendMessage(ChatManager.colorMessage("Commands.Stats-Command.Header"));
            player.sendMessage(ChatManager.colorMessage("Commands.Stats-Command.Kills"));
            player.sendMessage(ChatManager.colorMessage("Commands.Stats-Command.Deaths"));
            player.sendMessage(ChatManager.colorMessage("Commands.Stats-Command.Games-Played"));
            player.sendMessage(ChatManager.colorMessage("Commands.Stats-Command.Highest-Wave") + user.getInt("highestwave"));
            player.sendMessage(ChatManager.colorMessage("Commands.Stats-Command.Level") + user.getInt("level"));
            player.sendMessage(ChatManager.colorMessage("Commands.Stats-Command.Exp") + user.getInt("xp"));
            player.sendMessage(ChatManager.colorMessage("Commands.Stats-Command.Next-Level-Exp") + Math.ceil(Math.pow(50 * user.getInt("level"), 1.5)));
            player.sendMessage(ChatManager.colorMessage("Commands.Stats-Command.Footer"));
            return true;
    	}
    	return false;
    }
}
