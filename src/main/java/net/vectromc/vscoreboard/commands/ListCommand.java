package net.vectromc.vscoreboard.commands;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vscoreboard.VScoreboard;
import net.vectromc.vscoreboard.utils.Utils;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListCommand implements CommandExecutor {

    private VScoreboard plugin;
    private vNitrogen nitrogen;
    private vStaffUtils staffUtils;

    public ListCommand() {
        plugin = VScoreboard.getPlugin(VScoreboard.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
        staffUtils = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("Onlineplayers")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                int vanished = staffUtils.vanished.size();
                int online;
                online = Bukkit.getOnlinePlayers().size() - vanished;
                Utils.liner(player);
                Utils.sendMessage(player, "&eOnline Players&7: &6&l" + online);
                Utils.spacer(player);
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (!staffUtils.vanished.contains(target.getUniqueId())) {
                        if (target.hasPermission("vnitrogen.groups.owner")) {
                            nitrogen.setTargetColor(target);
                            Utils.sendMessage(player, "&7 - " + target.getDisplayName());
                        } else if (target.hasPermission("vnitrogen.groups.developer")) {
                            nitrogen.setTargetColor(target);
                            Utils.sendMessage(player, "&7 - " + target.getDisplayName());
                        } else if (target.hasPermission("vnitrogen.groups.manager")) {
                            nitrogen.setTargetColor(target);
                            Utils.sendMessage(player, "&7 - " + target.getDisplayName());
                        } else if (target.hasPermission("vnitrogen.groups.admin")) {
                            nitrogen.setTargetColor(target);
                            Utils.sendMessage(player, "&7 - " + target.getDisplayName());
                        } else if (target.hasPermission("vnitrogen.groups.seniormod")) {
                            nitrogen.setTargetColor(target);
                            Utils.sendMessage(player, "&7 - " + target.getDisplayName());
                        } else if (target.hasPermission("vnitrogen.groups.mod")) {
                            nitrogen.setTargetColor(target);
                            Utils.sendMessage(player, "&7 - " + target.getDisplayName());
                        } else if (target.hasPermission("vnitrogen.groups.trialmod")) {
                            nitrogen.setTargetColor(target);
                            Utils.sendMessage(player, "&7 - " + target.getDisplayName());
                        } else if (target.hasPermission("vnitrogen.groups.builder")) {
                            nitrogen.setTargetColor(target);
                            Utils.sendMessage(player, "&7 - " + target.getDisplayName());
                        } else {
                            nitrogen.setTargetColor(target);
                            Utils.sendMessage(player, "&7 - " + target.getDisplayName());
                        }
                    }
                }
                Utils.liner(player);
            }
        }
        return true;
    }
}
