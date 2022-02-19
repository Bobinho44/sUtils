package fr.bobinho.sutils.utils.safezone;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nonnull;

public class sUtilsSafezone {

    /**
     * Fields
     */
    private final Location center;
    private final int radius;
    private final World.Environment environment;

    /**
     * Creates a new safezone
     *
     * @param center      the center
     * @param radius      the radius
     * @param environment the world environment
     */
    public sUtilsSafezone(@Nonnull Location center, int radius, @Nonnull World.Environment environment) {
        Validate.notNull(center, "center is null");
        Validate.notNull(environment, "environment is null");

        this.center = center;
        this.radius = radius;
        this.environment = environment;
    }

    /**
     * Gets the safezone center
     *
     * @return the safezone center
     */
    @Nonnull
    public Location getCenter() {
        return center;
    }

    /**
     * Gets the safezone radius
     *
     * @return the safezone radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Gets the safezone environment
     *
     * @return the safezone environment
     */
    @Nonnull
    public World.Environment getEnvironment() {
        return environment;
    }

}