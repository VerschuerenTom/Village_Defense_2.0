package me.TomTheDeveloper.Stats;

import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.TomTheDeveloper.User;
import me.TomTheDeveloper.YoutuberInvasion;
import me.TomTheDeveloper.Handlers.ConfigurationManager;
import me.TomTheDeveloper.Handlers.UserManager;

/**
 * Created by Tom on 17/06/2015.
 */
public class FileStats {

    public YoutuberInvasion plugin;
    private FileConfiguration config;

    public FileStats(YoutuberInvasion plugin) {
        this.plugin = plugin;
        config = ConfigurationManager.getConfig("STATS");
    }


    public void saveStat(Player player, String stat) {
        User user = UserManager.getUser(player.getUniqueId());
        config.set(player.getUniqueId().toString() + "." + stat, user.getInt(stat));
        try {
            config.save(ConfigurationManager.getFile("STATS"));
        } catch (IOException e) {
            e.printStackTrace();
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
