package com.redslounge.fenixcast.models;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.MagmaCube;

// Custom object to track players and player data
public class RedPlayer
{
    // Variables to track data
    private OfflinePlayer player;
    private boolean durabilityPing;
    private boolean coordinatesHud;
    private boolean villagerWorkstation;
    private boolean itemAverages;
    private int averageItemTestID;
    private int averageItemEndID;
    private MagmaCube averageItemMagmaCube;

    // Constructer to setup the basic object
    public RedPlayer(OfflinePlayer player, boolean durabilityPing, boolean coordinatesHud)
    {
        this.player = player;
        this.durabilityPing = durabilityPing;
        this.coordinatesHud = coordinatesHud;
    }

    // Getters and setters for all the variables
    public OfflinePlayer getPlayer()
    {
        return player;
    }

    public boolean isDurabilityPing()
    {
        return durabilityPing;
    }

    public void setDurabilityPing(boolean durabilityPing)
    {
        this.durabilityPing = durabilityPing;
    }

    public boolean isCoordinatesHud()
    {
        return coordinatesHud;
    }

    public void setCoordinatesHud(boolean coordinatesHud)
    {
        this.coordinatesHud = coordinatesHud;
    }

    public void setPlayer(OfflinePlayer player)
    {
        this.player = player;
    }

    public boolean isVillagerWorkstation()
    {
        return villagerWorkstation;
    }

    public void setVillagerWorkstation(boolean villagerWorkstation)
    {
        this.villagerWorkstation = villagerWorkstation;
    }

    public boolean isItemAverages()
    {
        return itemAverages;
    }

    public void setItemAverages(boolean itemAverages)
    {
        this.itemAverages = itemAverages;
    }

    public int getAverageItemTestID()
    {
        return averageItemTestID;
    }

    public void setAverageItemTestID(int averageItemTestID)
    {
        this.averageItemTestID = averageItemTestID;
    }

    public int getAverageItemEndID()
    {
        return averageItemEndID;
    }

    public void setAverageItemEndID(int averageItemEndID)
    {
        this.averageItemEndID = averageItemEndID;
    }

    public MagmaCube getAverageItemMagmaCube()
    {
        return averageItemMagmaCube;
    }

    public void setAverageItemMagmaCube(MagmaCube averageItemMagmaCube)
    {
        this.averageItemMagmaCube = averageItemMagmaCube;
    }
}
