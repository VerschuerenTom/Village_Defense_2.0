# Village_Defense_2.0

Project depends on the GameAPI (https://github.com/TomTheCreator/GameAPI) which is 4 year old. 
This project was built 2 years ago and is very very messy after all those bugfixes. If you want to adjust something in it but can't figure stuff out, just contact me and I'll point you in the right direction.
I don't like the code and neither will you. Goodluck!

Pull requests will be reviewed although if I don't respond within 1-2 days, please PM me at SpigotMC.


SPIGOT PAGE:
____________________________________________________________________________________

[CENTER][IMG]http://i60.tinypic.com/28i8cw6.png[/IMG]
[/CENTER]

[CENTER][COLOR=#0080ff][B][B][IMG]https://www.ls-stories.pl/public/style_extra/cprofile_icons/discord_icon.gif[/IMG] [/B]Need support? [/B]
[B]Join our support server:[/B][/COLOR] https://discord.gg/UXzUdTP

Village Defense is a minigame where you're fighting aganist waves of zombies. They aren't normal zombies, they evolved into smarter and faster kind. Game is very configurable and you'll have a lot of fun playing it!

[COLOR=#80ff00][B][IMG]https://youtube.googleblog.com/favicon.ico[/IMG] Wanna see it in action?[/B][/COLOR]
[B][spoiler=YouTube videos][/spoiler][/B][spoiler=YouTube videos]
[B](videos are outdated)[/B]
[MEDIA=youtube]EJUqNHc_Stc[/MEDIA]

[MEDIA=youtube]PXdFsAz_meo[/MEDIA]

[MEDIA=youtube]OTn8eItZ57k[/MEDIA][/spoiler]
[COLOR=#ffff00]
[B]Currently supported version is [U]1.12[/U][/B]
[B][U]First version[/U] also support [U]1.7.10[/U] and [U]1.8.8[/U][/B][/COLOR]

[COLOR=#ff9999][B]Optional dependencies:[/B][/COLOR] [URL='https://dev.bukkit.org/projects/worldedit']WorldEdit[/URL]
[COLOR=#ff0000]
[B]Known caveats:[/B][/COLOR]
- Kit item can be used everywhere when the lore and item name are correct.
[/CENTER]
[FONT=Book Antiqua][SIZE=7][COLOR=#b35900][B]Images[/B][/COLOR][/SIZE][/FONT]
[B]todo[/B]
[FONT=Book Antiqua]
[SIZE=7][COLOR=#b35900][B]Commands[/B][/COLOR][/SIZE][/FONT]
[LIST]
[*]/leave - Leaves VillageDefense game
[*]/addsigns - Sets game signs (by WorldEdit selection)
[*]/setshopchest - Sets game shop in selected chest (by WorldEdit selection)
[*]/setprice - Set price of holding item (needed for shop)
[*]/stats - Show your game stats
[/LIST]

[FONT=Book Antiqua][SIZE=7][COLOR=#b35900][B]Permissions[/B][/COLOR][/SIZE][/FONT]
[LIST]
[*]minigames.vip - unlocks donator kits, able to join full games, get 50% more orbs
[*]minigames.mvip - unlocks donator kits, able to join full games, get 100% more orbs
[*]minigames.elite - unlocks donator kits, able to join full games, get 150% more orbs
[*]minigames.edit - permission to edit arenas
[/LIST]

[FONT=Book Antiqua][SIZE=7][COLOR=#b35900][B][B]Files[/B][/B][/COLOR][/SIZE][/FONT]
[LIST]
[*][URL='https://pastebin.com/0xqQkdeg']config.yml[/URL]
[*][URL='https://pastebin.com/0HjNaanQ']Kits.yml[/URL]
[*][URL='https://pastebin.com/c2MuNvCP']language.yml[/URL]
[*][URL='https://pastebin.com/zV0yNuQQ']rewards.yml[/URL]
[/LIST]
[B][COLOR=#b35900][SIZE=7][FONT=Book Antiqua]Setup[/FONT][/SIZE][/COLOR][/B]
* - optional
[B]1.[/B] Create arena with command [B]/villagedefense create <ARENA ID>[/B] (ARENA ID must be unique!).
[B]2.[/B] Type [B]/vd reload[/B] to reload plugin.
[B]3.[/B] Edit arena typing [B]/villagedefense <ARENA ID>[/B] edit.
You will see this menu:
[IMG]https://proxy.spigotmc.org/efba5c6921c8c0e3b93ccd309be1da555ec90acc?url=http%3A%2F%2Fi60.tinypic.com%2F2qi8qhl.png[/IMG]
[B]4. [/B]Configure arena from menu above.
[B]5.[/B] Get items that you want to be in the game and holding item type [B]/setprice <amount>[/B] to set it's game price.
[B]6.[/B] Set chest on a safe place where players can't reach and put items there then select chest with WorldEdit wand and use chest item from menu above to set the shop.
[B]*7.[/B] Set the secret well placing 4 hoppers and water above them.

Now you're done!

[B][FONT=Book Antiqua][SIZE=7][COLOR=#b35900]Configuration[/COLOR][/SIZE][/FONT][/B]
[U][SIZE=5][COLOR=#404040][B]Stats[/B][/COLOR][/SIZE][/U]
There are two options to store players statistics:
[LIST]
[*]Flat file (STATS.yml) (enabled by default)
[*]MySQL database (set option DatabaseActivated in config.yml to true then restart server)
After restart MySQL.yml file will be created, then configure it
[spoiler=MySQL.yml]address: jdbc:mysql://localhost:3306/<databasename>
user: <user>
password: <password>
min-connections: 5
max-connections: 10[/spoiler]
[/LIST]
[SIZE=5][COLOR=#404040][U][B]Sign modification[/B][/U][/COLOR][/SIZE]
You can modify sign messages in signModification.yml
This is default file:
[spoiler=signModification.yml]
signs:
    format:
        WaitingForNewGame:
            lines:
                '1': '--------'
                '2': Waiting
                '3': ''
                '4': '--------'
        WAITING_FOR_PLAYERS:
            lines:
                '1': "&2[Join]"
                '2': '%MAPNAME%'
                '3': '%ARENA%'
                '4': "&5[%PLAYERSIZE%/%MAXPLAYERS%]"
        STARTING:
            lines:
                '1': "&2[Starting]"
                '2': '%MAPNAME%'
                '3': '%ARENA%'
                '4': "&5[%PLAYERSIZE%/%MAXPLAYERS%]"
        FULL:
            lines:
                '1': "&4[Full]"
                '2': '%MAPNAME%'
                '3': '%ARENA%'
                '4': "&5[%PLAYERSIZE%/%MAXPLAYERS%]"
        INGAME:
            lines:
                '1': "&c[Ingame]"
                '2': '%MAPNAME%'
                '3': '%ARENA%'
                '4': "&5[%PLAYERSIZE%/%MAXPLAYERS%]"
        ENDING:
            lines:
                '1': "&c----------"
                '2': "&c--ENDING--"
                '3': "&c----------"
                '4': "&c----------"
        RESTARTING:
            lines:
                '1': "&c----------"
                '2': "&cRESTARTING"
                '3': "&c----------"
                '4': "&c----------"
[/spoiler]

[SIZE=5][COLOR=#404040][U][B]Bungeecord[/B][/U][/COLOR][/SIZE]
This minigame supports bungeecord but it's disabled by default.
To activate bungeecord set value BungeeActivated in config.yml to true
Setup for signs using bungeecord:
[LIST]
[*]Add signs with the /addsigns command
[*]Add servers in the config.yml file. An example can be seen in the picture below. Be aware that "default" can't be a server! (Example: See example below . Be aware that fm01;, fm02: matches with the server names in the Bungee config! However mapname is freely to choose!)
[*]Restart server.
[*]Done
[/LIST]
Example configuration:
[spoiler=Bungeecord configuration]
[code=yaml]signs:

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
CheckOnline: 1[/code]

[FONT=Book Antiqua][SIZE=7][COLOR=#b35900][B]To do[/B][/COLOR][/SIZE][/FONT]
[LIST]
[*]Add /remove sign command
[*]Reimplement 1.8 and 1.7.10 support
[*]Re-implement Bunny kit
[*]Make Villagers and Golems ridable again.
[/LIST]

[RIGHT][B]Thanks to:[/B]
- First version made together with @IvanTheBuilder
- Logo and banner by @[URL='https://www.spigotmc.org/members/xdizastercyx.9271/']xDizasterCYx[/URL]
- Plugin idea by DerpyKitteh[/RIGHT]



