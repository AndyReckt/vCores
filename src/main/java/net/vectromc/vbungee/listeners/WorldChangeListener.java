package me.yochran.vbungee.listeners;

import me.yochran.vbungee.vbungee;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeListener implements Listener {

    private vbungee plugin;

    public WorldChangeListener() {
        plugin = vbungee.getPlugin(vbungee.class);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        double X = plugin.data.config.getDouble("Servers." + world.getName() + ".Spawn.X");
        double Y = plugin.data.config.getDouble("Servers." + world.getName() + ".Spawn.Y");
        double Z = plugin.data.config.getDouble("Servers." + world.getName() + ".Spawn.Z");
        double Pitch = plugin.data.config.getDouble("Servers." + world.getName() + ".Spawn.Pitch");
        double Yaw = plugin.data.config.getDouble("Servers." + world.getName() + ".Spawn.Yaw");
        Location spawnLoc = new Location(world, X, Y, Z, (float) Pitch, (float) Yaw);
        player.teleport(spawnLoc);
    }
}
