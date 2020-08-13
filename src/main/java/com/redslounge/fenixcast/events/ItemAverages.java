package com.redslounge.fenixcast.events;

import com.redslounge.fenixcast.DataManager;
import com.redslounge.fenixcast.Plugin;
import com.redslounge.fenixcast.Utils;
import com.redslounge.fenixcast.models.RedPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

// Event class for calculating item averages running though a water stream
public class ItemAverages implements Listener
{
    private final Plugin plugin;

    public ItemAverages(Plugin plugin)
    {
        this.plugin = plugin;
    }

    // Gets called when the player interacts with anything
    @EventHandler
    public void onItemStandPlace(PlayerInteractEvent event)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(event.getPlayer().getUniqueId());

        if(!redPlayer.isItemAverages())
        {
            return;
        }

        // Returns if the player didnt right click a block
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            return;
        }

        // Grabs the main item in the player's hand
        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();

        // Returns if the item isnt a armor stand
        if(!itemStack.getType().equals(Material.ARMOR_STAND))
        {
            return;
        }

        // Returns if the item doenst have any lore
        if(itemStack.getItemMeta().getLore() == null)
        {
            return;
        }

        // Checks to see if the 4th line of lore is blank (almost like a key)
        if(!itemStack.getItemMeta().getLore().get(3).equals(""))
        {
            return;
        }

        // Gets the first number in the name of the item (time in minutes) and gets the location of the event and offsets the location by .5
        String[] splitString = itemStack.getItemMeta().getDisplayName().split(" ");
        int time = Integer.parseInt(ChatColor.stripColor(splitString[0]));
        Location location = event.getClickedBlock().getLocation();
        location.add(.5, 1, .5);

        // Spawn a magmacube and set all its values to glow, invulnrable, and size
        MagmaCube magmaCube = (MagmaCube) event.getPlayer().getWorld().spawnEntity(location, EntityType.MAGMA_CUBE);
        magmaCube.setAI(false);
        magmaCube.setGlowing(true);
        magmaCube.setAware(false);
        magmaCube.setSilent(true);
        magmaCube.setInvulnerable(true);
        magmaCube.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 0, false, false));
        magmaCube.setSize(2);

        // Add magmacue to a list to be tracked
        redPlayer.setAverageItemMagmaCube(magmaCube);

        // Removes the armor stand
        event.getPlayer().getInventory().removeItem(itemStack);

        // Two variables to track the amount of items being counted and items on the ground that have already been counted
        HashMap<Material, Integer> items = new HashMap<>();
        ArrayList<Item> countedItems = new ArrayList<>();

        // Send the player a message saying the test is beinging and start the timer that goes off every tick
        event.getPlayer().sendMessage(Utils.color("&8[&6Item Averages&8] &aRunning test for &6" + time + " &aminute(s)."));
        int repeatingTaskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            // Searches for nearby entities within the magmacube then loops between all of them
            for(Entity entity : magmaCube.getNearbyEntities(0, 0, 0))
            {
                // If the entity isnt a item, then continue
                if(!entity.getType().equals(EntityType.DROPPED_ITEM))
                {
                    continue;
                }

                // If the list contains an item thats already been counted, then continue
                if(countedItems.contains(entity))
                {
                    continue;
                }

                // Adds the item to the counted list
                countedItems.add((Item) entity);

                // Get the itemStack of the item so we can see how many items are stacked together
                ItemStack itemEntity = ((Item) entity).getItemStack();

                // Depending on if the item has already been tracked, we will either add it or create it
                if(items.containsKey(itemEntity.getType()))
                {
                    items.put(itemEntity.getType(), items.get(itemEntity.getType()) + itemEntity.getAmount());
                }
                else
                {
                    items.put(itemEntity.getType(), itemEntity.getAmount());
                }
            }
        }, 0, 0);

        // Timer for determining when the test is over
        int finalTimerID = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {

            // Cancel the repeating task above, remove the magmacube, send the player a message that states the test has ended
            plugin.getServer().getScheduler().cancelTask(repeatingTaskID);
            magmaCube.remove();
            event.getPlayer().sendMessage(Utils.color("&8[&6Item Averages&8] &aTest Finished &7(&oran for &6" + time + " &7&ominute(s)&7):"));

            redPlayer.setAverageItemMagmaCube(null);
            redPlayer.setAverageItemTestID(-1);
            redPlayer.setAverageItemEndID(-1);
            redPlayer.setItemAverages(false);

            // Loops between all of the counted items and messages the player the total of each item counted plus the hourly rate of those items
            for(Material item : items.keySet())
            {
                event.getPlayer().sendMessage(Utils.color("&6" + item.name().toLowerCase().replace("_", " ") + ": &a" + items.get(item) +
                        "&8(&a" + (items.get(item) / time) * 60 + "&8/&6hour&8)"));
            }
        }, time * 60 * 20);

        // Set the task id's to the player
        redPlayer.setAverageItemTestID(repeatingTaskID);
        redPlayer.setAverageItemEndID(finalTimerID);
    }

    // Called if the player leaves
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        // Variables for tracking data
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(event.getPlayer().getUniqueId());

        // If the player has enabled the item then it will cancel all tasks and remove the magma cube
        if(redPlayer.isItemAverages())
        {
            plugin.getServer().getScheduler().cancelTask(redPlayer.getAverageItemTestID());
            plugin.getServer().getScheduler().cancelTask(redPlayer.getAverageItemEndID());
            redPlayer.getAverageItemMagmaCube().remove();
            redPlayer.setItemAverages(false);
            redPlayer.setAverageItemTestID(-1);
            redPlayer.setAverageItemEndID(-1);
        }
    }
}
