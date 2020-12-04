package net.vectromc.vbasic.commands.economy;

import net.vectromc.vbasic.management.EconomyManagement;
import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class BalanceCommand implements CommandExecutor {

    private EconomyManagement economy = new EconomyManagement();

    private vBasic plugin;
    private vNitrogen nitrogen;

    public BalanceCommand() {
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
            Player player = (Player) sender;
            String server = player.getWorld().getName();
            if (!economy.economyIsEnabled(server)) {
                Utils.sendMessage(player, plugin.getConfig().getString("Economy.NotEnabledMessage")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (args.length == 0) {
                    nitrogen.setPlayerColor(player);
                    double balance = economy.getMoney(server, player);
                    DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
                    String str = df.format(balance);
                    Utils.sendMessage(player, plugin.getConfig().getString("Balance.Format")
                            .replace("%player%", player.getDisplayName())
                            .replace("%balance%", str));
                } else if (args.length == 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (!economy.isInitialized(target)) {
                        Utils.sendMessage(player, plugin.getConfig().getString("Balance.InvalidPlayer")
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
                        double balance = economy.getMoney(server, target);
                        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
                        String str = df.format(balance);
                        Utils.sendMessage(player, plugin.getConfig().getString("Balance.Format")
                                .replace("%player%", targetDisplay)
                                .replace("%balance%", str));
                    }
                } else {
                    Utils.sendMessage(player, plugin.getConfig().getString("Balance.IncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            }
        }
        return true;
    }
}
