package net.vectromc.vbasic.management;

import net.vectromc.vbasic.vBasic;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.util.HashMap;

public class StatManagement {

    private vBasic plugin;

    public StatManagement() {
        plugin = vBasic.getPlugin(vBasic.class);
    }

    public void setNameInConfig(OfflinePlayer player) {
        plugin.stats.config.set(player.getUniqueId().toString() + ".Name", player.getName());
    }

    public boolean isInitialized(OfflinePlayer player) {
        if (plugin.stats.config.contains(player.getUniqueId().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public void initializePlayer(OfflinePlayer player) {
        setNameInConfig(player);
        for (String server : plugin.getConfig().getStringList("Stats.EnabledWorlds")) {
            plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".Kills", 0);
            plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".Deaths", 0);
            plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".KDR", 0.0);
            plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".Streak", 0);
        }
        plugin.stats.saveData();
    }

    public boolean statsAreEnabled(String server) {
        if (plugin.getConfig().getStringList("Stats.EnabledWorlds").contains(server)) {
            return true;
        } else {
            return false;
        }
    }

    public int getKills(String server, OfflinePlayer player) {
        return plugin.stats.config.getInt(player.getUniqueId().toString() + "." + server + ".Kills");
    }

    public int getDeaths(String server, OfflinePlayer player) {
        return plugin.stats.config.getInt(player.getUniqueId().toString() + "." + server + ".Deaths");
    }

    public double getKDR(String server, OfflinePlayer player) {
        return plugin.stats.config.getDouble(player.getUniqueId().toString() + "." + server + ".KDR");
    }

    public int getStreak(String server, OfflinePlayer player) {
        return plugin.stats.config.getInt(player.getUniqueId().toString() + "." + server + ".Streak");
    }

    public HashMap<String, String> getAllStats(String server, OfflinePlayer player) {
        DecimalFormat df = new DecimalFormat("###,###.##");
        HashMap<String, String> stats = new HashMap<>();

        String kills = df.format(getKills(server, player));
        String deaths = df.format(getDeaths(server, player));
        String kdr = df.format(getKDR(server, player));
        String streak = df.format(getStreak(server, player));

        stats.put("Kills", kills);
        stats.put("Deaths", deaths);
        stats.put("KDR", kdr);
        stats.put("Streak", streak);

        return stats;
    }

    public void addKill(String server, OfflinePlayer player) {
        plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".Kills", getKills(server, player) + 1);
        updateKDR(server, player);
        plugin.stats.saveData();
    }

    public void addDeath(String server, OfflinePlayer player) {
        plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".Deaths", getDeaths(server, player) + 1);
        updateKDR(server, player);
        plugin.stats.saveData();
    }

    public void updateKDR(String server, OfflinePlayer player) {
        int kills = getKills(server, player);
        int deaths = getDeaths(server, player);

        int alternateKills;
        int alternateDeaths;

        if (deaths == 0) {
            alternateDeaths = 1;
        } else {
            alternateDeaths = deaths;
        }

        alternateKills = kills;

        double kdr = (double) alternateKills / (double) alternateDeaths;

        plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".KDR", kdr);
        plugin.stats.saveData();
    }

    public void addToStreak(String server, OfflinePlayer player) {
        plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".Streak", getStreak(server, player) + 1);
        plugin.stats.saveData();
    }

    public void endStreak(String server, OfflinePlayer player) {
        plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".Streak", 0);
        plugin.stats.saveData();
    }

    public boolean hasStreak(String server, OfflinePlayer player) {
        if (getStreak(server, player) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean streakShouldBeAnnounced(int streak) {
        if (streak >= plugin.getConfig().getInt("Stats.MinimumStreakEndBroadcast")) {
            return true;
        } else {
            return false;
        }
    }

    public void resetStats(String server, OfflinePlayer player) {
        plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".Kills", 0);
        plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".Deaths", 0);
        plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".KDR", 0.0);
        plugin.stats.config.set(player.getUniqueId().toString() + "." + server + ".Streak", 0);
        plugin.stats.saveData();
    }
}
