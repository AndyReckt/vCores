package net.vectromc.vnitrogen.commands;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRankCommand implements CommandExecutor {

    private vNitrogen plugin;

    public SetRankCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("vnitrogen.setrank")) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
        } else {
            if (args.length != 2) {
                Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.IncorrectUsage").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("CantFindPlayer").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                } else {
                    if (args[1].equalsIgnoreCase("Owner")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target.getName() + " group set Owner");
                        plugin.setTargetColor(target);
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.SenderSetRank").replaceAll("%target%", target.getDisplayName()).replaceAll("%rank%", plugin.getConfig().getString("Owner.color") + "Owner"));
                        Utils.sendMessage(target, plugin.getConfig().getString("Setrank.TargetSetRank").replaceAll("%rank%", plugin.getConfig().getString("Owner.color") + "Owner"));
                        plugin.data.config.set(target.getUniqueId() + ".Rank", "Owner");
                    } else if (args[1].equalsIgnoreCase("Developer")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target.getName() + " group set Developer");
                        plugin.setTargetColor(target);
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.SenderSetRank").replaceAll("%target%", target.getDisplayName()).replaceAll("%rank%", plugin.getConfig().getString("Developer.color") + "Developer"));
                        Utils.sendMessage(target, plugin.getConfig().getString("Setrank.TargetSetRank").replaceAll("%rank%", plugin.getConfig().getString("Developer.color") + "Developer"));
                        plugin.data.config.set(target.getUniqueId() + ".Rank", "Developer");
                    } else if (args[1].equalsIgnoreCase("Manager")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target.getName() + " group set Manager");
                        plugin.setTargetColor(target);
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.SenderSetRank").replaceAll("%target%", target.getDisplayName()).replaceAll("%rank%", plugin.getConfig().getString("Manager.color") + "Manager"));
                        Utils.sendMessage(target, plugin.getConfig().getString("Setrank.TargetSetRank").replaceAll("%rank%", plugin.getConfig().getString("Manager.color") + "Manager"));
                        plugin.data.config.set(target.getUniqueId() + ".Rank", "Manager");
                    } else if (args[1].equalsIgnoreCase("Admin")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target.getName() + " group set Admin");
                        plugin.setTargetColor(target);
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.SenderSetRank").replaceAll("%target%", target.getDisplayName()).replaceAll("%rank%", plugin.getConfig().getString("Admin.color") + "Admin"));
                        Utils.sendMessage(target, plugin.getConfig().getString("Setrank.TargetSetRank").replaceAll("%rank%", plugin.getConfig().getString("Admin.color") + "Admin"));
                        plugin.data.config.set(target.getUniqueId() + ".Rank", "Admin");
                    } else if (args[1].equalsIgnoreCase("Senior-Mod")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target.getName() + " group set Senior-Mod");
                        plugin.setTargetColor(target);
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.SenderSetRank").replaceAll("%target%", target.getDisplayName()).replaceAll("%rank%", plugin.getConfig().getString("Senior-Mod.color") + "Senior-Mod"));
                        Utils.sendMessage(target, plugin.getConfig().getString("Setrank.TargetSetRank").replaceAll("%rank%", plugin.getConfig().getString("Senior-Mod.color") + "Senior-Mod"));
                        plugin.data.config.set(target.getUniqueId() + ".Rank", "Senior-Mod");
                    } else if (args[1].equalsIgnoreCase("Mod")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target.getName() + " group set Mod");
                        plugin.setTargetColor(target);
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.SenderSetRank").replaceAll("%target%", target.getDisplayName()).replaceAll("%rank%", plugin.getConfig().getString("Mod.color") + "Mod"));
                        Utils.sendMessage(target, plugin.getConfig().getString("Setrank.TargetSetRank").replaceAll("%rank%", plugin.getConfig().getString("Mod.color") + "Mod"));
                        plugin.data.config.set(target.getUniqueId() + ".Rank", "Mod");
                    } else if (args[1].equalsIgnoreCase("Trial-Mod")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target.getName() + " group set Trial-Mod");
                        plugin.setTargetColor(target);
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.SenderSetRank").replaceAll("%target%", target.getDisplayName()).replaceAll("%rank%", plugin.getConfig().getString("Trial-Mod.color") + "Trial-Mod"));
                        Utils.sendMessage(target, plugin.getConfig().getString("Setrank.TargetSetRank").replaceAll("%rank%", plugin.getConfig().getString("Trial-Mod.color") + "Trial-Mod"));
                        plugin.data.config.set(target.getUniqueId() + ".Rank", "Trial-Mod");
                    } else if (args[1].equalsIgnoreCase("Builder")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target.getName() + " group set Builder");
                        plugin.setTargetColor(target);
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.SenderSetRank").replaceAll("%target%", target.getDisplayName()).replaceAll("%rank%", plugin.getConfig().getString("Builder.color") + "Builder"));
                        Utils.sendMessage(target, plugin.getConfig().getString("Setrank.TargetSetRank").replaceAll("%rank%", plugin.getConfig().getString("Builder.color") + "Builder"));
                        plugin.data.config.set(target.getUniqueId() + ".Rank", "Builder");
                    } else if (args[1].equalsIgnoreCase("Default")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target.getName() + " group set Default");
                        plugin.setTargetColor(target);
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.SenderSetRank").replaceAll("%target%", target.getDisplayName()).replaceAll("%rank%", plugin.getConfig().getString("Default.color") + "Default"));
                        Utils.sendMessage(target, plugin.getConfig().getString("Setrank.TargetSetRank").replaceAll("%rank%", plugin.getConfig().getString("Default.color") + "Default"));
                        plugin.data.config.set(target.getUniqueId() + ".Rank", "Default");
                    } else {
                        Utils.sendMessage(sender, plugin.getConfig().getString("Setrank.InvalidRank").replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")).replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")));
                    }
                }
            }
        }
        return true;
    }
}
