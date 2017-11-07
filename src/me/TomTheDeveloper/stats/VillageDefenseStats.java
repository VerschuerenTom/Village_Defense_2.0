package me.TomTheDeveloper.stats;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.TomTheDeveloper.VillageDefense;
import me.TomTheDeveloper.handlers.ConfigurationManager;
import me.TomTheDeveloper.handlers.UserManager;

/**
 * Created by Tom on 30/12/2015.
 * For LeaderHeads.
 */
public enum VillageDefenseStats {
    KILLS("kills"), DEATHS("deaths"), GAMES_PLAYED("gamesplayed"), HIGHEST_WAVE("highestwave"), LEVEL("level"), XP("xp");

    public static VillageDefense plugin;
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
