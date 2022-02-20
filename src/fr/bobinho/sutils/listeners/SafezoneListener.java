package fr.bobinho.sutils.listeners;

import fr.bobinho.sutils.utils.combat.sUtilsCombatTagManager;
import fr.bobinho.sutils.utils.safezone.sUtilsSafezoneManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SafezoneListener implements Listener {

    /**
     * Listen when a player leave or join a safezone
     *
     * @param e the player move event
     */
    @EventHandler
    public void onPlayerLeaveOrJoinSafezone(PlayerMoveEvent e) {
        String safezoneMessage = "";

        //Checks if player leave the safezone
        if (sUtilsSafezoneManager.isItInsUtilsSafezone(e.getFrom()) && !sUtilsSafezoneManager.isItInsUtilsSafezone(e.getTo())) {
            safezoneMessage = ChatColor.RED + "Safezone exited, watch out for enemies!";
        }

        //Checks if player join the safezone
        if (!sUtilsSafezoneManager.isItInsUtilsSafezone(e.getFrom()) && sUtilsSafezoneManager.isItInsUtilsSafezone(e.getTo())) {
            safezoneMessage = ChatColor.AQUA + "Safezone entered, you are safe!";
        }

        //Checks if player leave or join the safezone
        if (!safezoneMessage.equalsIgnoreCase("")) {
            if (sUtilsCombatTagManager.isItsUtilsPlayerCombatTag((e.getPlayer()))) {
                //e.getPlayer().setVelocity(e.getFrom().toVector().subtract(e.getTo().toVector()).normalize());
                e.setCancelled(true);
                return;
            }
            e.getPlayer().sendMessage(safezoneMessage);
        }
    }

    /**
     * Listen when a player leave or join a safezone with teleportation
     *
     * @param e the player teleport event
     */
    @EventHandler
    public void onPlayerTeleportAcrossSafezone(PlayerTeleportEvent e) {
        String safezoneMessage = "";

        //Checks if player leave the safezone
        if (sUtilsSafezoneManager.isItInsUtilsSafezone(e.getFrom()) && !sUtilsSafezoneManager.isItInsUtilsSafezone(e.getTo())) {
            safezoneMessage = ChatColor.RED + "Safezone exited, watch out for enemies!";
        }

        //Checks if player join the safezone
        if (!sUtilsSafezoneManager.isItInsUtilsSafezone(e.getFrom()) && sUtilsSafezoneManager.isItInsUtilsSafezone(e.getTo())) {
            safezoneMessage = ChatColor.AQUA + "Safezone entered, you are safe!";
        }

        //Checks if player leave or join the safezone
        if (!safezoneMessage.equalsIgnoreCase("")) {
            if (sUtilsCombatTagManager.isItsUtilsPlayerCombatTag((e.getPlayer()))) {
                e.setCancelled(true);
                return;
            }
            e.getPlayer().sendMessage(safezoneMessage);
        }
    }

    /**
     * Listen when a player launch an ender pearl in a safezone during combat
     *
     * @param e the projectile hit event
     */
    @EventHandler
    public void onPlayerShootInSafezone(ProjectileHitEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player) || !sUtilsCombatTagManager.isItsUtilsPlayerCombatTag((Player) e.getEntity().getShooter())) {
            return;
        }

        //Checks if the ender is launched in a safezone
        e.setCancelled(sUtilsSafezoneManager.isItInsUtilsSafezone(e.getEntity().getLocation()));
    }

}
