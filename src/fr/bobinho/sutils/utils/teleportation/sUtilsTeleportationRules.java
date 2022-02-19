package fr.bobinho.sutils.utils.teleportation;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class sUtilsTeleportationRules {

    public static int getsUtilsTeleportationCooldown(@Nonnull Player player) {

        //Checks if player is in the end world
        if (player.getWorld().getEnvironment() == World.Environment.THE_END)  {
            return 11;
        }

        //Checks if there is another player around the player
        return Bukkit.getOnlinePlayers().stream().
                anyMatch(onlinePlayer -> !onlinePlayer.equals(player) && onlinePlayer.getLocation().distance(player.getLocation()) < 100) ? 0 : 10;
    }

}