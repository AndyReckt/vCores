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

public class BountyCommand implements CommandExecutor {

    private EconomyManagement economy = new EconomyManagement();

    private vBasic plugin;
    private vNitrogen nitrogen;

    public BountyCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer")
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
        } else {
            Player player = (Player) sender;
            String server = player.getWorld().getName();
            if (!economy.economyIsEnabled(server)) {
                Utils.sendMessage(player, plugin.getConfig().getString("Economy.NotEnabledMessage")
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
            } else {
                if (!economy.bountyIsEnabled(server)) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Bounty.NotEnabledMessage")
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                } else {
                    if (args.length != 2) {
                        Utils.sendMessage(player, plugin.getConfig().getString("Bounty.IncorrectUsage")
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                    } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        double amount = Double.parseDouble(args[1]);
                        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
                        String str = df.format(amount);
                        if (!economy.isInitialized(target)) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Bounty.InvalidPlayer")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            if (!economy.hasEnoughMoney(server, player, amount)) {
                                Utils.sendMessage(player, plugin.getConfig().getString("Bounty.NotEnoughMoney")
                                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            } else {
                                if (economy.isUnderBountyMinimum(amount) || economy.isOverMaximum(amount)) {
                                    Utils.sendMessage(player, plugin.getConfig().getString("Bounty.InvalidAmount")
                                            .replace("%minimum%", df.format(plugin.getConfig().getDouble("Bounty.MinimumAmount")))
                                            .replace("%maximum%", df.format(plugin.getConfig().getDouble("Economy.MaximumAmount")))
                                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                                } else {
                                    nitrogen.setPlayerColor(player);
                                    String targetName = target.getName();
                                    String targetColor = "";
                                    for (String rank : nitrogen.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                                        if (nitrogen.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                            targetColor = nitrogen.getConfig().getString("Ranks." + rank + ".color");
                                        }
                                    }
                                    String targetDisplay = targetColor + targetName;
                                    if (!economy.isBountied(server, target)) {
                                        economy.setBounty(server, player, target, amount);
                                        Utils.sendMessage(player, plugin.getConfig().getString("Bounty.Format")
                                                .replace("%player%", targetDisplay)
                                                .replace("%amount%", str));
                                        for (Player onlinePlayers : Bukkit.getWorld(server).getPlayers()) {
                                            Utils.sendMessage(onlinePlayers, plugin.getConfig().getString("Bounty.Broadcast")
                                                    .replace("%player%", player.getDisplayName())
                                                    .replace("%target%", targetDisplay)
                                                    .replace("%amount%", str));
                                        }
                                    } else {
                                        double currentAmount = economy.getBountyAmount(server, target);
                                        double newAmount = currentAmount + amount;
                                        String newStr = df.format(newAmount);
                                        economy.increaseBounty(server, player, target, newAmount);
                                        Utils.sendMessage(player, plugin.getConfig().getString("Bounty.FormatIncreased")
                                                .replace("%player%", targetDisplay)
                                                .replace("%amount%", newStr));
                                        for (Player onlinePlayers : Bukkit.getWorld(server).getPlayers()) {
                                            Utils.sendMessage(onlinePlayers, plugin.getConfig().getString("Bounty.BroadcastIncreased")
                                                    .replace("%player%", player.getDisplayName())
                                                    .replace("%target%", targetDisplay)
                                                    .replace("%amount%", newStr));
                                        }
                                    }
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
