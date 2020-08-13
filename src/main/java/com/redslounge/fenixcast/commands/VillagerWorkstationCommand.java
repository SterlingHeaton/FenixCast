package com.redslounge.fenixcast.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.redslounge.fenixcast.DataManager;
import com.redslounge.fenixcast.Utils;
import com.redslounge.fenixcast.models.RedPlayer;
import org.bukkit.entity.Player;

// Command to track down the workstation for a villager
@CommandAlias("villagerworkshop | vws")
public class VillagerWorkstationCommand extends BaseCommand
{
    // Main command
    @Default
    public void onVillagerCommand(Player player)
    {
        // Variables to track player data
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        // Enables or disables the villager workstation funtion based on current player settings
        if(redPlayer.isVillagerWorkstation())
        {
            redPlayer.setVillagerWorkstation(false);
            player.sendMessage(Utils.color("&8[&6Villager Workstation&8] &cDisabled!"));
        }
        else
        {
            redPlayer.setVillagerWorkstation(true);
            player.sendMessage(Utils.color("&8[&6Villager Workstation&8] &aClick on the villager you want to view!"));
        }
    }
}
