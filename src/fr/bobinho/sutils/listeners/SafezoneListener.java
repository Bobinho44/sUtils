package fr.bobinho.sutils.listeners;

import fr.bobinho.sutils.utils.combat.sUtilsCombatTagManager;
import fr.bobinho.sutils.utils.safezone.sUtilsSafezoneManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SafezoneListener implements Listener {

    /**
     * Listen when a player leave or join a safezone
     *
     * @param e the player move event
     */
    @EventHandler
    public void onPlayerLeaveOrJoinSafezone(PlayerMoveEvent e) {

        if (sUtilsSafezoneManager.isItInsUtilsSafezone(e.getFrom()) && !sUtilsSafezoneManager.isItInsUtilsSafezone(e.getTo())) {
            e.getPlayer().sendMessage(ChatColor.RED + "Safezone exited, watch out for enemies!");
        }

        if (!sUtilsSafezoneManager.isItInsUtilsSafezone(e.getFrom()) && sUtilsSafezoneManager.isItInsUtilsSafezone(e.getTo())) {
            e.getPlayer().sendMessage(ChatColor.AQUA + "Safezone entered, you are safe!");
        }
    }

    /**
     * Listen when a player launch an ender pearl in a safezone during combat
     *
     * @param e the projectile hit event
     */
    @EventHandler
    public void onPlayerShootInSafezone(ProjectileHitEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player) ||!sUtilsCombatTagManager.isItsUtilsPlayerCombatTag((Player) e.getEntity().getShooter())) {
            return;
        }

        //Checks if the ender is launched in a safezone
        e.setCancelled(sUtilsSafezoneManager.isItInsUtilsSafezone(e.getEntity().getLocation()));
    }

}
