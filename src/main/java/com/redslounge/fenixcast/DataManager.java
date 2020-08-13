package com.redslounge.fenixcast;

import com.redslounge.fenixcast.models.RedPlayer;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

// Singleton class to track data for the plugin
public class DataManager
{
    // Setting up the singleton class
    private static DataManager dataManager;
    private DataManager() {}
    public static DataManager getInstance()
    {
        if(dataManager == null)
        {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    // Variables to track
    private boolean defaultDurabilityPing;
    private boolean defaultCoordinatesHud;
    private int stopWarnPercentage;
    private int warnPercentage;
    private int glowEffectTime;
    private ArrayList<String> loginColors = new ArrayList<>();
    private HashMap<UUID, RedPlayer> players = new HashMap<>();
    private HashMap<Villager, MagmaCube> villagers = new HashMap<>();

    // Getters and setters for specific variables
    public boolean isDefaultDurabilityPing()
    {
        return defaultDurabilityPing;
    }

    public void setDefaultDurabilityPing(boolean defaultDurabilityPing)
    {
        this.defaultDurabilityPing = defaultDurabilityPing;
    }

    public boolean isDefaultCoordinatesHud()
    {
        return defaultCoordinatesHud;
    }

    public void setDefaultCoordinatesHud(boolean defaultCoordinatesHud)
    {
        this.defaultCoordinatesHud = defaultCoordinatesHud;
    }

    public int getStopWarnPercentage()
    {
        return stopWarnPercentage;
    }

    public void setStopWarnPercentage(int stopWarnPercentage)
    {
        this.stopWarnPercentage = stopWarnPercentage;
    }

    public int getWarnPercentage()
    {
        return warnPercentage;
    }

    public void setWarnPercentage(int warnPercentage)
    {
        this.warnPercentage = warnPercentage;
    }

    public void addPlayer(UUID uuid, RedPlayer redPlayer)
    {
        players.put(uuid, redPlayer);
    }

    public HashMap<UUID, RedPlayer> getPlayers()
    {
        return players;
    }

    public void addVillager(Villager villager, MagmaCube magmaCube)
    {
        villagers.put(villager, magmaCube);
    }

    public HashMap<Villager, MagmaCube> getVillagers()
    {
        return villagers;
    }

    public int getGlowEffectTime()
    {
        return glowEffectTime;
    }

    public void setGlowEffectTime(int glowEffectTime)
    {
        this.glowEffectTime = glowEffectTime;
    }

    public ArrayList<String> getLoginColors()
    {
        return loginColors;
    }

    public void setLoginColors(ArrayList<String> loginColors)
    {
        this.loginColors = loginColors;
    }
}
