package net.vectromc.vbasic.commands.staff;

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
        if (!sender.hasPermission("vbasic.broadcast")) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (args.length == 0) {
                Utils.sendMessage(sender, plugin.getConfig().getString("BroadcastIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (args.length >= 1) {
                    String message = Joiner.on(" ").join(args);
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("BroadcastFormat").replaceAll("%message%", message)));
                    }
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        nitrogen.setPlayerColor(player);
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (plugin.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.Broadcast").replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", message)));
                            }
                        }
                    } else {
                        String consoleName = nitrogen.getConfig().getString("Console.name");
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (plugin.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.Broadcast").replaceAll("%player%", consoleName).replaceAll("%message%", message)));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
