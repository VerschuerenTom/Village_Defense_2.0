package me.TomTheDeveloper.Commands;

/**
 * Created by Tom on 10/08/2014.
 */
public class onReloadCommand { //implements CommandExecutor{

	//unused
    /*private GameAPI plugin;

    public onReloadCommand(GameAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getLabel().equalsIgnoreCase("smartreload") && commandSender.isOp()) {
            GameAPI.setRestart();
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                player.sendMessage(ChatColor.DARK_GREEN + "RELOADING THE SERVER AFTER ALL THE GAMES ENDED!");
                commandSender.sendMessage(ChatColor.GRAY + "Reloading process started!");


            }
        }
        return true;
    }


    public void checkreload() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin.getPlugin(), new Runnable() {
            @Override
            public void run() {
                boolean b = true;
                for (GameInstance gameInstance : plugin.getGameInstanceManager().getGameInstances()) {

                    if (gameInstance.getGameState() == GameState.INGAME || gameInstance.getGameState() == GameState.STARTING)
                        b = false;
                }
                if (b == true) {
                    plugin.getPlugin().getServer().reload();
                } else {
                    checkreload();
                }



            }
        }, 20L);
    }*/

}
