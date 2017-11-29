package me.tomthedeveloper.stats;

import me.tomthedeveloper.Main;
import me.tomthedeveloper.handlers.ConfigurationManager;
import me.tomthedeveloper.handlers.UserManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Tom on 30/12/2015.
 * For LeaderHeads.
 */
public enum VillageDefenseStats {
    KILLS("kills"), DEATHS("deaths"), GAMES_PLAYED("gamesplayed"), HIGHEST_WAVE("highestwave"), LEVEL("level"), XP("xp");

    public static Main plugin;
    private String name;


    VillageDefenseStats(String name) {
        this.name = name;
    }

    private static Map sortByValue(Map unsortMap) {
        List list = new LinkedList(unsortMap.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        Map sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<UUID, Integer> getStats() {
        if (plugin.isDatabaseActivated())
            return plugin.getMySQLDatabase().getColumn(name);
        else {
            FileConfiguration config = ConfigurationManager.getConfig("STATS");
            Map<UUID, Integer> stats = new LinkedHashMap<UUID, Integer>();
            for (String string : config.getKeys(false)) {
                stats.put(UUID.fromString(string), config.getInt(string + "." + name));
            }
            return sortByValue(stats);
        }
    }

    public int getStat(Player player) {
        return UserManager.getUser(player.getUniqueId()).getInt(name);
    }
}
