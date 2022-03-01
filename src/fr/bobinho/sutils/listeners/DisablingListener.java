package fr.bobinho.sutils.listeners;

import fr.bobinho.sutils.sUtilsCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.stream.IntStream;

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

    @EventHandler
    public void onPlayerAddShulkerBoxInEnderChest(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory().getType() != InventoryType.ENDER_CHEST) {
            return;
        }

        if (IntStream.range(0, 27).filter(i -> isItShulkerBox(e.getInventory().getItem(i))).count() < sUtilsCore.getMainSettings().getConfiguration().getInt("enderchest")) {
            return;
        }

        if (e.getClickedInventory().getType() == InventoryType.ENDER_CHEST && isItShulkerBox(e.getCursor()) && e.getAction().name().contains("PLACE")) {
            e.setCancelled(true);
        }

        if (e.getClickedInventory().getType() == InventoryType.PLAYER && isItShulkerBox(e.getCurrentItem()) && e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            e.setCancelled(true);
        }

        if (e.getClickedInventory().getType() == InventoryType.ENDER_CHEST && e.getAction() == InventoryAction.HOTBAR_SWAP && !isItShulkerBox(e.getCursor())) {
            e.setCancelled(true);
        }

        if (e.isCancelled()) {
            e.getWhoClicked().sendMessage(ChatColor.RED + "You have already reached the limit of shulker boxes in your ender chest!");
        }
    }

    private boolean isItShulkerBox(ItemStack item) {
        return item != null && item.getType().name().contains("SHULKER_BOX");
    }

}