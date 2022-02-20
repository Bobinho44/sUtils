package fr.bobinho.sutils.listeners;

import fr.bobinho.sutils.sUtilsCore;
import fr.bobinho.sutils.utils.home.sUtilsHomeManager;
import fr.bobinho.sutils.utils.location.sUtilsLocationUtil;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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

    /**
     * Listen when a player respawn
     *
     * @param e the player respawn event
     */
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {

        //Sets spawn point
        String worldType = e.getPlayer().getWorld().getName();
        Location spawn = sUtilsLocationUtil.getAsLocation(sUtilsCore.getMainSettings().getConfiguration().getString("spawn." + worldType, worldType + ":0:1000:0:0:0"));
        e.setRespawnLocation(spawn);
    }
}