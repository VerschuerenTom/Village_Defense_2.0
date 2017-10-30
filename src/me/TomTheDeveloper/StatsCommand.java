package me.TomTheDeveloper;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;

/**
 * Created by Tom on 18/08/2014.
 */
public class StatsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))
            return true;
        if (!(command.getLabel().equalsIgnoreCase("STATS")))
            return true;
        Player player = (Player) commandSender;
        User user = UserManager.getUser(player.getUniqueId());
        player.sendMessage(ChatManager.getSingleMessage("STATS-AboveLine", ChatColor.BOLD + "-----YOUR STATS----- "));
        player.sendMessage(ChatManager.getSingleMessage("STATS-Kills", ChatColor.GREEN + "Kills: " + ChatColor.YELLOW) + user.getInt("kills"));
        player.sendMessage(ChatManager.getSingleMessage("STATS-Deaths", ChatColor.GREEN + "Deaths: " + ChatColor.YELLOW) + user.getInt("deaths"));
        player.sendMessage(ChatManager.getSingleMessage("STATS-Games-Played", ChatColor.GREEN + "Games played: " + ChatColor.YELLOW) + user.getInt("gamesplayed"));
        player.sendMessage(ChatManager.getSingleMessage("STATS-Hihgest-Wave", ChatColor.GREEN + "Highest wave: " + ChatColor.YELLOW) + user.getInt("highestwave"));
        player.sendMessage(ChatManager.getSingleMessage("STATS-Level", ChatColor.GREEN + "Level: " + ChatColor.YELLOW) + user.getInt("level"));
        player.sendMessage(ChatManager.getSingleMessage("STATS-Exp", ChatColor.GREEN + "Exp: " + ChatColor.YELLOW) + user.getInt("xp"));
        player.sendMessage(ChatManager.getSingleMessage("STATS-Next-Level-Exp", ChatColor.GREEN + "Next Level Exp " + ChatColor.YELLOW) + Math.ceil(Math.pow(50 * user.getInt("level"), 1.5)));
        player.sendMessage(ChatManager.getSingleMessage("STATS-UnderLinen", ChatColor.BOLD + "--------------------"));
        return true;
    }
}
