package net.vectromc.vnitrogen.commands.punishments;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearHistoryCommand implements CommandExecutor {

    private vNitrogen plugin;

    public ClearHistoryCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("History.Clear.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (args.length != 1) {
                Utils.sendMessage(sender, plugin.getConfig().getString("History.Clear.IncorrectUsage")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (!plugin.data.config.contains(target2.getUniqueId().toString())) {
                        Utils.sendMessage(player, plugin.getConfig().getString("History.Clear.InvalidPlayer")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        String target2name = target2.getName();
                        String target2color = "";
                        for (String rank : plugin.ranks) {
                            if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                            }
                        }
                        String target2display = target2color + target2name;
                        Utils.sendMessage(player, plugin.getConfig().getString("History.Clear.HistoryCleared")
                                .replace("%player%", target2display));
                        plugin.data.config.set(target2.getUniqueId().toString(), null);
                        if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(target2.getUniqueId().toString())) {
                            plugin.data.config.set("MutedPlayers." + target2.getUniqueId().toString(), null);
                        }
                        if (plugin.data.config.getConfigurationSection("BannedPlayers").getKeys(false).contains(target2.getUniqueId().toString())) {
                            plugin.data.config.set("BannedPlayers." + target2.getUniqueId().toString(), null);
                        }
                        String ip = plugin.data.config.getString("IPs." + target2.getUniqueId().toString() + ".IP");
                        if (plugin.data.config.getConfigurationSection("BlacklistedIPs").getKeys(false).contains(ip)) {
                            plugin.data.config.set("BlacklistedIPs." + ip, null);
                        }
                        plugin.data.saveData();
                        plugin.banned.remove(target2.getUniqueId().toString());
                        plugin.muted.remove(target2.getUniqueId().toString());
                        plugin.blacklisted.remove(ip);
                    }
                } else {
                    if (!plugin.data.config.contains(target2.getUniqueId().toString())) {
                        Utils.sendMessage(sender, plugin.getConfig().getString("History.Clear.InvalidPlayer")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        String target2name = target2.getName();
                        String target2color = "";
                        for (String rank : plugin.ranks) {
                            if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                            }
                        }
                        String target2display = target2color + target2name;
                        Utils.sendMessage(sender, plugin.getConfig().getString("History.Clear.HistoryCleared")
                                .replace("%player%", target2display));
                        plugin.data.config.set(target2.getUniqueId().toString(), null);
                        if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(target2.getUniqueId().toString())) {
                            plugin.data.config.set("MutedPlayers." + target2.getUniqueId().toString(), null);
                        }
                        if (plugin.data.config.getConfigurationSection("BannedPlayers").getKeys(false).contains(target2.getUniqueId().toString())) {
                            plugin.data.config.set("BannedPlayers." + target2.getUniqueId().toString(), null);
                        }
                        String ip = plugin.data.config.getString("IPs." + target2.getUniqueId().toString() + ".IP");
                        if (plugin.data.config.getConfigurationSection("BlacklistedIPs").getKeys(false).contains(ip)) {
                            plugin.data.config.set("BlacklistedIPs." + ip, null);
                        }
                        plugin.data.saveData();
                        plugin.banned.remove(target2.getUniqueId().toString());
                        plugin.muted.remove(target2.getUniqueId().toString());
                        plugin.blacklisted.remove(ip);
                    }
                }
            }
        }
        return true;
    }
}
