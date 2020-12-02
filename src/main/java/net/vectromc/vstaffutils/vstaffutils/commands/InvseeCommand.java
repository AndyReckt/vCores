package net.vectromc.vstaffutils.commands;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvseeCommand implements CommandExecutor {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;

    public  InvseeCommand() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("InvseePermission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length != 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("InvseeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        nitrogen.setTargetColor(target);
                        Utils.sendMessage(player, "&eOpening inventory of " + target.getDisplayName());
                        plugin.invsee_inventory.put(target.getUniqueId(), target.getInventory().getContents());
                        plugin.invsee_armor.put(target.getUniqueId(), target.getInventory().getArmorContents());
                        Inventory targetInv = Bukkit.createInventory(player, 45, ChatColor.GREEN + "Inventory View");
                        targetInv.setContents(target.getInventory().getContents());
                        targetInv.setItem(36, target.getInventory().getHelmet());
                        targetInv.setItem(37, target.getInventory().getChestplate());
                        targetInv.setItem(38, target.getInventory().getLeggings());
                        targetInv.setItem(39, target.getInventory().getBoots());
                        targetInv.setItem(40, target.getInventory().getItemInHand());
                        player.openInventory(targetInv);
                    } else {
                        Utils.sendMessage(player, plugin.getConfig().getString("InvseeInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                }
            }
        }
        return true;
    }
}
