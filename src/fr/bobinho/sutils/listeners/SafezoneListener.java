package fr.bobinho.sutils.listeners;

import fr.bobinho.sutils.utils.combat.sUtilsCombatTagManager;
import fr.bobinho.sutils.utils.safezone.sUtilsSafezoneManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
        if (sUtilsSafezoneManager.isItInsUtilsSafezone(e.getFrom()) && !sUtilsSafezoneManager.isItInsUtilsSafezone(e.getTo()) && e.getTo().getWorld().equals(e.getFrom().getWorld())) {
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
        if (sUtilsSafezoneManager.isItInsUtilsSafezone(e.getEntity().getLocation())) {
            e.setCancelled(true);
        }
    }

    /**
     * Listen when a player is damaged in a safezone
     *
     * @param e the entity damage event
     */
    @EventHandler
    public void onPlayerIsDamagedInSafezone(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && sUtilsSafezoneManager.isItInsUtilsSafezone(e.getEntity().getLocation())) {
            e.setCancelled(true);
        }
    }

    /**
     * Listen when a player loss hunger in a safezone
     *
     * @param e the food level change event
     */
    @EventHandler
    public void onPlayerLossHungerInSafezone(FoodLevelChangeEvent e) {
        if (e.getEntity().getFoodLevel() >= e.getFoodLevel() && sUtilsSafezoneManager.isItInsUtilsSafezone(e.getEntity().getLocation())) {
            e.setCancelled(true);
        }
    }

    /**
     * Listen when a player break a block in a safezone during combat
     *
     * @param e the projectile hit event
     */
    @EventHandler
    public void onPlayerBreakBlockInSafezone(BlockBreakEvent e) {
        if (sUtilsSafezoneManager.isItInsUtilsSafezone(e.getBlock().getLocation()) && e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            e.setCancelled(true);
        }
    }

    /**
     * Listen when a player break a block in a safezone during combat
     *
     * @param e the projectile hit event
     */
    @EventHandler
    public void onPlayerPlaceBlockInSafezone(BlockPlaceEvent e) {
        if (sUtilsSafezoneManager.isItInsUtilsSafezone(e.getBlock().getLocation()) && e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            e.setCancelled(true);
        }
    }

    /**
     * Listen when a player place a boat in a safezone
     *
     * @param e the player interact event
     */
    @EventHandler
    public void onPlayerBoatPlace(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().getType().name().contains("BOAT") && e.getAction() == Action.RIGHT_CLICK_BLOCK && sUtilsSafezoneManager.isItInsUtilsSafezone(e.getClickedBlock().getLocation())) {
            e.setCancelled(true);
        }
    }

}