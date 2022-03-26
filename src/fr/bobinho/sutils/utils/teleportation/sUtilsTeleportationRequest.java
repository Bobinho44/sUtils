package fr.bobinho.sutils.utils.teleportation;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.UUID;

public class sUtilsTeleportationRequest {

    private final UUID sender;
    private final Player teleported;
    private final Location location;

    public sUtilsTeleportationRequest(@Nonnull UUID sender, @Nonnull Player teleported, @Nonnull Location location) {
        this.sender = sender;
        this.teleported = teleported;
        this.location = location;
    }

    public UUID getSender() {
        return sender;
    }

    public Player getTeleported() {
        return teleported;
    }

    public Location getLocation() {
        return location;
    }

}