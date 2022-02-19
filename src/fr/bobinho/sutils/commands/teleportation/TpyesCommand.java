package fr.bobinho.sutils.commands.teleportation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import fr.bobinho.sutils.utils.teleportation.sUtilsTeleportation;
import fr.bobinho.sutils.utils.teleportation.sUtilsTeleportationRequest;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("tpyes|tpaccept")
public class TpyesCommand extends BaseCommand {

    /**
     * Command tpyes
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/tpyes")
    @CommandPermission("sutils.tpyes")
    public void onSpawnCommand(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player receiver = (Player) commandSender;

            if (!sUtilsTeleportationRequest.isItsUtilsTeleportationRequest(receiver)) {
                receiver.sendMessage(ChatColor.RED + "You have not received any teleportation requests!");
                return;
            }

            Player sender = sUtilsTeleportationRequest.getsUtiPlayerPlayer(receiver).get().getValue();

            //Teleports the player
            sUtilsTeleportation.teleport(sender, receiver.getLocation());

            //Sends message
            sender.sendMessage(ChatColor.GREEN + receiver.getName() + " accepted your request for teleportation.");
            receiver.sendMessage(ChatColor.GREEN + "You have accepted the teleportation request from " + sender.getName() + ".");
        }
    }

}