package net.vectromc.vbasic.commands.staff;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleStaffAlertsCommand implements CommandExecutor {

    private vBasic plugin;

    public ToggleStaffAlertsCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer"));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("StaffAlertsPermission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission"));
            } else {
                Player player = (Player) sender;
                if (plugin.toggle_staff_alerts.contains(player.getUniqueId())) {
                    plugin.toggle_staff_alerts.remove(player.getUniqueId());
                    Utils.sendMessage(player, plugin.getConfig().getString("StaffAlerts.ToggleOffMessage"));
                } else {
                    plugin.toggle_staff_alerts.add(player.getUniqueId());
                    Utils.sendMessage(player, plugin.getConfig().getString("StaffAlerts.ToggleOnMessage"));
                }
            }
        }
        return true;
    }
}
