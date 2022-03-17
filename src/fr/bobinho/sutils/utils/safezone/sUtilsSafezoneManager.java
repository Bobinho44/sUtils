package fr.bobinho.sutils.utils.safezone;

import fr.bobinho.sutils.sUtilsCore;
import fr.bobinho.sutils.utils.location.sUtilsLocationUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class sUtilsSafezoneManager {

    private final static List<sUtilsSafezone> sUtilsSafezones = new ArrayList<>();

    public static List<sUtilsSafezone> getsUtilsSafezones() {
        return sUtilsSafezones;
    }

    public static Optional<sUtilsSafezone> getsUtilsSafezone(@Nonnull String name) {
        Validate.notNull(name, "name is null");

        return getsUtilsSafezones().stream().filter(safezone -> safezone.getName().equalsIgnoreCase(name)).findFirst();
    }

    public static boolean isItsUtilsSafezone(@Nonnull String name) {
        Validate.notNull(name, "name is null");

        return getsUtilsSafezone(name).isPresent();
    }

    public static void createsUtilsSafezone(@Nonnull String name, @Nonnull Location corner, @Nonnull Location oppositeCorner) {
        Validate.notNull(name, "name is null");
        Validate.notNull(corner, "corner is null");
        Validate.notNull(oppositeCorner, "oppositeCorner is null");
        Validate.isTrue(!isItsUtilsSafezone(name), "safezone already exist");

        getsUtilsSafezones().add(new sUtilsSafezone(name, corner, oppositeCorner));
    }

    public static void deleteUtilsSafezone(@Nonnull String name) {
        Validate.notNull(name, "name is null");
        Validate.isTrue(isItsUtilsSafezone(name), "safezone doesn't exist");

        getsUtilsSafezones().remove(getsUtilsSafezone(name).get());
    }

    public static boolean isItInsUtilsSafezone(@Nonnull Location location) {
        Validate.notNull(location, "location is null");

        return getsUtilsSafezones().stream()
                .filter(safezone -> safezone.getWorld().equals(location.getWorld()))
                .anyMatch(safezone -> sUtilsLocationUtil.isBetweenTwo2DPoint(safezone.getCorner(), safezone.getOppositeCorner(), location));
    }

    public static void showsUtilsSafezones(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        getsUtilsSafezones().forEach(safezone -> safezone.show(player));
    }

    public static void hidesUtilsSafezones(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        getsUtilsSafezones().forEach(safezone -> safezone.hide(player));
    }

    public static void loadsUtilsSafezone() {
        YamlConfiguration configuration = sUtilsCore.getSafezonesSettings().getConfiguration();

        //Loads the practice arenas
        for (String safezoneName : configuration.getKeys(false)) {

            //Gets safezones informations
            Location corner = sUtilsLocationUtil.getAsLocation(configuration.getString(safezoneName + ".corner", "world:0:100:0:0:0"));
            Location oppositeCorner = sUtilsLocationUtil.getAsLocation(configuration.getString(safezoneName + ".oppositeCorner", "world:0:100:0:0:0"));

            //Creates the safezone
            createsUtilsSafezone(safezoneName, corner, oppositeCorner);
        }
    }

    public static void savesUtilsSafezone() {
        YamlConfiguration configuration = sUtilsCore.getSafezonesSettings().getConfiguration();
        sUtilsCore.getSafezonesSettings().clear();

        //Saves safezones
        for (sUtilsSafezone safezone : getsUtilsSafezones()) {
            configuration.set(safezone.getName() + ".corner", sUtilsLocationUtil.getAsString(safezone.getCorner()));
            configuration.set(safezone.getName() + ".oppositeCorner", sUtilsLocationUtil.getAsString(safezone.getOppositeCorner()));
        }

        sUtilsCore.getSafezonesSettings().save();
    }

}