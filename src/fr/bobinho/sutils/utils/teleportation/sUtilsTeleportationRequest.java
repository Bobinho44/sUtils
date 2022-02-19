package fr.bobinho.sutils.utils.teleportation;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class sUtilsTeleportationRequest {

    private final static Map<Player, Player> sUtilTeleportationRequests = new HashMap<>();

    private static Map<Player, Player> getsUtilTeleportationRequests() {
        return sUtilTeleportationRequests;
    }

    public static Optional<Map.Entry<Player, Player>> getsUtiPlayerPlayer(@Nonnull Player receiver) {
        Validate.notNull(receiver, "receiver is null");

        return getsUtilTeleportationRequests().entrySet().stream().filter(request -> request.getKey().equals(receiver)).findFirst();
    }

    public static boolean isItsUtilsTeleportationRequest(@Nonnull Player receiver) {
        Validate.notNull(receiver, "receiver is null");

        return getsUtiPlayerPlayer(receiver).isPresent();
    }

    public static void createsUtilsTeleportationRequest(@Nonnull Player sender, @Nonnull Player receiver) {
        Validate.notNull(sender, "sender is null");
        Validate.notNull(receiver, "receiver is null");

        getsUtilTeleportationRequests().put(receiver, sender);
    }

    public static void createsUtilsTeleportationRequest(@Nonnull Player receiver) {
        Validate.notNull(receiver, "receiver is null");
        Validate.isTrue(!isItsUtilsTeleportationRequest(receiver));

        getsUtilTeleportationRequests().remove(receiver);
    }

}