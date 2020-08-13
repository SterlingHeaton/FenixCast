package com.redslounge.fenixcast.events;

import com.redslounge.fenixcast.DataManager;
import com.redslounge.fenixcast.models.RedPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

// Event for capturing when player first logs into the server
public class FirstTimeLogin implements Listener
{
    // Called when the player attempts to login to the server
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        DataManager dataManager = DataManager.getInstance();

        // If this players data already exists then we return
        if(dataManager.getPlayers().containsKey(event.getPlayer().getUniqueId()))
        {
            return;
        }

        // New player? Capture their data and track it with the datamanager
        RedPlayer redPlayer = new RedPlayer(event.getPlayer(), dataManager.isDefaultDurabilityPing(), dataManager.isDefaultCoordinatesHud());
        dataManager.addPlayer(event.getPlayer().getUniqueId(), redPlayer);
    }
}
