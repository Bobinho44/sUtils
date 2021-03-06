package fr.bobinho.sutils.utils.teleportation;

import fr.bobinho.steams.utils.team.Team;
import fr.bobinho.steams.utils.team.TeamManager;
import fr.bobinho.sutils.utils.safezone.sUtilsSafezoneManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Optional;

public class sUtilsTeleportationRules {

    public static int getsUtilsTeleportationCooldown(@Nonnull Player player) {

        //Checks if player is in the end world
        if (player.getWorld().getEnvironment() == World.Environment.THE_END)  {
            return 11;
        }

        //Checks if there is another player around the player
        return Bukkit.getOnlinePlayers().stream().anyMatch(onlinePlayer -> {
                    Optional<Team> team1 = TeamManager.getTeam(player.getUniqueId());
                    Optional<Team> team2 = TeamManager.getTeam(onlinePlayer.getUniqueId());

                    return ((team2.isEmpty() && !onlinePlayer.equals(player)) || (team1.isEmpty() && !onlinePlayer.equals(player)) || (team1.isPresent() && team2.isPresent() && !team1.get().equals(team2.get()) && !TeamManager.areAllied(team1.get(), team2.get()))) &&
                            onlinePlayer.getLocation().getWorld().equals(player.getWorld()) &&
                            onlinePlayer.getLocation().distance(player.getLocation()) < 100 &&
                            onlinePlayer.getGameMode() == GameMode.SURVIVAL &&
                            player.getGameMode() == GameMode.SURVIVAL &&
                            !sUtilsSafezoneManager.isItInsUtilsSafezone(player.getLocation());
                }) ? 10 : 0;
    }

}