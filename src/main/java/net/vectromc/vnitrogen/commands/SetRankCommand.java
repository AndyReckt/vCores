package net.vectromc.vnitrogen.commands;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRankCommand implements CommandExecutor {

    private vNitrogen plugin;

    public SetRankCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBeConsole").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
        } else {
            if (args.length != 2) {
                Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.IncorrectUsage").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                    if (plugin.ranks.contains(args[1].toUpperCase())) {
                        String rankName = args[1];
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfig().getString("Setrank.CommandToRun")
                                .replace("%player%", target2.getName())
                                .replace("%rank%", rankName));
                        String target2display = plugin.getConfig().getString("Ranks." + rankName.toUpperCase() + ".color") + target2.getName();
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.SenderSetRank").replaceAll("%target%", target2display).replaceAll("%rank%", plugin.getConfig().getString("Ranks." + rankName.toUpperCase() + ".display")));
                        plugin.pData.config.set(target2.getUniqueId().toString() + ".Rank", rankName.toUpperCase());
                        plugin.pData.config.set(target2.getUniqueId().toString() + ".Name", target2.getName());
                    } else {
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.InvalidRank").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                    }
                    plugin.pData.saveData();
                } else {
                    if (plugin.ranks.contains(args[1].toUpperCase())) {
                        plugin.setPlayerColor(target);
                        String rankName = args[1];
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfig().getString("Setrank.CommandToRun")
                                .replace("%player%", target.getName())
                                .replace("%rank%", rankName));
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.SenderSetRank").replaceAll("%target%", target.getDisplayName()).replaceAll("%rank%", plugin.getConfig().getString("Ranks." + rankName.toUpperCase() + ".display")));
                        Utils.sendMessage(target, plugin.getConfig().getString("Setrank.TargetSetRank").replaceAll("%rank%", plugin.getConfig().getString("Ranks." + rankName.toUpperCase() + ".display")));
                        plugin.pData.config.set(target.getUniqueId().toString() + ".Rank", rankName.toUpperCase());
                        plugin.pData.config.set(target.getUniqueId().toString() + ".Name", target.getName());

                    } else {
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.InvalidRank").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                    }
                    plugin.pData.saveData();
                }
            }
        }
        return true;
    }
}
