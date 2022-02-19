package fr.bobinho.sutils.commands.spawn;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import fr.bobinho.sutils.sUtilsCore;
import fr.bobinho.sutils.utils.location.sUtilsLocationUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("setspawn")
public class SetspawnCommand extends BaseCommand {

    /**
     * Command setspawn
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/setspawn")
    @CommandPermission("sutils.setspawn")
    public void onSetSpawnCommand(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            //Defines the new spawn
            sUtilsCore.getMainSettings().getConfiguration().set("spawn", sUtilsLocationUtil.getAsString(sender.getLocation()));
        }
    }

}