package net.vectromc.vnitrogen.commands.punishments;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnblacklistCommand implements CommandExecutor {

    private vNitrogen plugin;
    private Boolean silent;

    public UnblacklistCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("Unblacklist.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (args.length == 0) {
                Utils.sendMessage(sender, plugin.getConfig().getString("Unblacklist.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                if (!plugin.data.config.contains(target2.getUniqueId().toString())) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("Unblacklist.InvalidPlayer")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    String target2color = "";
                    String target2name = target2.getName();
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        String ip = plugin.data.config.getString("IPs." + target2.getUniqueId().toString() + ".IP");
                        if (!plugin.data.config.contains("BlacklistedIPs." + target2.getUniqueId().toString() + ".IP")) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Unblacklist.PlayerIsNotBlacklisted").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            if (args.length == 1) {
                                this.silent = false;
                                for (String rank : plugin.ranks) {
                                    if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                        target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                    }
                                }
                                String target2display = target2color + target2name;
                                plugin.setPlayerColor(player);
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unblacklist.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display)));
                                }
                                Utils.sendMessage(player, plugin.getConfig().getString("Unblacklist.ExecutorResponse").replaceAll("%player%", target2display));
                                plugin.blacklisted.remove(ip);
                                plugin.data.config.set("BlacklistedIPs." + target2.getUniqueId().toString(), null);
                                int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BlacklistsAmount");
                                plugin.data.config.set(target2.getUniqueId().toString() + ".Blacklists." + id + ".Status", "Revoked");
                                plugin.data.saveData();
                            } else if (args.length == 2) {
                                if (!args[1].equalsIgnoreCase("-s")) {
                                    Utils.sendMessage(player, plugin.getConfig().getString("Unblacklist.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                                } else {
                                    this.silent = true;
                                    for (String rank : plugin.ranks) {
                                        if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                            target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                        }
                                    }
                                    String target2display = target2color + target2name;
                                    plugin.setPlayerColor(player);
                                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unblacklist.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display)));
                                        }
                                    }
                                    Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unblacklist.ExecutorResponse").replaceAll("%player%", target2display));
                                    plugin.blacklisted.remove(ip);
                                    plugin.data.config.set("BlacklistedIPs." + target2.getUniqueId().toString(), null);
                                    int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BlacklistsAmount");
                                    plugin.data.config.set(target2.getUniqueId() + ".Blacklists." + id + ".Status", "Revoked");
                                    plugin.data.saveData();
                                }
                            }
                        }
                    } else {
                        String consoleName = plugin.getConfig().getString("Console.name");
                        String ip = plugin.data.config.getString("IPs." + target2.getUniqueId().toString() + ".IP");
                        if (!plugin.data.config.contains("BlacklistedIPs." + target2.getUniqueId().toString() + ".IP")) {
                            Utils.sendMessage(sender, plugin.getConfig().getString("Unblacklist.PlayerIsNotBlacklisted").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            if (args.length == 1) {
                                this.silent = false;
                                for (String rank : plugin.ranks) {
                                    if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                        target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                    }
                                }
                                String target2display = target2color + target2name;
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unblacklist.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target2display)));
                                }
                                Utils.sendMessage(sender, plugin.getConfig().getString("Unblacklist.ExecutorResponse").replaceAll("%player%", target2display));
                                plugin.blacklisted.remove(ip);
                                plugin.data.config.set("BlacklistedIPs." + target2.getUniqueId().toString(), null);
                                int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BlacklistsAmount");
                                plugin.data.config.set(target2.getUniqueId().toString() + ".Blacklists." + id + ".Status", "Revoked");
                                plugin.data.saveData();
                            } else if (args.length == 2) {
                                if (!args[1].equalsIgnoreCase("-s")) {
                                    Utils.sendMessage(sender, plugin.getConfig().getString("Unblacklist.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                                } else {
                                    this.silent = true;
                                    for (String rank : plugin.ranks) {
                                        if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                            target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                        }
                                    }
                                    String target2display = target2color + target2name;
                                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unblacklist.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target2display)));
                                        }
                                    }
                                    Utils.sendMessage(sender, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unblacklist.ExecutorResponse").replaceAll("%player%", target2display));
                                    plugin.blacklisted.remove(ip);
                                    plugin.data.config.set("BlacklistedIPs." + target2.getUniqueId().toString(), null);
                                    int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BlacklistsAmount");
                                    plugin.data.config.set(target2.getUniqueId() + ".Blacklists." + id + ".Status", "Revoked");
                                    plugin.data.saveData();
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
