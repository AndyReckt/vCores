package net.vectromc.vnitrogen.management;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PermissionManagement {

    private vNitrogen plugin;

    public PermissionManagement() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    public HashMap<UUID, PermissionAttachment> attachments = new HashMap<>();

    public boolean isInitialized(OfflinePlayer player) {
        return plugin.permData.config.contains(player.getUniqueId().toString());
    }

    public void setupPlayerPermissions(Player player) {
        String rank = plugin.pData.config.getString(player.getUniqueId().toString() + ".Rank");
        PermissionAttachment attachment = player.addAttachment(plugin);
        for (String rankPermissions : plugin.permData.config.getStringList("Ranks." + rank + ".Permissions")) {
            if (rankPermissions.startsWith("-")) {
                attachment.setPermission(rankPermissions, false);
            } else {
                attachment.setPermission(rankPermissions, true);
            }
        }

        for (String playerPermissions : plugin.permData.config.getStringList(player.getUniqueId().toString() + ".Permissions")) {
            if (playerPermissions.startsWith("-")) {
                attachment.setPermission(playerPermissions, false);
            } else {
                attachment.setPermission(playerPermissions, true);
            }
        }

        if (plugin.permData.config.getStringList(player.getUniqueId().toString() + ".Permissions").contains("*") || plugin.permData.config.getStringList("Ranks." + rank + ".Permissions").contains("*")) {
            player.setOp(true);
        } else {
            player.setOp(false);
        }

        attachments.put(player.getUniqueId(), attachment);
    }

    public void addPlayerPermission(OfflinePlayer player, String permission) {
        List<String> permissions = new ArrayList<>();
        permissions.add(permission);
        for (String playerPermissions : plugin.permData.config.getStringList(player.getUniqueId().toString() + ".Permissions")) {
            permissions.add(playerPermissions);
        }
        plugin.permData.config.set(player.getUniqueId().toString() + ".Permissions", permissions);
        plugin.permData.saveData();

        if (player.isOnline()) {
            Player newPlayer = Bukkit.getPlayer(player.getUniqueId());
            setupPlayerPermissions(newPlayer);
        }
    }

    public void removePlayerPermission(OfflinePlayer player, String permission) {
        List<String> permissions = new ArrayList<>();
        for (String playerPermissions : plugin.permData.config.getStringList(player.getUniqueId().toString() + ".Permissions")) {
            if (!playerPermissions.equalsIgnoreCase(permission)) {
                permissions.add(playerPermissions);
            }
        }
        permissions.remove(permission);
        plugin.permData.config.set(player.getUniqueId().toString() + ".Permissions", permissions);
        plugin.permData.saveData();

        if (player.isOnline()) {
            Player newPlayer = Bukkit.getPlayer(player.getUniqueId());
            setupPlayerPermissions(newPlayer);
        }
    }

    public void addRankPermission(String rank, String permission) {
        List<String> permissions = new ArrayList<>();
        permissions.add(permission);
        for (String rankPermissions : plugin.permData.config.getStringList("Ranks." + rank + ".Permissions")) {
            permissions.add(rankPermissions);
        }
        plugin.permData.config.set("Ranks." + rank + ".Permissions", permissions);
        plugin.permData.saveData();

        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (plugin.pData.config.getString(onlinePlayers.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                setupPlayerPermissions(onlinePlayers);
            }
        }
    }

    public void removeRankPermission(String rank, String permission) {
        List<String> permissions = new ArrayList<>();
        for (String rankPermissions : plugin.permData.config.getStringList("Ranks." + rank + ".Permissions")) {
            if (!rankPermissions.equalsIgnoreCase(permission)) {
                permissions.add(rankPermissions);
            }
        }
        permissions.remove(permission);
        plugin.permData.config.set("Ranks." + rank + ".Permissions", permissions);
        plugin.permData.saveData();

        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (plugin.pData.config.getString(onlinePlayers.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                setupPlayerPermissions(onlinePlayers);
                attachments.get(onlinePlayers.getUniqueId()).unsetPermission(permission);
                attachments.get(onlinePlayers.getUniqueId()).setPermission(permission, false);
                setupPlayerPermissions(onlinePlayers);
            }
        }
    }
}
