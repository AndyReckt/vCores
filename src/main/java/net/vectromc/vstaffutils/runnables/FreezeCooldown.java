package net.vectromc.vstaffutils.runnables;

import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FreezeCooldown extends BukkitRunnable {

    private vStaffUtils plugin;

    public FreezeCooldown() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    @Override
    public void run() {
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (plugin.freeze_cooldown.containsKey(onlinePlayers.getUniqueId())) {
                double time = plugin.freeze_cooldown.get(onlinePlayers.getUniqueId());
                if (time <= 0.0) {
                    plugin.freeze_cooldown.remove(onlinePlayers.getUniqueId());
                } else {
                    plugin.freeze_cooldown.put(onlinePlayers.getUniqueId(), plugin.freeze_cooldown.get(onlinePlayers.getUniqueId()) - 0.25);
                }
            }
        }
    }
}
