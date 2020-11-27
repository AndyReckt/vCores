package net.vectromc.vnitrogen.commands.toggles;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminChatToggle implements CommandExecutor {

    private vNitrogen plugin;

    public AdminChatToggle() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("adminchattoggle")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("YouMustBePlayer").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
            } else {
                if (!sender.hasPermission(plugin.getConfig().getString("AdminChat.permission"))) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                } else {
                    Player player = (Player) sender;
                    if (plugin.adminchat_toggle.contains(player.getUniqueId())) {
                        plugin.adminchat_toggle.remove(player.getUniqueId());
                        Utils.sendMessage(player, plugin.getConfig().getString("AdminChat.Toggles.ToggleOff"));
                    } else {
                        plugin.adminchat_toggle.add(player.getUniqueId());
                        Utils.sendMessage(player, plugin.getConfig().getString("AdminChat.Toggles.ToggleOn"));
                    }
                }
            }
        }
        return true;
    }
}
