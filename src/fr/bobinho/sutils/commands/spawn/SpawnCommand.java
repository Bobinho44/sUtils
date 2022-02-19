package fr.bobinho.sutils.commands.spawn;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import fr.bobinho.sutils.sUtilsCore;
import fr.bobinho.sutils.utils.location.sUtilsLocationUtil;
import fr.bobinho.sutils.utils.teleportation.sUtilsTeleportation;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("spawn")
public class SpawnCommand extends BaseCommand {

    /**
     * Command spawn
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/spawn")
    @CommandPermission("sutils.spawn")
    public void onSpawnCommand(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            //Gets spawn
            Location spawn = sUtilsLocationUtil.getAsLocation(sUtilsCore.getMainSettings().getConfiguration().getString("spawn", "world:0:1000:0:0:0"));

            //Teleport the player;
            sUtilsTeleportation.teleport(sender, spawn);
        }
    }

}