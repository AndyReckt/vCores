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
                double X = plugin.getConfig().getDouble("Hub.Spawn.X");
                double Y = plugin.getConfig().getDouble("Hub.Spawn.Y");
                double Z = plugin.getConfig().getDouble("Hub.Spawn.Z");
                double Pitch = plugin.getConfig().getDouble("Hub.Spawn.Pitch");
                double Yaw = plugin.getConfig().getDouble("Hub.Spawn.Yaw");
                Location tpLoc = new Location(world, X, Y, Z, (float) Pitch, (float) Yaw);
                player.teleport(tpLoc);
            }
        }
        return true;
    }
}
