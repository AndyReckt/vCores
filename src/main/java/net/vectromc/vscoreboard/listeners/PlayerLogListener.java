package net.vectromc.vscoreboard.listeners;

import net.vectromc.vscoreboard.vScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLogListener implements Listener {

    private vScoreboard plugin;

    public PlayerLogListener() {
        plugin = vScoreboard.getPlugin(vScoreboard.class);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            plugin.nametag.setNametag(player, onlinePlayers);
        }
    }
}
