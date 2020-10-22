package net.vectromc.vscoreboard.utils;

import net.vectromc.vscoreboard.VScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardRunnable extends BukkitRunnable {

    private VScoreboard plugin;

    public ScoreboardRunnable() {
        plugin = VScoreboard.getPlugin(VScoreboard.class);
    }

    @Override
    public void run() {
        for(Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            plugin.scoreboard.scoreboard(onlinePlayers);
        }
    }
}
