package net.vectromc.vstaffutils.commands;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.utils.XMaterial;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class OnlineStaffCommand implements CommandExecutor {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;

    public OnlineStaffCommand() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("OnlineStaffPermission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                Inventory onlinePlayers = Bukkit.createInventory(player, 54, ChatColor.YELLOW + "Online Staff");
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (target.hasPermission(plugin.getConfig().getString("OnlineStaffPermission"))) {
                        nitrogen.setPlayerColor(target);
                        ItemStack skull = XMaterial.PLAYER_HEAD.parseItem();
                        ItemMeta skullMeta = skull.getItemMeta();
                        List<String> staff_lore = new ArrayList<>();
                        skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', target.getDisplayName()));
                        staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------------"));
                        if (plugin.vanished.contains(target.getUniqueId())) {
                            staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eVanished: &aYes"));
                        } else {
                            staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eVanished: &cNo"));
                        }
                        if (plugin.modmode.contains(target.getUniqueId())) {
                            staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eModmoded: &aYes"));
                        } else {
                            staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eModmoded: &cNo"));
                        }
                        staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&7 "));
                        nitrogen.setPlayerPrefix(target);
                        staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eDisplay: " + target.getDisplayName()));
                        staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eServer: &6" + target.getWorld().getName()));
                        staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------------"));
                        nitrogen.setPlayerColor(target);
                        skullMeta.setLore(staff_lore);
                        skull.setItemMeta(skullMeta);
                        onlinePlayers.addItem(skull);
                    }
                }
                player.openInventory(onlinePlayers);
            }
        }
        return true;
    }
}
