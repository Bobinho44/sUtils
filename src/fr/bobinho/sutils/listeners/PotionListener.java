package fr.bobinho.sutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PotionListener implements Listener {

    /**
     * Listen when a lingering splash potion is launcher
     *
     * @param e the lingering potion splash event
     */
    @EventHandler
    public void onSplashLingeringPotion(LingeringPotionSplashEvent e) {

        //Checks if disable potion is used
        for (PotionEffect potion : e.getEntity().getEffects()) {
            if (potion.getType() == PotionEffectType.SLOW_FALLING || (potion.getType().getName().equalsIgnoreCase("slow") && potion.getAmplifier() >= 3)) {
                e.setCancelled(true);
                break;
            }
        }

        //Sends the message to the player
        if (e.getEntity().getShooter() instanceof Player && e.isCancelled()) {
            ((Player) e.getEntity().getShooter()).sendMessage(ChatColor.RED + "You can't use this potion!");
        }
    }

    /**
     * Listen when a splash potion is launched
     *
     * @param e the potion splash event
     */
    @EventHandler
    public void onSplashPotion(PotionSplashEvent e) {

        //Checks if disable potion is used
        for (PotionEffect potion : e.getEntity().getEffects()) {
            if (potion.getType() == PotionEffectType.SLOW_FALLING || (potion.getType().getName().equalsIgnoreCase("slow") && potion.getAmplifier() >= 3)) {
                e.setCancelled(true);
                break;
            }
        }

        //Sends the message to the player
        if (e.getEntity().getShooter() instanceof Player && e.isCancelled()) {
            ((Player) e.getEntity().getShooter()).sendMessage(ChatColor.RED + "You can't use this potion!");
        }
    }

    /**
     * Listen when a player consume a potion
     *
     * @param e the player item consume event
     */
    @EventHandler
    public void onConsumePotion(PlayerItemConsumeEvent e) {
        if (!(e.getItem().getItemMeta() instanceof PotionMeta)) {
            return;
        }

        //Checks if disable potion is used
        PotionType potionType = ((PotionMeta) e.getItem().getItemMeta()).getBasePotionData().getType();
        boolean upgrade = ((PotionMeta) e.getItem().getItemMeta()).getBasePotionData().isUpgraded();
        e.setCancelled(potionType == PotionType.SLOW_FALLING || potionType == PotionType.TURTLE_MASTER || (potionType == PotionType.SLOWNESS && upgrade));

        //Sends the message to the player
        if (e.isCancelled()) {
            e.getPlayer().sendMessage(ChatColor.RED + "You can't use this potion!");
        }
    }

    /**
     * Listen when a potion arrow is launched
     *
     * @param e the projectile launch event
     */
    @EventHandler
    public void onShootPotionArrow(ProjectileLaunchEvent e) {
        if (e.getEntity().getType() != EntityType.ARROW) {
            return;
        }

        //Checks if disable potion is used
        PotionType potionType = ((Arrow) e.getEntity()).getBasePotionData().getType();
        boolean upgrade = ((Arrow) e.getEntity()).getBasePotionData().isUpgraded();
        e.setCancelled(potionType == PotionType.SLOW_FALLING || potionType == PotionType.TURTLE_MASTER || (potionType == PotionType.SLOWNESS && upgrade));

        //Sends the message to the player
        if (e.getEntity().getShooter() instanceof Player && e.isCancelled()) {
            ((Player) e.getEntity().getShooter()).sendMessage(ChatColor.RED + "You can't use this potion!");
        }
    }

    /**
     * Listen when an entity is affected by a potion
     *
     * @param e the entity potion effect event
     */
    @EventHandler
    public void onAffectByPotion(EntityPotionEffectEvent e) {
        if (e.getNewEffect() == null) {
            return;
        }

        //Checks if disable potion is used
        PotionEffectType potionType = e.getNewEffect().getType();
        int upgrade = e.getNewEffect().getAmplifier();
        e.setCancelled(potionType == PotionEffectType.SLOW_FALLING || (potionType == PotionEffectType.SLOW && upgrade >= 3));

        //Sends the message to the player
        if (e.getEntity() instanceof Player && e.isCancelled()) {
            e.getEntity().sendMessage(ChatColor.RED + "You can't use this potion!");
        }
    }

}