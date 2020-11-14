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

public class MuteCommand implements CommandExecutor {

    private vNitrogen plugin;
    private Boolean silent;

    public MuteCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("Mute.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            String consoleName = plugin.getConfig().getString("Console.name");
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0 || args.length == 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Mute.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                    if (!plugin.pData.config.contains(target2.getUniqueId().toString())) {
                        Utils.sendMessage(player, plugin.getConfig().getString("Mute.InvalidPlayer")
                                .replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(target2.getUniqueId().toString())) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Mute.PlayerIsMuted").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            String target2name = target2.getName();
                            String target2color = "";
                            for (String rank : plugin.ranks) {
                                if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                    target2color = plugin.getConfig().getString("Ranks." + rank + ".color");
                                }
                            }
                            String target2display = target2color + target2name;
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
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                if (!silent) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Mute.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                } else {
                                    if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Mute.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                    }
                                }
                                if (target2.getUniqueId().equals(onlinePlayers.getUniqueId())) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Mute.TargetResponse")
                                            .replace("%reason%", reason)
                                            .replace("%executor%", player.getDisplayName())));
                                }
                            }
                            if (!silent) {
                                Utils.sendMessage(player, plugin.getConfig().getString("Mute.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                            } else {
                                Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Mute.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                            }
                            plugin.muted.add(target2.getUniqueId().toString());
                            PunishmentManagement punishmentManagement = new PunishmentManagement(target2);
                            int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".MutesAmount") + 1;
                            punishmentManagement.addMute();
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Executor", player.getUniqueId().toString());
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Reason", reason);
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Silent", silent.toString());
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Server", player.getWorld().getName());
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Date", System.currentTimeMillis());
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Status", "Active");
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Temp", "false");
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Duration", "Permanent");
                            plugin.data.config.set("MutedPlayers." + target2.getUniqueId().toString() + ".Name", target2.getName());
                            plugin.data.saveData();
                        }
                    }
                }
            } else {
                if (args.length == 0 || args.length == 1) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("Mute.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                    if (!plugin.pData.config.contains(target2.getUniqueId().toString())) {
                        Utils.sendMessage(sender, plugin.getConfig().getString("Mute.InvalidPlayer")
                                .replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(target2.getUniqueId().toString())) {
                            Utils.sendMessage(sender, plugin.getConfig().getString("Mute.PlayerIsMuted").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            String target2name = target2.getName();
                            String target2color = "";
                            String dfColor = "";
                            for (String rank : plugin.ranks) {
                                if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                    target2color = plugin.getConfig().getString("Ranks." + rank + ".color");
                                }
                            }
                            String target2display = target2color + target2name;
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
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                if (!silent) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Mute.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                } else {
                                    if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Mute.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                    }
                                }
                                if (target2.getUniqueId().equals(onlinePlayers.getUniqueId())) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Mute.TargetResponse")
                                            .replace("%reason%", reason)
                                            .replace("%executor%", consoleName)));
                                }
                            }
                            if (!silent) {
                                Utils.sendMessage(sender, plugin.getConfig().getString("Mute.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                            } else {
                                Utils.sendMessage(sender, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Mute.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                            }
                            plugin.muted.add(target2.getUniqueId().toString());
                            PunishmentManagement punishmentManagement = new PunishmentManagement(target2);
                            int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".MutesAmount") + 1;
                            punishmentManagement.addMute();
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Executor", "Console");
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Reason", reason);
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Silent", silent.toString());
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Server", "N/A");
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Date", System.currentTimeMillis());
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Status", "Active");
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Temp", "false");
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Duration", "Permanent");
                            plugin.data.config.set("MutedPlayers." + target2.getUniqueId().toString() + ".Name", target2.getName());
                            plugin.data.saveData();
                        }
                    }
                }
            }
        }
        return true;
    }
}
