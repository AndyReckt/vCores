package net.vectromc.vscoreboard.healthbar;

import net.vectromc.vscoreboard.vScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HealthbarUpdater extends BukkitRunnable {

    private vScoreboard plugin;

    public HealthbarUpdater() {
        plugin = vScoreboard.getPlugin(vScoreboard.class);
    }

    @Override
    public void run() {
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            plugin.healthbar.setHealthbar(onlinePlayers);
        }
    }
}
