package net.vectromc.vnitrogen.commands.toggles;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManagementChatToggle implements CommandExecutor {

    private vNitrogen plugin;

    public ManagementChatToggle() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("managementchattoggle")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("YouMustBePlayer").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
            } else {
                if (!sender.hasPermission(plugin.getConfig().getString("ManagementChat.permission"))) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                } else {
                    Player player = (Player) sender;
                    if (plugin.managementchat_toggle.contains(player.getUniqueId())) {
                        plugin.managementchat_toggle.remove(player.getUniqueId());
                        Utils.sendMessage(player, plugin.getConfig().getString("ManagementChat.Toggles.ToggleOff"));
                    } else {
                        plugin.managementchat_toggle.add(player.getUniqueId());
                        Utils.sendMessage(player, plugin.getConfig().getString("ManagementChat.Toggles.ToggleOn"));
                    }
                }
            }
        }
        return true;
    }
}
