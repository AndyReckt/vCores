package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.management.PunishmentManagement;
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
            PunishmentManagement punishmentManagement = new PunishmentManagement(player);
            if (plugin.data.config.getString(player.getUniqueId().toString() + ".Mutes." + punishmentManagement.getMutesAmount() + ".Temp").equalsIgnoreCase("true")) {
                if (System.currentTimeMillis() >= plugin.data.config.getLong(player.getUniqueId().toString() + ".Mutes." + punishmentManagement.getMutesAmount() + ".Duration")) {
                    plugin.muted.remove(player.getUniqueId().toString());
                    plugin.data.config.set("MutedPlayers." + player.getUniqueId(), null);
                    plugin.data.config.set(player.getUniqueId().toString() + ".Mutes." + punishmentManagement.getMutesAmount() + ".Status", "Expired");
                    plugin.data.saveData();
                } else {
                    event.setCancelled(true);
                    String reason = plugin.data.config.getString(player.getUniqueId().toString() + ".Mutes." + punishmentManagement.getMutesAmount() + ".Reason");
                    String expirationDate = Utils.TIME_FORMAT.format(new Date(plugin.data.config.getLong(player.getUniqueId().toString() + ".Mutes." + punishmentManagement.getMutesAmount() + ".Duration")));
                    Utils.sendMessage(player, plugin.getConfig().getString("TempMute.OnChatError").replaceAll("%reason%", reason).replaceAll("%expiry%", expirationDate));
                    plugin.data.saveData();
                }
            } else {
                event.setCancelled(true);
                String reason = plugin.data.config.getString(player.getUniqueId().toString() + ".Mutes." + punishmentManagement.getMutesAmount() + ".Reason");
                Utils.sendMessage(player, plugin.getConfig().getString("Mute.OnChatError").replaceAll("%reason%", reason));
            }
        }

        if (plugin.chatMute.contains("Active")) {
            if (!player.hasPermission(plugin.getConfig().getString("MuteChat.Bypass"))) {
                event.setCancelled(true);
                Utils.sendMessage(player, plugin.getConfig().getString("MuteChat.ChatError"));
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().toLowerCase();
        if (command.contains("/message") || command.contains("/msg") || command.contains("/tell") || command.contains("/whisper") || command.contains("/w") || command.contains("/t") || command.contains("/r") || command.contains("/reply")) {
            if (plugin.muted.contains(player.getUniqueId().toString())) {
                event.setCancelled(true);
                PunishmentManagement punishmentManagement = new PunishmentManagement(player);
                String reason = plugin.data.config.getString(player.getUniqueId().toString() + ".Mutes." + punishmentManagement.getMutesAmount() + ".Reason");
                Utils.sendMessage(player, plugin.getConfig().getString("Mute.OnChatError").replaceAll("%reason%", reason));
            }
        }
    }
}
