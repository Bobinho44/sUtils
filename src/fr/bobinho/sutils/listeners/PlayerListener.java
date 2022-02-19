package fr.bobinho.sutils.listeners;

import fr.bobinho.sutils.utils.home.sUtilsHomeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    /**
     * Listen when a player join the server
     *
     * @param e the player join event
     */
    @EventHandler
    public void onPlayerStartCombat(PlayerJoinEvent e) {

        //Loads player homes
        sUtilsHomeManager.loadsUtilsHomes(e.getPlayer());
    }

    /**
     * Listen when a player leave the server
     *
     * @param e the player quit event
     */
    @EventHandler
    public void onPlayerStartCombat(PlayerQuitEvent e) {

        //Saves player homes
        sUtilsHomeManager.savesUtilsHomes(e.getPlayer());
    }

}