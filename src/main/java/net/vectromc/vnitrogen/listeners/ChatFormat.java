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
        plugin.setPlayerPrefix(player);
        event.setFormat(ChatColor.translateAlternateColorCodes('&', player.getDisplayName() + "&7: &f" + event.getMessage()));
    }
}
