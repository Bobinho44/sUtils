package fr.bobinho.sutils.commands.teleportation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import fr.bobinho.sutils.utils.teleportation.sUtilsTeleportationRequestManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("tpahere")
public class TpahereCommand extends BaseCommand {

    /**
     * Command tpahere
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/tpahere")
    @CommandPermission("sutils.tpahere")
    public void onTpahereCommand(CommandSender commandSender, @Single OnlinePlayer commandReceiver) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            Player receiver = commandReceiver.getPlayer();

            //Checks if sender request to himself
            if (sender.equals(receiver)) {
                sender.sendMessage(ChatColor.RED + "You cannot send a teleportation request to yourself!");
                return;
            }

            //Sends message
            sender.sendMessage(ChatColor.GREEN + "You have sent a request for " + receiver.getName() + " to teleport to you.");
            receiver.sendMessage(ChatColor.GREEN + sender.getName() + " has requested that you teleport to them. Type /tpaccept to accept this request.");

            //Sends teleportation request
            sUtilsTeleportationRequestManager.createsUtilsTeleportationRequest(sender, receiver, sender.getLocation().clone(), receiver);
        }
    }

}