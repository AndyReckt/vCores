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

public class PayCommand implements CommandExecutor {

    private EconomyManagement economy = new EconomyManagement();

    private vBasic plugin;
    private vNitrogen nitrogen;

    public PayCommand() {
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
                if (args.length != 2) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Pay.IncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (!economy.isInitialized(target)) {
                        Utils.sendMessage(sender, plugin.getConfig().getString("Pay.InvalidPlayer")
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
                        double amount = Double.parseDouble(args[1]);
                        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
                        String str = df.format(amount);
                        if (!economy.hasEnoughMoney(server, player, amount)) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Pay.NotEnoughMoney")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            if (economy.isUnderPayMinimum(amount) || economy.isOverMaximum(amount)) {
                                Utils.sendMessage(player, plugin.getConfig().getString("Pay.InvalidAmount")
                                        .replace("%minimum%", "" + plugin.getConfig().getDouble("Pay.MinimumAmount"))
                                        .replace("%maximum%", "" + plugin.getConfig().getDouble("Economy.MaximumAmount"))
                                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            } else {
                                double newAmount = amount + economy.getMoney(server, target);
                                if (economy.isOverMaximum(newAmount)) {
                                    Utils.sendMessage(player, plugin.getConfig().getString("Pay.InvalidAmount")
                                            .replace("%minimum%", df.format(plugin.getConfig().getDouble("Pay.MinimumAmount")))
                                            .replace("%maximum%", df.format(plugin.getConfig().getDouble("Economy.MaximumAmount")))
                                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                                } else {
                                    economy.removeMoney(server, player, amount);
                                    economy.addMoney(server, target, amount);
                                    Utils.sendMessage(player, plugin.getConfig().getString("Pay.FormatSender")
                                            .replace("%player%", targetDisplay)
                                            .replace("%amount%", str));
                                    if (target.isOnline()) {
                                        nitrogen.setPlayerColor(player);
                                        Player target2 = Bukkit.getPlayer(args[0]);
                                        Utils.sendMessage(target2, plugin.getConfig().getString("Pay.FormatTarget")
                                                .replace("%player%", player.getDisplayName())
                                                .replace("%amount%", str));
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
