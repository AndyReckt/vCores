package net.vectromc.vscoreboard;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerScoreboard implements Listener {

    public ArrayList<UUID> enabled = new ArrayList<>();
    private VScoreboard plugin;
    private vNitrogen nitrogen;
    private vStaffUtils staffUtils;

    public PlayerScoreboard() {
        plugin = VScoreboard.getPlugin(VScoreboard.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
        staffUtils = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.tsb.contains(player.getUniqueId())) {
            scoreboard(player);
            this.enabled.add(player.getUniqueId());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.enabled.remove(player.getUniqueId());
    }


    public static String getEntryFromScore(final Objective o, final int score) {
        if (o == null) {
            return null;
        }
        if (!hasScoreTaken(o, score)) {
            return null;
        }
        for (final String s : o.getScoreboard().getEntries()) {
            if (o.getScore(s).getScore() == score) {
                return o.getScore(s).getEntry();
            }
        }
        return null;
    }

    public static boolean hasScoreTaken(Objective o, final int score) {
        for (final String s : o.getScoreboard().getEntries()) {
            if (o.getScore(s).getScore() == score) {
                return true;
            }
        }
        return false;
    }

    public static void replaceScore(Objective o, final int score, final String name) {
        if (hasScoreTaken(o, score)) {
            if (getEntryFromScore(o, score).equalsIgnoreCase(name)) {
                return;
            }
            if (!getEntryFromScore(o, score).equalsIgnoreCase(name)) {
                o.getScoreboard().resetScores(getEntryFromScore(o, score));
            }
        }
        o.getScore(name).setScore(score);
    }

    public void scoreboard(Player player) {
        if (plugin.getConfig().getBoolean("Scoreboard.Enabled")) {
            if (enabled.contains(player.getUniqueId())) {
                return;
            }
            if (player.getScoreboard().equals(Bukkit.getServer().getScoreboardManager().getMainScoreboard())) {
                player.setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard());
            }
            Scoreboard score = player.getScoreboard();
            final Objective objective = (score.getObjective(player.getName()) == null) ? score.registerNewObjective(player.getName(), "dummy") : score.getObjective(player.getName());
            int online;
            int staffonline;
            String rank = "";

            int vanished = staffUtils.vanished.size();
            online = Bukkit.getOnlinePlayers().size() - vanished;
            staffonline = 0;
            for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                if (onlineStaff.hasPermission(plugin.getConfig().getString("StaffScoreboard.Permission"))) {
                    staffonline++;
                }
            }

            for (String rankLoop : nitrogen.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                String permName = nitrogen.getConfig().getString("Ranks." + rankLoop + ".permission");
                if (player.hasPermission(permName)) {
                    rank = nitrogen.getConfig().getString("Ranks." + rankLoop + ".display");
                }
            }

            nitrogen.setPlayerColor(player);

            if (staffUtils.modmode.contains(player.getUniqueId())) {
                if (plugin.getConfig().getBoolean("StaffScoreboard.Enabled")) {
                    String title = plugin.getConfig().getString("StaffScoreboard.Title");
                    String format;
                    String vanish;
                    if (staffUtils.vanished.contains(player.getUniqueId())) {
                        vanish = "&aYes";
                    } else {
                        vanish = "&cNo";
                    }
                    objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));

                    int row = plugin.getConfig().getStringList("StaffScoreboard.Format").size();
                    for (String scores : plugin.getConfig().getStringList("StaffScoreboard.Format")) {
                        row--;
                        format = scores
                                .replace("%online%", "" + online)
                                .replace("%rank%", rank)
                                .replace("%name%", player.getName())
                                .replace("%displayname%", player.getDisplayName())
                                .replace("%vanish%", vanish)
                                .replace("%onlinestaff%", "" + staffonline)
                                .replace("%world%", player.getWorld().getName());
                        replaceScore(objective, row, ChatColor.translateAlternateColorCodes('&', format));
                    }
                    if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    }
                    player.setScoreboard(score);
                    return;
                } else {
                    if (!plugin.tsb.contains(player.getUniqueId())) {
                        String title = plugin.getConfig().getString("Scoreboard.Title");
                        String format;
                        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
                        int row = plugin.getConfig().getStringList("Scoreboard.Format").size();
                        for (String scores : plugin.getConfig().getStringList("Scoreboard.Format")) {
                            row--;
                            format = scores
                                    .replace("%online%", "" + online)
                                    .replace("%rank%", rank)
                                    .replace("%name%", player.getName())
                                    .replace("%displayname%", player.getDisplayName());
                            replaceScore(objective, row, ChatColor.translateAlternateColorCodes('&', format));
                        }
                        if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                        }
                        player.setScoreboard(score);
                    }
                }
            }

            if (!plugin.tsb.contains(player.getUniqueId())) {
                String title = plugin.getConfig().getString("Scoreboard.Title");
                String format = "";
                objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
                int row = plugin.getConfig().getStringList("Scoreboard.Format").size();
                for (String scores : plugin.getConfig().getStringList("Scoreboard.Format")) {
                    row--;
                    format = scores
                            .replace("%online%", "" + online)
                            .replace("%rank%", rank)
                            .replace("%name%", player.getName())
                            .replace("%displayname%", player.getDisplayName());
                    replaceScore(objective, row, ChatColor.translateAlternateColorCodes('&', format));
                }
                if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                }
                player.setScoreboard(score);
            }
        }
    }
}
