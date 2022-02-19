package fr.bobinho.sutils.listeners;

import fr.bobinho.sutils.sUtilsCore;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class NetheriteListener implements Listener {

    /**
     * Listen when a player interact with a smithing table
     *
     * @param e the player interact event
     */
    @EventHandler
    public void onInteractWithSmithingTable(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.SMITHING_TABLE) {
            return;
        }

        //Checks if the netherite system is enable
        e.setCancelled(sUtilsCore.getMainSettings().getConfiguration().getBoolean("enableNetherite"));
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
        ItemStack droppedItem = new ItemStack(Material.DIAMOND, miningTool == null ? 0 : getNumberOfDroppedDiamond(miningTool));
        e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), droppedItem);
    }

    /**
     * Gets the number of dropped diamond (fortune)
     *
     * @param item the mining tool
     * @return the number of dropped diamond
     */
    private int getNumberOfDroppedDiamond(@Nonnull ItemStack item) {
        Validate.notNull(item, "item is null");

        int fortune = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        for (int i = 3; i > 0; i--) {
            if (fortune > 0 && fortune >= i && Math.random() < 0.1 * (20 + (3 * i) * 5)) {
                return i + 1;
            }
        }
        return 1;
    }

}