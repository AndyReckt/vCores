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

        if (staffUtils.modmode.contains(player.getUniqueId())) {
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lVectro &7| &7Mod-Mode"));
            replaceScore(objective, 8, ChatColor.translateAlternateColorCodes('&', "&7&m----------------------&r"));
            if (staffUtils.vanished.contains(player.getUniqueId())) {
                replaceScore(objective, 7, ChatColor.translateAlternateColorCodes('&', "&e * &f&lVanished: &aYes"));
            } else {
                replaceScore(objective, 7, ChatColor.translateAlternateColorCodes('&', "&e * &f&lVanished: &cNo"));
            }
            replaceScore(objective, 6, ChatColor.translateAlternateColorCodes('&', "&e * &f&lStaff Online: &6" + staffonline));
            replaceScore(objective, 5, ChatColor.translateAlternateColorCodes('&', "&e * &f&lPlayers Online: &6" + online));
            replaceScore(objective, 4, ChatColor.translateAlternateColorCodes('&', "&e * &f&lServer: &6" + player.getWorld().getName()));
            replaceScore(objective, 3, ChatColor.translateAlternateColorCodes('&', " "));
            replaceScore(objective, 2, ChatColor.translateAlternateColorCodes('&', "&7play.vectromc.net"));
            replaceScore(objective, 1, ChatColor.translateAlternateColorCodes('&', "&7&m----------------------"));
            if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            player.setScoreboard(score);
            return;
        }

        if (!plugin.tsb.contains(player.getUniqueId())) {
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lVectroMC"));
            replaceScore(objective, 9, ChatColor.translateAlternateColorCodes('&', "&7&m----------------------&r"));
            replaceScore(objective, 8, ChatColor.translateAlternateColorCodes('&', "&6&lOnline:"));
            replaceScore(objective, 7, ChatColor.translateAlternateColorCodes('&', "&f " + online));
            replaceScore(objective, 6, ChatColor.translateAlternateColorCodes('&', "&7 "));
            replaceScore(objective, 5, ChatColor.translateAlternateColorCodes('&', "&6&lRank:"));
            replaceScore(objective, 4, ChatColor.translateAlternateColorCodes('&', "&f " + rank));
            replaceScore(objective, 3, ChatColor.translateAlternateColorCodes('&', " "));
            replaceScore(objective, 2, ChatColor.translateAlternateColorCodes('&', "&7play.vectromc.net"));
            replaceScore(objective, 1, ChatColor.translateAlternateColorCodes('&', "&7&m----------------------"));
            if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            player.setScoreboard(score);
        }
    }
}
