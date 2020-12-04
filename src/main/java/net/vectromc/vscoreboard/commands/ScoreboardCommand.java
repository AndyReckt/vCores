package net.vectromc.vscoreboard.commands;

import net.vectromc.vscoreboard.utils.Utils;
import net.vectromc.vscoreboard.vScoreboard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ScoreboardCommand implements CommandExecutor {

    private vScoreboard plugin;

    public ScoreboardCommand() {
        plugin = vScoreboard.getPlugin(vScoreboard.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Utils.liner(sender);
            Utils.sendMessage(sender, "&7[&6&lvScoreboard&7]:");
            Utils.spacer(sender);
            Utils.sendMessage(sender, "&ePlugin By: &6&oYochran");
            Utils.sendMessage(sender, "&ePlugin Version: &61.0");
            Utils.sendMessage(sender, "&6/vscoreboard help");
            Utils.liner(sender);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission(plugin.getConfig().getString("ReloadPermission"))) {
                    plugin.reloadConfig();
                    Utils.sendMessage(sender, plugin.getConfig().getString("ReloadMessage")
                            .replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                            .replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                net.vectromc.vnitrogen.utils.Utils.liner(sender);
                net.vectromc.vnitrogen.utils.Utils.sendMessage(sender, "&7[&6&lvScoreboard&7]: &eHelp:");
                net.vectromc.vnitrogen.utils.Utils.sendMessage(sender, "&e ");
                net.vectromc.vnitrogen.utils.Utils.sendMessage(sender, "&6&lGeneral:");
                net.vectromc.vnitrogen.utils.Utils.sendMessage(sender, "&e/list, /togglescoreboard, /vscoreboard");
                net.vectromc.vnitrogen.utils.Utils.liner(sender);
            } else {
                Utils.liner(sender);
                Utils.sendMessage(sender, "&7[&6&lvScoreboard&7]:");
                Utils.spacer(sender);
                Utils.sendMessage(sender, "&ePlugin By: &6&oYochran");
                Utils.sendMessage(sender, "&ePlugin Version: &61.0");
                Utils.sendMessage(sender, "&6/vscoreboard help");
                Utils.liner(sender);
            }
        } else {
            Utils.liner(sender);
            Utils.sendMessage(sender, "&7[&6&lvScoreboard&7]:");
            Utils.spacer(sender);
            Utils.sendMessage(sender, "&ePlugin By: &6&oYochran");
            Utils.sendMessage(sender, "&ePlugin Version: &61.0");
            Utils.sendMessage(sender, "&6/vscoreboard help");
            Utils.liner(sender);
        }
        return true;
    }
}
