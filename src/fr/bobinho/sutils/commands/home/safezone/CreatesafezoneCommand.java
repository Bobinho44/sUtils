package fr.bobinho.sutils.commands.home.safezone;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.bobinho.sutils.utils.safezone.sUtilsSafezoneManager;
import org.bukkit.ChatColor;
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
    @Syntax("/createsafezone <radius>")
    @CommandPermission("sutils.createsafezone")
    public void onDelhomeCommand(CommandSender commandSender, @Single int radius) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            //Checks if the radius is positive
            if (radius < 1) {
                sender.sendMessage(ChatColor.RED + "The specified radius is not valid!");
                return;
            }

            //Creates the safezone
            sUtilsSafezoneManager.createsUtilsSafezone(sender.getLocation().toCenterLocation(), radius);

            //Sends message
            sender.sendMessage(ChatColor.GREEN + "You have defined the safezone of the world " + sender.getWorld().getEnvironment().name() + ".");
        }
    }

}