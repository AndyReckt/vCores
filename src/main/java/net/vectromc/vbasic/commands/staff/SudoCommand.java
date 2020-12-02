package net.vectromc.vbasic.commands.staff;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoCommand implements CommandExecutor {

    private vBasic plugin;
    private vNitrogen nitrogen;

    public SudoCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("Sudo.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (args.length < 2) {
                Utils.sendMessage(sender, plugin.getConfig().getString("Sudo.IncorrectUsage")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                String sudo = args[1];
                if (target != null) {
                    if (sudo.startsWith("/")) {
                        target.performCommand(sudo.replaceFirst("/", ""));
                    } else {
                        target.chat(sudo);
                    }
                    nitrogen.setPlayerColor(target);
                    Utils.sendMessage(sender, plugin.getConfig().getString("Sudo.Format")
                            .replace("%player%", target.getDisplayName())
                            .replace("%sudo%", sudo));
                    String player;
                    if (sender instanceof Player) {
                        nitrogen.setPlayerColor((Player) sender);
                        player = ((Player) sender).getDisplayName();
                    } else {
                        player = nitrogen.getConfig().getString("Console.name");
                    }
                    for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                        if (plugin.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                            Utils.sendMessage(onlineStaff, plugin.getConfig().getString("StaffAlerts.Sudo")
                                    .replace("%player%", player)
                                    .replace("%target%", target.getDisplayName())
                                    .replace("%sudo%", sudo));
                        }
                    }
                } else {
                    Utils.sendMessage(sender, plugin.getConfig().getString("Sudo.InvalidPlayer")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            }
        }
        return true;
    }
}
