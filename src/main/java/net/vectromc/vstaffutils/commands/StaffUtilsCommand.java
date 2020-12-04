package net.vectromc.vstaffutils.commands;

import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffUtilsCommand implements CommandExecutor {

    private vStaffUtils plugin;

    public StaffUtilsCommand() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            Utils.liner(sender);
            Utils.sendMessage(sender, "&7[&6&lvStaffUtils&7]:");
            Utils.spacer(sender);
            Utils.sendMessage(sender, "&ePlugin By: &6&oYochran");
            Utils.sendMessage(sender, "&ePlugin Version: &61.0");
            Utils.sendMessage(sender, "&6/vstaffutils help");
            Utils.liner(sender);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission(plugin.getConfig().getString("ReloadPermission"))) {
                    plugin.reloadConfig();
                    Utils.sendMessage(sender, plugin.getConfig().getString("ReloadConfig")
                            .replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                            .replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                net.vectromc.vnitrogen.utils.Utils.liner(sender);
                net.vectromc.vnitrogen.utils.Utils.sendMessage(sender, "&7[&6&lvStaffUtils&7]: &eHelp:");
                net.vectromc.vnitrogen.utils.Utils.sendMessage(sender, "&e ");
                net.vectromc.vnitrogen.utils.Utils.sendMessage(sender, "&6&lCommands:");
                net.vectromc.vnitrogen.utils.Utils.sendMessage(sender, "&e/build, /freeze, /invsee, /modmode, /vanish, /report, /onlinestaff.");
                net.vectromc.vnitrogen.utils.Utils.liner(sender);
            } else {
                Utils.liner(sender);
                Utils.sendMessage(sender, "&7[&6&lvStaffUtils&7]:");
                Utils.spacer(sender);
                Utils.sendMessage(sender, "&ePlugin By: &6&oYochran");
                Utils.sendMessage(sender, "&ePlugin Version: &61.0");
                Utils.sendMessage(sender, "&6/vstaffutils help");
                Utils.liner(sender);
            }
        } else {
            Utils.liner(sender);
            Utils.sendMessage(sender, "&7[&6&lvStaffUtils&7]:");
            Utils.spacer(sender);
            Utils.sendMessage(sender, "&ePlugin By: &6&oYochran");
            Utils.sendMessage(sender, "&ePlugin Version: &61.0");
            Utils.sendMessage(sender, "&6/vstaffutils help");
            Utils.liner(sender);
        }
        return true;
    }
}
