package net.vectromc.vnitrogen.commands.toggles;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatToggle implements CommandExecutor {

    private vNitrogen plugin;

    public StaffChatToggle() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("staffchattoggle")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("YouMustBePlayer").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
            } else {
                if (!sender.hasPermission(plugin.getConfig().getString("StaffChat.permission"))) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                } else {
                    Player player = (Player) sender;
                    if (plugin.staffchat_toggle.contains(player.getUniqueId())) {
                        plugin.staffchat_toggle.remove(player.getUniqueId());
                        Utils.sendMessage(player, plugin.getConfig().getString("StaffChat.Toggles.ToggleOff"));
                    } else {
                        plugin.staffchat_toggle.add(player.getUniqueId());
                        Utils.sendMessage(player, plugin.getConfig().getString("StaffChat.Toggles.ToggleOn"));
                    }
                }
            }
        }
        return true;
    }
}
