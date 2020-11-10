package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.management.PlayerManagement;
import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Date;

public class MuteChatListener implements Listener {

    private vNitrogen plugin;

    public MuteChatListener() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.muted.contains(player.getUniqueId().toString())) {
            PlayerManagement playerManagement = new PlayerManagement(player);
            if (plugin.data.config.getString(player.getUniqueId().toString() + ".Mutes." + playerManagement.getMutesAmount() + ".Temp").equalsIgnoreCase("true")) {
                if (System.currentTimeMillis() >= plugin.data.config.getLong(player.getUniqueId().toString() + ".Mutes." + playerManagement.getMutesAmount() + ".Duration")) {
                    plugin.muted.remove(player.getUniqueId().toString());
                    plugin.data.config.set("MutedPlayers." + player.getUniqueId(), null);
                    plugin.data.config.set(player.getUniqueId().toString() + ".Mutes." + playerManagement.getMutesAmount() + ".Status", "Expired");
                    plugin.data.saveData();
                } else {
                    event.setCancelled(true);
                    String reason = plugin.data.config.getString(player.getUniqueId().toString() + ".Mutes." + playerManagement.getMutesAmount() + ".Reason");
                    String expirationDate = Utils.TIME_FORMAT.format(new Date(plugin.data.config.getLong(player.getUniqueId().toString() + ".Mutes." + playerManagement.getMutesAmount() + ".Duration")));
                    Utils.sendMessage(player, plugin.getConfig().getString("TempMute.OnChatError").replaceAll("%reason%", reason).replaceAll("%expiry%", expirationDate));
                    plugin.data.saveData();
                }
            } else {
                event.setCancelled(true);
                String reason = plugin.data.config.getString(player.getUniqueId().toString() + ".Mutes." + playerManagement.getMutesAmount() + ".Reason");
                Utils.sendMessage(player, plugin.getConfig().getString("Mute.OnChatError").replaceAll("%reason%", reason));
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage();
        if (command.equalsIgnoreCase("/message") || command.equalsIgnoreCase("/msg") || command.equalsIgnoreCase("/tell") || command.equalsIgnoreCase("/whisper") || command.equalsIgnoreCase("/w") || command.equalsIgnoreCase("/t")) {
            if (plugin.muted.contains(player.getUniqueId().toString())) {
                event.setCancelled(true);
                PlayerManagement playerManagement = new PlayerManagement(player);
                String reason = plugin.data.config.getString(player.getUniqueId().toString() + ".Mutes." + playerManagement.getMutesAmount() + ".Reason");
                Utils.sendMessage(player, plugin.getConfig().getString("Mute.OnChatError").replaceAll("%reason%", reason));
            }
        }
    }
}
