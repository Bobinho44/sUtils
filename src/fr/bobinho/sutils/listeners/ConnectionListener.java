package fr.bobinho.sutils.listeners;

import fr.bobinho.sutils.sUtilsCore;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.annotation.Nonnull;
import java.util.List;

public class ConnectionListener implements Listener {

    /**
     * Listen when a player join the server
     *
     * @param e the player join event
     */
    @EventHandler
    public void onConnect(PlayerJoinEvent e) {

        //Gets the join message
        List<String> joinMessage = sUtilsCore.getMainSettings().getConfiguration().getStringList("joinMessage");

        //Sends the join message to all player
        e.joinMessage(null);
        Bukkit.getOnlinePlayers().forEach(player -> joinMessage.forEach(message -> player.sendMessage(reformatJoinMessage(e.getPlayer(), message))));
    }

    /**
     * Reformat the join message
     *
     * @param joiner  the player who joined the server
     * @param message the message
     * @return the join message reformatted
     */
    @Nonnull
    private String reformatJoinMessage(@Nonnull Player joiner, @Nonnull String message) {
        Validate.notNull(joiner, "joiner is null");
        Validate.notNull(message, "message is null");

        return ChatColor.translateAlternateColorCodes('&', message).replace("%player%", joiner.getName());
    }

}