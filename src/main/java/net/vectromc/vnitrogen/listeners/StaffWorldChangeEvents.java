package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class StaffWorldChangeEvents implements Listener {

    private vNitrogen plugin;

    public StaffWorldChangeEvents() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String oldWorld = event.getFrom().getName();
        String newWorld = player.getWorld().getName();
        if (player.hasPermission(plugin.getConfig().getString("StaffWorldChange.permission"))) {
            plugin.setPlayerColor(player);
            for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                if (onlineStaff.hasPermission(plugin.getConfig().getString("StaffWorldChange.notifypermission"))) {
                    onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffWorldChange.format").replaceAll("%player%", player.getName()).replaceAll("%oldWorld%", oldWorld)).replaceAll("%newWorld%", newWorld));
                }
            }
        }
    }
}
