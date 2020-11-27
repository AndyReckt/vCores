package net.vectromc.vbasic.commands.staff;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommands implements CommandExecutor {

    private vBasic plugin;
    private vNitrogen nitrogen;

    public TeleportCommands() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("teleport")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (!sender.hasPermission(plugin.getConfig().getString("TeleportPermission"))) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player player = (Player) sender;
                    if (args.length == 0) {
                        Utils.sendMessage(player, plugin.getConfig().getString("TeleportIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            plugin.back.remove(player);
                            Location oldLoc = player.getLocation();
                            plugin.back.put(player, oldLoc);
                            nitrogen.setTargetColor(target);
                            nitrogen.setPlayerColor(player);
                            player.teleport(target);
                            Utils.sendMessage(player, plugin.getConfig().getString("TeleportedToPlayer").replaceAll("%target%", target.getDisplayName()));
                            for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                                if (plugin.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                    onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.TeleportToPlayer").replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName())));
                                }
                            }
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("TeleportInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        }
                    } else if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[0]);
                        Player target2 = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            if (target2 != null) {
                                plugin.back.remove(target);
                                Location oldLoc = target.getLocation();
                                plugin.back.put(target, oldLoc);
                                nitrogen.setTargetColor(target);
                                nitrogen.setTarget2Color(target2);
                                nitrogen.setPlayerColor(player);
                                target.teleport(target2);
                                Utils.sendMessage(player, plugin.getConfig().getString("YouTeleportedPlayerToPlayer").replaceAll("%target1%", target.getDisplayName()).replaceAll("%target2%", target2.getDisplayName()));
                                Utils.sendTargetMessage(target, plugin.getConfig().getString("PlayerTeleportedPlayerToPlayer").replaceAll("%target%", target.getDisplayName()).replaceAll("%player%", player.getDisplayName()));
                                for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                                    if (plugin.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                        onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.PlayerTeleportedPlayerToPlayer").replaceAll("%player%", player.getDisplayName()).replaceAll("%target1%", target.getDisplayName()).replaceAll("%target2%", target2.getDisplayName())));
                                    }
                                }
                            } else {
                                Utils.sendMessage(player, plugin.getConfig().getString("TeleportInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            }
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("TeleportInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        }
                    } else if (args.length > 2) {
                        Utils.sendMessage(player, plugin.getConfig().getString("TeleportIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                    return true;
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("teleporthere")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (!sender.hasPermission(plugin.getConfig().getString("TeleportPermission"))) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player player = (Player) sender;
                    if (args.length == 0) {
                        Utils.sendMessage(player, plugin.getConfig().getString("TeleportHereIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            plugin.back.remove(target);
                            Location oldLoc = target.getLocation();
                            plugin.back.put(target, oldLoc);
                            nitrogen.setTargetColor(target);
                            nitrogen.setPlayerColor(player);
                            target.teleport(player);
                            Utils.sendMessage(player, plugin.getConfig().getString("TeleportPlayerToYou").replaceAll("%target%", target.getDisplayName()));
                            Utils.sendTargetMessage(target, plugin.getConfig().getString("TeleportedToPlayerNotSender").replaceAll("%player%", player.getDisplayName()));
                            for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                                if (plugin.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                    onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.TeleportHerePlayer").replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName())));
                                }
                            }
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("TeleportInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        }
                    } else if (args.length > 1) {
                        Utils.sendMessage(player, plugin.getConfig().getString("TeleportHereIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("teleportall")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (!sender.hasPermission(plugin.getConfig().getString("NoPermission"))) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player player = (Player) sender;
                    nitrogen.setPlayerColor(player);
                    Utils.sendMessage(player, plugin.getConfig().getString("TpAll"));
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("TeleportPermission"))) {
                            plugin.back.remove(onlinePlayers);
                            Location oldLoc = player.getLocation();
                            plugin.back.put(onlinePlayers, oldLoc);
                        }
                        onlinePlayers.teleport(player);
                    }
                    for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                        if (plugin.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                            onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.TpAll").replaceAll("%player%", player.getDisplayName())));
                        }
                    }
                }
            }
        }
        return true;
    }
}
