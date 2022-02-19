package fr.bobinho.sutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import com.lishid.openinv.util.InventoryAccess;
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

            //Opens the ender chest
            InventoryAccess.getEnderChest(sender.getEnderChest());
        }
    }


}
