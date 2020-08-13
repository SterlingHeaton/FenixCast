package com.redslounge.fenixcast.events;

import com.redslounge.fenixcast.DataManager;
import com.redslounge.fenixcast.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Random;

// Upon login the server will give the player a random color from a set
public class RandomLoginColors implements Listener
{
    // Gets called whent he player attempts to join the server
    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event)
    {
        // Calls the method to determin the random color string
        String randomColorCode = getRandomColor();

        // Sets the players name to include the color code
        event.getPlayer().setDisplayName(Utils.color(randomColorCode + event.getPlayer().getName() + "&f"));
    }

    // Method for determining the random color
    private String getRandomColor()
    {
        // Grabs a list of colors from the datamanager and chooses a random color
        DataManager dataManager = DataManager.getInstance();
        Random random = new Random();
        int randomNumber = random.nextInt(dataManager.getLoginColors().size()-1);
        return dataManager.getLoginColors().get(randomNumber);
    }
}
