package com.redslounge.fenixcast;

import co.aikar.commands.BukkitCommandManager;
import com.redslounge.fenixcast.commands.*;
import com.redslounge.fenixcast.events.*;
import com.redslounge.fenixcast.models.RedPlayer;
import com.redslounge.fenixcast.timers.CoordinatesHud;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Plugin extends JavaPlugin
{
    // Basic Variables
    private BukkitCommandManager commandManager;
    private FileConfiguration config, players;
    private final File configFile = new File(getDataFolder(), "config.yml");
    private final File playersFile = new File(getDataFolder(), "players.yml");

    // Starting method for the plugin
    @Override
    public void onEnable()
    {
        // Creates and Loads config files
        if(!configFile.exists())
        {
            this.saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        if(!playersFile.exists())
        {
            try
            {
                playersFile.createNewFile();
            }
            catch(IOException e)
            {
                this.getLogger().severe("Failed to create players.yml file. \n" + e.toString());
            }
        }
        players = YamlConfiguration.loadConfiguration(playersFile);

        // Starting Methods
        setupCommands();
        setupEvnets();
        loadConfig();

        // Loads the class to start the timer
        new CoordinatesHud(this);
//        new TabData(this);
    }

    // Disable method for the plugin
    @Override
    public void onDisable()
    {
        DataManager dataManager = DataManager.getInstance();
        for(UUID playerUUID : dataManager.getPlayers().keySet())
        {
            players.set(playerUUID.toString() + ".durabilityPing", dataManager.getPlayers().get(playerUUID).isDurabilityPing());
            players.set(playerUUID.toString() + ".coordinatesHud", dataManager.getPlayers().get(playerUUID).isCoordinatesHud());

            if(dataManager.getPlayers().get(playerUUID).isItemAverages())
            {
                this.getServer().getScheduler().cancelTask(dataManager.getPlayers().get(playerUUID).getAverageItemTestID());
                this.getServer().getScheduler().cancelTask(dataManager.getPlayers().get(playerUUID).getAverageItemEndID());
                dataManager.getPlayers().get(playerUUID).getAverageItemMagmaCube().remove();
                dataManager.getPlayers().get(playerUUID).setItemAverages(false);
                dataManager.getPlayers().get(playerUUID).setAverageItemTestID(-1);
                dataManager.getPlayers().get(playerUUID).setAverageItemEndID(-1);
            }
        }

        try
        {
            players.save(playersFile);
        }
        catch(IOException e)
        {
            this.getLogger().severe("Failed to save players.yml file! \n" + e.toString());
        }

        for(Villager villager : dataManager.getVillagers().keySet())
        {
            villager.setGlowing(false);
            dataManager.getVillagers().get(villager).remove();
        }
    }

    // Method for registering all the commands
    private void setupCommands()
    {
        commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new DurabilityPingCommand());
        commandManager.registerCommand(new CoordinatesHudCommand());
        commandManager.registerCommand(new VillagerWorkstationCommand());
        commandManager.registerCommand(new KillBoatsCommand(this));
        commandManager.registerCommand(new ItemAveragesCommand(this));
    }

    // Method for registering all the events
    private void setupEvnets()
    {
        getServer().getPluginManager().registerEvents(new DurabilityPing(), this);
        getServer().getPluginManager().registerEvents(new DoubleShulker(), this);
        getServer().getPluginManager().registerEvents(new DragonDrops(), this);
        getServer().getPluginManager().registerEvents(new FirstTimeLogin(), this);
        getServer().getPluginManager().registerEvents(new RandomLoginColors(), this);
        getServer().getPluginManager().registerEvents(new SilenceEntities(), this);
        getServer().getPluginManager().registerEvents(new VillagerWorkstation(this), this);
        getServer().getPluginManager().registerEvents(new ItemAverages(this), this);
    }

    // Method for loading in data from the config files
    private void loadConfig()
    {
        // Loads in the settings from config.yml
        DataManager dataManager = DataManager.getInstance();
        dataManager.setDefaultDurabilityPing(config.getBoolean("defaultDurabilityPing"));
        dataManager.setDefaultCoordinatesHud(config.getBoolean("defaultCoordinatesHud"));
        dataManager.setWarnPercentage(config.getInt("warnPercentage"));
        dataManager.setStopWarnPercentage(config.getInt("stopWarnPercentage"));
        dataManager.setGlowEffectTime(config.getInt("glowEffectTime"));
        dataManager.setLoginColors((ArrayList<String>) config.getStringList("loginColors"));

        // Loads in the players from players.yml
        ConfigurationSection section = players.getConfigurationSection("");
        for(String playerUUID : section.getKeys(false))
        {
            RedPlayer redPlayer = new RedPlayer(
                    this.getServer().getOfflinePlayer(UUID.fromString(playerUUID)),
                    players.getBoolean(playerUUID + ".durabilityPing"),
                    players.getBoolean(playerUUID + ".coordinatesHud"));

            dataManager.addPlayer(UUID.fromString(playerUUID), redPlayer);
        }
    }
}
