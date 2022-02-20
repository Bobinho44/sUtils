package fr.bobinho.sutils.utils.home;

import fr.bobinho.sutils.sUtilsCore;
import fr.bobinho.sutils.utils.location.sUtilsLocationUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class sUtilsHomeManager {

    private static final List<sUtilsHome> sUtilsHomes = new ArrayList<>();

    private static List<sUtilsHome> getsUtilsHomes() {
        return sUtilsHomes;
    }

    public static Optional<sUtilsHome> getsUtilsHome(@Nonnull Player owner, @Nonnull String name) {
        Validate.notNull(owner, "owner is null");
        Validate.notNull(name, "name is null");

        return getsUtilsHomes().stream().filter(home -> home.getOwner().equals(owner) && home.getName().equalsIgnoreCase(name)).findFirst();
    }

    public static boolean isItsUtilsHome(@Nonnull Player owner, @Nonnull String name) {
        Validate.notNull(owner, "owner is null");
        Validate.notNull(name, "name is null");

        return getsUtilsHome(owner, name).isPresent();
    }

    public static List<sUtilsHome> getPlayersUtilsHomes(@Nonnull Player owner) {
        Validate.notNull(owner, "owner is null");

        return getsUtilsHomes().stream().filter(home -> home.getOwner().equals(owner)).collect(Collectors.toList());
    }

    public static void createsUtilsHome(@Nonnull Player owner, @Nonnull String name) {
        Validate.notNull(owner, "owner is null");
        Validate.notNull(name, "name is null");
        Validate.isTrue(getNumberOfsUtilsHomes(owner) < getNumberOfsUtilsHomesAllowed(owner), "owner has already used all its homes");

        createsUtilsHome(owner, name, owner.getLocation());
    }

    public static void createsUtilsHome(@Nonnull Player owner, @Nonnull String name, @Nonnull Location center) {
        Validate.notNull(owner, "owner is null");
        Validate.notNull(name, "name is null");
        Validate.notNull(center, "center is null");
        Validate.isTrue(!isItsUtilsHome(owner, name), "home is already set");

        Validate.isTrue(getNumberOfsUtilsHomes(owner) < getNumberOfsUtilsHomesAllowed(owner), "owner has already used all its homes");

        getsUtilsHomes().add(new sUtilsHome(owner, name, center));
    }

    public static void deletesUtilsHome(@Nonnull Player owner, @Nonnull String name) {
        Validate.notNull(owner, "owner is null");
        Validate.notNull(name, "name is null");
        Validate.isTrue(isItsUtilsHome(owner, name), "home is not set");

        getsUtilsHomes().remove(getsUtilsHome(owner, name).get());
    }

    public static int getNumberOfsUtilsHomes(@Nonnull Player owner) {
        Validate.notNull(owner, "owner is null");

        return (int) getsUtilsHomes().stream().filter(home -> home.getOwner().equals(owner)).count();
    }

    public static int getNumberOfsUtilsHomesAllowed(@Nonnull Player owner) {
        Validate.notNull(owner, "owner is null");

        return owner.getEffectivePermissions().stream()
                .filter(permission -> permission.getPermission().contains("sutils.homes"))
                .map(permission -> Integer.valueOf(permission.getPermission().split("sutils.homes")[1]))
                .max(Integer::compare).stream().filter(value -> value >= 5).findFirst().orElse(5);
    }

    public static BaseComponent[] getsUtilsHomesAsClickableString(@Nonnull Player owner) {
        Validate.notNull(owner, "owner is null");

        //Creates the homes clickable string
        List<sUtilsHome> homes = getPlayersUtilsHomes(owner);
        ComponentBuilder homesClickableString = new ComponentBuilder(ChatColor.GRAY + "-=+=- Home list (" + getNumberOfsUtilsHomes(owner) + "/" + getNumberOfsUtilsHomesAllowed(owner) + ") -=+=-\n[ ");
        for (int i = 0; i < homes.size(); i++) {
            homesClickableString.append(homes.get(i).getClickableTeleportationString());
            if (i < homes.size() - 1) {
                homesClickableString.append(ChatColor.GRAY  + ", ");
            }
        }

        return homesClickableString.append(ChatColor.GRAY  + " ]").create();
    }

    public static void loadsUtilsHomes(@Nonnull Player owner) {
        Validate.notNull(owner, "owner is null");

        YamlConfiguration configuration = sUtilsCore.getHomesSettings().getConfiguration();

        //Checks if player have homes
        if (configuration.getConfigurationSection(owner.getUniqueId().toString()) == null) {
            return;
        }

        //Loads player homes
        for (String safezoneName : Objects.requireNonNull(configuration.getConfigurationSection(owner.getUniqueId().toString())).getKeys(false)) {
            Location center = sUtilsLocationUtil.getAsLocation(configuration.getString(owner.getUniqueId() + "." + safezoneName, "world:0:0:0:0:0"));
            createsUtilsHome(owner, safezoneName, center);
        }

    }

    public static void savesUtilsHomes(@Nonnull Player owner) {
        Validate.notNull(owner, "owner is null");

        YamlConfiguration configuration = sUtilsCore.getHomesSettings().getConfiguration();
        configuration.set(owner.getUniqueId().toString(), null);

        //Saves player homes
        for (sUtilsHome home : getPlayersUtilsHomes(owner)) {
            configuration.set(owner.getUniqueId() + "." + home.getName(), sUtilsLocationUtil.getAsString(home.getLocation()));
        }

        sUtilsCore.getHomesSettings().save();
    }

}