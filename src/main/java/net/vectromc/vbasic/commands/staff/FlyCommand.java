package net.vectromc.vbasic.commands.staff;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    private vBasic plugin;
    private vNitrogen nitrogen;

    public FlyCommand() {
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
            if (!sender.hasPermission(plugin.getConfig().getString("FlyPermission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                nitrogen.setPlayerColor(player);
                if (args.length == 0) {
                    if (!plugin.fly.contains(player.getUniqueId())) {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        plugin.fly.add(player.getUniqueId());
                        Utils.sendMessage(player, plugin.getConfig().getString("FlyTargetOn"));
                    } else {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        plugin.fly.remove(player.getUniqueId());
                        Utils.sendMessage(player, plugin.getConfig().getString("FlyTargetOff"));
                    }
                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        nitrogen.setPlayerColor(target);
                        if (!plugin.fly.contains(target.getUniqueId())) {
                            target.setAllowFlight(true);
                            target.setFlying(true);
                            plugin.fly.add(target.getUniqueId());
                            Utils.sendMessage(target, plugin.getConfig().getString("FlyTargetOn"));
                            Utils.sendMessage(player, plugin.getConfig().getString("FlyPlayerOn")
                                    .replace("%player%", target.getDisplayName()));
                        } else {
                            target.setAllowFlight(false);
                            target.setFlying(false);
                            plugin.fly.remove(target.getUniqueId());
                            Utils.sendMessage(target, plugin.getConfig().getString("FlyTargetOff"));
                            Utils.sendMessage(player, plugin.getConfig().getString("FlyPlayerOff")
                                    .replace("%player%", target.getDisplayName()));
                        }
                    } else {
                        Utils.sendMessage(player, plugin.getConfig().getString("FlyInvalidPlayer")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                } else {
                    Utils.sendMessage(sender, plugin.getConfig().getString("FlyIncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            }
        }
        return true;
    }
}
