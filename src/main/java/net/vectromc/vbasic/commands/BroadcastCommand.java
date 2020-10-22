package net.vectromc.vbasic.commands;

import com.google.common.base.Joiner;
import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor {

    private vBasic plugin;
    private vNitrogen nitrogen;

    public BroadcastCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("yosmpcore.admin")) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            Player player = (Player) sender;
            if (args.length == 0) {
                Utils.sendMessage(sender, plugin.getConfig().getString("BroadcastIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (args.length >= 1) {
                    nitrogen.setPlayerColor(player);
                    final String message = Joiner.on(" ").join((Object[])args);
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("BroadcastFormat").replaceAll("%message%", message)));
                    }
                    for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                        if (plugin.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                            onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.Broadcast").replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", message)));
                        }
                    }
                }
            }
        }
        return true;
    }
}
