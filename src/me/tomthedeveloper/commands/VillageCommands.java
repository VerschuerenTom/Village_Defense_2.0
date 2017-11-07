package me.tomthedeveloper.commands;

public class VillageCommands {//implements CommandExecutor {

	/*private GameAPI plugin;
	
	public VillageCommands(GameAPI plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("villagedefense")) {
			if(args.length == 1) {
				/*
				 * leave command
				 *
				if(args[0].equalsIgnoreCase("leave")) {
					if(!(sender instanceof Player)) {
						sender.sendMessage("Only player can do that!");
						return true;
					}
					Player p = (Player) sender;
					if(plugin.getGameInstanceManager().getGameInstance(p) == null) {
			            p.sendMessage("You are not in arena!");
			            return true;
			        }
					if(plugin.isBungeeActivated()) {
			            bungee.connectToHub(p);
			            System.out.print(p.getName() + " is teleported to the Hub Server");
			            return true;
			        } else {
			            plugin.getGameInstanceManager().getGameInstance(p).teleportToEndLocation(p);
			            plugin.getGameInstanceManager().getGameInstance(p).leaveAttempt(p);
			            System.out.print(p.getName() + " has left the arena! He is teleported to the end location.");
			            return true;
			        }
				}
				*
				 * stats command
				 *
				if(args[0].equals("stats")) {
		    		if(!(sender instanceof Player)) {
		    			sender.sendMessage("Only player can do that!");
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
				/*
				 * addsigns command
				 *
				if(args[0].equalsIgnoreCase("addsigns")){
					if(!(sender instanceof Player)) {
						sender.sendMessage("Only player can do that!");
						return true;
					}
					Player p = (Player) sender;
					Selection selection = plugin.getWorldEditPlugin().getSelection(p);
		            int i = plugin.getPlugin().getConfig().getConfigurationSection("signs").getKeys(false).size();
		             i = i+2;
		            if(selection == null){
		                p.sendMessage("You have to select a region with 1 or more signs in it with World Edit before clicking on the sign");
		                return true;
		            }
		            int counter = 0;
		            if(selection instanceof CuboidSelection){
		                CuboidSelection cuboidSelection = (CuboidSelection) selection;
		                Vector min = cuboidSelection.getNativeMinimumPoint();
		                Vector max = cuboidSelection.getNativeMaximumPoint();
		                for(int x = min.getBlockX();x <= max.getBlockX(); x=x+1){
		                    for(int y = min.getBlockY();y <= max.getBlockY(); y=y+1){
		                        for(int z = min.getBlockZ();z <= max.getBlockZ(); z=z+1){
		                            Location tmpblock = new Location(p.getWorld(), x, y, z);
		                            if(tmpblock.getBlock().getState() instanceof Sign && !getSigns().contains(tmpblock.getBlock().getState())){
		                                boolean b = plugin.getSignManager().registerSign((Sign) tmpblock.getBlock().getState());
		                                plugin.saveLoc("signs." + i, tmpblock);
		                                counter++;
		                                i++;
		                            }

		                        }
		                    }
		                }
		            } else{
		                if(selection.getMaximumPoint().getBlock().getState() instanceof Sign&& !getSigns().contains(selection.getMaximumPoint().getBlock().getState())){
		                    plugin.getSignManager().registerSign((Sign) selection.getMaximumPoint().getBlock().getState());
		                    plugin.saveLoc("signs." + i, selection.getMaximumPoint());
		                    counter++;
		                    i++;
		                }
		                if(selection.getMinimumPoint().getBlock().getState() instanceof Sign&& !getSigns().contains(selection.getMinimumPoint().getBlock().getState())){
		                    plugin.getSignManager().registerSign((Sign) selection.getMinimumPoint().getBlock().getState());
		                    plugin.saveLoc("signs." + i, selection.getMinimumPoint());
		                    counter++;
		                    i++;
		                }
		            }
				}
				/*
				 * smartstop command
				 *
				if(args[0].equalsIgnoreCase("smartstop") && sender.isOp()){
		            GameAPI.setRestart();
		            for(Player player : Bukkit.getServer().getOnlinePlayers()){
		                player.sendMessage(ChatColor.DARK_GREEN + "RESTARTING THE SERVER AFTER ALL THE GAMES ENDED!");
		                sender.sendMessage(ChatColor.GRAY + "Restarting process started!");

		                Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin.getPlugin(), new Runnable() {
		                    @Override
		                    public void run() {
		                        boolean b = true;
		                      for(GameInstance gameInstance: plugin.getGameInstanceManager().getGameInstances()){

		                          if(gameInstance.getGameState() == GameState.INGAME || gameInstance.getGameState() == GameState.STARTING)
		                            b = false;
		                      }
		                        if(b == true){
		                            plugin.getPlugin().getServer().shutdown();
		                        }
		                        b = true;

		                    }
		                }, 20L, 1L);
		            }
		        }
			}
		}
		return false;
	}
	
    public List<Sign> getSigns(){
        List<Sign> list = new ArrayList<Sign>();
        for(String s: plugin.getPlugin().getConfig().getConfigurationSection("signs").getKeys(false)){
            s = "signs." + s;
            Location location = plugin.getLocation(s);
            if(location.getBlock().getState() instanceof Sign)
                list.add((Sign) location.getBlock().getState());
        }
        return list;
    }*/
	
}
