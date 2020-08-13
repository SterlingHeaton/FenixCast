package com.redslounge.fenixcast.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.redslounge.fenixcast.Plugin;
import com.redslounge.fenixcast.Utils;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

// Command to remove any empty boats that are currently loaded
@CommandAlias("killboats")
public class KillBoatsCommand extends BaseCommand
{
    private Plugin plugin;

    public KillBoatsCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    // Main command
    @Default
    @CommandPermission("fenixcast.killboats")
    public void killBoatsCommand(Player player)
    {
        // Variable to track how many boats are killed
        int boatsKilled = 0;

        // Loop though each world to find boats
        for(World world : plugin.getServer().getWorlds())
        {
            // Loop though all entities in specified world
            for(Entity entity : world.getEntities())
            {
                // Checks if the entity isnt a boat and goes to the next entity
                if(!(entity instanceof Boat))
                {
                    continue;
                }

                // Variable for the boat
                Boat boat = (Boat) entity;

                // Checks if the boat is empty and if so remove it and add 1 to the boatsKilled int
                if(!boat.isEmpty())
                {
                    continue;
                }

                boat.remove();
                boatsKilled++;
            }
        }
        // After both loops finish, send a message to the player stating how many boats were destoryed
        player.sendMessage(Utils.color("&8[&6Kill Boats&8] &aSuccessfully removed &2" + boatsKilled + " &aempty boats!"));
    }
}
