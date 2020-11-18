package net.vectromc.vnitrogen.commands;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NitrogenCommand implements CommandExecutor {

    private vNitrogen plugin;

    public NitrogenCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            Utils.liner(sender);
            Utils.sendMessage(sender, "&7[&6&lvNitrogen&7]:");
            Utils.spacer(sender);
            Utils.sendMessage(sender, "&ePlugin By&7: &6&oYochran");
            Utils.sendMessage(sender, "&ePlugin Version&7: &61.0");
            Utils.sendMessage(sender, "&7[&6/vnitrogen help&7]");
            Utils.liner(sender);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission(plugin.getConfig().getString("ReloadPermission"))) {
                    plugin.reloadConfig();
                    plugin.data.saveData();
                    plugin.data.reloadData();
                    plugin.registerRanks();
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
                Utils.sendMessage(sender, "&7[&6&lvNitrogen&7]: &eHelp:");
                Utils.sendMessage(sender, "&e ");
                Utils.sendMessage(sender, "&6&lPunishments:");
                Utils.sendMessage(sender, "&e/warn, /kick, /tempmute, /mute, /tempban, /ban, /blacklist");
                Utils.sendMessage(sender, "&e/unmute, /unban, /unblacklist, /history, /alts, /mutechat, /clearchat.");
                Utils.sendMessage(sender, "&e ");
                Utils.sendMessage(sender, "&6&lRanks:");
                Utils.sendMessage(sender, "&e/grant, /setrank &7(console only)&e, /grants, /ungrant.");
                Utils.sendMessage(sender, "&e ");
                Utils.sendMessage(sender, "&6&lChats:");
                Utils.sendMessage(sender, "&e/sc, /ac, /mc, /bldc, /sct, /act, /mct, /bct.");
                Utils.sendMessage(sender, "&e&lChat Prefixes&7: &e~, #, @, !. &7(put a space&7)");
                Utils.liner(sender);
            } else {
                Utils.liner(sender);
                Utils.sendMessage(sender, "&7[&6&lvNitrogen&7]:");
                Utils.spacer(sender);
                Utils.sendMessage(sender, "&ePlugin By&7: &6&oYochran");
                Utils.sendMessage(sender, "&ePlugin Version&7: &61.0");
                Utils.sendMessage(sender, "&7[&6/vnitrogen help&7]");
                Utils.liner(sender);
            }
        } else {
            Utils.liner(sender);
            Utils.sendMessage(sender, "&7[&6&lvNitrogen&7]:");
            Utils.spacer(sender);
            Utils.sendMessage(sender, "&ePlugin By&7: &6&oYochran");
            Utils.sendMessage(sender, "&ePlugin Version&7: &61.0");
            Utils.sendMessage(sender, "&7[&6/vnitrogen help&7]");
            Utils.liner(sender);
        }
        return true;
    }
}
