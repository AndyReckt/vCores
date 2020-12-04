package net.vectromc.vbasic.listeners.stats;

import net.vectromc.vbasic.management.StatManagement;
import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.text.DecimalFormat;

public class PlayerDeathListener implements Listener {

    private StatManagement stats = new StatManagement();

    private vBasic plugin;
    private vNitrogen nitrogen;

    public  PlayerDeathListener() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) event.getEntity();
            String server = player.getWorld().getName();
            EntityDamageEvent.DamageCause cause = player.getLastDamageCause().getCause();
            if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                Player killer = player.getKiller();
                if (stats.statsAreEnabled(server)) {
                    nitrogen.setPlayerColor(player);
                    nitrogen.setPlayerColor(killer);
                    stats.addDeath(server, player);
                    stats.addKill(server, killer);
                    stats.addToStreak(server, killer);
                    if (stats.hasStreak(server, player)) {
                        DecimalFormat df = new DecimalFormat("###,###.##");
                        int streak = stats.getStreak(server, player);
                        String str = df.format(streak);
                        stats.endStreak(server, player);
                        if (stats.streakShouldBeAnnounced(streak)) {
                            for (Player onlinePlayers : Bukkit.getWorld(server).getPlayers()) {
                                Utils.sendMessage(onlinePlayers, plugin.getConfig().getString("Stats.StreakEndBroadcast")
                                        .replace("%player%", killer.getDisplayName())
                                        .replace("%target%", player.getDisplayName())
                                        .replace("%streak%", str));
                            }
                        }
                        stats.updateKDR(server, player);
                        stats.updateKDR(server, killer);
                    }
                }
            }
        }
    }

    @EventHandler
    public void checkDeathMessages(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!plugin.getConfig().getBoolean("DeathMessages")) {
            event.setDeathMessage("");
        }
    }
}
