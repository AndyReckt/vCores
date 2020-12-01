package me.yochran.vbungee.listeners;

import me.yochran.vbungee.vbungee;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerLogListeners implements Listener {

    private vbungee plugin;
    private vStaffUtils staffUtils;

    public PlayerLogListeners() {
        plugin = vbungee.getPlugin(vbungee.class);
        staffUtils = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("Hub.HubEveryJoin")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    World world = Bukkit.getWorld(plugin.getConfig().getString("Hub.World"));
                    double X = plugin.getConfig().getDouble("Hub.Spawn.X");
                    double Y = plugin.getConfig().getDouble("Hub.Spawn.Y");
                    double Z = plugin.getConfig().getDouble("Hub.Spawn.Z");
                    double Pitch = plugin.getConfig().getDouble("Hub.Spawn.Pitch");
                    double Yaw = plugin.getConfig().getDouble("Hub.Spawn.Yaw");
                    Location hubSpawnLocation = new Location(world, X, Y, Z, (float) Pitch, (float) Yaw);
                    player.teleport(hubSpawnLocation);
                    for (Player onlinePlayers : player.getWorld().getPlayers()) {
                        if (!staffUtils.vanished.contains(player.getUniqueId()) && !staffUtils.vanish_logged.contains(player.getUniqueId())) {
                            onlinePlayers.showPlayer(player);
                        }
                    }
                }
            }.runTaskLater(plugin, 10);
        }
    }
}
