package net.vectromc.vbasic.commands;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleMessages implements CommandExecutor {

    private vBasic plugin;

    public ToggleMessages() {
        plugin = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            Player player = (Player) sender;
            if (plugin.tpm.contains(player.getUniqueId())) {
                plugin.tpm.remove(player.getUniqueId());
                Utils.sendMessage(player, plugin.getConfig().getString("TpmOn"));
            } else {
                plugin.tpm.add(player.getUniqueId());
                Utils.sendMessage(player, plugin.getConfig().getString("TpmOff"));
            }
        }
        return true;
    }
}

