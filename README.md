# Village_Defense_2.0

This project was built 2 years ago and is very very messy after all those bug fixes. If you want to adjust something in it but can't figure stuff out, just contact me and I'll point you in the right direction.
I don't like the code and neither will you. Good luck!

Pull requests will be reviewed although if I don't respond within 1-2 days, please PM me at SpigotMC.


SPIGOT PAGE:
____________________________________________________________________________________

[CENTER][IMG]http://i60.tinypic.com/28i8cw6.png[/IMG]
[/CENTER]

[CENTER][COLOR=#0080ff][B][B][IMG]https://www.ls-stories.pl/public/style_extra/cprofile_icons/discord_icon.gif[/IMG] [/B]Need support? [/B]
[B]Join our support server:[/B][/COLOR] [URL]https://discord.gg/UXzUdTP[/URL]

Village Defense is a minigame where you're fighting against waves of zombies to protect a village. The zombies you're fighting against aren't a normal kind though, they are an evolved kind which are a lot more difficult to handle. The ultimate goal is to keep the villagers alive.

[COLOR=#80ff00][B][IMG]https://youtube.googleblog.com/favicon.ico[/IMG] Wanna see it in action?[/B][/COLOR]
[spoiler=YouTube videos]
[B](videos are outdated)[/B]
[MEDIA=youtube]EJUqNHc_Stc[/MEDIA]

[MEDIA=youtube]PXdFsAz_meo[/MEDIA]

[MEDIA=youtube]OTn8eItZ57k[/MEDIA][/spoiler]
[COLOR=#ffff00]
[B]Currently supported version is [U]1.12 AND 1.8[/U][/B][/COLOR]

[COLOR=#ff9999][B]Optional dependencies:[/B][/COLOR] [URL='https://dev.bukkit.org/projects/worldedit']WorldEdit, [/URL][URL='https://www.spigotmc.org/resources/leaderheads.2079/']LeaderHeads[/URL] (yes LeaderHeads support[B]s[/B] VillageDefense (please see Configuration section))
[COLOR=#ff0000]
[B]Known bugs:[/B][/COLOR]
- Kit item can be used everywhere when the lore and item name are correct.
[/CENTER]
[IMG]https://i.imgur.com/sB1IxxS.jpg[/IMG]
[LIST]
[*][URL='https://www.spigotmc.org/resources/leaderheads.2079/']LeaderHeads[/URL] support
[*][B]Overpowered zombies![/B]

[*]Over [B]24[/B] ingame kits!
[*]5 zombie types! (Fast zombies, Baby zombies, Tank zombies, Golem and Player busters)

[*]Donator and leveling kits
[*]Configurable rewards after game
[*]Most of messages configurable
[*]Create shop that can contain every item and can be different for each arena
[*]Flat file and MySQL support
[*]Translatable signs
[*]Easy GUI setup
[/LIST]
[IMG]https://i.imgur.com/WIftUFr.jpg[/IMG]
Images of gameplay will be soon™!

[IMG]https://i.imgur.com/N75BbDG.jpg[/IMG]
[LIST]
[*]/leave - Leaves Village Defense game
[*]/addsigns - Sets game signs (by WorldEdit selection)
[*]/setshopchest - Sets game shop in selected chest (by WorldEdit selection)
[*]/setprice - Set price of holding item (needed for shop)
[*]/stats - Show your game stats
[/LIST]

[IMG]https://i.imgur.com/oGVYsYu.jpg[/IMG]
[LIST]
[*]minigames.vip - Permission to get [B]donator kits[/B], join [B]full games[/B] and get [B]50% more orbs[/B]

[*]minigames.mvip - Same as above but get [B]100% more orbs[/B]

[*]minigames.elite - Same as above but get [B]150% more orbs[/B]

[*]minigames.edit - Permission to edit game arenas

[*]minigames.fullgame - Permission to join full games
[/LIST]

[IMG]https://i.imgur.com/dBz4mIs.jpg[/IMG]
[LIST]
[*][URL='https://pastebin.com/0xqQkdeg']config.yml[/URL]
[*][URL='https://pastebin.com/0HjNaanQ']Kits.yml[/URL]
[*][URL='https://pastebin.com/c2MuNvCP']language.yml[/URL]
[*][URL='https://pastebin.com/zV0yNuQQ']rewards.yml[/URL]
[*][URL='https://www.dropbox.com/sh/91wyg1labne0uvx/AAASCjSzY8QwtvAIeLFlH_Qua?dl=1'][U]Example Village Defense map[/U][/URL] (screenshots soon™)
[/LIST]

[IMG]https://i.imgur.com/AfPoDcC.jpg[/IMG]
* - optional
[B]1.[/B] Create arena with command [B]/villagedefense create <ARENA ID>[/B] (ARENA ID must be unique!).
[B]2.[/B] Type [B]/vd reload[/B] to reload plugin.
[B]3.[/B] Edit arena typing [B]/villagedefense <ARENA ID>[/B] edit.
You will see this menu:
[IMG]https://i.imgur.com/HfaXM5C.png[/IMG]
[B]4. [/B]Configure arena from menu above.
[B]5.[/B] Get items that you want to be in the game and holding item type [B]/setprice <amount>[/B] to set it's game price.
[B]6.[/B] Set chest on a safe place where players can't reach and put items there then select chest with WorldEdit wand and use chest item from menu above to set the shop.
[B]*7.[/B] Set the secret well placing 4 hoppers and water above them.

Now you're done!

[IMG]https://i.imgur.com/AWHhplE.jpg[/IMG]
[U][SIZE=5][COLOR=#404040][B]Stats[/B][/COLOR][/SIZE][/U]
[INDENT]There are two options to store players statistics:
[LIST]
[*]Flat file (STATS.yml) (enabled by default)
[*]MySQL database (set option DatabaseActivated in config.yml to true then restart server)
After restart MySQL.yml file will be created, then configure it
[spoiler=MySQL.yml][code=yaml]address: jdbc:mysql://localhost:3306/<databasename>
user: <user>
password: <password>
min-connections: 5
max-connections: 10[/code][/spoiler]
[/LIST][/INDENT]
[SIZE=5][COLOR=#404040][U][B]Sign modification[/B][/U][/COLOR][/SIZE]
[INDENT]You can modify sign messages in signModification.yml
This is default file:
[spoiler=signModification.yml][code=yaml]
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
[/code][/spoiler][/INDENT]

[SIZE=5][COLOR=#404040][U][B]Bungeecord[/B][/U][/COLOR][/SIZE]
[INDENT]This minigame supports bungeecord but it's disabled by default.
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
                '1': "&2[Join]"
                '2': '%MAPNAME%'
                '3': '%SERVER%'
                '4': "&5[%PLAYERSIZE%/%MAXPLAYERS%]"
        STARTING:
            lines:
                '1': "&2[Join]"
                '2': '%MAPNAME%'
                '3': '%SERVER%'
                '4': "&5[%PLAYERSIZE%/%MAXPLAYERS%]"
        INGAME:
            lines:
                '1': "&c[Ingame]"
                '2': '%MAPNAME%'
                '3': '%SERVER%'
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
[/spoiler][/INDENT]

[SIZE=5][COLOR=#404040][U][B]LeaderHeads Support[/B][/U][/COLOR][/SIZE]
[INDENT]This plugin supports [URL='https://www.spigotmc.org/resources/leaderheads.2079/']LeaderHeads plugin[/URL] that means you can create leaderboards for your players!
Available statistics:
[spoiler=LeaderHeads Statistics]
[B]vd-kills[/B]: shows the players with the most kills
[B]vd-deaths[/B]: shows the players with the most deaths
[B]vd-games[/B]: shows the players with the most games played
[B]vd-highestwave[/B]: shows the players with the highest wave reached
[B]vd-xp[/B]: shows the players with the most xp
[B]vd-level[/B]: shows the players with the highest level
[/spoiler][/INDENT]
[FONT=Book Antiqua][SIZE=7][COLOR=#b35900][B]To do[/B][/COLOR][/SIZE][/FONT]
[LIST]
[*]Add /remove sign command
[*]Re-implement Bunny kit
[*]Make Villagers and Golems ridable again.
[/LIST]

[RIGHT][B]Thanks to:[/B]
- First version made together with [USER=18881]@IvanTheBuilder[/USER]
- Logo and banner by @[URL='https://www.spigotmc.org/members/xdizastercyx.9271/']xDizasterCYx[/URL]
-Thread design by [USER=423193]@Plajer[/USER]
- Plugin idea by DerpyKitteh[/RIGHT]
