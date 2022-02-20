package fr.bobinho.sutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EchestListener implements Listener {

    @EventHandler
    public void a(PlayerCommandPreprocessEvent e) {
        if (!e.getMessage().contains("openender")) {
            return;
        }

        if (!e.getMessage().split(" ")[1].equalsIgnoreCase(e.getPlayer().getName()) && !e.getPlayer().hasPermission("sutils.echestall")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }
    }
}