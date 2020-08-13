package com.redslounge.fenixcast.events;

import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

// Makes the dragon drop an egg and elytra
public class DragonDrops implements Listener
{
    // Called when an entity dies
    @EventHandler
    public void onDragonDrops(EntityDeathEvent event)
    {
        // If it's anything but the dragon, return
        if(!(event.getEntity() instanceof EnderDragon))
        {
            return;
        }

        // Add the dragon egg and elytra to the dragon's drops
        event.getDrops().add(new ItemStack(Material.DRAGON_EGG, 1));
        event.getDrops().add(new ItemStack(Material.ELYTRA, 1));
    }
}
