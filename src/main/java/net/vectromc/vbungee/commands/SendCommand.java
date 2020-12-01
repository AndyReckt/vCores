package me.yochran.vbungee.commands;

import me.yochran.vbungee.utils.Utils;
import me.yochran.vbungee.vbungee;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SendCommand implements CommandExecutor {

    private vbungee plugin;
    private vNitrogen nitrogen;

    public SendCommand() {
        plugin = vbungee.getPlugin(vbungee.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("Send.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (args.length != 2) {
                Utils.sendMessage(sender, plugin.getConfig().getString("Send.IncorrectUsage")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    nitrogen.setPlayerColor(target);
                    String server = args[1];
                    List<String> servers = new ArrayList<>();
                    for (World worlds : Bukkit.getServer().getWorlds()) {
                        servers.add(worlds.getName());
                    }
                    if (!servers.contains(server)) {
                        Utils.sendMessage(sender, plugin.getConfig().getString("Send.InvalidServer")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        Location tpLoc = new Location(Bukkit.getWorld(server), 0.5, 0.5, 0.5);
                        target.teleport(tpLoc);
                        Utils.sendMessage(sender, plugin.getConfig().getString("Send.FormatSender")
                                .replace("%server%", Bukkit.getWorld(server).getName())
                                .replace("%player%", target.getDisplayName()));
                        Utils.sendMessage(target, plugin.getConfig().getString("Send.FormatTarget")
                                .replace("%server%", Bukkit.getWorld(server).getName())
                                .replace("%player%", target.getDisplayName()));
                    }
                } else {
                    Utils.sendMessage(sender, plugin.getConfig().getString("Send.InvalidPlayer")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            }
        }
        return true;
    }
}
