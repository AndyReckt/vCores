package me.yochran.vbungee.listeners;

import me.yochran.vbungee.utils.Utils;
import me.yochran.vbungee.vbungee;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandListener implements Listener {

    private vbungee plugin;

    public CommandListener() {
        plugin = vbungee.getPlugin(vbungee.class);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        List<String> commands = new ArrayList<>();
        for (String cmd : plugin.getConfig().getConfigurationSection("BungeeCommands.Commands").getKeys(false)) {
            commands.add("/" + cmd);
        }
        for (String cmdList : commands) {
            if (event.getMessage().toLowerCase().startsWith(cmdList)) {
                List<World> worlds = new ArrayList<>();
                for (String enabledWorlds : plugin.getConfig().getStringList("BungeeCommands.Commands." + cmdList.replace("/", "") + ".EnabledWorlds")) {
                    worlds.add(Bukkit.getWorld(enabledWorlds));
                }
                if (!worlds.contains(player.getWorld())) {
                    event.setCancelled(true);
                    Utils.sendMessage(player, plugin.getConfig().getString("BungeeCommands.Message")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            }
        }
    }
}
