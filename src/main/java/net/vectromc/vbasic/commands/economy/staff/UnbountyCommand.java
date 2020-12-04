package net.vectromc.vbasic.commands.economy.staff;

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
import java.util.UUID;

public class UnbountyCommand implements CommandExecutor {

    private EconomyManagement economy = new EconomyManagement();

    private vBasic plugin;
    private vNitrogen nitrogen;

    public UnbountyCommand() {
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
            if (!sender.hasPermission(plugin.getConfig().getString("Unbounty.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
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
                    if (!economy.bountyIsEnabled(server)) {
                        Utils.sendMessage(player, plugin.getConfig().getString("Bounty.NotEnabledMessage")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        if (args.length != 1) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Unbounty.IncorrectUsage")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                            if (!economy.isInitialized(target)) {
                                Utils.sendMessage(player, plugin.getConfig().getString("Unbounty.InvalidPlayer")
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
                                if (!economy.isBountied(server, target)) {
                                    Utils.sendMessage(player, plugin.getConfig().getString("Unbounty.PlayerNotBountied")
                                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                                } else {
                                    double amount = economy.getBountyAmount(server, target);
                                    DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
                                    String str = df.format(amount);
                                    String exeUuid = economy.getBountyExecutor(server, target);
                                    System.out.println(exeUuid);
                                    OfflinePlayer executor = Bukkit.getOfflinePlayer(UUID.fromString(exeUuid));
                                    economy.removeBounty(server, target);
                                    economy.addMoney(server, executor, amount);
                                    Utils.sendMessage(player, plugin.getConfig().getString("Unbounty.Format")
                                            .replace("%amount%", str)
                                            .replace("%player%", targetDisplay));
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
