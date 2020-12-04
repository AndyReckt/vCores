package net.vectromc.vnitrogen.commands.punishments;

import net.vectromc.vnitrogen.management.PunishmentManagement;
import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {

    private vNitrogen plugin;
    private Boolean silent;

    public BanCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("Ban.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            String consoleName = plugin.getConfig().getString("Console.name");
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0 || args.length == 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Ban.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (!plugin.pData.config.contains(target.getUniqueId().toString())) {
                            Utils.sendMessage(sender, plugin.getConfig().getString("Ban.InvalidPlayer")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            if (plugin.data.config.getConfigurationSection("BannedPlayers").getKeys(false).contains(target.getUniqueId().toString())) {
                                Utils.sendMessage(sender, plugin.getConfig().getString("Ban.PlayerIsBanned").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            } else {
                                plugin.setPlayerColor(target);
                                plugin.setPlayerColor(player);
                                String reason = "";
                                for (int i = 1; i < args.length; i++) {
                                    reason = reason + " " + args[i];
                                }
                                if (reason.contains("-s")) {
                                    reason = reason.replaceFirst(" -s", "");
                                    this.silent = true;
                                } else {
                                    this.silent = false;
                                }
                                target.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Ban.BanMessage")
                                        .replace("%reason%", reason)
                                        .replace("%executor%", player.getDisplayName())));
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    if (!silent) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Ban.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                                    } else {
                                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Ban.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                                        }
                                    }
                                }
                                if (!silent) {
                                    Utils.sendMessage(player, plugin.getConfig().getString("Ban.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                                } else {
                                    Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Ban.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                                }
                                PunishmentManagement punishmentManagement = new PunishmentManagement(target);
                                int id = plugin.data.config.getInt(target.getUniqueId().toString() + ".BansAmount") + 1;
                                punishmentManagement.addBan();
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Executor", player.getUniqueId().toString());
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Reason", reason);
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Silent", silent.toString());
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Server", player.getWorld().getName());
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Date", System.currentTimeMillis());
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Temp", "false");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Duration", "Permanent");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Status", "Active");
                                plugin.data.config.set("BannedPlayers." + target.getUniqueId().toString() + ".Name", target.getName());
                                plugin.data.saveData();
                                plugin.banned.add(target.getUniqueId().toString());
                            }
                        }
                    } else {
                        OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                        if (!plugin.pData.config.contains(target2.getUniqueId().toString())) {
                            Utils.sendMessage(sender, plugin.getConfig().getString("Ban.InvalidPlayer")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            if (plugin.data.config.getConfigurationSection("BannedPlayers").getKeys(false).contains(target2.getUniqueId().toString())) {
                                Utils.sendMessage(sender, plugin.getConfig().getString("Ban.PlayerIsBanned").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            } else {
                                String target2color = "";
                                String target2name = target2.getName();
                                plugin.setPlayerColor(player);
                                String reason = "";
                                for (int i = 1; i < args.length; i++) {
                                    reason = reason + " " + args[i];
                                }
                                if (reason.contains("-s")) {
                                    reason = reason.replaceFirst(" -s", "");
                                    this.silent = true;
                                } else {
                                    this.silent = false;
                                }
                                for (String rank : plugin.ranks) {
                                    if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                        target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                    }
                                }
                                String target2display = target2color + target2name;
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    if (!silent) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Ban.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                    } else {
                                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Ban.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                        }
                                    }
                                }
                                if (!silent) {
                                    Utils.sendMessage(player, plugin.getConfig().getString("Ban.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                                } else {
                                    Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Ban.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                                }
                                PunishmentManagement punishmentManagement = new PunishmentManagement(target2);
                                int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BansAmount") + 1;
                                punishmentManagement.addBan();
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Executor", player.getUniqueId().toString());
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Reason", reason);
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Silent", silent.toString());
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Server", player.getWorld().getName());
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Date", System.currentTimeMillis());
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Temp", "false");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Duration", "Permanent");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Status", "Active");
                                plugin.data.config.set("BannedPlayers." + target2.getUniqueId().toString() + ".Name", target2.getName());
                                plugin.data.saveData();
                                plugin.banned.add(target2.getUniqueId().toString());
                            }
                        }
                    }
                }
            } else {
                if (args.length == 0 || args.length == 1) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("Ban.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (!plugin.pData.config.contains(target.getUniqueId().toString())) {
                            Utils.sendMessage(sender, plugin.getConfig().getString("Ban.InvalidPlayer")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            if (plugin.data.config.getConfigurationSection("BannedPlayers").getKeys(false).contains(target.getUniqueId().toString())) {
                                Utils.sendMessage(sender, plugin.getConfig().getString("Ban.PlayerIsBanned").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            } else {
                                plugin.setPlayerColor(target);
                                String reason = "";
                                for (int i = 1; i < args.length; i++) {
                                    reason = reason + " " + args[i];
                                }
                                if (reason.contains("-s")) {
                                    reason = reason.replaceFirst(" -s", "");
                                    this.silent = true;
                                } else {
                                    this.silent = false;
                                }
                                target.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Ban.BanMessage")
                                        .replace("%reason%", reason)
                                        .replace("%executor%", consoleName)));
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    if (!silent) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Ban.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                                    } else {
                                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Ban.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                                        }
                                    }
                                }
                                if (!silent) {
                                    Utils.sendMessage(sender, plugin.getConfig().getString("Ban.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                                } else {
                                    Utils.sendMessage(sender, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Ban.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                                }
                                PunishmentManagement punishmentManagement = new PunishmentManagement(target);
                                int id = plugin.data.config.getInt(target.getUniqueId().toString() + ".BansAmount") + 1;
                                punishmentManagement.addBan();
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Executor", "Console");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Reason", reason);
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Silent", silent.toString());
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Server", target.getWorld().getName());
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Date", System.currentTimeMillis());
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Temp", "false");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Duration", "Permanent");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Status", "Active");
                                plugin.data.config.set("BannedPlayers." + target.getUniqueId().toString() + ".Name", target.getName());
                                plugin.data.saveData();
                                plugin.banned.add(target.getUniqueId().toString());
                            }
                        }
                    } else {
                        OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                        if (!plugin.pData.config.contains(target2.getUniqueId().toString())) {
                            Utils.sendMessage(sender, plugin.getConfig().getString("Ban.InvalidPlayer")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            if (plugin.data.config.getConfigurationSection("BannedPlayers").getKeys(false).contains(target2.getUniqueId().toString())) {
                                Utils.sendMessage(sender, plugin.getConfig().getString("Ban.PlayerIsBanned").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            } else {
                                String target2color = "";
                                String target2name = target2.getName();
                                String reason = "";
                                for (int i = 1; i < args.length; i++) {
                                    reason = reason + " " + args[i];
                                }
                                if (reason.contains("-s")) {
                                    reason = reason.replaceFirst(" -s", "");
                                    this.silent = true;
                                } else {
                                    this.silent = false;
                                }
                                for (String rank : plugin.ranks) {
                                    if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                        target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                    }
                                }
                                String target2display = target2color + target2name;
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    if (!silent) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Ban.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                    } else {
                                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Ban.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                        }
                                    }
                                }
                                if (!silent) {
                                    Utils.sendMessage(sender, plugin.getConfig().getString("Ban.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                                } else {
                                    Utils.sendMessage(sender, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Ban.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                                }
                                PunishmentManagement punishmentManagement = new PunishmentManagement(target2);
                                int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BansAmount") + 1;
                                punishmentManagement.addBan();
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Executor", "Console");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Reason", reason);
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Silent", silent.toString());
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Server", "N/A");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Date", System.currentTimeMillis());
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Temp", "false");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Duration", "Permanent");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Status", "Active");
                                plugin.data.config.set("BannedPlayers." + target2.getUniqueId().toString() + ".Name", target2.getName());
                                plugin.data.saveData();
                                plugin.banned.add(target2.getUniqueId().toString());
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
