package fr.bobinho.sutils.commands.spawn;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import fr.bobinho.sutils.sUtilsCore;
import fr.bobinho.sutils.utils.location.sUtilsLocationUtil;
import net.md_5.bungee.api.ChatColor;
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

            //Sets the spawn
            sUtilsCore.getMainSettings().getConfiguration().set("spawn." + sender.getWorld().getName(), sUtilsLocationUtil.getAsString(sender.getLocation()));
            sUtilsCore.getMainSettings().save();

            //Sends the message
            sender.sendMessage(ChatColor.GREEN + "You have defined the new " + sender.getWorld().getName() + " spawn.");
        }
    }

}