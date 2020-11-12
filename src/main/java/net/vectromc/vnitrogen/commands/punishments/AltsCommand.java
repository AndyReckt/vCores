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

public class AltsCommand implements CommandExecutor {

    private vNitrogen plugin;

    public AltsCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("Alts.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length != 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Alts.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        int amount = 0;
                        for (String allUUIDS : plugin.data.config.getConfigurationSection("IPs").getKeys(false)) {
                            if (plugin.data.config.getString("IPs." + target.getUniqueId().toString() + ".IP").equals(plugin.data.config.get("IPs." + allUUIDS + ".IP")) && !allUUIDS.equals(target.getUniqueId().toString())) {
                                amount++;
                                Player altPlayer = Bukkit.getPlayer(plugin.data.config.getString("IPs." + allUUIDS + ".Name"));
                                String altStatus = "";
                                if (altPlayer != null) {
                                    if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(altPlayer.getUniqueId().toString())) {
                                        altStatus = "&6" + altPlayer.getName();
                                    } else {
                                        altStatus = "&a" + altPlayer.getName();
                                    }
                                } else {
                                    OfflinePlayer altOfflinePlayer = Bukkit.getOfflinePlayer(plugin.data.config.getString("IPs." + allUUIDS + ".Name"));
                                    if (plugin.data.config.getConfigurationSection("BlacklistedIPs").getKeys(false).contains(altOfflinePlayer.getUniqueId().toString())) {
                                        if (plugin.data.config.getString("BlacklistedIPs." + altOfflinePlayer.getUniqueId().toString() + ".IP").equalsIgnoreCase(plugin.data.config.getString("IPs." + altOfflinePlayer.getUniqueId().toString() + ".IP"))) {
                                            altStatus = "&4" + altOfflinePlayer.getName();
                                        }
                                    } else if (plugin.data.config.getConfigurationSection("BannedPlayers").getKeys(false).contains(altOfflinePlayer.getUniqueId().toString())) {
                                        altStatus = "&c" + altOfflinePlayer.getName();
                                    } else if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(altOfflinePlayer.getUniqueId().toString())) {
                                        altStatus = "&6" + altOfflinePlayer.getName();
                                    } else {
                                        altStatus = "&7" + altOfflinePlayer.getName();
                                    }
                                }
                                plugin.alts.add(altStatus);
                            }
                        }
                        if (amount >= 1) {
                            plugin.setTargetColor(target);
                            String str = "";
                            for (String alt : plugin.alts) {
                                if (str.length() == 0) {
                                    str = alt;
                                } else {
                                    str = str + " " + alt;
                                }
                            }
                            Utils.sendMessage(player, plugin.getConfig().getString("Alts.CommandMessage").replaceAll("%player%", target.getDisplayName()).replaceAll("%alts%", str));
                            plugin.alts.clear();
                        }
                    } else {
                        OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                        int amount = 0;
                        for (String allUUIDS : plugin.data.config.getConfigurationSection("IPs").getKeys(false)) {
                            if (plugin.data.config.getString("IPs." + target2.getUniqueId().toString() + ".IP").equals(plugin.data.config.get("IPs." + allUUIDS + ".IP")) && !allUUIDS.equals(target2.getUniqueId().toString())) {
                                amount++;
                                Player altPlayer = Bukkit.getPlayer(plugin.data.config.getString("IPs." + allUUIDS + ".Name"));
                                String altStatus= "";
                                if (altPlayer != null) {
                                    if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(altPlayer.getUniqueId().toString())) {
                                        altStatus = "&6" + altPlayer.getName();
                                    } else {
                                        altStatus = "&a" + altPlayer.getName();
                                    }
                                } else {
                                    OfflinePlayer altOfflinePlayer = Bukkit.getOfflinePlayer(plugin.data.config.getString("IPs." + allUUIDS + ".Name"));
                                    if (plugin.data.config.getConfigurationSection("BlacklistedIPs").getKeys(false).contains(altOfflinePlayer.getUniqueId().toString())) {
                                        if (plugin.data.config.getString("BlacklistedIPs." + altOfflinePlayer.getUniqueId().toString() + ".IP").equalsIgnoreCase(plugin.data.config.getString("IPs." + altOfflinePlayer.getUniqueId().toString() + ".IP"))) {
                                            altStatus = "&4" + altOfflinePlayer.getName();
                                        }
                                    } else if (plugin.data.config.getConfigurationSection("BannedPlayers").getKeys(false).contains(altOfflinePlayer.getUniqueId().toString())) {
                                        altStatus = "&c" + altOfflinePlayer.getName();
                                    } else if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(altOfflinePlayer.getUniqueId().toString())) {
                                        altStatus = "&6" + altOfflinePlayer.getName();
                                    } else {
                                        altStatus = "&7" + altOfflinePlayer.getName();
                                    }
                                }
                                plugin.alts.add(altStatus);
                            }
                        }
                        if (amount >= 1) {
                            String target2color = "";
                            String target2name = target2.getName();
                            String dfColor = "";
                            for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                                if (plugin.getConfig().getBoolean("Ranks." + rank.toUpperCase() + ".default")) {
                                    dfColor = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                }
                            }
                            if (!plugin.data.config.contains(target2.getUniqueId().toString()) || !plugin.data.config.contains(target2.getUniqueId().toString() + ".Rank")) {
                                target2color = dfColor;
                            } else {
                                for (String rank : plugin.ranks) {
                                    if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                        target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                    }
                                }
                            }
                            String target2display = target2color + target2name;
                            String str = "";
                            for (String alt : plugin.alts) {
                                if (str.length() == 0) {
                                    str = alt;
                                } else {
                                    str = str + " " + alt;
                                }
                            }
                            Utils.sendMessage(player, plugin.getConfig().getString("Alts.CommandMessage").replaceAll("%player%", target2display).replaceAll("%alts%", str));
                            plugin.alts.clear();
                        }
                    }
                }
            }
        }
        return true;
    }
}
