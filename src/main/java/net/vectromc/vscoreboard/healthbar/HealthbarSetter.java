package net.vectromc.vscoreboard.healthbar;

import net.vectromc.vscoreboard.vScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class HealthbarSetter {

    private vScoreboard plugin;

    public HealthbarSetter() {
        plugin = vScoreboard.getPlugin(vScoreboard.class);
    }

    public void setHealthbar(Player player1) {
        List<String> servers = new ArrayList<>();
        for (String enabled : plugin.getConfig().getStringList("HealthBar.EnabledWorlds")) {
            servers.add(enabled);
        }
        if (servers.contains(player1.getWorld().getName())) {
            Scoreboard scoreboard = player1.getScoreboard();
            if (scoreboard.getObjective("health") == null) {
                Objective objective = scoreboard.registerNewObjective("health", "health");
                objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c❤"));
                objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
        }
    }
}
