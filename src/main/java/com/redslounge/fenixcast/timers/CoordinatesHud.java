package com.redslounge.fenixcast.timers;

import com.redslounge.fenixcast.DataManager;
import com.redslounge.fenixcast.Plugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

// Timer to refresh the players hud
public class CoordinatesHud
{
    // Variables
    private final Plugin plugin;

    // Constructor for grabing the main plugin instance and starting the timer
    public CoordinatesHud(Plugin plugin)
    {
        this.plugin = plugin;

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> onTimer(), 0, 0);
    }

    // Timer method that gets called every tick
    public void onTimer()
    {
        DataManager dataManager = DataManager.getInstance();

        // Gets all current online players and updates their hud based on if they have it enabled or not
        for(Player player : plugin.getServer().getOnlinePlayers())
        {
            if(dataManager.getPlayers().get(player.getUniqueId()).isCoordinatesHud())
            {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, getHudInformation(player, player.getLocation()));
            }
        }
    }

    // Builds the action bar message
    private TextComponent getHudInformation(Player player, Location location)
    {
        // xyz
        TextComponent textCoords = new TextComponent("XYZ: ");
        textCoords.setColor(ChatColor.GOLD);
        textCoords.setBold(true);

        // Coords
        TextComponent coordinates = new TextComponent(location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ() + "  ");
        coordinates.setColor(ChatColor.GRAY);
        coordinates.setBold(false);

        // Direction
        TextComponent direction = new TextComponent(player.getFacing().name().subSequence(0, 1) + "   ");
        direction.setColor(ChatColor.GOLD);

        // Time
        TextComponent time = new TextComponent(getClockTime(location.getWorld().getTime()));
        time.setColor(ChatColor.AQUA);

        // Puts it together
        textCoords.addExtra(coordinates);
        textCoords.addExtra(direction);
        textCoords.addExtra(time);

        return textCoords;
    }

    // Calculates the time and puts it into a 24 hour clock
    private String getClockTime(long time)
    {
        long gameMinutes = (time % 1000) * 60 / 1000;
        String stringMinutes = "0" + gameMinutes;
        stringMinutes = stringMinutes.substring(stringMinutes.length() - 2);
        long gameHours = time / 1000 + 6;
        gameHours %= 24;

        return gameHours + ":" + stringMinutes;
    }
}
