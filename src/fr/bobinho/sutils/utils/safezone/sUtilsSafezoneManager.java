package fr.bobinho.sutils.utils.safezone;

import fr.bobinho.sutils.sUtilsCore;
import fr.bobinho.sutils.utils.location.sUtilsLocationUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class sUtilsSafezoneManager {

    private final static List<sUtilsSafezone> sUtilsSafezones = new ArrayList<>();

    private static List<sUtilsSafezone> getsUtilsSafezones() {
        return sUtilsSafezones;
    }

    public static Optional<sUtilsSafezone> getsUtilsSafezone(@Nonnull World.Environment environment) {
        Validate.notNull(environment, "environment is null");

        return getsUtilsSafezones().stream().filter(safezone -> safezone.getEnvironment().equals(environment)).findFirst();
    }

    public static boolean isItsUtilsSafezone(@Nonnull World.Environment environment) {
        Validate.notNull(environment, "environment is null");

        return getsUtilsSafezone(environment).isPresent();
    }

    public static void createsUtilsSafezone(@Nonnull Location center, int radius, @Nonnull World.Environment environment) {
        Validate.notNull(center, "center is null");
        Validate.notNull(environment, "environment is null");

        getsUtilsSafezones().add(new sUtilsSafezone(center, radius, environment));
    }

    public static boolean isItInsUtilsSafezone(@Nonnull Location location) {
        Validate.notNull(location, "location is null");

        //Checks if there is a safezone in the world location
        if (!isItsUtilsSafezone(location.getWorld().getEnvironment())) {
            return false;
        }

        //Gets the safezone
        sUtilsSafezone safezone = getsUtilsSafezone(location.getWorld().getEnvironment()).get();

        //Checks if the distance between location and safezone center <= safezone radius
        return Math.sqrt(Math.pow(location.getX() - safezone.getCenter().getX(), 2) + Math.pow(location.getZ() - safezone.getCenter().getZ(), 2)) <= safezone.getRadius();
    }

    public static void showsUtilsSafezone(@Nonnull Player player) {
        Validate.notNull(player, "player is null");
        Validate.isTrue(isItsUtilsSafezone(player.getWorld().getEnvironment()), "safezone is not set");

        //Gets the safezone
        sUtilsSafezone safezone = getsUtilsSafezone(player.getWorld().getEnvironment()).get();

        //Show the safezone
        sUtilsCore.getWorldBorderApi().setBorder(player, safezone.getRadius(), safezone.getCenter());
    }

    public static void hidesUtilsSafezone(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        sUtilsCore.getWorldBorderApi().resetWorldBorderToGlobal(player);
    }

    public static void loadsUtilsSafezone() {
        YamlConfiguration configuration = sUtilsCore.getSafezonesSettings().getConfiguration();

        //Loads the practice arenas
        for (String safezoneEnvironment : configuration.getKeys(false)) {

            //Gets safezones informations
            Location center = sUtilsLocationUtil.getAsLocation(configuration.getString(safezoneEnvironment + ".center", safezoneEnvironment + ":0:100:0:0:0"));
            int radius = configuration.getInt(safezoneEnvironment + ".radius");

            //Creates the safezone
            createsUtilsSafezone(center, radius, World.Environment.valueOf(safezoneEnvironment));
        }
    }

    public static void savesUtilsSafezone() {
        YamlConfiguration configuration = sUtilsCore.getSafezonesSettings().getConfiguration();

        //Saves safezones
        for (sUtilsSafezone safezone : getsUtilsSafezones()) {
            configuration.set(safezone.getEnvironment() + ".center", safezone.getCenter());
            configuration.set(safezone.getEnvironment() + ".radius", safezone.getRadius());
        }
    }

}