package net.vectromc.vscoreboard.commands;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vscoreboard.VScoreboard;
import net.vectromc.vscoreboard.utils.Utils;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListCommand implements CommandExecutor {

    private VScoreboard plugin;
    private vNitrogen nitrogen;
    private vStaffUtils staffUtils;

    public ListCommand() {
        plugin = VScoreboard.getPlugin(VScoreboard.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
        staffUtils = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("Onlineplayers")) {
            Player player = (Player) sender;
            int vanished = staffUtils.vanished.size();
            int online;
            online = Bukkit.getOnlinePlayers().size() - vanished;
            List<String> ranks = new ArrayList<>();
            for (String rank : nitrogen.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                String rankName = nitrogen.getConfig().getString("Ranks." + rank + ".display");
                ranks.add(rankName);
            }
            String rankMessage = "";
            for (String rankList : ranks) {
                if (rankMessage.length() == 0) {
                    rankMessage = rankList;
                } else {
                    rankMessage = rankMessage + "&f, " + rankList;
                }
            }
            Utils.sendMessage(player, rankMessage);
            String finalMsg = "";
            String playerMessage = "";
            for (String rank : nitrogen.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                List<String> players = new ArrayList<>();
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    if (nitrogen.pData.config.getString(onlinePlayers.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                        if (!staffUtils.vanished.contains(onlinePlayers.getUniqueId())) {
                            nitrogen.setPlayerColor(onlinePlayers);
                            players.add(onlinePlayers.getDisplayName());
                        }
                    }
                }
                for (String playerList : players) {
                    if (playerMessage.length() == 0) {
                        playerMessage = playerList;
                    } else {
                        playerMessage = playerMessage + "&f, " + playerList;
                    }
                }
            }
            finalMsg = playerMessage;
            Utils.sendMessage(player, "&7(&f" + online + "&7/" + plugin.getServer().getMaxPlayers() + "&7) " + finalMsg);
        }
        return true;
    }
}
