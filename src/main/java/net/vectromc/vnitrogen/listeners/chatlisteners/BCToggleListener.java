package net.vectromc.vnitrogen.listeners.chatlisteners;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BCToggleListener implements Listener {

    private vNitrogen plugin;

    public BCToggleListener() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.buildchat_toggle.contains(player.getUniqueId())) {
            String message = event.getMessage();
            event.setCancelled(true);
            String world = player.getWorld().getName();
            plugin.setPlayerColor(player);
            for (Player builders : Bukkit.getOnlinePlayers()) {
                if (builders.hasPermission(plugin.getConfig().getString("BuildChat.permission"))) {
                    builders.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("BuildChat.format").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", message)));
                }
            }
        }
    }
}
