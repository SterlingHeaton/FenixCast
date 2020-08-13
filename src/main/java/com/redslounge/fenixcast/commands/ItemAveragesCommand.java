package com.redslounge.fenixcast.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import com.redslounge.fenixcast.DataManager;
import com.redslounge.fenixcast.Plugin;
import com.redslounge.fenixcast.Utils;
import com.redslounge.fenixcast.models.RedPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

// Command for figuring out average amount of items running though water
@CommandAlias("itemaverage")
public class ItemAveragesCommand extends BaseCommand
{
    private Plugin plugin;

    public ItemAveragesCommand(Plugin plugin)
    {
        this.plugin = plugin;
    }

    // Main command=
    @Subcommand("item")
    public void onItemTimeCommand(Player player, int time)
    {
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        if(redPlayer.isItemAverages())
        {
            player.sendMessage(Utils.color("&8[&6Item Averages&8] &cYou already have the item starter or started a test."));
            return;
        }

        if(player.getInventory().firstEmpty() == -1)
        {
            player.sendMessage(Utils.color("&8[&6Item Averages&8] &cYou must have at least 1 empty inventory slot."));
            return;
        }

        // If the player inputs a time below 0 or above 60 it will return and send them a message with valid arguments
        if(time < 1 || time > 60)
        {
            player.sendMessage(Utils.color("&8[&6Item Averages&8] &cMust pick a time between 1-60 minutes"));
            return;
        }

        redPlayer.setItemAverages(true);

        // Setup the armor stand that the player will place to start tracking items
        ItemStack itemStack = new ItemStack(Material.ARMOR_STAND);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.color("&6" + time + " Minute Item Timer"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Utils.color("&5Place this armor stand"));
        lore.add(Utils.color("&5where you want it to count blocks."));
        lore.add(Utils.color("&5This will last for &6" + time + " &5minute(s)."));
        lore.add("");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        // Add the armor stand to the player's inventory
        player.getInventory().addItem(itemStack);
    }

    // Subcommand to stop the test once it's been started
    @Subcommand("stop")
    public void onStopCommand(Player player)
    {
        // Variables to track data
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        // If the player hasnt enabled the command then return
        if(!redPlayer.isItemAverages())
        {
            player.sendMessage(Utils.color("&8[&6Item Averages&8] &cYou dont have a test running."));
            return;
        }

        // Cancel all tasks, reset all data, remove the magma cube
        plugin.getServer().getScheduler().cancelTask(redPlayer.getAverageItemTestID());
        plugin.getServer().getScheduler().cancelTask(redPlayer.getAverageItemEndID());
        redPlayer.getAverageItemMagmaCube().remove();
        redPlayer.setItemAverages(false);
        redPlayer.setAverageItemTestID(-1);
        redPlayer.setAverageItemEndID(-1);

        player.sendMessage(Utils.color("&8[&6Item Averages&8] &aYou have stopped the item test."));
    }
}
