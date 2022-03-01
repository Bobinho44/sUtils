package fr.bobinho.sutils.commands.safezone;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.RegionSelector;
import fr.bobinho.sutils.utils.safezone.sUtilsSafezoneManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("createsafezone")
public class CreatesafezoneCommand extends BaseCommand {

    /**
     * Command createsafezone
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/createsafezone <name>")
    @CommandPermission("sutils.createsafezone")
    public void onCreateSafezoneCommand(CommandSender commandSender, @Single String name) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            //Checks if the safezone exist
            if (sUtilsSafezoneManager.isItsUtilsSafezone(name)) {
                sender.sendMessage(ChatColor.RED + "The name " + name + " is already used!");
                return;
            }

            //Gets player selection
            LocalSession session = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(sender));
            RegionSelector selector = session.getRegionSelector(BukkitAdapter.adapt(sender.getWorld()));

            try {

                //Gets player selection points
                BlockVector3 maximumPoint = selector.getRegion().getMaximumPoint();
                BlockVector3 minimumPoint = selector.getRegion().getMinimumPoint();
                Location corner = new Location(sender.getWorld(), maximumPoint.getX() + 1, maximumPoint.getY(), maximumPoint.getZ() + 1);
                Location oppositeCorner = new Location(sender.getWorld(), minimumPoint.getX(), minimumPoint.getY(), minimumPoint.getZ());

                //Creates the safezone
                sUtilsSafezoneManager.createsUtilsSafezone(name, corner, oppositeCorner);

                //Sends message
                sender.sendMessage(ChatColor.GREEN + "You have created the safezone " + name + ".");

            } catch (IncompleteRegionException e) {

                //Sends no selection error message
                sender.sendMessage(ChatColor.RED + "You do not have a selected region!!");
            }
        }
    }

}