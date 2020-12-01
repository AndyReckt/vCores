package me.yochran.vbungee.listeners;

import me.yochran.vbungee.vbungee;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLogListeners implements Listener {

    private vbungee plugin;

    public PlayerLogListeners() {
        plugin = vbungee.getPlugin(vbungee.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("Hub.HubEveryJoin")) {
            World world = Bukkit.getWorld(plugin.getConfig().getString("Hub.World"));
            int X = plugin.getConfig().getInt("Hub.Spawn.X");
            int Y = plugin.getConfig().getInt("Hub.Spawn.Y");
            int Z = plugin.getConfig().getInt("Hub.Spawn.Z");

            int pitch = plugin.getConfig().getInt("Hub.Spawn.Pitch");
            int yaw = plugin.getConfig().getInt("Hub.Spawn.Yaw");
            Location hubSpawnLocation = new Location(world, X, Y, Z, (float) pitch, (float) yaw);
            player.teleport(hubSpawnLocation);
        }
    }
}
