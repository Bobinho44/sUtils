package fr.bobinho.sutils.utils.combat;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class sUtilsCombatTag {

    /**
     * Fields
     */
    private final Player player;
    private Player lastDamager;
    private final Stopwatch duration;

    /**
     * Creates a new combat tag
     *
     * @param player the player
     */
    public sUtilsCombatTag(@Nonnull Player player, @Nullable Player lastDamager) {
        Validate.notNull(player, "player is null");

        this.player = player;
        this.lastDamager = lastDamager;
        this.duration = Stopwatch.createStarted();
    }

    /**
     * Gets the player
     *
     * @return the player
     */
    @Nonnull
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the last player damager
     *
     * @return the last player damager
     */
    @Nullable
    public Player getLastDamager() {
        return lastDamager;
    }

    /**
     * Gets the duration
     *
     * @return the duration
     */
    @Nonnull
    public Stopwatch getDuration() {
        return duration;
    }

    /**
     * Sets the last player damager
     *
     * @param lastDamager the last player damager
     */
    public void setLastDamager(Player lastDamager) {
        this.lastDamager = lastDamager;
    }

    /**
     * Resets the duration
     */
    public void resetDuration() {
        getDuration().reset();
        getDuration().start();
    }

}