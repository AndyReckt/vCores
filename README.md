# vCores
These are the server cores for VectroMC. Source code for all 4 of them are under the 'src' folder.

# About:
These plugins are 4 of the server cores, for the relaunch of a server VectroMC. My friend EtshawMC is the owner of this server, and when we discussed starting it back up, I opted in to become a developer, so these server cores are the biproduct of that. 

# Note:
vBasic, vStaffUtils and vScoreboard are all dependant on vNitrogen. vNitrogen is dependant on some sort of permissions plugin, however there are no hooks therefore you can use ANY permissions plugin, as it is concurrent with every single one because of the config.yml.

vStaffUtils is dependant on vBasic as well as vNitrogen, and vScoreboard is dependant on vStaffUtils, and vNitrogen.

These plugins need parts of the other plugins to have functionality.

# vNitrogen:

### Commands:
  - /setrank <player> <rank>
  - /grant <player>
  - /ungrant <player> <id>
  - /grants <player>
  - /staffchat <message>
  - /adminchat <message>
  - /managementchat <message>
  - /buildchat <message>
  - /sct
  - /act
  - /mct
  - /bct
  - /warn <player> <reason>
  - /mute <player> <-s> <reason>
  - /unmute <player> <-s>
  - /ban <player> <-s> <reason>
  - /unban <player> <-s>
  - /kick <player> <-s> <reason>
  - /tempmute <player> <time> <-s> <reason>
  - /tempban <player> <time> <-s> <reason>
  - /blacklist <player> <-s> <reason>
  - /alts <player>
  - /history <player>
  - /punish <player>
  
### Listeners:
  - A chat formatting listener, that formats the prefixes of the ranks (customizable in the config.yml) to be shown in chat.
  - Staff login/logout events, to notify the other online staff.
  - Staff world change events, to notify the other online staff.
  - Listeners for the toggles of all of the different chats, as well as listeners for the starter prefixes for these chats. E.x, to talk in staff chat, you can just type "# HeLlo", or for admin chat, "@ Hello".
  - A join listener for banned players, that either expires or refreshes a temporary ban from it's time, as well as preventing banned players from joining.
  - A blacklist join listener, that checks if the player who joined's IP address is the same as blacklisted player's IP address, and if it is, it links the joined player to that blacklist of the main account.
  - GUI Click listeners that prevent moving items in the /grants and /history commands.
  - A mute listener, that either expires or refhreses a temporary mute, as well as preventing muted players from talking.
  - Player log events that update the player's rank upon join, and logs their IP address into the punishments.yml file, so that if they are an alt account of another player that has played the server, it notifies staff.
  
### API Features:
While none of these plugins actually have an API, in vNitrogen I am using the setPlayerColor(), setTargetColor(), setPlayerPrefix() and setTargetPrefix() for setting the displaynames of players throughout the other 3 cores.

# vStaffUtils:

### Commands:
  - /buildmode
  - /vanish [player]
  - /modmode [player]
  - /report <player>
  - /freeze <player>
  - /invsee <player>
  
### Listeners:
  - Modmode item listeners, for when you use certain items.
  - Vanish updaters, such as keeping a vanished player vanished when they log out/log back in the server, and when other players log out and back in, etc.
  - Modmode prohibiters, to prevent people in modmode from pvping, etc.
  - Freeze listeners, to prevent frozen players from moving, pvping, building, etc.
  - Reports GUI listeners & report custom reason listeners, pretty self explanatory, they just create the functionality of the /report command.
  
### Runnables:
  - Vanish Ticker. This hides everone in the Vanished ArrayList from all online players, every 1/2 of a second.
  - Freeze Cooldown. This prevents staff from spamming the freeze item in modmode.
  
# vBasic:

### Commands:
  - /broadcast <message>
  - /feed [player]
  - /heal [player]
  - /gamemode <gamemode> [player] (as well as other subcommands)
  - /teleport <player> [player]
  - /tphere <player>
  - /tpall
  - /togglestaffalerts (! IMPORTANT !)
  - /msg <player> <message>
  - /reply <message>
  - /togglemessages
  - /togglemessagesounds
  - /toggleglobalchat
  - /settings
  
### Listeners:
  - Player log listeners, where you can set custom join messages through the config.yml.
  - Chat listeners, so that if you have global chat disabled, you are not able to see anyone chat, and you are not able to chat yourself.
  - Settings GUI click listeners, that way when you click an item in the settings GUI, it toggles that selection.
  
### Toggle Staff Alerts:
This is an important feature. For commands in vBasic such as /heal, /feed, /teleport, /broadcast, there are staff alerts that notify online staff what action a player is doing. However, this isn't just in the vBasic plugin. As I said earlier, vStaffUtils is dependant on vBasic. So, if you have staff alerts toggled, whenever a player vanishes, modmodes, buildmodes, freezes, or anything like that, it also triggers the staff alert notification. All staff alert messages customizable through the config.yml.
  
# vScoreboard:

### Commands:
  - /list
  - /togglescoreboard
  
### Listeners:
  - Player join listener that sets the scoreboard initially.
  
### Runnables:
  - The scoreboard runnable that refreshes the scoreboard.
  
# vBungee:

### About:
Since vBungee is a bit of a wildcard among the mix, I should explain it properly here. Back when VoleMC originally released, i had a skript "bungeecord" type plugin that separated the worlds. This produced an identical effect to what a bungeecord proxy does, except only one one server, not on multiple, and without a proxy. It was actually very bad when I made it in skript, so I remade this in Java, and will be using this on VectroMC, and VoleMC if this ever starts up again.

### Commands:
  - /server [server]
  - /send <player> <server>
  - /find <player>
  - /glist [showall/current]
  
### Listeners:
  - Player log listener that sends the user to the hub
  - Chat listener that separates the chats by world, producing a similar effect to bungeecord chat if enabled in the config.yml.
  
### Runnables:
  - Player hider runnable. This is what actually hides the players if they are in a different world, which produces an effet extremely similar to bungeecord.


