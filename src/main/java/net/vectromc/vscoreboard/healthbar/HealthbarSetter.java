package net.vectromc.vscoreboard.healthbar;

import net.vectromc.vscoreboard.vScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class HealthbarSetter {

    private vScoreboard plugin;

    public HealthbarSetter() {
        plugin = vScoreboard.getPlugin(vScoreboard.class);
    }

    public void setHealthbar(Player player1) {
        Scoreboard scoreboard = player1.getScoreboard();
        if (scoreboard.getObjective("health") == null) {
            Objective objective = scoreboard.registerNewObjective("health", "health");
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c❤"));
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }
    }
}
