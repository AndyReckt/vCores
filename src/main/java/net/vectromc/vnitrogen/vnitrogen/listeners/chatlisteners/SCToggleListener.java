package net.vectromc.vnitrogen.listeners.chatlisteners;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SCToggleListener implements Listener {

    private vNitrogen plugin;

    public SCToggleListener() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.staffchat_toggle.contains(player.getUniqueId())) {
            String message = event.getMessage();
            event.setCancelled(true);
            String world = player.getWorld().getName();
            plugin.setPlayerColor(player);
            for (Player managers : Bukkit.getOnlinePlayers()) {
                if (managers.hasPermission(plugin.getConfig().getString("StaffChat.permission"))) {
                    managers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffChat.format").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", message)));
                }
            }
        }
    }
}
