package fr.bobinho.sutils.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionListener implements Listener {

    @EventHandler
    public void onCrystalExplode(EntityExplodeEvent e) {
        Bukkit.getConsoleSender().sendMessage(e.getEntity().getName());
        e.setCancelled(true);
        e.getEntity().remove();
    }

    @EventHandler
    public void onAnchorExplode(BlockExplodeEvent e) {
        Bukkit.getConsoleSender().sendMessage(e.getBlock().getType().name());
        e.setCancelled(true);
        e.getBlock().setType(Material.AIR);
    }

}
