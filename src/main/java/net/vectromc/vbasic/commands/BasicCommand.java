package net.vectromc.vbasic.commands;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BasicCommand implements CommandExecutor {

    private vBasic plugin;

    public BasicCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
        } else {
            Player player = (Player) sender;
            if (args.length == 0) {
                Utils.liner(player);
                Utils.sendMessage(player, "&a&lvBasic");
                Utils.spacer(player);
                Utils.sendMessage(player, "&2Plugin By: &b&oYochran");
                Utils.sendMessage(player, "&2Plugin Version: &a1.0");
                Utils.liner(player);
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    Utils.sendMessage(player, plugin.getConfig().getString("ReloadConfig").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Utils.liner(player);
                    Utils.sendMessage(player, "&a&lvBasic");
                    Utils.spacer(player);
                    Utils.sendMessage(player, "&2Plugin By: &b&oYochran");
                    Utils.sendMessage(player, "&2Plugin Version: &a1.0");
                    Utils.liner(player);
                }
            } else {
                Utils.liner(player);
                Utils.sendMessage(player, "&a&lvBasic");
                Utils.spacer(player);
                Utils.sendMessage(player, "&2Plugin By: &b&oYochran");
                Utils.sendMessage(player, "&2Plugin Version: &a1.0");
                Utils.liner(player);
            }
        }
        return true;
    }
}
