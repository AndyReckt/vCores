package net.vectromc.vbasic.commands;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleGlobalChat implements CommandExecutor {

    private vBasic plugin;

    public ToggleGlobalChat() {
        plugin = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            Player player = (Player) sender;
            if (plugin.tgc.contains(player.getUniqueId())) {
                plugin.tgc.remove(player.getUniqueId());
                Utils.sendMessage(player, plugin.getConfig().getString("TgcOn"));
            } else {
                plugin.tgc.add(player.getUniqueId());
                Utils.sendMessage(player, plugin.getConfig().getString("TgcOff"));
            }
        }
        return true;
    }
}
