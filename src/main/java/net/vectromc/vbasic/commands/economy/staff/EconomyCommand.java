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

public class EconomyCommand implements CommandExecutor {

    private EconomyManagement economy = new EconomyManagement();

    private vBasic plugin;
    private vNitrogen nitrogen;

    public EconomyCommand() {
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
            if (!sender.hasPermission(plugin.getConfig().getString("Economy.Command.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
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
                    if (args.length < 2) {
                        Utils.sendMessage(player, plugin.getConfig().getString("Economy.Command.IncorrectUsage")
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                    } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
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
                            DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
                            if (args.length == 2) {
                                if (args[0].equalsIgnoreCase("reset")) {
                                    economy.resetPlayer(server, target);
                                    Utils.sendMessage(player, plugin.getConfig().getString("Economy.Command.Format.Reset")
                                            .replace("%player%", targetDisplay)
                                            .replace("%amount%", df.format(economy.getMoney(server, target))));
                                    if (target.isOnline()) {
                                        Player target2 = Bukkit.getPlayer(args[1]);
                                        Utils.sendMessage(target2, plugin.getConfig().getString("Economy.Command.FormatTarget")
                                                .replace("%balance%", df.format(economy.getMoney(server, target))));
                                    }
                                } else {
                                    Utils.sendMessage(player, plugin.getConfig().getString("Economy.Command.IncorrectUsage")
                                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                                }
                            } else if (args.length == 3) {
                                double amount = Double.parseDouble(args[2]);
                                if (economy.isOverMaximum(amount)) {
                                    Utils.sendMessage(player, plugin.getConfig().getString("Economy.Command.InvalidAmount")
                                            .replace("%maximum%", df.format(plugin.getConfig().getDouble("Economy.MaximumAmount")))
                                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                                } else {
                                    if (args[0].equalsIgnoreCase("set")) {
                                        economy.resetPlayer(server, target);
                                        economy.addMoney(server, target, amount);
                                        Utils.sendMessage(player, plugin.getConfig().getString("Economy.Command.Format.Set")
                                                .replace("%player%", targetDisplay)
                                                .replace("%amount%", df.format(economy.getMoney(server, target))));
                                        if (target.isOnline()) {
                                            Player target2 = Bukkit.getPlayer(args[1]);
                                            Utils.sendMessage(target2, plugin.getConfig().getString("Economy.Command.FormatTarget")
                                                    .replace("%balance%", df.format(economy.getMoney(server, target))));
                                        }
                                    } else if (args[0].equalsIgnoreCase("give")) {
                                        double newAmount = amount + economy.getMoney(server, target);
                                        if (economy.isOverMaximum(newAmount)) {
                                            Utils.sendMessage(player, plugin.getConfig().getString("Economy.Command.InvalidAmount")
                                                    .replace("%maximum%", df.format(plugin.getConfig().getDouble("Economy.MaximumAmount")))
                                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                                        } else {
                                            economy.addMoney(server, target, amount);
                                            Utils.sendMessage(player, plugin.getConfig().getString("Economy.Command.Format.Give")
                                                    .replace("%player%", targetDisplay)
                                                    .replace("%amount%", df.format(economy.getMoney(server, target))));
                                            if (target.isOnline()) {
                                                Player target2 = Bukkit.getPlayer(args[1]);
                                                Utils.sendMessage(target2, plugin.getConfig().getString("Economy.Command.FormatTarget")
                                                        .replace("%balance%", df.format(economy.getMoney(server, target))));
                                            }
                                        }
                                    } else if (args[0].equalsIgnoreCase("take")) {
                                        economy.removeMoney(server, player, amount);
                                        Utils.sendMessage(player, plugin.getConfig().getString("Economy.Command.Format.Set")
                                                .replace("%player%", targetDisplay)
                                                .replace("%amount%", df.format(economy.getMoney(server, target))));
                                        if (target.isOnline()) {
                                            Player target2 = Bukkit.getPlayer(args[1]);
                                            Utils.sendMessage(target2, plugin.getConfig().getString("Economy.Command.FormatTarget")
                                                    .replace("%balance%", df.format(economy.getMoney(server, target))));
                                        }
                                    } else {
                                        Utils.sendMessage(player, plugin.getConfig().getString("Economy.Command.IncorrectUsage")
                                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                                    }
                                }
                            } else {
                                Utils.sendMessage(player, plugin.getConfig().getString("Economy.Command.IncorrectUsage")
                                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
