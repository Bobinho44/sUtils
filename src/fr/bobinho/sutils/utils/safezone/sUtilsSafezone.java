package fr.bobinho.sutils.utils.safezone;

import fr.bobinho.sutils.utils.scheduler.sUtilsScheduler;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class sUtilsSafezone {

    /**
     * Fields
     */
    private final String name;
    private final Location corner;
    private final Location oppositeCorner;
    private final List<Player> viewers = new ArrayList<>();

    /**
     * Creates a new safezone
     *
     * @param name           the name
     * @param corner         the corner
     * @param oppositeCorner the opposite corner
     */
    public sUtilsSafezone(@Nonnull String name, @Nonnull Location corner, @Nonnull Location oppositeCorner) {
        Validate.notNull(name, "name is null");
        Validate.notNull(corner, "corner is null");
        Validate.notNull(oppositeCorner, "oppositeCorner is null");

        this.name = name;
        this.corner = corner;
        this.oppositeCorner = oppositeCorner;

        sUtilsScheduler.asyncScheduler().every(1, TimeUnit.SECONDS).run(() -> {

            if (!sUtilsSafezoneManager.isItsUtilsSafezone(getName())) {
                return;
            }

            //Shows safezone particles
            new ArrayList<>(getViewers()).forEach(this::show);
        });
    }

    /**
     * Gets the safezone name
     *
     * @return the safezone name
     */
    @Nonnull
    public String getName() {
        return name;
    }

    /**
     * Gets the safezone world
     *
     * @return the safezone world
     */
    @Nonnull
    public World getWorld() {
        return getCorner().getWorld();
    }

    /**
     * Gets the safezone corner
     *
     * @return the safezone corner
     */
    @Nonnull
    public Location getCorner() {
        return corner;
    }

    /**
     * Gets the safezone opposite corner
     *
     * @return the safezone opposite corner
     */
    @Nonnull
    public Location getOppositeCorner() {
        return oppositeCorner;
    }

    /**
     * Gets all viewers
     *
     * @return all viewers
     */
    @Nonnull
    public List<Player> getViewers() {
        return viewers;
    }

    /**
     * Adds a viewer
     *
     * @param player the viewer
     */
    public void addViewer(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        getViewers().add(player);
    }

    /**
     * Removes a viewer
     *
     * @param player the viewer
     */
    public void removeViewer(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        getViewers().remove(player);
    }

    /**
     * Shows the safezone particles
     *
     * @param player the viewer
     */
    public void show(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        //Shows safezone segments
        showSafezoneSegment(player, getOppositeCorner().getX() - getCorner().getX(), getCorner().getX(), getCorner().getZ(), true);
        showSafezoneSegment(player, getOppositeCorner().getZ() - getCorner().getZ(), getOppositeCorner().getX(), getCorner().getZ(), false);
        showSafezoneSegment(player, getCorner().getX() - getOppositeCorner().getX(), getOppositeCorner().getX(), getOppositeCorner().getZ(), true);
        showSafezoneSegment(player, getCorner().getZ() - getOppositeCorner().getZ(), getCorner().getX(), getOppositeCorner().getZ(), false);
    }

    /**
     * Shows a safezone segment particles
     *
     * @param player    the viewer
     * @param length    the segment lenght
     * @param x         the initial x coordinates
     * @param z         the initial z coordinates
     * @param isChangeX if the x value is incremented
     */
    private void showSafezoneSegment(@Nonnull Player player, double length, double x, double z, boolean isChangeX) {
        Validate.notNull(player, "player is null");

        //Loops safezone height
        for (int i = 0; i < 256; i++) {
            int increment = (int) ((length) / Math.abs(length));

            //Loops safezone side
            for (int j = 0; (length > 0 && j < (int) length) || (length < 0 && j > (int) length); j += increment) {
                player.spawnParticle(Particle.VILLAGER_HAPPY, new Location(getWorld(), x + (isChangeX ? j : 0), i, z + (isChangeX ? 0 : j)), 1, 0, 0, 0, 0);
            }
        }
    }
}