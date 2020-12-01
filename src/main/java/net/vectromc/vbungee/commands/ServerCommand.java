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

import java.util.ArrayList;
import java.util.List;

public class ServerCommand implements CommandExecutor {

    private vbungee plugin;

    public ServerCommand() {
        plugin = vbungee.getPlugin(vbungee.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("Server.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (args.length > 1) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("Server.IncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    if (args.length == 1) {
                        String server = args[0];
                        List<String> servers = new ArrayList<>();
                        for (World worlds : Bukkit.getServer().getWorlds()) {
                            servers.add(worlds.getName());
                        }
                        if (!servers.contains(server)) {
                            Utils.sendMessage(sender, plugin.getConfig().getString("Server.InvalidServer")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            Location tpLoc = new Location(Bukkit.getWorld(server), 0.5, 0.5, 0.5);
                            Player player = (Player) sender;
                            player.teleport(tpLoc);
                            Utils.sendMessage(player, plugin.getConfig().getString("Server.FormatSent")
                                    .replace("%server%", Bukkit.getWorld(server).getName()));
                        }
                    } else {
                        Player player = (Player) sender;
                        Utils.sendMessage(sender, plugin.getConfig().getString("Server.FormatNoArgs")
                                .replace("%server%", player.getWorld().getName()));
                    }
                }
            }
        }
        return true;
    }
}
