package fr.bobinho.sutils.utils.teleportation;

import fr.bobinho.steams.utils.team.Team;
import fr.bobinho.steams.utils.team.TeamManager;
import org.bukkit.Bukkit;
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

                    return team1.isPresent() && team2.isPresent() && !team1.get().equals(team2.get())
                            && !TeamManager.areAllied(team1.get(), team2.get()) && onlinePlayer.getLocation().distance(player.getLocation()) < 100;
                }) ? 10 : 0;
    }

}