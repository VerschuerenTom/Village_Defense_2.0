package me.tomthedeveloper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.tomthedeveloper.User;
import me.tomthedeveloper.handlers.ChatManager;
import me.tomthedeveloper.handlers.UserManager;

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
            player.sendMessage(ChatManager.colorMessage("commands.Stats-Command.Header"));
            player.sendMessage(ChatManager.colorMessage("commands.Stats-Command.Kills"));
            player.sendMessage(ChatManager.colorMessage("commands.Stats-Command.Deaths"));
            player.sendMessage(ChatManager.colorMessage("commands.Stats-Command.Games-Played"));
            player.sendMessage(ChatManager.colorMessage("commands.Stats-Command.Highest-Wave") + user.getInt("highestwave"));
            player.sendMessage(ChatManager.colorMessage("commands.Stats-Command.Level") + user.getInt("level"));
            player.sendMessage(ChatManager.colorMessage("commands.Stats-Command.Exp") + user.getInt("xp"));
            player.sendMessage(ChatManager.colorMessage("commands.Stats-Command.Next-Level-Exp") + Math.ceil(Math.pow(50 * user.getInt("level"), 1.5)));
            player.sendMessage(ChatManager.colorMessage("commands.Stats-Command.Footer"));
            return true;
    	}
    	return false;
    }
}
