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
        e.setCancelled(e.getEntityType() == EntityType.ENDER_CRYSTAL);
    }

    @EventHandler
    public void onPlayerIsDamagedByCrystal(EntityDamageByEntityEvent e) {
        e.setCancelled(e.getDamager().getType() == EntityType.ENDER_CRYSTAL);
    }

    @EventHandler
    public void onMinecartTntExplode(EntityExplodeEvent e) {
        e.setCancelled(e.getEntityType() == EntityType.MINECART_TNT);
    }

    /**
     * Listen when a minecraft tnt damage a player
     *
     * @param e the entity damage by entity event
     */
    @EventHandler
    public void onPlayerIsDamagedByMinecartTnt(EntityDamageByEntityEvent e) {
        e.setCancelled(e.getDamager() instanceof ExplosiveMinecart);
    }

    @EventHandler
    public void onAnchorExplode(BlockExplodeEvent e) {
        e.setCancelled(e.getYield() == 0.2F);
    }

    @EventHandler
    public void onPlayerIsDamagedByAnchor(EntityDamageEvent e) {
        e.setCancelled(e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION);
    }

}