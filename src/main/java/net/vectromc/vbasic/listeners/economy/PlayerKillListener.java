package net.vectromc.vbasic.listeners.economy;

import net.vectromc.vbasic.management.EconomyManagement;
import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PlayerKillListener implements Listener {

    private EconomyManagement economy = new EconomyManagement();

    private vBasic plugin;
    private vNitrogen nitrogen;

    public PlayerKillListener() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) event.getEntity();
            String server = player.getWorld().getName();
            EntityDamageEvent.DamageCause deathCause = player.getLastDamageCause().getCause();
            if (deathCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                Player killer = event.getEntity().getKiller();
                if (economy.economyIsEnabled(server) && economy.bountyIsEnabled(server)) {
                    DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
                    nitrogen.setPlayerColor(player);
                    nitrogen.setPlayerColor(killer);
                    if (economy.moneyPerKillEnabled(server)) {
                        double amountToAdd = economy.getMoneyPerKill();
                        economy.addMoney(server, killer, amountToAdd);
                        Utils.sendMessage(killer, plugin.getConfig().getString("Economy.MoneyOnKillMessage")
                                .replace("%player%", player.getDisplayName())
                                .replace("%amount%", df.format(amountToAdd)));
                    }
                    if (economy.isBountied(server, player)) {
                        double amount = economy.getBountyAmount(server, player);
                        economy.claimBounty(server, player, killer, amount);
                        for (Player onlinePlayers : Bukkit.getWorld(server).getPlayers()) {
                            Utils.sendMessage(onlinePlayers, plugin.getConfig().getString("Bounty.Completed")
                                    .replace("%player%", killer.getDisplayName())
                                    .replace("%target%", player.getDisplayName())
                                    .replace("%amount%", df.format(amount)));
                        }
                    }
                }
            }
        }
    }
}
