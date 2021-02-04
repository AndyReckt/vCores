package net.vectromc.vnitrogen.commands.permissions;

import net.vectromc.vnitrogen.management.PermissionManagement;
import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RankCommand implements CommandExecutor {

    private vNitrogen plugin;

    PermissionManagement permissions = new PermissionManagement();

    public RankCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("RankCommand.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
        } else {
            if (args.length < 2) {
                Utils.sendMessage(sender, plugin.getConfig().getString("RankCommand.IncorrectUsage")
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
            } else {
                String rank = args[0].toUpperCase();
                String display = plugin.getConfig().getString("Ranks." + rank + ".display");
                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("list")) {
                        List<String> permissions = new ArrayList<>();
                        for (String rankPermissions : plugin.permData.config.getStringList("Ranks." + rank + ".Permissions")) {
                            permissions.add(rankPermissions);
                        }
                        String listMsg = "";
                        for (String permission : permissions) {
                            if (listMsg.length() == 0) {
                                listMsg = permission + "\n";
                            } else {
                                listMsg = listMsg + permission + "\n";
                            }
                        }
                        Utils.sendMessage(sender, plugin.getConfig().getString("UserCommand.ListUserPermissions")
                                .replace("%player%", display)
                                .replace("%permissions%", listMsg));
                    } else {
                        Utils.sendMessage(sender, plugin.getConfig().getString("UserCommand.IncorrectUsage")
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                    }
                } else if (args.length == 3) {
                    String permission = args[2];
                    if (args[1].equalsIgnoreCase("add")) {
                        permissions.addRankPermission(rank, permission);
                        Utils.sendMessage(sender, plugin.getConfig().getString("UserCommand.AddPlayerPermission")
                                .replace("%player%", display)
                                .replace("%permission%", permission));
                    } else if (args[1].equalsIgnoreCase("remove")) {
                        permissions.removeRankPermission(rank, permission);
                        Utils.sendMessage(sender, plugin.getConfig().getString("UserCommand.RemovePlayerPermission")
                                .replace("%player%", display)
                                .replace("%permission%", permission));
                    } else {
                        Utils.sendMessage(sender, plugin.getConfig().getString("UserCommand.IncorrectUsage")
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                    }
                } else {
                    Utils.sendMessage(sender, plugin.getConfig().getString("UserCommand.IncorrectUsage")
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                }
            }
        }
        return true;
    }
}
