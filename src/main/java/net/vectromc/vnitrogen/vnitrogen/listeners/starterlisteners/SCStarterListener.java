package net.vectromc.vnitrogen.listeners.starterlisteners;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SCStarterListener implements Listener {

    private vNitrogen plugin;

    public SCStarterListener() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (message.startsWith("# ")) {
            if (player.hasPermission(plugin.getConfig().getString("StaffChat.permission"))) {
                event.setCancelled(true);
                String str = message.replaceFirst("# ", "");
                String world = player.getWorld().getName();
                plugin.setPlayerColor(player);
                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission(plugin.getConfig().getString("StaffChat.permission"))) {
                        staff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffChat.format").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", str)));
                    }
                }
            }
        }
    }
}
