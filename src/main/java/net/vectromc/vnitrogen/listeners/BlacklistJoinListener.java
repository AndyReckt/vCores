package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.management.PlayerManagement;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BlacklistJoinListener implements Listener {

    private vNitrogen plugin;

    public BlacklistJoinListener() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String ip = player.getAddress().getAddress().getHostAddress();
        if (plugin.blacklisted.contains(ip)) {
            String main = "";
            String reason = "";
            for (String uuid : plugin.data.config.getConfigurationSection("BlacklistedIPs").getKeys(false)) {
                if (plugin.data.config.getString("BlacklistedIPs." + uuid + ".IP").equals(ip)) {
                    int id = plugin.data.config.getInt(uuid + ".BlacklistsAmount");
                    main = plugin.data.config.getString("BlacklistedIPs." + uuid + ".Main");
                    reason = plugin.data.config.getString(main + ".Blacklists." + id + ".Reason");
                }
            }
            int id = plugin.data.config.getInt(main + ".BlacklistsAmount");
            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Blacklist.BlacklistMessage").replace("%reason%", reason)));
            plugin.data.config.getStringList(main + ".Blacklists." + id + ".LinkedAccounts").add(player.getName());
            plugin.data.saveData();
        }
    }
}
