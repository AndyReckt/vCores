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

public class UserCommand implements CommandExecutor {

    private vNitrogen plugin;

    PermissionManagement permissions = new PermissionManagement();

    public UserCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("UserCommand.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
        } else {
            if (args.length < 2) {
                Utils.sendMessage(sender, plugin.getConfig().getString("UserCommand.IncorrectUsage")
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
            } else {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (!permissions.isInitialized(target)) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("UserCommand.InvalidPlayer")
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                } else {
                    String displayName = "";
                    for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                        if (plugin.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                            displayName = plugin.getConfig().getString("Ranks." + rank + ".color") + target.getName();
                        }
                    }
                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("list")) {
                            List<String> permissions = new ArrayList<>();
                            for (String playerPermissions : plugin.permData.config.getStringList(target.getUniqueId().toString() + ".Permissions")) {
                                permissions.add(playerPermissions);
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
                                    .replace("%player%", displayName)
                                    .replace("%permissions%", listMsg));
                        } else {
                            Utils.sendMessage(sender, plugin.getConfig().getString("UserCommand.IncorrectUsage")
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                        }
                    } else if (args.length == 3) {
                        String permission = args[2];
                        if (args[1].equalsIgnoreCase("add")) {
                            permissions.addPlayerPermission(target, permission);
                            Utils.sendMessage(sender, plugin.getConfig().getString("UserCommand.AddPlayerPermission")
                                    .replace("%player%", displayName)
                                    .replace("%permission%", permission));
                        } else if (args[1].equalsIgnoreCase("remove")) {
                            permissions.removePlayerPermission(target, permission);
                            permissions.attachments.get(target.getUniqueId()).unsetPermission(permission);
                            permissions.attachments.get(target.getUniqueId()).setPermission(permission, false);
                            Utils.sendMessage(sender, plugin.getConfig().getString("UserCommand.RemovePlayerPermission")
                                    .replace("%player%", displayName)
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
        }
        return true;
    }
}
