package com.redslounge.fenixcast.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.redslounge.fenixcast.DataManager;
import com.redslounge.fenixcast.Utils;
import com.redslounge.fenixcast.models.RedPlayer;
import org.bukkit.entity.Player;

// Command to enable or disable the hud
@CommandAlias("coordinateshud")
public class CoordinatesHudCommand extends BaseCommand
{
    // Main command
    @Default
    public void onCoordinatesHud(Player player)
    {
        // Variables to track the player information
        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(player.getUniqueId());

        // Enableds or disables the hud based on current player settings
        if(redPlayer.isCoordinatesHud())
        {
            redPlayer.setCoordinatesHud(false);
            player.sendMessage(Utils.color("&8[&6CoordinatesHud&8] &cDisabled!"));
        }
        else
        {
            redPlayer.setCoordinatesHud(true);
            player.sendMessage(Utils.color("&8[&6CoordinatesHud&8] &aEnabled!"));
        }
        dataManager.getPlayers().put(player.getUniqueId(), redPlayer);
    }
}
