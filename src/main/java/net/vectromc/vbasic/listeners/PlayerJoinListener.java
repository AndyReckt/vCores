package net.vectromc.vbasic.listeners;

import net.vectromc.vbasic.management.EconomyManagement;
import net.vectromc.vbasic.management.StatManagement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private EconomyManagement economy = new EconomyManagement();
    private StatManagement stats = new StatManagement();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String server = player.getWorld().getName();

        if (economy.economyIsEnabled(server)) {
            if (!economy.isInitialized(player)) {
                economy.initializePlayer(player);
            }
        }

        if (stats.statsAreEnabled(server)) {
            if (!stats.isInitialized(player)) {
                stats.initializePlayer(player);
            }
        }
    }
}
