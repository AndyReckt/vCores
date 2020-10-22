package net.vectromc.vnitrogen.chats;

import com.google.common.base.Joiner;
import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    private vNitrogen plugin;

    public StaffChatCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("YouMustBePlayer").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("StaffChat.permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length == 0) {
                    Utils.sendMessage(player, plugin.getConfig().getString("StaffChat.incorrectusage").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                }
                else if (args.length > 0) {
                    plugin.setPlayerColor(player);
                    String world = player.getWorld().getName();
                    String message = Joiner.on(" ").join((Object[])args);
                    for (Player staff : Bukkit.getOnlinePlayers()) {
                        if (staff.hasPermission(plugin.getConfig().getString("StaffChat.permission"))) {
                            staff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffChat.format").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", message)));
                        }
                    }
                }
            }
        }
        return true;
    }
}
