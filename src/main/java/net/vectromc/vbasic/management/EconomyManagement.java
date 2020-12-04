package net.vectromc.vbasic.management;

import net.vectromc.vbasic.vBasic;
import org.bukkit.OfflinePlayer;

public class EconomyManagement {

    private vBasic plugin;

    public  EconomyManagement() {
        plugin = vBasic.getPlugin(vBasic.class);
    }

    public void setNameInConfig(OfflinePlayer player) {
        plugin.economy.config.set(player.getUniqueId().toString() + ".Name", player.getName());
    }

    public boolean isInitialized(OfflinePlayer player) {
        if (plugin.economy.config.contains(player.getUniqueId().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public void initializePlayer(OfflinePlayer player) {
        setNameInConfig(player);
        for (String server : plugin.getConfig().getStringList("Economy.EnabledWorlds")) {
            double startingAmount = plugin.getConfig().getDouble("Economy.StartingAmount");
            plugin.economy.config.set(player.getUniqueId().toString() + "." + server + ".Balance", startingAmount);
            plugin.economy.config.set(player.getUniqueId().toString() + "." + server + ".Bountied", false);
            plugin.economy.config.set(player.getUniqueId().toString() + "." + server + ".Bounty", null);
        }
        plugin.economy.saveData();
    }

    public void resetPlayer(String server, OfflinePlayer player) {
        setNameInConfig(player);
        double startingAmount = plugin.getConfig().getInt("Economy.StartingAmont");
        plugin.economy.config.set(player.getUniqueId().toString() + "." + server + ".Balance", startingAmount);
        plugin.economy.config.set(player.getUniqueId().toString() + "." + server + ".Bountied", false);
        plugin.economy.config.set(player.getUniqueId().toString() + "." + server + ".Bounty", null);
        plugin.economy.saveData();
    }

    public boolean economyIsEnabled(String server) {
        if (plugin.getConfig().getStringList("Economy.EnabledWorlds").contains(server)) {
            return true;
        } else {
            return false;
        }
    }

    public double getMoney(String server, OfflinePlayer player) {
        return plugin.economy.config.getDouble(player.getUniqueId().toString() + "." + server + ".Balance");
    }

    public void addMoney(String server, OfflinePlayer player, double amount) {
        plugin.economy.config.set(player.getUniqueId().toString() + "." + server + ".Balance", getMoney(server, player) + amount);
        setNameInConfig(player);
        plugin.economy.saveData();
    }

    public void removeMoney(String server, OfflinePlayer player, double amount) {
        double amountAfter = getMoney(server, player) - amount;
        if (amountAfter < 0) {
            resetPlayer(server, player);
            setNameInConfig(player);
            plugin.economy.saveData();
            return;
        }
        plugin.economy.config.set(player.getUniqueId().toString() + "." + server + ".Balance", getMoney(server, player) - amount);
        setNameInConfig(player);
        plugin.economy.saveData();
    }

    public boolean hasEnoughMoney(String server, OfflinePlayer player, double amount) {
        if (getMoney(server, player) >= amount) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isBountied(String server, OfflinePlayer player) {
        if (plugin.economy.config.getBoolean(player.getUniqueId().toString() + "." + server + ".Bountied")) {
            return true;
        } else {
            return false;
        }
    }

    public void setBounty(String server, OfflinePlayer executor, OfflinePlayer target, double amount) {
        removeMoney(server, executor, amount);
        if (!isBountied(server, target)) {
            removeBounty(server, target);
        }
        plugin.economy.config.set(target.getUniqueId().toString() + "." + server + ".Bountied", true);
        plugin.economy.config.set(target.getUniqueId().toString() + "." + server + ".Bounty.Amount", amount);
        plugin.economy.config.set(target.getUniqueId().toString() + "." + server + ".Bounty.Executor", executor.getUniqueId().toString());
        plugin.economy.saveData();
    }

    public void increaseBounty(String server, OfflinePlayer executor, OfflinePlayer target, double amount) {
        removeMoney(server, executor, amount);
        plugin.economy.config.set(target.getUniqueId().toString() + "." + server + ".Bounty.Amount", amount);
        plugin.economy.saveData();
    }

    public void removeBounty(String server, OfflinePlayer player) {
        plugin.economy.config.set(player.getUniqueId().toString() + "." + server + ".Bountied", false);
        plugin.economy.config.set(player.getUniqueId().toString() + "." + server + ".Bounty", null);
        plugin.economy.saveData();
    }

    public void claimBounty(String server, OfflinePlayer target, OfflinePlayer claimer, double amount) {
        addMoney(server, claimer, amount);
        removeBounty(server, target);
        plugin.economy.saveData();
    }

    public double getBountyAmount(String server, OfflinePlayer player) {
        return plugin.economy.config.getDouble(player.getUniqueId().toString() + "." + server + ".Bounty.Amount");
    }

    public String getBountyExecutor(String server, OfflinePlayer player) {
        return plugin.economy.config.getString(player.getUniqueId().toString() + "." + server + ".Bounty.Executor");
    }

    public boolean isOverMaximum(double amount) {
        double max = plugin.getConfig().getDouble("Economy.MaximumAmount");
        if (amount > max) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUnderPayMinimum(double amount) {
        double min = plugin.getConfig().getDouble("Pay.MinimumAmount");
        if (amount < min) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUnderBountyMinimum(double amount) {
        double min = plugin.getConfig().getDouble("Bounty.MinimumAmount");
        if (amount < min) {
            return true;
        } else {
            return false;
        }
    }

    public boolean bountyIsEnabled(String server) {
        if (plugin.getConfig().getStringList("Bounty.EnabledWorlds").contains(server)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean moneyPerKillEnabled(String server) {
        if (plugin.getConfig().getStringList("Economy.MoneyPerKill.EnabledWorlds").contains(server)) {
            return true;
        } else {
            return false;
        }
    }

    public double getMoneyPerKill() {
        double highest = plugin.getConfig().getDouble("Economy.MoneyPerKill.Highest");
        double lowest = plugin.getConfig().getDouble("Economy.MoneyPerKill.Lowest");

        double range = (highest - lowest) + 1;

        double outcome = (Math.random() * range) + lowest;

        return outcome;
    }
}
