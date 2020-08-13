package com.redslounge.fenixcast.events;

import com.redslounge.fenixcast.DataManager;
import com.redslounge.fenixcast.Plugin;
import com.redslounge.fenixcast.models.RedPlayer;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VillagerWorkstation implements Listener
{
    private final Plugin plugin;

    public VillagerWorkstation(Plugin plugin)
    {
        this.plugin = plugin;
    }

    // Called when a player interacts with another entity
    @EventHandler
    public void onVillagerInteract(PlayerInteractEntityEvent event)
    {
        // If the entity wasnt a villager it returns
        if(!(event.getRightClicked() instanceof Villager))
        {
            return;
        }

        DataManager dataManager = DataManager.getInstance();
        RedPlayer redPlayer = dataManager.getPlayers().get(event.getPlayer().getUniqueId());

        // if the player already has the villager workstation enabled it will return
        if(!redPlayer.isVillagerWorkstation())
        {
            return;
        }

        // Set a variable for the villager
        Villager villager = (Villager) event.getRightClicked();

        // Only looks for villagers with a profession
        if(villager.getProfession().equals(Villager.Profession.NONE) || villager.getProfession().equals(Villager.Profession.NITWIT))
        {
            return;
        }

        // If the villager already is being highlighted then it will return
        if(dataManager.getVillagers().containsKey(villager))
        {
            return;
        }

        villager.setGlowing(true);

        // Get the location of the jobsite and offset .5 to both x/z so the magmacube glow effect goes around the block correctly
        Location workStation = villager.getMemory(MemoryKey.JOB_SITE);
        workStation.add(.5, 0, .5);

        // Spawns in a magma cube and sets all the metaData so its invisable, invulrable, and glows
        MagmaCube magmaCube = (MagmaCube) workStation.getWorld().spawnEntity(workStation, EntityType.MAGMA_CUBE);
        magmaCube.setAI(false);
        magmaCube.setGlowing(true);
        magmaCube.setAware(false);
        magmaCube.setSilent(true);
        magmaCube.setInvulnerable(true);
        magmaCube.setSize(2);
        magmaCube.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 0, false, false));
        dataManager.addVillager(villager, magmaCube);

        // Starts a timer for 10 seconds (default) and then removes the magma cube and removes the glow from the villager
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            villager.setGlowing(false);
            magmaCube.remove();
            dataManager.getVillagers().remove(villager);
            }, 20 * dataManager.getGlowEffectTime());
        event.setCancelled(true);
    }
}
