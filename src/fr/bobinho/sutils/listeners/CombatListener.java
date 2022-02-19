package fr.bobinho.sutils.listeners;

import fr.bobinho.sutils.utils.combat.sUtilsCombatTagManager;
import fr.bobinho.sutils.utils.scheduler.sUtilsScheduler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

public class CombatListener implements Listener {

    /**
     * Listen when a player leave the server
     *
     * @param e the player quit event
     */
    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        if (!sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer())) {
            return;
        }

        //Kills the player
        e.getPlayer().setHealth(0);
        Player lastDamager = sUtilsCombatTagManager.getsUtilsPlayerCombatTag(e.getPlayer()).get().getLastDamager();

        //Checks if the player leave the game because someone attacks him
        if (lastDamager != null) {

            //Sends the death message
            Bukkit.getOnlinePlayers().forEach(player ->
                    player.sendMessage(ChatColor.RED + "[Combat Logger] " + e.getPlayer().getName() + " was slain by " + lastDamager.getName() + "."));
        }

        //Delete the player combat tag
        sUtilsCombatTagManager.deletesUtilsPlayerCombatTag(e.getPlayer());
    }

    /**
     * Listen when a player attack another player
     *
     * @param e the entity damage by entity event
     */
    @EventHandler
    public void onPlayerStartCombat(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) {
            return;
        }

        //Adds combat tag to the players
        sUtilsCombatTagManager.createsUtilsPlayerCombatTag((Player) e.getEntity(), (Player) e.getDamager());
        sUtilsCombatTagManager.createsUtilsPlayerCombatTag((Player) e.getDamager(), null);
    }

    /**
     * Listen when a player use a rocket with elytra during combat
     *
     * @param e the player interact event
     */
    @EventHandler
    public void onPlayerUseRocketWithElytra(PlayerInteractEvent e) {
        if (!sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer()) || e.getItem() == null || e.getItem().getType() != Material.FIREWORK_ROCKET) {
            return;
        }

        //Cancel the use of the rocket
        e.setCancelled(true);
        e.getPlayer().sendMessage(ChatColor.RED + "You can’t use this in combat!");
    }

    /**
     * Listen when a player use an ender pearl during combat
     *
     * @param e the player interact event
     */
    @EventHandler
    public void onPlayerUseEnderPearl(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().getType() == Material.ENDER_PEARL && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {

            if (!sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer())) {
                return;
            }

            //Checks if the player is already in the cooldown
            if (e.getPlayer().hasCooldown(Material.ENDER_PEARL)) {
                return;
            }

            //Adds cooldown
            sUtilsScheduler.syncScheduler().after(50, TimeUnit.MILLISECONDS).run(() ->
                    e.getPlayer().setCooldown(Material.ENDER_PEARL, 600));
        }
    }

    /**
     * Listen when a player take an ender pearl during combat
     *
     * @param e the player item held event
     */
    @EventHandler
    public void onPlayerTakeEnderPearl(PlayerItemHeldEvent e) {
        if (!sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer())) {
            return;
        }

        //Gets new item in player main
        ItemStack item = e.getPlayer().getInventory().getItem(e.getNewSlot());

        //Checks if the item is an ender pearl
        if (item == null || item.getType() != Material.ENDER_PEARL) {
            return;
        }

        //Sends cooldown remaining time
        e.getPlayer().sendActionBar(Component.text("Pearl Cooldown:", NamedTextColor.RED)
                .append(Component.text(e.getPlayer().getCooldown(Material.ENDER_PEARL), NamedTextColor.GRAY))
                .append(Component.text("s", NamedTextColor.GRAY)));
    }

}