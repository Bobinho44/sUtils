package fr.bobinho.sutils.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionListener implements Listener {

    @EventHandler
    public void onCrystalExplode(EntityExplodeEvent e) {
        if (e.getEntityType() == EntityType.ENDER_CRYSTAL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerIsDamagedByCrystal(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.ENDER_CRYSTAL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMinecartTntExplode(EntityExplodeEvent e) {
        if (e.getEntityType() == EntityType.MINECART_TNT) {
            e.setCancelled(true);
        }
    }

    /**
     * Listen when a minecraft tnt damage a player
     *
     * @param e the entity damage by entity event
     */
    @EventHandler
    public void onPlayerIsDamagedByMinecartTnt(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof ExplosiveMinecart) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAnchorExplode(BlockExplodeEvent e) {
        if (e.getYield() == 0.2F) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerIsDamagedByAnchor(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            e.setCancelled(true);
        }
    }

}