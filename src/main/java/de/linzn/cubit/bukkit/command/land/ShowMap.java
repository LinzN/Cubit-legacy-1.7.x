package de.linzn.cubit.bukkit.command.land;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by spatium on 20.06.17.
 */
public class ShowMap implements ICommand {

    private CubitBukkitPlugin plugin;

    public ShowMap(CubitBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean runCmd(Command cmd, CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "You need to be a Player!");
        } else {
            Player player = (Player) sender;
            if (!player.hasPermission("landlord.player.map")) {
                player.sendMessage(ChatColor.RED + "You dont have the permissions");
                return true;
            }
            try {
                plugin.getMapManager().toggleMap(player);
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage(ChatColor.RED + "No map available");
            }

        }


        return true;
    }
}
