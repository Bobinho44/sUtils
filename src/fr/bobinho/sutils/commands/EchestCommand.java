package fr.bobinho.sutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import fr.bobinho.sutils.utils.combat.sUtilsCombatTagManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("echest")
public class EchestCommand extends BaseCommand {

    /**
     * Command echest
     *
     * @param commandSender the sender
     */
    @Default
    @Syntax("/echest")
    @CommandPermission("sutils.echest")
    public void onEchestCommand(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            //Checks if the sender is in combat
            if (sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(sender)) {
                sender.sendMessage(ChatColor.RED + "You are in combat!" + ChatColor.GRAY + " You can't use this command!");
                return;
            }

            //Opens the ender chest
            sender.performCommand("openender " + sender.getName());
        }
    }


}
