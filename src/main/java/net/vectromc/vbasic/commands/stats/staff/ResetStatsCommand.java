package net.vectromc.vbasic.commands.stats.staff;

import net.vectromc.vbasic.management.StatManagement;
import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ResetStatsCommand implements CommandExecutor {

    private StatManagement stats = new StatManagement();

    private vBasic plugin;
    private vNitrogen nitrogen;

    public ResetStatsCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("ResetStats.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                String server = player.getWorld().getName();
                if (!stats.statsAreEnabled(server)) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Stats.NotEnabledMessage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    if (args.length != 1) {
                        Utils.sendMessage(player, plugin.getConfig().getString("ResetStats.IncorrectUsage")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        if (!stats.isInitialized(target)) {
                            Utils.sendMessage(player, plugin.getConfig().getString("ResetStats.InvalidPlayer")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            String targetName = target.getName();
                            String targetColor = "";
                            for (String rank : nitrogen.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                                if (nitrogen.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                    targetColor = nitrogen.getConfig().getString("Ranks." + rank + ".color");
                                }
                            }
                            String targetDisplay = targetColor + targetName;
                            stats.resetStats(server, target);
                            Utils.sendMessage(player, plugin.getConfig().getString("ResetStats.Format")
                                    .replace("%player%", targetDisplay));
                        }
                    }
                }
            }
        }
        return true;
    }
}
