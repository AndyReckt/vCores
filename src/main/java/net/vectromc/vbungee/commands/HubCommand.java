package me.yochran.vbungee.commands;

import me.yochran.vbungee.utils.Utils;
import me.yochran.vbungee.vbungee;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {

    private vbungee plugin;

    public HubCommand() {
        plugin = vbungee.getPlugin(vbungee.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!plugin.getConfig().getBoolean("Hub.CommandEnabled")) {
                Utils.sendMessage(sender, plugin.getConfig().getString("Hub.CommandNotEnabled")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                World world = Bukkit.getWorld(plugin.getConfig().getString("Hub.World"));
                int X = plugin.getConfig().getInt("Hub.Spawn.X");
                int Y = plugin.getConfig().getInt("Hub.Spawn.Y");
                int Z = plugin.getConfig().getInt("Hub.Spawn.Z");
                int Pitch = plugin.getConfig().getInt("Hub.Spawn.Pitch");
                int Yaw = plugin.getConfig().getInt("Hub.Spawn.Yaw");
                Location tpLoc = new Location(world, X, Y, Z, (float) Pitch, (float) Yaw);
                player.teleport(tpLoc);
            }
        }
        return true;
    }
}
