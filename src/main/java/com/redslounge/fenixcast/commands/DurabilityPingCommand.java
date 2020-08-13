package com.redslounge.fenixcast.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.redslounge.fenixcast.DataManager;
import com.redslounge.fenixcast.Utils;
import com.redslounge.fenixcast.models.RedPlayer;
import org.bukkit.entity.Player;

// Command to enable or disable the durability ping
@CommandAlias("durabilityping")
public class DurabilityPingCommand extends BaseCommand
{
    // Main command
    @Default
    public void onDurabilityPing(Player player)
    {
        // Variables to track player data
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        // Enables or disables the durability ping based on current player setting
        if(redPlayer.isDurabilityPing())
        {
            redPlayer.setDurabilityPing(false);
            player.sendMessage(Utils.color("&8[&6Durability Ping&8] &cDisabled!"));
        }
        else
        {
            redPlayer.setDurabilityPing(true);
            player.sendMessage(Utils.color("&8[&6Durability Ping&8] &aEnabled!"));
        }
        dataManager.getPlayers().put(player.getUniqueId(), redPlayer);
    }
}
