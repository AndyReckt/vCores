package net.vectromc.vnitrogen.commands;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UngrantCommand implements CommandExecutor {

    private vNitrogen plugin;

    public UngrantCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("YouMustBePlayer")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("Ungrant.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length != 2) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Ungrant.IncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        plugin.setPlayerColor(player);
                        plugin.setTargetColor(target);
                        String id = args[1];
                        if (plugin.gData.config.contains(target.getUniqueId().toString() + ".Grants." + "'" + args[1] + "'")) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Ungrant.InvalidGrant")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            String prevRank = plugin.gData.config.getString(target.getUniqueId().toString() + ".Grants." + id + ".PrevRank");
                            plugin.gData.config.set(target.getUniqueId().toString() + ".Grants." + id + ".Status", "Revoked");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setrank " + target.getName() + " " + prevRank);
                            Utils.sendMessage(player, plugin.getConfig().getString("Ungrant.RevokedMessage")
                                    .replace("%id%", id)
                                    .replace("%player%", target.getDisplayName()));
                            plugin.gData.saveData();
                            plugin.gData.reloadData();
                        }
                    } else {
                        OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                        if (!plugin.gData.config.contains(target2.getUniqueId().toString())) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Ungrant.InvalidPlayer")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            String target2color = "";
                            String target2name = target2.getName();
                            String dfColor = "";
                            for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                                if (plugin.getConfig().getBoolean("Ranks." + rank.toUpperCase() + ".default")) {
                                    dfColor = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                }
                            }
                            if (!plugin.pData.config.contains(target2.getUniqueId().toString()) || !plugin.pData.config.contains(target2.getUniqueId().toString() + ".Rank")) {
                                target2color = dfColor;
                            } else {
                                for (String rank : plugin.ranks) {
                                    if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                                        target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                    } else {
                                        target2color = dfColor;
                                    }
                                }
                            }
                            String target2display = target2color + target2name;
                            plugin.setPlayerColor(player);
                            String id = args[1];
                            if (plugin.gData.config.contains(target2.getUniqueId().toString() + ".Grants." + args[1])) {
                                Utils.sendMessage(player, plugin.getConfig().getString("Ungrant.InvalidGrant")
                                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            } else {
                                String prevRank = plugin.gData.config.getString(target2.getUniqueId().toString() + ".Grants." + id + ".PrevRank");
                                plugin.gData.config.set(target2.getUniqueId().toString() + ".Grants." + id + ".Status", "Revoked");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setrank " + target2name + " " + prevRank);
                                Utils.sendMessage(player, plugin.getConfig().getString("Ungrant.RevokedMessage")
                                        .replace("%id%", id)
                                        .replace("%player%", target2display));
                                plugin.gData.saveData();
                                plugin.gData.reloadData();
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
