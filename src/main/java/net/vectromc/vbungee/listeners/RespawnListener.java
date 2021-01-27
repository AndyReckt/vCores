package me.yochran.vbungee.listeners;

import me.yochran.vbungee.utils.Utils;
import me.yochran.vbungee.vbungee;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnListener implements Listener {

    private vbungee plugin;

    public RespawnListener() {
        plugin = vbungee.getPlugin(vbungee.class);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable() {
            public void run() {
                World world = player.getWorld();
                Utils utils = new Utils();
                utils.spawn(player, world);
            }
        }.runTaskLater(plugin, 5);
    }
}
