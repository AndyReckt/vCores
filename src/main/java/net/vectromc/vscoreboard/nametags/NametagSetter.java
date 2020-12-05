package net.vectromc.vscoreboard.nametags;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vscoreboard.vScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class NametagSetter {

    private vScoreboard plugin;
    private vNitrogen nitrogen;

    public NametagSetter() {
        plugin = vScoreboard.getPlugin(vScoreboard.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    public void setNametag(Player player1, Player player2) {
        Scoreboard scoreboard = player1.getScoreboard();
        int count = 0;
        for (String rank : nitrogen.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
            HashMap<String, Integer> team_priority = new HashMap();
            count++;
            team_priority.put(rank, count);
            if (nitrogen.pData.config.getString(player2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                if (scoreboard.getTeam("" + team_priority.get(rank)) != null) {
                    if (!scoreboard.getTeam("" + team_priority.get(rank)).hasPlayer(player2)) {
                        for (Team teams : scoreboard.getTeams()) {
                            if (teams.hasPlayer(player2)) {
                                teams.removePlayer(player2);
                            }
                        }
                        scoreboard.getTeam("" + team_priority.get(rank)).addPlayer(player2);
                    } else {
                        return;
                    }
                } else {
                    Team team = scoreboard.registerNewTeam("" + team_priority.get(rank));
                    team.setPrefix(ChatColor.translateAlternateColorCodes('&', nitrogen.getConfig().getString("Ranks." + rank + ".color")));
                    scoreboard.getTeam("" + team_priority.get(rank)).addPlayer(player2);
                }
            }
        }
    }
}
