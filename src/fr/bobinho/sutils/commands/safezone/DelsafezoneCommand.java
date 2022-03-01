package fr.bobinho.sutils.commands.safezone;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.bobinho.sutils.utils.safezone.sUtilsSafezoneManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("delsafezone")
public class DelsafezoneCommand extends BaseCommand {

    /**
     * Command delsafezone
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/delsafezone <name>")
    @CommandPermission("sutils.delsafezone")
    public void onDelsafezoneCommand(CommandSender commandSender, @Single String name) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            //Checks if the safezone exist
            if (!sUtilsSafezoneManager.isItsUtilsSafezone(name)) {
                sender.sendMessage(ChatColor.RED + "The safezone " + name + " doesn't exist!");
                return;
            }

            //Deletes the safezone
            sUtilsSafezoneManager.deleteUtilsSafezone(name);

            //Sends message
            sender.sendMessage(ChatColor.GREEN + "You have deleted the safezone " + name + ".");
        }
    }

}