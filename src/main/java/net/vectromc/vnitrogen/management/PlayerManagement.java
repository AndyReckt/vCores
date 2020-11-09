package net.vectromc.vnitrogen.management;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.OfflinePlayer;

public class PlayerManagement {

    private vNitrogen plugin;
    private OfflinePlayer player;

    public PlayerManagement(OfflinePlayer player) {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
        this.player = player;
    }

    public int getWarnsAmount() {
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".WarnsAmount");
    }

    public void addWarn() {
        plugin.data.config.set(player.getUniqueId().toString() + ".Name", player.getName());
        plugin.data.config.set(player.getUniqueId().toString() + ".WarnsAmount", getWarnsAmount() + 1);
        plugin.data.saveData();
    }

    public int getMutesAmount() {
        return plugin.data.config.getInt(player.getUniqueId().toString() + ".MutesAmount");
    }

    public void addMute() {
        plugin.data.config.set(player.getUniqueId().toString() + ".Name", player.getName());
        plugin.data.config.set(player.getUniqueId().toString() + ".MutesAmount", getMutesAmount() + 1);
        plugin.data.saveData();
    }
}
