package net.vectromc.vscoreboard.utils;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vscoreboard.VScoreboard;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardRunnable extends BukkitRunnable {

    private VScoreboard plugin;
    private vStaffUtils staffUtils;
    private vNitrogen nitrogen;

    public ScoreboardRunnable() {
        plugin = VScoreboard.getPlugin(VScoreboard.class);
        staffUtils = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public void run() {
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (!plugin.tsb.contains(onlinePlayers.getUniqueId())) {
                plugin.scoreboard.scoreboard(onlinePlayers);
            }
        }
    }
}
