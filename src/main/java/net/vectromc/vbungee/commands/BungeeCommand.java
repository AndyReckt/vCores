package me.yochran.vbungee.commands;

import me.yochran.vbungee.vbungee;
import net.vectromc.vnitrogen.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BungeeCommand implements CommandExecutor {

    private vbungee plugin;

    public BungeeCommand() {
        plugin = vbungee.getPlugin(vbungee.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Utils.liner(sender);
            Utils.sendMessage(sender, "&7[&6&lvBungee&7]:");
            Utils.spacer(sender);
            Utils.sendMessage(sender, "&ePlugin By&7: &6&oYochran");
            Utils.sendMessage(sender, "&ePlugin Version&7: &61.0");
            Utils.sendMessage(sender, "&7[&6/vbungee help&7]");
            Utils.liner(sender);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission(plugin.getConfig().getString("ReloadPermission"))) {
                    plugin.reloadConfig();
                    Utils.sendMessage(sender, plugin.getConfig().getString("ReloadConfig")
                            .replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                            .replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                } else {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                            .replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                            .replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                Utils.liner(sender);
                Utils.sendMessage(sender, "&7[&6&lvBungee&7]: &eHelp:");
                Utils.spacer(sender);
                Utils.sendMessage(sender, "&6&lCommands:");
                Utils.sendMessage(sender, "&e/send, /server, /hub, /find, /glist, /vbungee.");
                Utils.liner(sender);
            } else {
                Utils.liner(sender);
                Utils.sendMessage(sender, "&7[&6&lvBungee&7]:");
                Utils.spacer(sender);
                Utils.sendMessage(sender, "&ePlugin By&7: &6&oYochran");
                Utils.sendMessage(sender, "&ePlugin Version&7: &61.0");
                Utils.sendMessage(sender, "&7[&6/vbungee help&7]");
                Utils.liner(sender);
            }
        } else {
            Utils.liner(sender);
            Utils.sendMessage(sender, "&7[&6&lvBungee&7]:");
            Utils.spacer(sender);
            Utils.sendMessage(sender, "&ePlugin By&7: &6&oYochran");
            Utils.sendMessage(sender, "&ePlugin Version&7: &61.0");
            Utils.sendMessage(sender, "&7[&6/vbungee help&7]");
            Utils.liner(sender);
        }
        return true;
    }
}
