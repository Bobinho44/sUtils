package fr.bobinho.sutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("rules")
public class RulesCommand extends BaseCommand {

    /**
     * Command rules
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/rules")
    @CommandPermission("sutils.rules")
    public void onRulesCommand(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            sender.sendMessage(ChatColor.GOLD + "Be sure to read over our rules at: &eluxepvp.net/rules");
        }
    }

}