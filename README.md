# Village_Defense_2.0

Project depends on the GameAPI (https://github.com/TomTheCreator/GameAPI) which is 4 year old. 
This project was built 2 years ago and is very very messy after all those bugfixes. If you want to adjust something in it but can't figure stuff out, just contact me and I'll point you in the right direction.
I don't like the code and neither will you. Goodluck!

Pull requests will be reviewed although if I don't respond within 1-2 days, please PM me at SpigotMC.


SPIGOT PAGE:






ONLY WORKS FOR 1.12
FIRST VERSION ALSO WORKS FOR 1.7.10 AND 1.8.8. SUPPORT FOR THESE VERSIONS WILL COME BACK IN THE WEEK OF 20 AUGUST

Click here for support channel.



Hello there guys and girls,



Today I am proud to present another unique minigame named Village Defense. In this minigame you'll have to defend a village (not just the villagers) against waves of zombies. Though, those zombies aren't just normal zombies. They evolved into smarter and faster kinds of zombies. While playing the game you'll also be able to upgrade your armor and weapons by shopping and enchanting. You will also be able to gain more life by offering the meat from the zombies to the gods.





The ultimate goal of the game is to survive as many waves as possible. Don't be afraid that you will the game will run out of waves sooner or later cause it has an infinite amount of waves! While playing the game you'll be able to do some special stuff:

First of all, you'll be able to upgrade your gear by shopping from the villagers. The shopprices and the shop itself is fully configurable!



Secondly, you'll be able to gain more life by offering zombie meat (rotten flesh) to the gods. This is done by throwing the rotten flesh in a (secret) well. (More information later)
Thirdly, you'll be able to buy iron golems and wolfs in the shop that will help u defeat the zombies. You are able to ride the golems like horses. I also adjusted the behaviour from the wolves so they attack the zombies immediatly. Also villagers are ridable!

You might think that this will not be a great game cause zombies are so slow. Don't worry guys, we fixed this issue! We adjusted the zombies so they run faster, stronger and have special attacks. We made 4 types of zombies:

Fastzombies: These zombies are just normal zombies that run a little bit faster. You'll meet them in every wave.
BabyZombies: These type is so small and so fast that they aren't easy to hit. However they don't have much life so they are mostly a one hit kill. You'll encounter them once wave 2 starts.
Tank Zombies: These zombies are decked out with heavy armor and have more health. However cause they wear heavy armor, they are a little bit slower than the fast zombies. You'll encounter once wave 3-4 starts
Golem Busters: These zombies carry TNT with them and will drop some upon death. They will focus the golems and try to kill them with explosions. They will start spawning at wave 6
Player Busters: These zombies do the same as the Golem Busters but focus players. Yup they'll drop TNT just next to you. You'll encounter these dangerous ones starting from wave 10.
.... (maybe more coming soon, I said maybe!)

Be also aware that zombies weren't able anymore to break down doors since 1.7.10. Though we coded these zombies in a way that they are still able to break down doors in 1.8!


Some very very old game play, be aware that I recoded the minigame completely so the game is much improved! A gameplay from the new version is not available. If you want to create a video for me, feel free to contact me.

[MEDIA=youtube]EJUqNHc_Stc[/MEDIA]

Some more gameplay in the spoiler:

[spoiler][MEDIA=youtube]PXdFsAz_meo[/MEDIA]

[MEDIA=youtube]OTn8eItZ57k[/MEDIA][/spoiler]






When the game starts you'll be able to choose between a couple of kits. In this game there are three main types: The default kit(s), the leveling kits and the donator kits. At this moment we have a total of 13 kits but I'll try to add some more in the future. I'll explain the three types of kits first.

The default kit: The default kit is as it says the default kit. If u join the game and forgot to choose a kit, you'll be given the default kit. (Everybody is able to choose the default kit). However if u already played a game before (without leaving the server), you'll be given the kit u used in the previous game.
The leveling kits: These kits are locked in the beginning. You can unlock these kits by leveling. You level by killing zombies, playing the game, etc.. Once u reach the needed level for that kit it'll be unlocked. We implemented these leveling kits to give the players a goal to keep playing the game. At this moment there are 6 leveling kits
The donator kits: These kits can be unlocked by donating to the server. Or u can just give the permission minigames.vip to the player so he can choose donator kits. Each of these kits has a special ability. For example insta arrow shooting, or double jumping, or teleporting,...

You'll be able to choose the kits from a kit menu. You are able to open the kit menu by right clicking a nether start which you will be given when u join the game. The kit menu looks like this:








There are serveral VIP features in this game:

Donator kits
Join when a game is full
Get more orbs ingame (50%,100%,150%).
More ideas might come from u.

The permission minigames.vp will unlock all the donator kits, full game joining and 50% more orbs gaining ingame.

The permission minigames.mvp will do the same except it will give u 100% more orbs ingame. The same for minigames.elite, this will give u 150% more orbs ingame.





minigames.vip:
unlocks donator kits
Able to join full games
Get 50% more orbs
minigames.mvp:
unlocks donator kits
Able to join full games
Get 100% more orbs
minigames.elite:
unlocks donator kits
Able to join full games
Get 150% more orbs
minigames.edit:
being able to create and edit arena's.




No setup video. If you are able to create one and want to create on for me, feel free to contact me.


Creating an arena


The setup, the part of all the plugins that server owners and admins fear the most. Don't worry guys, I thought about u!


The setup from this plugin is so easy that even the dumbest person can do it. I've added a setup menu in this plugin so u only need to know one two commands. (One for creating a new arena, and one for opening the setup menu to edit the arena).


Lets get started:

First you'll need to create an arena. This can be done with the /villagedefense create <ARENAID>. Be aware that the arena ID must be unique! For now, when using this command, perform /vd reload after it! Else the next command won't work.
Secondly you can edit the arena with /villagedefense <ARENAID> edit. Once u performed this command a menu (shown below) will open. In this menu you'll find the instructions on how to setup this minigame! These instructions are very easy so I will not explain each item. However I will upload a setup video soon!



Thirdly, you'll need to know another command. This command is /setprice <amount>. This is used to set the prices in the shop. Just hold an item in your hand and perform the command. Once you performed it, the item lore will say: "x orbs". This means that you performed the command succesfully. When you've set the price of the item, just put in in the chest which shall be used as the shop. Note: The shop is opened by right clicking villagers ingame, so put the chest shop somewhere safe where players can't reach!
The next thing you'll have to do is create the "secret well". In this well people will offer rotten flesh to the gods to get extra life. You create the well by putting hoppers under the water. Be sure that if u drop rotten flesh in the well, that they will be consumed by the droppers. You don't have to do something extra cause this plugin is made in a way that the well/droppers will check theirselves in which arena they are part of.

Setting up the shop

Note: The shop is opened by right clicking villagers ingame, so put the chest shop somewhere safe where players can't reach!

Setting up the shop is easy. Like said above you'll need the command /setprice <amount>. However u also need to know some small little things to make use of every item that can be sold.


Be aware that everything can be sold (enchanted weapons, named items, blocks). However not everything has a use in the game. Selling blocks for example is useless. Sometimes it can be quite hard to balance the shop prices.


Some special items can be sold in the shop:

Golems: Name an item with an anvil "Spawn Golem" and price it with the /setprice command. If a player will buy this item, it'll spawn a golem at the start location which is ridable and will have the name from the player. Players can only ride their own golems. Except for donators.
Wolfs: Wolfs can also be sold in the shop. Name an item "Spawn Wolf" and price it. If a player buys this item, it'll spawn a wolf at the start location. We adjusted the behaviour from the wolf so that it will attack the zombies directly and defend the villagers.

Language file


When the plugin is installed, it'll create a language file. In this language file u can adjust every single message that is send to the players! This means that u can translate this plugin to another language. You can even change the name from the plugin!


Stats



There are also stats in this game. There are two options to store the stats. You can either store them in a MySQL database or you can just save them in a .yml file.

STATS.yml file: If u don't have a database or you don't want to save the stats in a database u can save them in a simple .yml file. To do this just change the option "DatabaseActivated" in the config.yml file to false.
MySQL Database: If u want to save the stats in a MySQL database, you'll need to put the "DatabaseActivated" option in the config.yml to true. If you've done this, then you'll need to restart your server. A file named MySQL.yml will be created in the plugin direction folder. It'll look like this:

[CODE]address: jdbc:mysql://localhost:3306/<databasename>[/SIZE][/CENTER]
[SIZE=4]
[CENTER]user: <user>

password: <password>

min-connections: 5

max-connections: 10

[/CODE]





Just fill in the ip, databasename, username and password. U don't have to worry about min- and max-connections.


Sign modification


You will be able to modify everything that is presented on the signs. This can be done in the signModification.yml file. This file looks like this:

[CODE]signs:

  format:

  WaitingForNewGame:

  lines:
  '1': '--------'
  '2': Waiting
  '3': ''
  '4': '--------'

  WAITING_FOR_PLAYERS:

  lines:
  '1': "\xa72[Join]"
  '2': '%MAPNAME%'
  '3': '%ARENA%'
  '4': "\xa75[%PLAYERSIZE%/%MAXPLAYERS%]"

  STARTING:

  lines:
   '1': "\xa72[Starting]"
  '2': '%MAPNAME%'
  '3': '%ARENA%'
  '4': "\xa75[%PLAYERSIZE%/%MAXPLAYERS%]"

  FULL:

  lines:
  '1': "\xa74[Full]"
  '2': '%MAPNAME%'
  '3': '%ARENA%'
  '4': "\xa75[%PLAYERSIZE%/%MAXPLAYERS%]"

  INGAME:

  lines:
  '1': "\xa7c[Ingame]"
  '2': '%MAPNAME%'
  '3': '%ARENA%'
  '4': "\xa75[%PLAYERSIZE%/%MAXPLAYERS%]"

  ENDING:

  lines:
  '1': "\xa7c----------"
  '2': "\xa7c--ENDING--"
  '3': "\xa7c----------"
  '4': "\xa7c----------"

  RESTARTING:

  lines:
  '1': "\xa7c----------"
  '2': "\xa7cRESTARTING"
  '3': "\xa7c----------"
  '4': "\xa7c----------"

[/CODE]



As you see, it's very easy to edit....


You can also choose between a static sign system and a dynamic. The differences between these two is that a dynamic sign system will remove the sign from the sign-wall once an arena is full or ingame. It'll be presented back onto the sign wall onces the game is waiting for new playres again. With the static sign system, the arena's will just stay on the signwall forever.




This minigame is able to perform with bungeecord or without. This means that u can either run mutiple arena's on one server (even next to your faction or survival word) or u can use bungeecord to make serveral servers which each one single arena.


By default Bungeecord is disabled. To activate Bungeecord you'll have to set the "BungeeActivated" option in the config.yml file to true.


Using this plugin without Bungeecord: You'll setup the sign system with the setup menu explained earlier on this page.
Using this plugin with Bungeecord: There is an extra file named BungeeGameSigns in the config. Drop this plugin into the hub for the sign system. How to set this up will be explained below.

Bungeecord sign system setup:

Add signs with the /addsigns command

Add servers in the config.yml file. An example can be seen in the picture below. Be aware that "default" can't be a server! (Example: See example below . Be aware that fm01;, fm02: matches with the server names in the Bungee config! However mapname is freely to choose!)
Restart server.
Done


[CODE]signs:

  format:

  WAITING_FOR_PLAYERS:
  lines:
  '1': "\xa72[Join]"
  '2': '%MAPNAME%'
  '3': '%SERVER%'
  '4': "\xa75[%PLAYERSIZE%/%MAXPLAYERS%]"

  STARTING:

  lines:
  '1': "\xa72[Join]"
  '2': '%MAPNAME%'
  '3': '%SERVER%'
  '4': "\xa75[%PLAYERSIZE%/%MAXPLAYERS%]"

  INGAME:

  lines:
  '1': "\xa7c[Ingame]"
  '2': '%MAPNAME%'
  '3': '%SERVER%'
  '4': "\xa75[%PLAYERSIZE%/%MAXPLAYERS%]"

  ENDING:

  lines:
  '1': "\xa7c----------"
  '2': "\xa7c--ENDING--"
  '3': "\xa7c----------"
  '4': "\xa7c----------"

  RESTARTING:

  lines:
  '1': "\xa7c----------"
  '2': "\xa7cRESTARTING"
  '3': "\xa7c----------"
  '4': "\xa7c----------"

  signlocations:
  example: world,-169.0,4.0,-332.0,0.0,0.0
  '1': world,-192.0,26.0,-329.0,0.0,0.0
  '2': world,-192.0,26.0,-330.0,0.0,0.0
  '3': world,-192.0,26.0,-331.0,0.0,0.0
  '4': world,-192.0,26.0,-332.0,0.0,0.0
  '5': world,-192.0,26.0,-333.0,0.0,0.0
  '6': world,-192.0,26.0,-334.0,0.0,0.0
  '7': world,-192.0,26.0,-335.0,0.0,0.0
  '8': world,-192.0,26.0,-336.0,0.0,0.0

servers:
  fm01:
    hostname: 0.0.0.0
    port: 25567
    mapname: fm01
  fm02:
    hostname: 0.0.0.0
    port: 25568
    mapname: fm02
  fm03:
    hostname: 0.0.0.0
    port: 25569
    mapname: fm03
  fm04:
    hostname: 0.0.0.0
    port: 25570
    mapname: fm04
  fm05:
    hostname: 0.0.0.0
    port: 25571
    mapname: fm05
  fm06:
    hostname: 0.0.0.0
    port: 25572
    mapname: fm06
  fm07:
    hostname: 0.0.0.0
    port: 25573
    mapname: fm07
  fm08:
    hostname: 0.0.0.0
    port: 25574
    mapname: fm08
  default:
    hostname: localhost
    port: 25565
    mapname: Testmap
CheckOffline: 10
CheckOnline: 1[/CODE]


Known bugs:
LOW: Fix kit item can be used everywhere when the lore and item name are correct.
To Do list:
Add /remove sign command
Reimplement 1.8 and 1.7.10 support
Re-implement Bunny kit
Make Villagers and Golems ridable again.

License

You do not have the right sell it or rent it!


Thanks to:

Logo and banner made by @xDizasterCYx
Previous and first version made together with [USER=18881]@IvanTheBuilder[/USER]. Huge thanks to this guy!
Idea by DerpyKitteh




