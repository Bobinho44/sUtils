package fr.bobinho.sutils.utils.teleportation;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class sUtilsTeleportationRequestManager {

    private final static Map<Player, sUtilsTeleportationRequest> sUtilTeleportationRequests = new HashMap<>();

    private static Map<Player, sUtilsTeleportationRequest> getsUtilTeleportationRequests() {
        return sUtilTeleportationRequests;
    }

    public static Optional<Map.Entry<Player, sUtilsTeleportationRequest>> getsUtiPlayerPlayer(@Nonnull Player receiver) {
        Validate.notNull(receiver, "receiver is null");

        return getsUtilTeleportationRequests().entrySet().stream().filter(request -> request.getKey().equals(receiver)).findFirst();
    }

    public static boolean isItsUtilsTeleportationRequest(@Nonnull Player receiver) {
        Validate.notNull(receiver, "receiver is null");

        return getsUtiPlayerPlayer(receiver).isPresent();
    }

    public static void createsUtilsTeleportationRequest(@Nonnull Player sender, @Nonnull Player receiver, @Nonnull Location location, @Nonnull Player teleported) {
        Validate.notNull(sender, "sender is null");
        Validate.notNull(receiver, "receiver is null");
        Validate.notNull(location, "location is null");
        Validate.notNull(teleported, "teleported is null");

        getsUtilTeleportationRequests().put(receiver, new sUtilsTeleportationRequest(sender.getUniqueId(), teleported, location));
    }

    public static void deleteUtilsTeleportationRequest(@Nonnull Player receiver) {
        Validate.notNull(receiver, "receiver is null");
        Validate.isTrue(isItsUtilsTeleportationRequest(receiver));

        getsUtilTeleportationRequests().remove(receiver);
    }

}