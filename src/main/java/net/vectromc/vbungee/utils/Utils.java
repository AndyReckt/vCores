package me.yochran.vbungee.utils;

import me.yochran.vbungee.vbungee;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

public class Utils {

    private vbungee plugin;

    public Utils() {
        plugin = vbungee.getPlugin(vbungee.class);
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void liner(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------"));
    }

    public static void liner(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------"));
    }

    public static void spacer(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 "));
    }

    public static void spacer(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 "));
    }

    public void spawn(Player player, World world) {
        double X = plugin.data.config.getDouble("Servers." + world.getName() + ".Spawn.X");
        double Y = plugin.data.config.getDouble("Servers." + world.getName() + ".Spawn.Y");
        double Z = plugin.data.config.getDouble("Servers." + world.getName() + ".Spawn.Z");
        double Pitch = plugin.data.config.getDouble("Servers." + world.getName() + ".Spawn.Pitch");
        double Yaw = plugin.data.config.getDouble("Servers." + world.getName() + ".Spawn.Yaw");
        Location spawnLoc = new Location(world, X, Y, Z, (float) Pitch, (float) Yaw);
        player.teleport(spawnLoc);
    }
}
