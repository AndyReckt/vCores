package net.vectromc.vscoreboard.listeners;

import net.vectromc.vscoreboard.vScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldChangeListener implements Listener {

    private vScoreboard plugin;

    public WorldChangeListener() {
        plugin = vScoreboard.getPlugin(vScoreboard.class);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        plugin.tsb.add(player.getUniqueId());
        new BukkitRunnable() {
            public void run() {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }.runTaskLater(plugin, 5);
        plugin.tsb.remove(player.getUniqueId());
    }
}
