package fr.bobinho.sutils.commands.teleportation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import fr.bobinho.sutils.utils.teleportation.sUtilsTeleportationRequest;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("tpa")
public class TpaCommand extends BaseCommand {

    /**
     * Command tpa
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/tpa")
    @CommandPermission("sutils.tpa")
    public void onSpawnCommand(CommandSender commandSender, @Single OnlinePlayer commandReceiver) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            Player receiver = commandReceiver.getPlayer();

            //Checks if sender request to himself
            if (sender.equals(receiver)) {
                sender.sendMessage(ChatColor.RED + "You cannot send a teleportation request to yourself!");
                return;
            }

            //Sends message
            sender.sendMessage(ChatColor.GREEN + "You have sent a teleportation request to " + receiver.getName() + ".");
            receiver.sendMessage(ChatColor.GREEN + "You have received a teleportation request from " + sender.getName() + ".");

            //Sends teleportation request
            sUtilsTeleportationRequest.createsUtilsTeleportationRequest(sender, receiver);
        }
    }

}