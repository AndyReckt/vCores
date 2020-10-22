package net.vectromc.vnitrogen.listeners.chatlisteners;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ACToggleListener implements Listener {

    private vNitrogen plugin;

    public ACToggleListener() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.adminchat_toggle.contains(player.getUniqueId())) {
            String message = event.getMessage();
            event.setCancelled(true);
            String world = player.getWorld().getName();
            plugin.setPlayerColor(player);
            for (Player admins : Bukkit.getOnlinePlayers()) {
                if (admins.hasPermission(plugin.getConfig().getString("AdminChat.permission"))) {
                    admins.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("AdminChat.format").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", message)));
                }
            }
        }
    }
}
