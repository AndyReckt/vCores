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

public class FreezeCommand implements CommandExecutor {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;
    private vBasic basic;

    public FreezeCommand() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
        basic = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("FreezePermission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (plugin.freeze_cooldown.containsKey(player.getUniqueId())) {
                    Utils.sendMessage(player, "&cYou are still on cooldown for that!");
                } else {
                    if (args.length == 0) {
                        Utils.sendMessage(player, plugin.getConfig().getString("FreezeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        nitrogen.setPlayerColor(player);
                        if (target != null) {
                            nitrogen.setTargetColor(target);
                            if (plugin.frozen.contains(target.getUniqueId())) {
                                plugin.frozen.remove(target.getUniqueId());
                                Utils.sendTargetMessage(target, plugin.getConfig().getString("FreezeOffPlayer"));
                                Utils.sendMessage(player, plugin.getConfig().getString("FreezeOff").replaceAll("%target%", target.getDisplayName()));
                                for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                                    if (basic.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                        onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.FreezeOff").replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName())));
                                    }
                                }
                                plugin.freeze_cooldown.put(player.getUniqueId(), 1.0);
                            } else {
                                plugin.frozen.add(target.getUniqueId());
                                Utils.sendMessage(player, plugin.getConfig().getString("FreezeOn").replaceAll("%target%", target.getDisplayName()));
                                Utils.sendTargetMessage(target, plugin.getConfig().getString("FreezePopup"));
                                for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                                    if (basic.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                        onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.FreezeOn").replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName())));
                                    }
                                }
                                plugin.freeze_cooldown.put(player.getUniqueId(), 1.0);
                            }
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("FreezeInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        }
                    } else {
                        Utils.sendMessage(player, plugin.getConfig().getString("FreezeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                }
            }
        }
        return true;
    }
}
