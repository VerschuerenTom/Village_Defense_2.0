package me.TomTheDeveloper;

import com.sk89q.worldedit.bukkit.selections.Selection;
import me.TomTheDeveloper.Permmissions.PermStrings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tom on 16/08/2014.
 */
public class ChestCommand implements CommandExecutor {

    private YoutuberInvasion plugin;

    public ChestCommand(YoutuberInvasion plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))
            return true;

        Player player = (Player) commandSender;

        if (!command.getLabel().equalsIgnoreCase("setshopchest"))
            return true;
        if (!(player.isOp() || player.hasPermission(PermStrings.getEditGames())))
            return true;
        if (plugin.getGameAPI().getWorldEditPlugin().getSelection(player) == null)
            return true;
        Selection selection = plugin.getGameAPI().getWorldEditPlugin().getSelection(player);
        if ((selection.getMaximumPoint().getX() != selection.getMinimumPoint().getX() ||
                selection.getMaximumPoint().getY() != selection.getMaximumPoint().getY() ||
                selection.getMaximumPoint().getZ() != selection.getMinimumPoint().getZ()))
            return true;
        plugin.getGameAPI().saveLoc("shop.location", plugin.getGameAPI().getWorldEditPlugin().getSelection(player).getMinimumPoint());
        player.sendMessage(ChatColor.GREEN + "Shop for chest set!");
        return true;
    }
}
