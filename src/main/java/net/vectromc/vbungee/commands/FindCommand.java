package me.yochran.vbungee.commands;

import me.yochran.vbungee.utils.Utils;
import me.yochran.vbungee.vbungee;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FindCommand implements CommandExecutor {

    private vbungee plugin;
    private vNitrogen nitrogen;

    public FindCommand() {
        plugin = vbungee.getPlugin(vbungee.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("Find.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (args.length != 1) {
                Utils.sendMessage(sender, plugin.getConfig().getString("Find.IncorrectUsage")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    nitrogen.setPlayerColor(target);
                    String world = target.getWorld().getName();
                    Utils.sendMessage(sender, plugin.getConfig().getString("Find.Format")
                            .replace("%player%", target.getDisplayName())
                            .replace("%server%", world));
                } else {
                    Utils.sendMessage(sender, plugin.getConfig().getString("Find.PlayerNotFound")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            }
        }
        return true;
    }
}
