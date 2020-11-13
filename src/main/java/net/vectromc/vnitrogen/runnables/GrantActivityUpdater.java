package net.vectromc.vnitrogen.runnables;

import net.vectromc.vnitrogen.management.GrantManagement;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GrantActivityUpdater extends BukkitRunnable {

    private vNitrogen plugin;

    public GrantActivityUpdater() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public void run() {
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            GrantManagement grantManagement = new GrantManagement(onlinePlayers);
            if (plugin.gData.config.contains(onlinePlayers.getUniqueId().toString())) {
                if (plugin.gData.config.getString(onlinePlayers.getUniqueId().toString() + ".Grants." + grantManagement.getGrantsAmount() + ".Temp").equalsIgnoreCase("true")) {
                    if (plugin.gData.config.getString(onlinePlayers.getUniqueId().toString() + ".Grants." + grantManagement.getGrantsAmount() + ".Status").equalsIgnoreCase("Active")) {
                        if (System.currentTimeMillis() >= plugin.gData.config.getLong(onlinePlayers.getUniqueId().toString() + ".Grants." + grantManagement.getGrantsAmount() + ".Duration")) {
                            String prevRank = plugin.gData.config.getString(onlinePlayers.getUniqueId().toString() + ".Grants." + grantManagement.getGrantsAmount() + ".PrevRank");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setrank " + onlinePlayers.getName() + " " + prevRank);
                            plugin.gData.config.set(onlinePlayers.getUniqueId().toString() + ".Grants." + grantManagement.getGrantsAmount() + ".Status", "Expired");
                            plugin.gData.saveData();
                        }
                    }
                }
            }
        }
        for (OfflinePlayer offlinePlayers : Bukkit.getOfflinePlayers()) {
            GrantManagement grantManagement = new GrantManagement(offlinePlayers);
            if (plugin.gData.config.contains(offlinePlayers.getUniqueId().toString())) {
                if (plugin.gData.config.getString(offlinePlayers.getUniqueId().toString() + ".Grants." + grantManagement.getGrantsAmount() + ".Temp").equalsIgnoreCase("true")) {
                    if (plugin.gData.config.getString(offlinePlayers.getUniqueId().toString() + ".Grants." + grantManagement.getGrantsAmount() + ".Status").equalsIgnoreCase("Active")) {
                        if (System.currentTimeMillis() >= plugin.gData.config.getLong(offlinePlayers.getUniqueId().toString() + ".Grants." + grantManagement.getGrantsAmount() + ".Duration")) {
                            String prevRank = plugin.gData.config.getString(offlinePlayers.getUniqueId().toString() + ".Grants." + grantManagement.getGrantsAmount() + ".PrevRank");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setrank " + offlinePlayers.getName() + " " + prevRank);
                            plugin.gData.config.set(offlinePlayers.getUniqueId().toString() + ".Grants." + grantManagement.getGrantsAmount() + ".Status", "Expired");
                            plugin.gData.saveData();
                        }
                    }
                }
            }
        }
    }
}
