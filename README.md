# vCores
These are the server cores for VectroMC. Source code for all 4 of them are under the 'src' folder.

# About:
These plugins are 4 of the server cores, for the relaunch of a server VectroMC. My friend EtshawMC is the owner of this server, and when we discussed starting it back up, I opted in to become a developer, so these server cores are the biproduct of that. 

# Note:
vBasic, vStaffUtils and vScoreboard are all dependant on vNitrogen. vNitrogen is currently dependant on PermissionsEX (Only for the /setrank command), but later I will add a section in the config.yml where you can customize the command that is run on /setrank.

vStaffUtils is dependant on vBasic as well as vNitrogen, and vScoreboard is dependant on vStaffUtils, and vNitrogen.

These plugins need parts of the other plugins to have functionality.

# vNitrogen:

### Commands:
  - /setrank <player> <rank>
  - /staffchat <message>
  - /adminchat <message>
  - /managementchat <message>
  - /buildchat <message>
  - /sct
  - /act
  - /mct
  - /bct
  - /warn <player> <reason>
  - /mute <player> [-s] <reason>
  - /unmute <player> [-s]
  - /ban <player> [-s] <reason>
  - /unban <player> [-s]
  - /kick <player> [-s] <reason>
  - /tempmute <player> <time> [-] <reason>
  - /tempban <player> <time> [-s] <reason>
  - /blacklist <player> [-s] <reason>
  - /alts <player>
  - /history <player>
  
### Listeners:
  - A chat formatting listener, that formats the prefixes of the ranks (customizable in the config.yml) to be shown in chat.
  - Staff login/logout events, to notify the other online staff.
  - Staff world change events, to notify the other online staff.
  - Listeners for the toggles of all of the different chats, as well as listeners for the starter prefixes for these chats. E.x, to talk in staff chat, you can just type "# HeLlo", or for admin chat, "@ Hello".
  
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

I don't think I need a description for this, it's just a scoreboard plugin that hooks into the other cores.


