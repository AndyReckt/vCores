package net.vectromc.vnitrogen.commands.punishments;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MutechatCommand implements CommandExecutor {

    private vNitrogen plugin;

    public MutechatCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("MuteChat.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            String consoleName = plugin.getConfig().getString("Console.name");
            if (sender instanceof Player) {
                Player player = (Player) sender;
                plugin.setPlayerColor(player);
                String world = player.getWorld().getName();
                if (!plugin.chatMute.contains("Active")) {
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MuteChat.GlobalMessageOn").replaceAll("%player%", player.getDisplayName())));
                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("MuteChat.Bypass"))) {
                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MuteChat.StaffMessageOn").replaceAll("%player%", player.getDisplayName()).replaceAll("%world%", world)));
                        }
                    }
                    plugin.chatMute.add("Active");
                } else {
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MuteChat.GlobalMessageOff").replaceAll("%player%", player.getDisplayName())));
                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("MuteChat.Bypass"))) {
                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MuteChat.StaffMessageOff").replaceAll("%player%", player.getDisplayName()).replaceAll("%world%", world)));
                        }
                    }
                    plugin.chatMute.remove("Active");
                }
            } else {
                String  world = "CONSOLE";
                if (!plugin.chatMute.contains("Active")) {
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MuteChat.GlobalMessageOn").replaceAll("%player%", consoleName)));
                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("MuteChat.Bypass"))) {
                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MuteChat.StaffMessageOn").replaceAll("%player%", consoleName).replaceAll("%world%", world)));
                        }
                    }
                    plugin.chatMute.add("Active");
                } else {
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MuteChat.GlobalMessageOff").replaceAll("%player%", consoleName)));
                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("MuteChat.Bypass"))) {
                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MuteChat.StaffMessageOff").replaceAll("%player%", consoleName).replaceAll("%world%", world)));
                        }
                    }
                    plugin.chatMute.remove("Active");
                }
            }
        }
        return true;
    }
}
