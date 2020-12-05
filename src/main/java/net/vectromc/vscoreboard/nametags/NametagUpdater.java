package net.vectromc.vscoreboard.nametags;

import net.vectromc.vscoreboard.vScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NametagUpdater extends BukkitRunnable {

    private vScoreboard plugin;

    public NametagUpdater() {
        plugin = vScoreboard.getPlugin(vScoreboard.class);
    }

    @Override
    public void run() {
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            for (Player player2 : Bukkit.getOnlinePlayers()) {
                plugin.nametag.setNametag(player1, player2);
            }
        }
    }
}
