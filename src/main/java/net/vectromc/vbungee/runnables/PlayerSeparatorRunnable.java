package me.yochran.vbungee.runnables;

import me.yochran.vbungee.vbungee;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerSeparatorRunnable extends BukkitRunnable {

    private vStaffUtils staffUtils;
    private vbungee plugin;

    public PlayerSeparatorRunnable() {
        staffUtils = vStaffUtils.getPlugin(vStaffUtils.class);
        plugin = vbungee.getPlugin(vbungee.class);
    }

    @Override
    public void run() {
        if (plugin.getConfig().getBoolean("WorldSeparation")) {
            for (Player playerone : Bukkit.getOnlinePlayers()) {
                for (Player playertwo : Bukkit.getOnlinePlayers()) {
                    if (playerone.getWorld() != playertwo.getWorld()) {
                        playerone.hidePlayer(playertwo);
                    } else {
                        if (!staffUtils.vanished.contains(playertwo.getUniqueId())) {
                            playerone.showPlayer(playertwo);
                        }
                    }
                }
            }
        }
    }
}
