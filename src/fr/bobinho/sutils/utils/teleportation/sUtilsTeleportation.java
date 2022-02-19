package fr.bobinho.sutils.utils.teleportation;

import fr.bobinho.sutils.utils.combat.sUtilsCombatTagManager;
import fr.bobinho.sutils.utils.scheduler.sUtilsScheduler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class sUtilsTeleportation {

    public static void teleport(@Nonnull Player player, @Nonnull Location location) {
        int teleportationCooldown = sUtilsTeleportationRules.getsUtilsTeleportationCooldown(player);

        //Checks if the player is in the end
        if (teleportationCooldown > 10) {
            player.sendMessage(ChatColor.DARK_PURPLE + "TPs are disabled in the End, you must use the exit portal!");
            return;
        }

        //Checks if the player is in combat
        else if (sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(player)) {
            player.sendMessage(ChatColor.RED + "you are in combat!" + ChatColor.GRAY + "Teleporting in 10 seconds.");
        }

        //Checks if there is another player around the player
        else if (teleportationCooldown == 10) {
            player.sendMessage(ChatColor.RED + "Enemies nearby!" + ChatColor.GRAY + "Teleporting in 10 seconds.");
        }

        //Adds teleportation cooldown
        sUtilsScheduler.syncScheduler().after(sUtilsTeleportationRules.getsUtilsTeleportationCooldown(player), TimeUnit.SECONDS).run(() -> {

            //Sends message
            player.sendMessage(ChatColor.GOLD + "Warping…");

            //Teleport the player
            player.teleport(location);
        });
    }
}
