package fr.bobinho.sutils.listeners;

import fr.bobinho.sutils.sUtilsCore;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class NetheriteListener implements Listener {

    /**
     * Listen when a player interact with a smithing table
     *
     * @param e the player interact event
     */
    @EventHandler
    public void onInteractWithSmithingTable(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.SMITHING_TABLE || e.getAction().name().contains("LEFT")) {
            return;
        }

        //Checks if the netherite system is enable
        if (!sUtilsCore.getMainSettings().getConfiguration().getBoolean("enableNetherite")) {
            e.setCancelled(true);
        }
    }

    /**
     * Listen when a player mine an ancient debris
     *
     * @param e the block break event
     */
    @EventHandler
    public void onMineAncientDebris(BlockBreakEvent e) {
        if (e.getBlock().getType() != Material.ANCIENT_DEBRIS || sUtilsCore.getMainSettings().getConfiguration().getBoolean("enableNetherite")) {
            return;
        }

        //Drops diamond (with fortune system)
        e.setDropItems(false);
        ItemStack miningTool = e.getPlayer().getInventory().getItemInMainHand();
        ItemStack droppedItem = new ItemStack(Material.DIAMOND, new Random().nextInt(3) + 1);
        e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), droppedItem);
    }


}