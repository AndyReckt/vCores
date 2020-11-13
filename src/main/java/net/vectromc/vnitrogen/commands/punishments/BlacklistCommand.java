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

public class BlacklistCommand implements CommandExecutor {

    private vNitrogen plugin;
    private Boolean silent;

    public BlacklistCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("YouMustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("Blacklist.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length == 0 || args.length == 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Blacklist.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        plugin.setPlayerColor(player);
                        plugin.setTargetColor(target);
                        String reason = "";
                        for (int i = 1; i < args.length; i++) {
                            reason = reason + " " + args[i];
                        }
                        if (reason.contains("-s")) {
                            reason = reason.replaceFirst("-s", "");
                            this.silent = true;
                            reason = reason.replaceFirst(" ", "");
                        } else {
                            this.silent = false;
                        }
                        target.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Blacklist.BlacklistMessage").replaceAll("%reason%", reason).replaceAll("%executor%", player.getDisplayName())));
                        int id = plugin.data.config.getInt(target.getUniqueId().toString() + ".BlacklistsAmount") + 1;
                        plugin.data.config.createSection(target.getUniqueId().toString() + ".Blacklists." + id + ".LinkedAccounts");
                        for (Player onlineLinks : Bukkit.getOnlinePlayers()) {
                            if (onlineLinks.getAddress().getAddress().getHostAddress().equals(target.getAddress().getAddress().getHostAddress())) {
                                onlineLinks.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Blacklist.BlacklistMessage").replaceAll("%reason%", reason).replaceAll("%executor%", player.getDisplayName())));
                                plugin.data.config.set(target.getUniqueId().toString() + ".Blacklists." + id + ".LinkedAccounts." + onlineLinks.getUniqueId() + ".Name", player.getName());
                            }
                        }
                        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                            if (!silent) {
                                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Blacklist.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                            } else {
                                if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Blacklist.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                                }
                            }
                        }
                        if (!silent) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Blacklist.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Blacklist.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                        }
                        PunishmentManagement punishmentManagement = new PunishmentManagement(target);
                        punishmentManagement.addBlacklist();
                        plugin.data.config.set(target.getUniqueId() + ".Blacklists." + id + ".Executor", player.getUniqueId().toString());
                        plugin.data.config.set(target.getUniqueId() + ".Blacklists." + id + ".Reason", reason);
                        plugin.data.config.set(target.getUniqueId() + ".Blacklists." + id + ".Silent", silent.toString());
                        plugin.data.config.set(target.getUniqueId() + ".Blacklists." + id + ".Server", player.getWorld().getName());
                        plugin.data.config.set(target.getUniqueId() + ".Blacklists." + id + ".Date", System.currentTimeMillis());
                        plugin.data.config.set(target.getUniqueId() + ".Blacklists." + id + ".Temp", "false");
                        plugin.data.config.set(target.getUniqueId() + ".Blacklists." + id + ".Duration", "Permanent");
                        plugin.data.config.set(target.getUniqueId() + ".Blacklists." + id + ".Status", "Active");
                        plugin.data.config.set("BlacklistedIPs." + target.getUniqueId().toString() + ".IP", target.getAddress().getAddress().getHostAddress());
                        plugin.data.config.set("BlacklistedIPs." + target.getUniqueId().toString() + ".Main", target.getUniqueId().toString());
                        plugin.data.saveData();
                        plugin.blacklisted.add(target.getAddress().getAddress().getHostAddress());
                    } else {
                        OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                        plugin.setPlayerColor(player);
                        String target2color = "";
                        String target2name = args[0];
                        if (!plugin.data.config.contains("IPs." + target2.getUniqueId().toString() + ".IP")) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Blacklist.CantBlacklist").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            String ip = plugin.data.config.getString("IPs." + target2.getUniqueId().toString() + ".IP");
                            String reason = "";
                            for (int i = 1; i < args.length; i++) {
                                reason = reason + " " + args[i];
                            }
                            if (reason.contains("-s")) {
                                reason = reason.replaceFirst("-s", "");
                                this.silent = true;
                                reason = reason.replaceFirst(" ", "");
                            } else {
                                this.silent = false;
                            }
                            int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BlacklistsAmount") + 1;
                            plugin.data.config.createSection(target2.getUniqueId().toString() + ".Blacklists." + id + ".LinkedAccounts");
                            for (Player onlineLinks : Bukkit.getOnlinePlayers()) {
                                if (onlineLinks.getAddress().getAddress().getHostAddress().equals(ip)) {
                                    onlineLinks.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Blacklist.BlacklistMessage").replaceAll("%reason%", reason).replaceAll("%executor%", player.getDisplayName())));
                                    plugin.data.config.set(target2.getUniqueId().toString() + ".Blacklists." + id + ".LinkedAccounts." + onlineLinks.getUniqueId() + ".Name", player.getName());
                                }
                            }
                            String dfColor = "";
                            for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                                if (plugin.getConfig().getBoolean("Ranks." + rank.toUpperCase() + ".default")) {
                                    dfColor = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                }
                            }
                            if (!plugin.pData.config.contains(target2.getUniqueId().toString()) || !plugin.pData.config.contains(target2.getUniqueId().toString() + ".Rank")) {
                                target2color = dfColor;
                            } else {
                                for (String rank : plugin.ranks) {
                                    if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                        target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                    }
                                }
                            }
                            String target2display = target2color + target2name;
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                if (!silent) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Blacklist.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                } else {
                                    if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Blacklist.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                    }
                                }
                            }
                            if (!silent) {
                                Utils.sendMessage(player, plugin.getConfig().getString("Blacklist.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                            } else {
                                Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Blacklist.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                            }
                            PunishmentManagement punishmentManagement = new PunishmentManagement(target2);
                            punishmentManagement.addBlacklist();
                            plugin.data.config.set(target2.getUniqueId() + ".Blacklists." + id + ".Executor", player.getUniqueId().toString());
                            plugin.data.config.set(target2.getUniqueId() + ".Blacklists." + id + ".Reason", reason);
                            plugin.data.config.set(target2.getUniqueId() + ".Blacklists." + id + ".Silent", silent.toString());
                            plugin.data.config.set(target2.getUniqueId() + ".Blacklists." + id + ".Server", player.getWorld().getName());
                            plugin.data.config.set(target2.getUniqueId() + ".Blacklists." + id + ".Date", System.currentTimeMillis());
                            plugin.data.config.set(target2.getUniqueId() + ".Blacklists." + id + ".Temp", "false");
                            plugin.data.config.set(target2.getUniqueId() + ".Blacklists." + id + ".Duration", "Permanent");
                            plugin.data.config.set(target2.getUniqueId() + ".Blacklists." + id + ".Status", "Active");
                            plugin.data.config.set("BlacklistedIPs." + target2.getUniqueId().toString() + ".IP", ip);
                            plugin.data.config.set("BlacklistedIPs." + target2.getUniqueId().toString() + ".Main", target2.getUniqueId().toString());
                            plugin.data.saveData();
                            plugin.blacklisted.add(ip);
                        }
                    }
                }
            }
        }
        return true;
    }
}
