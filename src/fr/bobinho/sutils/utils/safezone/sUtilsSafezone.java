package fr.bobinho.sutils.utils.safezone;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class sUtilsSafezone {

    /**
     * Fields
     */
    private final String name;
    private final Location corner;
    private final Location oppositeCorner;

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
     * Shows the safezone
     *
     * @param player the viewer
     */
    public void show(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        //Shows safezone segments
        /*showSafezoneSegment(player, getOppositeCorner().getX() - getCorner().getX(), getCorner().getX(), getCorner().getZ(), true);
        showSafezoneSegment(player, getOppositeCorner().getZ() - getCorner().getZ(), getOppositeCorner().getX(), getCorner().getZ(), false);
        showSafezoneSegment(player, getCorner().getX() - getOppositeCorner().getX(), getOppositeCorner().getX(), getOppositeCorner().getZ(), true);
        showSafezoneSegment(player, getCorner().getZ() - getOppositeCorner().getZ(), getCorner().getX(), getOppositeCorner().getZ(), false);
    */
    }


    /**
     * Shows a safezone segment
     *
     * @param player    the viewer
     * @param length    the segment length
     * @param x         the initial x coordinates
     * @param z         the initial z coordinates
     * @param isChangeX if the x value is incremented
     */
    private void showSafezoneSegment(@Nonnull Player player, double length, double x, double z, boolean isChangeX) {
        Validate.notNull(player, "player is null");

        //Loops safezone height
        for (int i = 0; i < 5; i++) {
            int increment = (int) ((length) / Math.abs(length));

            //Loops safezone side
            for (int j = 0; (length > 0 && j < (int) length) || (length < 0 && j > (int) length); j += increment) {
                Location location = new Location(getWorld(), x + (isChangeX ? j : 0), i, z + (isChangeX ? 0 : j));

                if (location.getWorld().equals(player.getWorld()) && location.distance(player.getLocation()) < 10) {
                    break;
                }

                location.setY(player.getLocation().getX() + i);

                if (location.getBlock().isPassable()) {
                    player.sendBlockChange(location, Material.LIGHT_BLUE_STAINED_GLASS.createBlockData());
                }
            }
        }
    }

    /**
     * Hides the safezone
     *
     * @param player the viewer
     */
    public void hide(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        //Hides safezone segments
        /*hideSafezoneSegment(player, getOppositeCorner().getX() - getCorner().getX(), getCorner().getX(), getCorner().getZ(), true);
        hideSafezoneSegment(player, getOppositeCorner().getZ() - getCorner().getZ(), getOppositeCorner().getX(), getCorner().getZ(), false);
        hideSafezoneSegment(player, getCorner().getX() - getOppositeCorner().getX(), getOppositeCorner().getX(), getOppositeCorner().getZ(), true);
        hideSafezoneSegment(player, getCorner().getZ() - getOppositeCorner().getZ(), getCorner().getX(), getOppositeCorner().getZ(), false);
         */
    }

    /**
     * Shows a safezone segment
     *
     * @param player    the viewer
     * @param length    the segment length
     * @param x         the initial x coordinates
     * @param z         the initial z coordinates
     * @param isChangeX if the x value is incremented
     */
    private void hideSafezoneSegment(@Nonnull Player player, double length, double x, double z, boolean isChangeX) {
        Validate.notNull(player, "player is null");

        //Loops safezone height
        for (int i = 0; i < 256; i++) {
            int increment = (int) ((length) / Math.abs(length));

            //Loops safezone side
            for (int j = 0; (length > 0 && j < (int) length) || (length < 0 && j > (int) length); j += increment) {
                Location location = new Location(getWorld(), x + (isChangeX ? j : 0), i, z + (isChangeX ? 0 : j));
                player.sendBlockChange(location, location.getBlock().getBlockData());
            }
        }
    }
}