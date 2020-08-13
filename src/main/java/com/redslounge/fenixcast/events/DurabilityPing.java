package com.redslounge.fenixcast.events;

import com.redslounge.fenixcast.DataManager;
import com.redslounge.fenixcast.Utils;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

// Warns the player when their tool/armor gets below a threshhold (5% by default)
public class DurabilityPing implements Listener
{
    // Called when the player's item takes damage
    @EventHandler
    public void onItemUse(PlayerItemDamageEvent event)
    {
        // Checks to see if the player has this feature enabled
        DataManager dataManager = DataManager.getInstance();
        if(!dataManager.getPlayers().get(event.getPlayer().getUniqueId()).isDurabilityPing())
        {
            return;
        }

        // Gets the item that was damaged and gets it's durability
        ItemStack item = event.getItem();
        Damageable itemDamage = (Damageable) item.getItemMeta();
        int itemDurability = (item.getType().getMaxDurability() - itemDamage.getDamage()) * 100 / item.getType().getMaxDurability();

        // If the durability is above or below the threshhold it will return
        if(itemDurability < dataManager.getStopWarnPercentage() || itemDurability > dataManager.getWarnPercentage())
        {
            return;
        }

        // Send the player with a title message and play a tone when their item is within the threshhold
        event.getPlayer().sendTitle(Utils.color("&cWarning!"), Utils.color("&cDurability below " + dataManager.getWarnPercentage() + "%"), 0, 60, 20);
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
    }
}
