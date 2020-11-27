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
        if (args.length == 0) {
            Utils.liner(sender);
            Utils.sendMessage(sender, "&7[&6&lvBasic&7]:");
            Utils.spacer(sender);
            Utils.sendMessage(sender, "&ePlugin By&7: &6&oYochran");
            Utils.sendMessage(sender, "&ePlugin Version&7: &61.0");
            Utils.sendMessage(sender, "&6/vbasic reload");
            Utils.liner(sender);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission(plugin.getConfig().getString("ReloadPermission"))) {
                    plugin.reloadConfig();
                    Utils.sendMessage(sender, plugin.getConfig().getString("ReloadConfig").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                Utils.liner(sender);
                Utils.sendMessage(sender, "&7[&6&lvBasic&7]: &eHelp:");
                Utils.sendMessage(sender, "&e ");
                Utils.sendMessage(sender, "&6&lMisc:");
                Utils.sendMessage(sender, "&e/feed, /heal, /message, /reply, /settings");
                Utils.sendMessage(sender, "&e/tpm, /tms, /tgc, /teleport, /tphere, /back");
                Utils.sendMessage(sender, "&e ");
                Utils.sendMessage(sender, "&6&lStaff:");
                Utils.sendMessage(sender, "&e/tsa, /broadcast, /seen, /fly, /speed, /gamemode");
                Utils.liner(sender);
            } else {
                Utils.liner(sender);
                Utils.sendMessage(sender, "&7[&6&lvBasic&7]:");
                Utils.spacer(sender);
                Utils.sendMessage(sender, "&ePlugin By&7: &6&oYochran");
                Utils.sendMessage(sender, "&ePlugin Version&7: &61.0");
                Utils.sendMessage(sender, "&6/vbasic reload");
                Utils.liner(sender);
            }
        } else {
            Utils.liner(sender);
            Utils.sendMessage(sender, "&7[&6&lvBasic&7]:");
            Utils.spacer(sender);
            Utils.sendMessage(sender, "&ePlugin By&7: &6&oYochran");
            Utils.sendMessage(sender, "&ePlugin Version&7: &61.0");
            Utils.sendMessage(sender, "&6/vbasic reload");
            Utils.liner(sender);
        }
        return true;
    }
}
