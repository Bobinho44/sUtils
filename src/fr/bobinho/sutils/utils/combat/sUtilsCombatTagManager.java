package fr.bobinho.sutils.utils.combat;

import fr.bobinho.sutils.utils.format.sUtilsDurationFormat;
import fr.bobinho.sutils.utils.safezone.sUtilsSafezoneManager;
import fr.bobinho.sutils.utils.scheduler.sUtilsScheduler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class sUtilsCombatTagManager {

    static {
        sUtilsScheduler.syncScheduler().every(1, TimeUnit.SECONDS).run(() -> {

            //Removes players who have not been in combat for 60 seconds
            getsUtilsPlayersCombatTagList().stream()
                    .filter(combatTag -> combatTag.getDuration().elapsed().toSeconds() >= 60).map(sUtilsCombatTag::getPlayer)
                    .collect(Collectors.toList())
                    .forEach(sUtilsCombatTagManager::deletesUtilsPlayerCombatTag);

            //Sends players combat tag remaining time in the action bar
            getsUtilsPlayersCombatTagList().stream().map(sUtilsCombatTag::getPlayer)
                    .forEach(player -> player.sendActionBar(getUtilsPlayerCombatTagRemainingTimeAsComponent(player)));
        });
    }

    /**
     * The players combat tag list
     */
    private static final List<sUtilsCombatTag> sUtilsPlayersCombatTagList = new ArrayList<>();

    /**
     * Gets all player combat tag
     *
     * @return all player combat tag
     */
    @Nonnull
    private static List<sUtilsCombatTag> getsUtilsPlayersCombatTagList() {
        return sUtilsPlayersCombatTagList;
    }

    /**
     * Gets a player combat tag
     *
     * @param player the player
     * @return the player combat tag
     */
    @Nonnull
    public static Optional<sUtilsCombatTag> getsUtilsPlayerCombatTag(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        return getsUtilsPlayersCombatTagList().stream().filter(combatTag -> combatTag.getPlayer().equals(player)).findFirst();
    }

    /**
     * Checks if the player have a combat tag
     *
     * @param player the player
     * @return if the player have a combat tag
     */
    public static boolean isItsUtilsPlayerCombatTag(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        return getsUtilsPlayerCombatTag(player).isPresent();
    }

    /**
     * Creates a new player combat tag or update it
     *
     * @param player the player
     */
    public static void createsUtilsPlayerCombatTag(@Nonnull Player player, @Nonnull Player lastDamager) {
        Validate.notNull(player, "player is null");

        //Creates the player combat tag
        if (!isItsUtilsPlayerCombatTag(player)) {
            getsUtilsPlayersCombatTagList().add(new sUtilsCombatTag(player, lastDamager.getName()));
            player.sendMessage(ChatColor.RED + "You are now in combat!");
        }

        //Updates the player combat tag
        else {
            sUtilsCombatTag combatTag = getsUtilsPlayerCombatTag(player).get();
            combatTag.setLastDamagerName(lastDamager.getName());
            combatTag.resetDuration();
        }

        //Show safezone border
        sUtilsSafezoneManager.showsUtilsSafezones(player);
    }

    /**
     * Delete a player combat tag
     *
     * @param player the player
     */
    public static void deletesUtilsPlayerCombatTag(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        //Delete the player combat tag
        getsUtilsPlayersCombatTagList().remove(getsUtilsPlayerCombatTag(player).get());
        player.setCooldown(Material.ENDER_PEARL, 0);
        sUtilsSafezoneManager.hidesUtilsSafezones(player);
        player.sendMessage(ChatColor.GREEN + "You are no longer in combat.");
    }

    /**
     * Gets the player combat tag remaining time as component
     *
     * @param player the player
     * @return the player combat tag remaining time as component
     */
    @Nonnull
    public static Component getUtilsPlayerCombatTagRemainingTimeAsComponent(@Nonnull Player player) {
        Validate.notNull(player, "player is null");

        //Gets player combat tag informations
        sUtilsCombatTag combatTag = getsUtilsPlayerCombatTag(player).get();
        String remainingTime = sUtilsDurationFormat.getAsSecondFormat(60 - combatTag.getDuration().elapsed(TimeUnit.SECONDS));

        //Creates the player combat tag timer
        return Component.text("Combat Timer: ", NamedTextColor.YELLOW)
                .append(Component.text(remainingTime, NamedTextColor.GRAY));
    }

}