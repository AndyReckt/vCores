package net.vectromc.vnitrogen.commands.punishments;

import net.vectromc.vnitrogen.management.PunishmentManagement;
import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {

    private vNitrogen plugin;
    private Boolean silent;

    public KickCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("Kick.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            String consoleName = plugin.getConfig().getString("Console.name");
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0 || args.length == 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Kick.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        Utils.sendMessage(player, plugin.getConfig().getString("Kick.InvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        plugin.setPlayerColor(player);
                        plugin.setTargetColor(target);
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
                        target.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kick.KickMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%reason%", reason)));
                        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                            if (!silent) {
                                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kick.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                            } else {
                                if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Kick.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                                }
                            }
                        }
                        if (!silent) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Kick.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Kick.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                        }
                        PunishmentManagement punishmentManagement = new PunishmentManagement(target);
                        int id = plugin.data.config.getInt(target.getUniqueId().toString() + ".KicksAmount") + 1;
                        punishmentManagement.addKick();
                        plugin.data.config.set(target.getUniqueId() + ".Kicks." + id + ".Executor", player.getUniqueId().toString());
                        plugin.data.config.set(target.getUniqueId() + ".Kicks." + id + ".Reason", reason);
                        plugin.data.config.set(target.getUniqueId() + ".Kicks." + id + ".Silent", silent.toString());
                        plugin.data.config.set(target.getUniqueId() + ".Kicks." + id + ".Server", player.getWorld().getName());
                        plugin.data.config.set(target.getUniqueId() + ".Kicks." + id + ".Date", System.currentTimeMillis());
                        plugin.data.saveData();
                    }
                }
            } else {
                if (args.length == 0 || args.length == 1) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("Kick.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        Utils.sendMessage(sender, plugin.getConfig().getString("Kick.InvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        plugin.setTargetColor(target);
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
                        target.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kick.KickMessage").replaceAll("%executor%", consoleName).replaceAll("%reason%", reason)));
                        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                            if (!silent) {
                                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kick.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                            } else {
                                if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Kick.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                                }
                            }
                        }
                        if (!silent) {
                            Utils.sendMessage(sender, plugin.getConfig().getString("Kick.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                        } else {
                            Utils.sendMessage(sender, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Kick.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                        }
                        PunishmentManagement punishmentManagement = new PunishmentManagement(target);
                        int id = plugin.data.config.getInt(target.getUniqueId().toString() + ".KicksAmount") + 1;
                        punishmentManagement.addKick();
                        plugin.data.config.set(target.getUniqueId() + ".Kicks." + id + ".Executor", "Console");
                        plugin.data.config.set(target.getUniqueId() + ".Kicks." + id + ".Reason", reason);
                        plugin.data.config.set(target.getUniqueId() + ".Kicks." + id + ".Silent", silent.toString());
                        plugin.data.config.set(target.getUniqueId() + ".Kicks." + id + ".Server", target.getWorld().getName());
                        plugin.data.config.set(target.getUniqueId() + ".Kicks." + id + ".Date", System.currentTimeMillis());
                        plugin.data.saveData();
                    }
                }
            }
        }
        return true;
    }
}
