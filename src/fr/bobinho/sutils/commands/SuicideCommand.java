package fr.bobinho.sutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("suicide")
public class SuicideCommand extends BaseCommand {

    /**
     * Command suicide
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/suicide")
    @CommandPermission("sutils.suicide")
    public void onSuicideCommand(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            sender.setHealth(0);

            sender.sendMessage(ChatColor.RED + "You've killed yourself!");
        }
    }

}