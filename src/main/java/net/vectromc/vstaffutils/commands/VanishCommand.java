package net.vectromc.vstaffutils.commands;

import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;
    private vBasic basic;

    public VanishCommand() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
        basic = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("VanishPermission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length == 0) {
                    if (!plugin.vanished.contains(player.getUniqueId())) {
                        plugin.vanished.add(player.getUniqueId());
                        nitrogen.setPlayerColor(player);
                        Utils.sendMessage(player, plugin.getConfig().getString("VanishOnSelf").replaceAll("%player%", player.getDisplayName()));
                        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                            if (!onlinePlayers.hasPermission(plugin.getConfig().getString("VanishPermission"))) {
                                onlinePlayers.hidePlayer(player);
                            }
                            if (basic.toggle_staff_alerts.contains(onlinePlayers.getUniqueId())) {
                                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.VanishOnSelf").replaceAll("%player%", player.getDisplayName())));
                            }
                        }
                    } else {
                        nitrogen.setPlayerColor(player);
                        plugin.vanished.remove(player.getUniqueId());
                        Utils.sendMessage(player, plugin.getConfig().getString("VanishOffSelf").replaceAll("%player%", player.getDisplayName()));
                        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                            onlinePlayers.showPlayer(player);
                            if (basic.toggle_staff_alerts.contains(onlinePlayers.getUniqueId())) {
                                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.VanishOffSelf").replaceAll("%player%", player.getDisplayName())));
                            }
                        }
                    }
                } else if (args.length == 1) {
                    if (!player.hasPermission(plugin.getConfig().getString("VanishOthersPermission"))) {
                        Utils.sendMessage(player, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            if (!plugin.vanished.contains(target.getUniqueId())) {
                                nitrogen.setPlayerColor(player);
                                nitrogen.setPlayerColor(target);
                                plugin.vanished.add(target.getUniqueId());
                                Utils.sendMessage(player, plugin.getConfig().getString("VanishOnOther").replaceAll("%target%", target.getDisplayName()));
                                Utils.sendTargetMessage(target, plugin.getConfig().getString("VanishOnSelf"));
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    if (!onlinePlayers.hasPermission(plugin.getConfig().getString("VanishPermission"))) {
                                        onlinePlayers.hidePlayer(target);
                                    }
                                    if (basic.toggle_staff_alerts.contains(onlinePlayers.getUniqueId())) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.VanishOnOther").replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName())));
                                    }
                                }
                            } else {
                                nitrogen.setPlayerColor(player);
                                nitrogen.setPlayerColor(target);
                                plugin.vanished.remove(target.getUniqueId());
                                Utils.sendMessage(player, plugin.getConfig().getString("VanishOffOther").replaceAll("%target%", target.getDisplayName()));
                                Utils.sendTargetMessage(target, plugin.getConfig().getString("VanishOffSelf"));
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    onlinePlayers.showPlayer(player);
                                    if (basic.toggle_staff_alerts.contains(onlinePlayers.getUniqueId())) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.VanishOffOther").replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName())));
                                    }
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
