package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StaffLogEvents implements Listener {

    private vNitrogen plugin;

    public StaffLogEvents() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String world = player.getWorld().getName();
        if (player.hasPermission(plugin.getConfig().getString("StaffLogin.permission"))) {
            if (!plugin.banned.contains(player.getUniqueId().toString()) && !plugin.blacklisted.contains(player.getAddress().getAddress().getHostAddress())) {
                plugin.setPlayerColor(player);
                for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                    if (onlineStaff.hasPermission(plugin.getConfig().getString("StaffLogin.notifypermission"))) {
                        onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffLogin.format").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName())));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuot(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String world = player.getWorld().getName();
        if (player.hasPermission(plugin.getConfig().getString("StaffLogout.permission"))) {
            if (!plugin.banned.contains(player.getUniqueId().toString()) && !plugin.blacklisted.contains(player.getAddress().getAddress().getHostAddress())) {
                plugin.setPlayerColor(player);
                for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                    if (onlineStaff.hasPermission(plugin.getConfig().getString("StaffLogout.notifypermission"))) {
                        onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffLogout.format").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName())));
                    }
                }
            }
        }
    }
}
