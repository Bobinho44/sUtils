package fr.bobinho.sutils.listeners;

import fr.bobinho.steams.utils.team.TeamManager;
import fr.bobinho.sutils.utils.combat.sUtilsCombatTagManager;
import fr.bobinho.sutils.utils.safezone.sUtilsSafezoneManager;
import fr.bobinho.sutils.utils.scheduler.sUtilsScheduler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CombatListener implements Listener {

    /**
     * Listen when a player leave the server
     *
     * @param e the player quit event
     */
    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        if (!sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer()) || Bukkit.getServer().isStopping()) {
            return;
        }

        //Kills the player
        e.getPlayer().damage(9999D);
    }

    @EventHandler
    public void onDeathBeforeDisconnect(PlayerDeathEvent e) {

        if (sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer())) {
            Optional.ofNullable(Bukkit.getPlayer(sUtilsCombatTagManager.getsUtilsPlayerCombatTag(e.getPlayer()).get().getLastDamagerName()))
                    .ifPresent(sUtilsCombatTagManager::deletesUtilsPlayerCombatTag);
        }

        //Checks if the player leave the game because someone attacks him
        if ((e.getPlayer().getLastDamageCause() != null && e.getPlayer().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CUSTOM) || e.getPlayer().getLastDamageCause().getDamage() == 999D) {

            String lastDamagerName = sUtilsCombatTagManager.getsUtilsPlayerCombatTag(e.getPlayer()).get().getLastDamagerName();

            //Sends the death message
            e.deathMessage(Component.text("[Combat Logger] " + e.getPlayer().getName() + " was slain by " + lastDamagerName + ".", NamedTextColor.RED));
        }

        //Checks if player was in combat
        if (sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer())) {

            //Delete the player combat tag
            sUtilsCombatTagManager.deletesUtilsPlayerCombatTag(e.getPlayer());
        }
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

        if (sUtilsSafezoneManager.isItInsUtilsSafezone(e.getEntity().getLocation()) || sUtilsSafezoneManager.isItInsUtilsSafezone(e.getDamager().getLocation())) {
            e.setCancelled(true);
            return;
        }

        //Adds combat tag to the players
        if (!e.isCancelled()) {
            sUtilsCombatTagManager.createsUtilsPlayerCombatTag((Player) e.getEntity(), (Player) e.getDamager());
            sUtilsCombatTagManager.createsUtilsPlayerCombatTag((Player) e.getDamager(), (Player) e.getEntity());
        }
    }

    @EventHandler
    public void onPlayerAttackPlayerWithProjectil(ProjectileHitEvent e) {
        if (!(e.getHitEntity() instanceof Player) || !(e.getEntity().getShooter() instanceof Player)) {
            return;
        }

        if (sUtilsSafezoneManager.isItInsUtilsSafezone(e.getHitEntity().getLocation()) || sUtilsSafezoneManager.isItInsUtilsSafezone(((Player) e.getEntity().getShooter()).getLocation())) {
            e.setCancelled(true);
            return;
        }

        //Adds combat tag to the players
        if (!e.isCancelled()) {
            sUtilsCombatTagManager.createsUtilsPlayerCombatTag((Player) e.getHitEntity(), ((Player) e.getEntity().getShooter()));
            sUtilsCombatTagManager.createsUtilsPlayerCombatTag(((Player) e.getEntity().getShooter()), (Player) e.getHitEntity());
        }
    }

    /**
     * Listen when a player use a rocket with elytra during combat
     *
     * @param e the player interact event
     */
    @EventHandler
    public void onPlayerUseRocketWithElytra(PlayerInteractEvent e) {
        if (!e.getPlayer().isGliding() || !sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer()) || e.getItem() == null || e.getItem().getType() != Material.FIREWORK_ROCKET || (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        //Cancel the use of the rocket
        e.setCancelled(true);
        e.getPlayer().sendMessage(ChatColor.RED + "You can’t use this in combat!");
    }

    /**
     * Listen when a player use a rocket with elytra during combat
     *
     * @param e the player interact event
     */
    @EventHandler
    public void onPlayerUseCobwebInCombat(PlayerInteractEvent e) {
        if (!sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer()) || e.getItem() == null || e.getItem().getType() != Material.COBWEB || (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)) {
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

            //Checks if the player is already in the cooldown
            if (e.getPlayer().hasCooldown(Material.ENDER_PEARL) || !sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer())) {
                return;
            }

            //Adds cooldown
            sUtilsScheduler.syncScheduler().after(50, TimeUnit.MILLISECONDS).run(() ->
                    e.getPlayer().setCooldown(Material.ENDER_PEARL, 300));
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
        e.getPlayer().sendActionBar(Component.text("Pearl Cooldown: ", NamedTextColor.RED)
                .append(Component.text(e.getPlayer().getCooldown(Material.ENDER_PEARL) / 20, NamedTextColor.GRAY))
                .append(Component.text("s", NamedTextColor.GRAY)));
    }

    /**
     * Listen when a player use the ender portal to go to the overworld
     *
     * @param e the player teleport event
     */
    @EventHandler
    public void onUseEndPortal(PlayerTeleportEvent e) {
        if (e.getFrom().getWorld().getEnvironment() == World.Environment.THE_END && e.getTo().getWorld().getEnvironment() != World.Environment.THE_END) {

            //Removes the combat tag
            if (sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer())) {
                sUtilsCombatTagManager.deletesUtilsPlayerCombatTag(e.getPlayer());
            }

        }
    }

    @EventHandler
    public void onWalk(PlayerMoveEvent e) {
        if (e.hasChangedBlock() && sUtilsCombatTagManager.isItsUtilsPlayerCombatTag(e.getPlayer())) {
            sUtilsSafezoneManager.showsUtilsSafezones(e.getPlayer());
        }
    }
}