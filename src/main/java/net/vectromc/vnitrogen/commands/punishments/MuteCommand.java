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
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("YouMustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("Mute.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length == 0 || args.length == 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Mute.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(target.getUniqueId().toString())) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Mute.PlayerIsMuted").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
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
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                if (!silent) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Mute.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                                } else {
                                    if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Mute.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                                    }
                                }
                            }
                            if (!silent) {
                                Utils.sendMessage(player, plugin.getConfig().getString("Mute.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                            } else {
                                Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Mute.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                            }
                            Utils.sendMessage(target, plugin.getConfig().getString("Mute.TargetResponse").replaceAll("%reason%", reason).replaceAll("%executor%", player.getDisplayName()));
                            plugin.muted.add(target.getUniqueId().toString());
                            PunishmentManagement punishmentManagement = new PunishmentManagement(target);
                            int id = plugin.data.config.getInt(target.getUniqueId().toString() + ".MutesAmount") + 1;
                            punishmentManagement.addMute();
                            plugin.data.config.set(target.getUniqueId() + ".Mutes." + id + ".Executor", player.getUniqueId().toString());
                            plugin.data.config.set(target.getUniqueId() + ".Mutes." + id + ".Reason", reason);
                            plugin.data.config.set(target.getUniqueId() + ".Mutes." + id + ".Silent", silent.toString());
                            plugin.data.config.set(target.getUniqueId() + ".Mutes." + id + ".Server", player.getWorld().getName());
                            plugin.data.config.set(target.getUniqueId() + ".Mutes." + id + ".Date", System.currentTimeMillis());
                            plugin.data.config.set(target.getUniqueId() + ".Mutes." + id + ".Status", "Active");
                            plugin.data.config.set(target.getUniqueId() + ".Mutes." + id + ".Temp", "false");
                            plugin.data.config.set(target.getUniqueId() + ".Mutes." + id + ".Duration", "Permanent");
                            plugin.data.config.set("MutedPlayers." + target.getUniqueId().toString() + ".Name", target.getName());
                            plugin.data.saveData();
                        }
                    } else {
                        OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                        if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(target2.getUniqueId().toString())) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Mute.PlayerIsMuted").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            String target2name = args[0];
                            String target2color = "";
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
                            plugin.setPlayerColor(player);
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
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                if (!silent) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Mute.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                } else {
                                    if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Mute.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                    }
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
            }
        }
        return true;
    }
}
