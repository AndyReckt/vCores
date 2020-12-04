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

public class SpeedCommand implements CommandExecutor {

    private vBasic plugin;
    private vNitrogen nitrogen;

    public SpeedCommand() {
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
            if (!sender.hasPermission(plugin.getConfig().getString("SpeedPermission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                nitrogen.setPlayerColor(player);
                if (args.length == 0) {
                    Utils.sendMessage(player, plugin.getConfig().getString("SpeedIncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    String speedStr = args[0];
                    if (!(speedStr instanceof String)) {
                        Utils.sendMessage(player, plugin.getConfig().getString("SpeedInvalidSpeed")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        double speed = Double.parseDouble(speedStr);
                        if (args.length == 1) {
                            if (speed > 1 || speed < -1) {
                                Utils.sendMessage(player, plugin.getConfig().getString("SpeedInvalidSpeed")
                                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            } else {
                                if (!player.isFlying()) {
                                    player.setWalkSpeed((float) speed);
                                } else {
                                    player.setFlySpeed((float) speed);
                                }
                                Utils.sendMessage(player, plugin.getConfig().getString("SpeedTargetSet")
                                        .replace("%speed%", speedStr));
                            }
                        } else if (args.length == 2) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target != null) {
                                nitrogen.setPlayerColor(target);
                                if (!target.isFlying()) {
                                    target.setWalkSpeed((float) speed);
                                } else {
                                    target.setFlySpeed((float) speed);
                                }
                                Utils.sendMessage(target, plugin.getConfig().getString("SpeedTargetSet")
                                        .replace("%speed%", speedStr));
                                Utils.sendMessage(player, plugin.getConfig().getString("SpeedPlayerSet")
                                        .replace("%player%", target.getDisplayName())
                                        .replace("%speed%", speedStr));
                            } else {
                                Utils.sendMessage(player, plugin.getConfig().getString("SpeedInvalidPlayer")
                                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            }
                        } else {
                            Utils.sendMessage(sender, plugin.getConfig().getString("SpeedIncorrectUsage")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        }
                    }
                }
            }
        }
        return true;
    }
}
