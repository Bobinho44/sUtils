package fr.bobinho.sutils.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DisablingListener implements Listener {

    /**
     * Listen when a totem of undying is dropped
     *
     * @param e the entity death event
     */
    @EventHandler
    public void onDropTotemOfUndying(EntityDeathEvent e) {
        if (e.getEntity() instanceof Evoker) {
            e.getDrops().removeIf(item -> item.getType() == Material.TOTEM_OF_UNDYING);
        }
    }

    /**
     * Listen when a minecraft tnt damage a player
     *
     * @param e the entity damage by entity event
     */
    @EventHandler
    public void onMinecraftTntExplode(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof ExplosiveMinecart) {
            e.setCancelled(true);
        }
    }

    /**
     * Listen when a player use a shield
     *
     * @param e the entity damage by entity event
     */
    @EventHandler
    public void onPlayerUseShield(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            //Checks if the player use a shield
            if (player.isBlocking()) {

                //Damage the player
                player.damage(e.getDamage());
                e.setCancelled(true);
            }
        }
    }

    /**
     * Listen when a player use a shield
     *
     * @param e the entity damage by entity event
     */
    @EventHandler
    public void onPlayerUseShield2(PlayerInteractEvent e) {
        if (e.getItem() == null || e.getItem().getType() != Material.SHIELD) {
            return;
        }

        //Adds cooldown
        e.getPlayer().setCooldown(Material.SHIELD, 99999999);
    }

}