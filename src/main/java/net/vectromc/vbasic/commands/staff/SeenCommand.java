package net.vectromc.vbasic.commands.staff;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

public class SeenCommand implements CommandExecutor {

    private vBasic plugin;
    private vNitrogen nitrogen;

    public SeenCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("SeenPermission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (args.length != 1) {
                Utils.sendMessage(sender, plugin.getConfig().getString("SeenIncorrectUsage")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (!nitrogen.pData.config.contains(target.getUniqueId().toString())) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("SeenInvalidPlayer")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    String targetName = target.getName();
                    String targetColor = "";
                    String targetRank = "";
                    for (String rank : nitrogen.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                        if (nitrogen.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                            targetColor = nitrogen.getConfig().getString("Ranks." + rank + ".color");
                            targetRank = nitrogen.getConfig().getString("Ranks." + rank + ".display");
                        }
                    }
                    String targetDisplay = targetColor + targetName;
                    String firstJoined = Utils.dateFormat.format(new Date(target.getFirstPlayed()));
                    if (!sender.hasPermission(plugin.getConfig().getString("SeenIPViewPermission"))) {
                        Utils.sendMessage(sender, plugin.getConfig().getString("SeenFormat")
                                .replace("%player%", targetDisplay)
                                .replace("%ip%", plugin.getConfig().getString("SeenHiddenIP"))
                                .replace("%firstjoin%", firstJoined)
                                .replace("%rank%", targetRank));
                    } else {
                        Utils.sendMessage(sender, plugin.getConfig().getString("SeenFormat")
                                .replace("%player%", targetDisplay)
                                .replace("%ip%", nitrogen.data.config.getString("IPs." + target.getUniqueId().toString() + ".IP"))
                                .replace("%firstjoin%", firstJoined)
                                .replace("%rank%", targetRank));
                    }
                }
            }
        }
        return true;
    }
}
