package com.redslounge.fenixcast.events;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

// Feature to stop mobs from making sounds when named
public class SilenceEntities implements Listener
{
    // Called when a player interacts with an entity
    @EventHandler
    public void onMobName(PlayerInteractEntityEvent event)
    {
        // Returns the event if the player has anything else but a name tag in their main hand
        if(!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NAME_TAG))
        {
            return;
        }

        // Grabs the namtag as a variable
        ItemStack nameTag = event.getPlayer().getInventory().getItemInMainHand();

        // Makes sure the name tag is named as silence
        if(!nameTag.getItemMeta().getDisplayName().equalsIgnoreCase("silence"))
        {
            return;
        }

        // Makes sure the player interacted with a living entity and not any other entity
        if(!(event.getRightClicked() instanceof LivingEntity))
        {
            return;
        }

        // Grab the livingentity as a variable
        LivingEntity livingEntity = (LivingEntity) event.getRightClicked();

        // Sets the entity to be silent and not despawn
        livingEntity.setSilent(true);
        livingEntity.setRemoveWhenFarAway(false);
    }
}
