package net.vectromc.vstaffutils.runnables;

import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class VanishTicker extends BukkitRunnable {

    private vStaffUtils plugin;

    public VanishTicker() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    @Override
    public void run() {
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (plugin.vanished.contains(onlinePlayers.getUniqueId())) {
                    if (player.hasPermission(plugin.getConfig().getString("VanishPermission"))) {
                        player.showPlayer(onlinePlayers);
                    } else {
                        player.hidePlayer(onlinePlayers);
                    }
                }
            }
        }
    }
}
