package fr.bobinho.sutils.commands.home;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.bobinho.sutils.utils.home.sUtilsHomeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("delhome")
public class DelhomeCommand extends BaseCommand {

    /**
     * Command delhome
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/delhome <name>")
    @CommandPermission("sutils.delhome")
    public void onDelhomeCommand(CommandSender commandSender, @Single String homeName) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            //Checks if the home exist
            if (!sUtilsHomeManager.isItsUtilsHome(sender, homeName)) {
                sender.sendMessage(ChatColor.RED + "The home " + homeName + " doesn't exist!");
                return;
            }

            //Deletes the home
            sUtilsHomeManager.deletesUtilsHome(sender, homeName);

            //Sends message
            sender.sendMessage(ChatColor.GREEN + "You have deleted the home " + homeName + ".");
        }
    }

}