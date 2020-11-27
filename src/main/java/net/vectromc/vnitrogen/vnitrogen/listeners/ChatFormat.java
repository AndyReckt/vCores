package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormat implements Listener {

    private vNitrogen plugin;

    public ChatFormat() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = "";
        if (!player.hasPermission(plugin.getConfig().getString("ColorChat.Permission"))) {
            message = ChatColor.stripColor(event.getMessage().replaceAll("%", "%%").replace("&", ""));
        } else {
            message = event.getMessage().replaceAll("%", "%%");
        }
        plugin.setPlayerPrefix(player);
        event.setFormat(ChatColor.translateAlternateColorCodes('&', player.getDisplayName() + "&7: &f" + message));
    }
}
