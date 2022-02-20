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
     * Command home
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/home")
    @CommandPermission("sutils.home")
    public void onHomeInformationCommand(CommandSender commandSender, @Optional String homeName) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            if (homeName == null) {

                //Sends the homes list
                sender.sendMessage(sUtilsHomeManager.getsUtilsHomesAsClickableString(sender));
            }

            else {

                //Checks if the home exist
                if (!sUtilsHomeManager.isItsUtilsHome(sender, homeName)) {
                    sender.sendMessage(ChatColor.RED + "The home " + homeName + " doesn't exist!");
                    return;
                }

                //Sends message
                sender.sendMessage(ChatColor.GREEN + "Teleportation to the home " + homeName + ".");

                //Teleports the player
                sUtilsTeleportation.teleport(sender, sUtilsHomeManager.getsUtilsHome(sender, homeName).get().getLocation());
            }

        }
    }

}