package net.vectromc.vnitrogen.commands.punishments;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor {

    private vNitrogen plugin;

    public ClearChatCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("ClearChat.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            String consoleName = plugin.getConfig().getString("Console.name");
            if (sender instanceof Player) {
                Player player = (Player) sender;
                plugin.setPlayerColor(player);
                String world = player.getWorld().getName();
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    Utils.chatClearer(onlinePlayers);
                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ClearChat.GlobalMessage").replaceAll("%player%", player.getDisplayName())));
                    if (onlinePlayers.hasPermission("ClearChat.NotifyPermission")) {
                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ClearChat.StaffMessage").replaceAll("%player%", player.getDisplayName()).replaceAll("%world%", world)));
                    }
                }
            } else {
                String world = "CONSOLE";
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    Utils.chatClearer(onlinePlayers);
                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ClearChat.GlobalMessage").replaceAll("%player%", consoleName)));
                    if (onlinePlayers.hasPermission("ClearChat.NotifyPermission")) {
                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ClearChat.StaffMessage").replaceAll("%player%", consoleName).replaceAll("%world%", world)));
                    }
                }
            }
        }
        return true;
    }
}
