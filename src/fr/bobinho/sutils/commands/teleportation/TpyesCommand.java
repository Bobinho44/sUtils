package fr.bobinho.sutils.commands.teleportation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import fr.bobinho.sutils.utils.teleportation.sUtilsTeleportation;
import fr.bobinho.sutils.utils.teleportation.sUtilsTeleportationRequestManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

            if (!sUtilsTeleportationRequestManager.isItsUtilsTeleportationRequest(receiver)) {
                receiver.sendMessage(ChatColor.RED + "You have not received any teleportation requests!");
                return;
            }

            Player sender = Bukkit.getPlayer(sUtilsTeleportationRequestManager.getsUtiPlayerPlayer(receiver).get().getValue().getSender());

            if (sender == null) {
                receiver.sendMessage(ChatColor.RED + "This player is no longer connected!");
                return;
            }

            Location location = sUtilsTeleportationRequestManager.getsUtiPlayerPlayer(receiver).get().getValue().getLocation();
            Player teleported = sUtilsTeleportationRequestManager.getsUtiPlayerPlayer(receiver).get().getValue().getTeleported();

            sUtilsTeleportationRequestManager.deleteUtilsTeleportationRequest(receiver);

            //Sends message
            sender.sendMessage(ChatColor.GREEN + receiver.getName() + " accepted your request for teleportation.");
            receiver.sendMessage(ChatColor.GREEN + "You have accepted the teleportation request from " + sender.getName() + ".");

            //Teleports the player
            sUtilsTeleportation.teleport(teleported, location);
        }
    }

}