package me.TomTheDeveloper.Rewards;

import me.TomTheDeveloper.Game.GameInstance;
import me.TomTheDeveloper.Handlers.ConfigurationManager;
import me.TomTheDeveloper.InvasionInstance;
import me.TomTheDeveloper.YoutuberInvasion;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.*;

/**
 * Created by Tom on 30/01/2016.
 */
public class RewardsHandler {


    private FileConfiguration config;
    private YoutuberInvasion plugin;
    private boolean enabled = false;


    public RewardsHandler(YoutuberInvasion plugin) {
        this.plugin = plugin;

        if (!plugin.getConfig().contains("Rewards-Enabled")) {
            plugin.getConfig().set("Rewards-Enabled", false);
            plugin.saveConfig();
        }
        enabled = plugin.getConfig().getBoolean("Rewards-Enabled");
        File file = new File(plugin.getDataFolder() + File.separator + "rewards.yml");
        if (!file.exists()) {
            System.out.print("Creating new file rewards.yml");
            System.out.print("Writing to file rewards.yml");
            InputStream inputStream = RewardsHandler.class.getResourceAsStream("rewards.yml");
            OutputStream outputStream = null;
            try {
                outputStream =
                        new FileOutputStream(file);
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                System.out.println("Done!");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }
        }
        config = ConfigurationManager.getConfig("rewards");
    }

    public void performEndGameRewards(InvasionInstance gameInstance) {
        for (String string : config.getStringList("rewards.endgame")) {
            performCommand(gameInstance, string);
        }
    }

    public void performEndWaveRewards(InvasionInstance invasionInstance, int wave) {
        if(!config.contains("rewards.endwave." + wave))
            return;
        for (String string : config.getStringList("rewards.endwave." + wave))
            performCommand(invasionInstance, string);
    }

    public void performZombieKillReward(Player player) {
        for (String string : config.getStringList("rewards.zombiekill")) {
            performCommand(player, string);
        }
    }


    private void performCommand(InvasionInstance gameInstance, String string) {
        if (!enabled)
            return;
        String command = string.replaceAll("%ARENA-ID%", gameInstance.getID())
                .replaceAll("%MAPNAME%", gameInstance.getMapName())
                .replaceAll("%PLAYERAMOUNT%", String.valueOf(gameInstance.getPlayers().size()))
                .replaceAll("%WAVE%", String.valueOf(gameInstance.getWave()));
        for (Player player : gameInstance.getPlayers()) {
            if (command.contains("p:")) {
                player.performCommand(command.substring(2, command.length())
                        .replaceAll("%PLAYER%", player.getName()));
            } else {
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command.replaceAll("%PLAYER%", player.getName()));
            }
        }

    }

    private void performCommand(Player player, String string) {
        if (!enabled)
            return;
        GameInstance gameInstance = plugin.getGameAPI().getGameInstanceManager().getGameInstance(player);
        if (gameInstance == null)
            return;
        String command = string.replaceAll("%ARENA-ID%", gameInstance.getID())
                .replaceAll("%MAPNAME%", gameInstance.getMapName())
                .replaceAll("%PLAYERAMOUNT%", String.valueOf(gameInstance.getPlayers().size()))
                .replaceAll("%WAVE%", String.valueOf(((InvasionInstance) gameInstance).getWave()));
        if (command.contains("p:")) {
            player.performCommand(command.substring(2, command.length())
                    .replaceAll("%PLAYER%", player.getName()));
        } else {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command.replaceAll("%PLAYER%", player.getName()));
        }

    }


}
