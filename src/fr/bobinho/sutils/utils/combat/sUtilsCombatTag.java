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
    private String lastDamagerName;
    private final Stopwatch duration;

    /**
     * Creates a new combat tag
     *
     * @param player the player
     */
    public sUtilsCombatTag(@Nonnull Player player, @Nullable String lastDamagerName) {
        Validate.notNull(player, "player is null");
        Validate.notNull(lastDamagerName, "lastDamagerName is null");

        this.player = player;
        this.lastDamagerName = lastDamagerName;
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
     * Gets the last damager name
     *
     * @return the last damager name
     */
    @Nullable
    public String getLastDamagerName() {
        return lastDamagerName;
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
     * Sets the last damager name
     *
     * @param lastDamagerName the last damager name
     */
    public void setLastDamagerName(String lastDamagerName) {
        this.lastDamagerName = lastDamagerName;
    }

    /**
     * Resets the duration
     */
    public void resetDuration() {
        getDuration().reset();
        getDuration().start();
    }

}