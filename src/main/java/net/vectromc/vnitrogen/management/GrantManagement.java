package net.vectromc.vnitrogen.management;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.OfflinePlayer;

public class GrantManagement {

    private vNitrogen plugin;
    private OfflinePlayer player;

    public GrantManagement(OfflinePlayer player) {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
        this.player = player;
    }

    public int getGrantsAmount() {
        return plugin.gData.config.getInt(player.getUniqueId().toString() + ".GrantsAmount");
    }

    public void addGrant() {
        plugin.gData.config.set(player.getUniqueId().toString() + ".Name", player.getName());
        plugin.gData.config.set(player.getUniqueId().toString() + ".GrantsAmount", getGrantsAmount() + 1);
        plugin.gData.saveData();
    }
}
