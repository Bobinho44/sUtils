package fr.bobinho.sutils.commands.home;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.bobinho.sutils.utils.home.sUtilsHomeManager;
import fr.bobinho.sutils.utils.teleportation.sUtilsTeleportation;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("home|go")
public class HomeCommand extends BaseCommand {

    /**
     * Command home (information)
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/home")
    @CommandPermission("sutils.home")
    public void onHomeInformationCommand(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            //Sends the homes list
            sender.sendMessage(sUtilsHomeManager.getsUtilsHomesAsClickableString(sender));

        }
    }

    /**
     * Command home (teleportation)
     *
     * @param commandSender the sender
     */
    @Syntax("/home <name>")
    @CommandPermission("sutils.home")
    public void onHomeTeleportationCommand(CommandSender commandSender, @Single String homeName) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            //Checks if the home exist
            if (!sUtilsHomeManager.isItsUtilsHome(sender, homeName)) {
                sender.sendMessage(ChatColor.RED + "The home " + homeName + " doesn't exist!");
                return;
            }

            //Teleports the player
            sUtilsTeleportation.teleport(sender, sUtilsHomeManager.getsUtilsHome(sender, homeName).get().getLocation());
        }
    }

}