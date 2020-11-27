package net.vectromc.vnitrogen.utils;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Optional;

public class Utils {

    private static vNitrogen plugin;

    public Utils() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
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

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy HH:mm:ss z");

    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("MM/dd/yy HH:mm:ss z");

    public static void chatClearer(Player onlinePlayers) {
        for (int i = 0; i < 50; i++) {
            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n &a \n "));
        }
    }
}