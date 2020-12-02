package me.yochran.vbungee.commands;

import me.yochran.vbungee.utils.Utils;
import me.yochran.vbungee.vbungee;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private vbungee plugin;
    private vNitrogen nitrogen;

    public SpawnCommand() {
        plugin = vbungee.getPlugin(vbungee.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("Spawn.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (args.length == 0) {
                    Player player = (Player) sender;
                    World world = player.getWorld();
                    Utils utils = new Utils();
                    utils.spawn(player, world);
                    Utils.sendMessage(player, plugin.getConfig().getString("Spawn.FormatTarget"));
                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        nitrogen.setPlayerColor(target);
                        World world = target.getWorld();
                        Utils utils = new Utils();
                        utils.spawn(target, world);
                        Utils.sendMessage(target, plugin.getConfig().getString("Spawn.FormatTarget"));
                        Utils.sendMessage(sender, plugin.getConfig().getString("Spawn.FormatSender")
                                .replace("%player%", target.getDisplayName()));
                    } else {
                        Utils.sendMessage(sender, plugin.getConfig().getString("Spawn.InvalidPlayer")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                } else {
                    Utils.sendMessage(sender, plugin.getConfig().getString("Spawn.IncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            }
        }
        return true;
    }
}
