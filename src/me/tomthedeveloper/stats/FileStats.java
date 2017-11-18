package me.tomthedeveloper.stats;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.tomthedeveloper.User;
import me.tomthedeveloper.Main;
import me.tomthedeveloper.handlers.ChatManager;
import me.tomthedeveloper.handlers.ConfigurationManager;
import me.tomthedeveloper.handlers.UserManager;

/**
 * Created by Tom on 17/06/2015.
 */
public class FileStats {

	public Main plugin;
	private FileConfiguration config;

	public FileStats(Main plugin) {
		this.plugin = plugin;
		config = ConfigurationManager.getConfig("STATS");
	}

	public void saveStat(Player player, String stat) {
		User user = UserManager.getUser(player.getUniqueId());
		config.set(player.getUniqueId().toString() + "." + stat, user.getInt(stat));
		try {
			config.save(ConfigurationManager.getFile("STATS"));
		} catch (IOException e) {
			ChatManager.sendErrorHeader("saving STATS.yml file");
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("§cDon't panic! Try to do this steps:");
			Bukkit.getConsoleSender().sendMessage("§c- restart the server");
			Bukkit.getConsoleSender().sendMessage("§c- create blank file named STATS.yml");
			Bukkit.getConsoleSender().sendMessage("§c- contact the developer");
		}
	}

	public void loadStat(Player player, String stat) {
		User user = UserManager.getUser(player.getUniqueId());
		if (config.contains(player.getUniqueId().toString() + "." + stat))
			user.setInt(stat, config.getInt(player.getUniqueId().toString() + "." + stat));
		else
			user.setInt(stat, 0);
	}

}
