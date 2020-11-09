package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.management.PlayerManagement;
import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class MuteChatListener implements Listener {

    private vNitrogen plugin;

    public MuteChatListener() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.muted.contains(player.getUniqueId().toString())) {
            event.setCancelled(true);
            PlayerManagement playerManagement = new PlayerManagement(player);
            String reason = plugin.data.config.getString(player.getUniqueId().toString() + ".Mutes." + playerManagement.getMutesAmount() + ".Reason");
            Utils.sendMessage(player, plugin.getConfig().getString("Mute.OnChatError").replaceAll("%reason%", reason));
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
