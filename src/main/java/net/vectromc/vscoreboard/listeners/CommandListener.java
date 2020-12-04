package net.vectromc.vscoreboard.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (event.getMessage().equalsIgnoreCase("/list") || event.getMessage().equalsIgnoreCase("/online")) {
            event.setCancelled(true);
            player.performCommand("onlineplayers");
        }
    }
}
