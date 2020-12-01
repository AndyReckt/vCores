package me.yochran.vbungee.listeners;

import me.yochran.vbungee.vbungee;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private vbungee plugin;

    public ChatListener() {
        plugin = vbungee.getPlugin(vbungee.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (plugin.getConfig().getBoolean("ChatSeparation")) {
            Player player = event.getPlayer();
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                if (onlinePlayers.getWorld() != player.getWorld()) {
                    event.getRecipients().remove(onlinePlayers);
                }
            }
         }
    }
}
