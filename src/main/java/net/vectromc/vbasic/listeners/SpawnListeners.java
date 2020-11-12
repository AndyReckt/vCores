package net.vectromc.vbasic.listeners;

import net.vectromc.vbasic.vBasic;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpawnListeners implements Listener {

    private vBasic plugin;

    public SpawnListeners() {
        plugin = vBasic.getPlugin(vBasic.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            double X = plugin.getConfig().getDouble("Spawn.X");
            double Y = plugin.getConfig().getDouble("Spawn.Y");
            double Z = plugin.getConfig().getDouble("Spawn.Z");
            double pitch = plugin.getConfig().getDouble("Spawn.Pitch");
            double yaw = plugin.getConfig().getDouble("Spawn.Yaw");
            World world = Bukkit.getWorld(plugin.getConfig().getString("Spawn.World"));
            Location spawnLoc = new Location(world, X, Y, Z, (float) pitch, (float) yaw);
            player.teleport(spawnLoc);
        } else {
            if (plugin.getConfig().getBoolean("SpawnEveryJoin")) {
                double X = plugin.getConfig().getDouble("Spawn.X");
                double Y = plugin.getConfig().getDouble("Spawn.Y");
                double Z = plugin.getConfig().getDouble("Spawn.Z");
                double pitch = plugin.getConfig().getDouble("Spawn.Pitch");
                double yaw = plugin.getConfig().getDouble("Spawn.Yaw");
                World world = Bukkit.getWorld(plugin.getConfig().getString("Spawn.World"));
                Location spawnLoc = new Location(world, X, Y, Z, (float) pitch, (float) yaw);
                player.teleport(spawnLoc);
            }
        }
    }
}
