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

public class BuildModeCommand implements CommandExecutor {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;
    private vBasic basic;

    public BuildModeCommand() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
        basic = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("BuildModePermission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                nitrogen.setPlayerColor(player);
                if (plugin.buildmode.contains(player.getUniqueId())) {
                    plugin.buildmode.remove(player.getUniqueId());
                    Utils.sendMessage(player, plugin.getConfig().getString("BuildOn"));
                    for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                        if (basic.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                            onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.BuildOn").replaceAll("%player%", player.getDisplayName())));
                        }
                    }
                } else {
                    plugin.buildmode.add(player.getUniqueId());
                    Utils.sendMessage(player, plugin.getConfig().getString("BuildOff"));
                    for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                        if (basic.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                            onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.BuildOff").replaceAll("%player%", player.getDisplayName())));
                        }
                    }
                }
            }
        }
        return true;
    }
}
