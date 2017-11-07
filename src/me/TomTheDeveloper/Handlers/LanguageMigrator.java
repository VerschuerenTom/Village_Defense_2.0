package me.TomTheDeveloper.handlers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;

public class LanguageMigrator {

	private static List<String> oldmessages = 
			Arrays.asList("Teleported-To-The-Lobby", "No-Arena-Like-That", "STATS-AboveLine", "STATS-UnderLinen", "STATS-Kills", "STATS-Deaths", "STATS-Games-Played", "STATS-Hihgest-Wave", "STATS-Level", "STATS-Exp", "STATS-Next-Level-Exp", "SCOREBOARD-Header", "SCOREBOARD-Villagers", "SCOREBOARD-Zombies", "SCOREBOARD-Players-Left" , "SCOREBOARD-Min-Players", "SCOREBOARD-Starting-In", "SCOREBOARD-Players", "SCOREBOARD-Next-Wave-In",
					"SCOREBOARD-Orbs", "SCOREBOARD-Rotten-Flesh", "Kit Menu-Title", "KitUnlockedLoreInKitMenu", "KitLockedLoreInKitMenu", "Unlocks-at-level", "Unlock-This-Kit-In-The-Store", "KitChosenButNotUnlockedMessage", "KitChosenMessage", "Kit-Menu-Item-Name", "Open-Kit-Menu", "Ability-Still-On-Cooldown", "Cleaner-Kit-Name", "Cleaner-Kit-Description", "Cleaner-Wand-Name","Cleaner-Item-Lore", "Player-has-cleaned-the-map", "Map-is-already-empty", "You-Can't-Clean-You're-Spectator", "Zombie-Teleporter-Kit-Name", "Zombie-Teleporter-Kit-Description", "Zombie-Teleporter-Name", "Zombie-Teleporter-Lore", "Knight-Kit-Name",
					"Knight-Kit-Description", "Light-Tank-Kit-Name", "Light-Tank-Kit-Description", "Archer-Kit-Name", "Archer-Kit-Description", "Puncher-Kit-Name", "Puncher-Kit-Description", "Healer-Kit-Name", "Healer-Kit-Description", "SuperArcher-Kit-Name", "SuperArcher-Kit-Description",
					"Looter-Kit-Name", "Looter-Kit-Description", "Runner-Kit-Name", "Runner-Kit-Description", "Medium-Tank-Kit", "Medium-Tank-Kit-Description","Worker-Kit-Name", "Worker-Kit-Description", "Door-Placed", "Dog-Friend-Kit-Name", "Dog-Friend-Kit-Description","Hardcore-Kit-Name", "Hardcore-Kit-Description", "Golem-Friend-Kit-Name", "Golem-Friend-Kit-Description", "Tornado-Kit-Name",
					"Tornado-Kit-Description", "Tornado-Item-Name", "Tornado-Item-Lore", "Terminator-Kit-Name", "Terminator-Kit-Description","Teleporter-Kit-Name", "Teleporter-Kit-Description", "Teleportion-Menu-Name", "Teleportion-Item-Lore", "Teleportation-Menu-Name","Teleported-To-Villager", "You-Can't-Teleport-You're-Spectator", "Didn't-Found-The-Villager", "Teleported-To-Player","Player-Not-Found", "Heavy-Tank-Kit-Name", "Heavy-Tank-Kit-Description", "Shotbow-Kit-Name", "ShotBow-Kit-Description",
					"The-Blocker-Kit-Name", "Blocker-Kit-Description", "Blocker-Fence-Item-Name", "Blocker-Fence-Item-Lore", "Barrier-Placed",
					"Barrier-Can't-Be-Placed-Here", "PremiumHardcore-Kit-Name", "PremiumHardcore-Kit-Description", "Medic-Kit", "Medic-Kit-Description","The-Bunny-Kit-Name", "Jumper-Kit-Description", "YouAreAlreadyIngame", "NoPermissionToJoinFullGames", "FullGameAlreadyFullWithPermiumPlayers","Dead-Tag-On-Death", "DEAD-SCREEN", "Died-Respawn-In-Next-Wave", "You're-Back-In-Game", "You-Are-Spectator", "You-leveled-up","RottenFleshLevelUp", "Only-Command-Ingame-Is-Leave", "Seconds-Left-Until-Game-Starts", "Waiting-For-Players","Enough-Players-To-Start", "The-Game-Has-Started", "KickedToMakePlaceForPremiumPlayer","YouGotKickedToMakePlaceForAPremiumPlayer", "Join", "Death", "Leave", "Next-Wave-Starts-In",
					"Wave-Started", "A-Villager-Has-Died", "You-Feel-Refreshed", "You-Can't-Ride-Golem-From-Somebody-Else", "Golem-Spawned","Wolf-Spawned", "Zombie-Got-Stuck-In-The-Map", "Spawn-Golem", "Need-More-Orbs-To-Buy-this",
					"Don't-Hit-Me-With-Weapon", "orbs-In-Shop", "All-Players-Have-Died", "All-Villagers-Have-Died","Reached-Wave-X", "Teleporting-To-Lobby-In-10-Seconds", "Teleport-To-EndLocation-In-X-Seconds",
					"Admin-ForceStart-Game", "Admin-Set-Starting-In-To-0", "Admin-Removed-Zombies", "Admin-Removed-Golems","Admin-Removed-Villagers","Admin-Removed-Zombies", "Admin-Changed-Wave");
	private static List<String> migratedmessages = 
			Arrays.asList("Commands.Teleported-To-The-Lobby", "Commands.No-Arena-Like-That", "Commands.Stats-Command.Header", "Commands.Stats-Command.Footer", "Commands.Stats-Command.Kills", "Commands.Stats-Command.Deaths", "Commands.Stats-Command.Games-Played", "Commands.Stats-Command.Highest-Wave", "Commands.Stats-Command.Level", "Commands.Stats-Command.Exp", "Commands.Stats-Command.Next-Level-Exp", "Scoreboard.Header", "Scoreboard.Villagers-Left", "Scoreboard.Zombies-Left", "Scoreboard.Players-Left", "Scoreboard.Minimum-Players", "Scoreboard.Starting-In", "Scoreboard.Players", "Scoreboard.Next-Wave-In",
					"Scoreboard.Orbs", "Scoreboard.Rotten-Flesh", "Kits.Kit-Menu.Title", "Kits.Kit-Menu.Unlocked-Kit-Lore", "Kits.Kit-Menu.Locked-Lores.Locked-Lore", "Kits.Kit-Menu.Locked-Lores.Unlock-At-Level", "Kits.Kit-Menu.Locked-Lores.Unlock-In-Store", "Kits.Not-Unlocked-Message", "Kits.Choose-Message", "Kits.Kit-Menu-Item-Name", "Kits.Open-Kit-Menu", "Kits.Ability-Still-On-Cooldown", "Kits.Cleaner.Kit-Name", "Kits.Cleaner.Kit-Description", "Kits.Cleaner.Game-Item-Name","Kits.Cleaner.Game-Item-Lore", "Kits.Cleaner.Cleaned-Map", "Kits.Cleaner.Nothing-To-Clean", "Kits.Cleaner.Spectator-Warning", "Kits.Zombie-Teleporter.Kit-Name", "Kits.Zombie-Teleporter.Kit-Description", "Kits.Zombie-Teleporter.Game-Item-Name", "Kits.Zombie-Teleporter.Game-Item-Lore", "Kits.Knight.Kit-Name",
					"Kits.Knight.Kit-Description", "Kits.Light-Tank.Kit-Name", "Kits.Light-Tank.Kit-Description", "Kits.Archer.Kit-Name", "Kits.Archer.Kit-Description",  "Kits.Puncher.Kit-Name", "Kits.Puncher.Kit-Description", "Kits.Healer.Kit-Name", "Kits.Healer.Kit-Description", "Kits.Super-Archer.Kit-Name", "Kits.Super-Archer.Kit-Description",
					"Kits.Looter.Kit-Name", "Kits.Looter.Kit-Description", "Kits.Runner.Kit-Name", "Kits.Runner.Kit-Description", "Kits.Medium-Tank.Kit-Name", "Kits.Medium-Tank.Kit-Description","Kits.Worker.Kit-Name", "Kits.Worker.Kit-Description", "Kits.Worker.Game-Item-Place-Message", "Kits.Dog-Friend.Kit-Name", "Kits.Dog-Friend.Kit-Description","Kits.Hardcore.Kit-Name", "Kits.Hardcore.Kit-Description", "Kits.Golem-Friend.Kit-Name", "Kits.Golem-Friend.Kit-Description", "Kits.Tornado.Kit-Name",
					"Kits.Tornado.Kit-Description", "Kits.Tornado.Game-Item-Name","Kits.Tornado.Game-Item-Lore","Kits.Terminator.Kit-Name","Kits.Terminator.Kit-Description","Kits.Teleporter.Kit-Name","Kits.Teleporter.Kit-Description","Kits.Teleporter.Game-Item-Name","Kits.Teleporter.Game-Item-Lore", "Kits.Teleporter.Game-Item-Menu-Name","Kits.Teleporter.Teleported-To-Villager","Kits.Teleporter.Spectator-Warning", "Kits.Teleporter.Villager-Warning", "Kits.Teleporter.Teleported-To-Player","Kits.Teleporter.Player-Not-Found","Kits.Heavy-Tank.Kit-Name","Kits.Heavy-Tank.Kit-Description","Kits.Shot-Bow.Kit-Name","Kits.Shot-Bow.Kit-Description",
					"Kits.Blocker.Kit-Name","Kits.Blocker.Kit-Description","Kits.Blocker.Game-Item-Name","Kits.Blocker.Game-Item-Lore","Kits.Blocker.Game-Item-Place-Message",
					"Kits.Blocker.Game-Item-Place-Fail", "Kits.Premium-Hardcore.Kit-Name", "Kits.Premium-Hardcore.Kit-Description", "Kits.Medic.Kit-Name", "Kits.Medic.Kit-Description","Kits.Bunny.Kit-Name", "Kits.Bunny.Kit-Description","In-Game.Already-Playing","In-Game.Full-Game-No-Permission","In-Game.No-Slots-For-Premium","In-Game.Dead-Tag-On-Death","In-Game.Death-Screen","In-Game.Died-Respawn-In-Next-Wave","In-Game.Back-In-Game", "In-Game.You-Are-Spectator","In-Game.You-Leveled-Up","In-Game.Rotten-Flesh-Level-Up","In-Game.Only-Command-Ingame-Is-Leave","In-Game.Messages.Lobby-Messages.Start-In","In-Game.Messages.Lobby-Messages.Waiting-For-Players","In-Game.Messages.Lobby-Messages.Enough-Players-To-Start","In-Game.Messages.Lobby-Messages.Game-Started","In-Game.Messages.Lobby-Messages.Kicked-For-Premium-Slot","In-Game.Messages.Lobby-Messages.You-Were-Kicked-For-Premium-Slot", "In-Game.Messages.Join","In-Game.Messages.Death","In-Game.Messages.Leave","In-Game.Messages.Next-Wave-In",
					"In-Game.Messages.Wave-Started","In-Game.Messages.Villager-Died","In-Game.Messages.You-Feel-Refreshed","In-Game.Messages.Cant-Ride-Others-Golem", "In-Game.Messages.Golem-Spawned","In-Game.Messages.Wolf-Spawned","In-Game.Messages.Zombie-Got-Stuck-In-The-Map","In-Game.Messages.Shop-Messages.Golem-Item-Name","In-Game.Messages.Shop-Messages.Not-Enough-Orbs",
					"In-Game.Messages.Shop-Messages.Rude-Message","In-Game.Messages.Shop-Messages.Currency-In-Shop","In-Game.Messages.Game-End-Messages.All-Players-Died","In-Game.Messages.Game-End-Messages.All-Villagers-Died","In-Game.Messages.Game-End-Messages.Reached-Wave-X","In-Game.Messages.Game-End-Messages.Teleporting-To-Lobby-In-10-Seconds","In-Game.Messages.Game-End-Messages.Teleporting-To-Lobby-In-X-Seconds",
					"In-Game.Messages.Admin-Messages.Force-Start-Game","In-Game.Messages.Admin-Messages.Set-Starting-In-To-0","In-Game.Messages.Admin-Messages.Removed-Zombies","In-Game.Messages.Admin-Messages.Removed-Golems","In-Game.Messages.Admin-Messages.Removed-Villagers","In-Game.Messages.Admin-Messages.Removed-Zombies","In-Game.Messages.Admin-Messages.Changed-Wave");
	
	public static void initiateMigration() {
		if(LanguageManager.getLanguageMessage("File-Version") != null) {
			return;
		}
		System.out.println("[GameAPI] Initiated language.yml migration process! (File-Version: 1)");
		int counter = 0;
		int nomessages = 0;
		for(String oldmessage : LanguageManager.getLanguageFile().getKeys(false)) {
			if(oldmessages.contains(oldmessage)) {
				for(int i = 0; i < oldmessages.size(); i++) {
					if(oldmessages.get(i).equals(oldmessage)) {
						LanguageManager.getLanguageFile().set(migratedmessages.get(i), LanguageManager.getLanguageFile().get(oldmessage));
						LanguageManager.getLanguageFile().set(oldmessage, null);
						counter++;
					}
				}
			}
		}
		LanguageManager.saveLanguageFile();
		for(String newmessage : migratedmessages) {
			if(!LanguageManager.getLanguageFile().isSet(newmessage)) {
				LanguageManager.getLanguageFile().set(newmessage, "MESSAGE NOT FOUND! PLEASE GENERATE NEW LANGUAGE.YML FILE!");
				System.out.println("[GameAPI] Message " + newmessage + " doesn't exists in your old language.yml!");
				nomessages++;
			}
		}
		LanguageManager.getLanguageFile().set("File-Version", 1);
		LanguageManager.saveLanguageFile();
		System.out.println("[GameAPI] Successfully migrated language.yml to new format! Changed " + counter + " lines!");
		if(nomessages > 0) {
			Bukkit.getConsoleSender().sendMessage("§c[GameAPI] WARNING! Your old language.yml didn't have all messages needed for migration,");
			Bukkit.getConsoleSender().sendMessage("§cplease backup 'language.yml' file and generate new to copy needed messages to file from backup!");
		}
	}
	
}
