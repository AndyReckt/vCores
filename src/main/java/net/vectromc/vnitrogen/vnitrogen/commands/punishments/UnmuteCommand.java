package net.vectromc.vnitrogen.commands.punishments;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnmuteCommand implements CommandExecutor {

    private vNitrogen plugin;
    private Boolean silent;

    public UnmuteCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("Unmute.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            String consoleName = plugin.getConfig().getString("Console.name");
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Unmute.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                    if (!plugin.data.config.contains("MutedPlayers." + target2.getUniqueId().toString())) {
                        Utils.sendMessage(player, plugin.getConfig().getString("Unmute.PlayerIsNotMuted").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        String target2name = target2.getName();
                        String target2color = "";
                        if (args.length == 1) {
                            this.silent = false;
                            for (String rank : plugin.ranks) {
                                if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                    target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                }
                            }
                            String target2display = target2color + target2name;
                            plugin.setPlayerColor(player);
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unmute.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display)));
                            }
                            Utils.sendMessage(player, plugin.getConfig().getString("Unmute.ExecutorResponse").replaceAll("%player%", target2display));
                            plugin.muted.remove(target2.getUniqueId().toString());
                            plugin.data.config.set("MutedPlayers." + target2.getUniqueId(), null);
                            int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".MutesAmount");
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Status", "Revoked");
                            plugin.data.saveData();
                        } else {
                            if (!args[1].equalsIgnoreCase("-s")) {
                                Utils.sendMessage(player, plugin.getConfig().getString("Unmute.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            } else {
                                this.silent = true;
                                for (String rank : plugin.ranks) {
                                    if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                        target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                    }
                                }
                                String target2display = target2color + target2name;
                                plugin.setPlayerColor(player);
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unmute.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display)));
                                    }
                                }
                                Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unmute.ExecutorResponse").replaceAll("%player%", target2display));
                                plugin.muted.remove(target2.getUniqueId().toString());
                                plugin.data.config.set("MutedPlayers." + target2.getUniqueId(), null);
                                int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".MutesAmount");
                                plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Status", "Revoked");
                                plugin.data.saveData();
                            }
                        }
                    }
                }
            } else {
                if (args.length == 0) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("Unmute.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                    if (!plugin.data.config.contains("MutedPlayers." + target2.getUniqueId().toString())) {
                        Utils.sendMessage(sender, plugin.getConfig().getString("Unmute.PlayerIsNotMuted").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        String target2name = target2.getName();
                        String target2color = "";
                        if (args.length == 1) {
                            this.silent = false;
                            for (String rank : plugin.ranks) {
                                if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                    target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                }
                            }
                            String target2display = target2color + target2name;
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unmute.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target2display)));
                            }
                            Utils.sendMessage(sender, plugin.getConfig().getString("Unmute.ExecutorResponse").replaceAll("%player%", target2display));
                            plugin.muted.remove(target2.getUniqueId().toString());
                            plugin.data.config.set("MutedPlayers." + target2.getUniqueId(), null);
                            int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".MutesAmount");
                            plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Status", "Revoked");
                            plugin.data.saveData();
                        } else {
                            if (!args[1].equalsIgnoreCase("-s")) {
                                Utils.sendMessage(sender, plugin.getConfig().getString("Unmute.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            } else {
                                this.silent = true;
                                for (String rank : plugin.ranks) {
                                    if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                        target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                                    }
                                }
                                String target2display = target2color + target2name;
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unmute.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target2display)));
                                    }
                                }
                                Utils.sendMessage(sender, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unmute.ExecutorResponse").replaceAll("%player%", target2display));
                                plugin.muted.remove(target2.getUniqueId().toString());
                                plugin.data.config.set("MutedPlayers." + target2.getUniqueId(), null);
                                int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".MutesAmount");
                                plugin.data.config.set(target2.getUniqueId() + ".Mutes." + id + ".Status", "Revoked");
                                plugin.data.saveData();
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
