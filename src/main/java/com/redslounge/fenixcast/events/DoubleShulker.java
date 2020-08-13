package com.redslounge.fenixcast.events;

import org.bukkit.Material;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

// Makes shulkers drop 2 shells
public class DoubleShulker implements Listener
{
    // Gets called when an entity dies
    @EventHandler
    public void onShulkderDeath(EntityDeathEvent event)
    {
        // Check if its not a shulker and returns
        if(!(event.getEntity() instanceof Shulker))
        {
            return;
        }

        // Sets the drops of the shulker
        event.getDrops().clear();
        event.getDrops().add(0, new ItemStack(Material.SHULKER_SHELL, 2));
    }
}
