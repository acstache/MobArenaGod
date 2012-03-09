package com.ACStache.ArenaGodPlus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AGPListener implements Listener
{
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event)
    {
        if(!(event.getEntity() instanceof Player)) {return;}
        Player player = (Player)event.getEntity();
        if(!AGPSetter.isGod(player)) {return;}
        event.setCancelled(true);
        DamageCause cause = event.getCause();
        if(cause == DamageCause.FIRE || cause == DamageCause.LAVA || cause == DamageCause.FIRE_TICK)
            player.setFireTicks(0);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if(!(event.getEntity() instanceof Player)) {return;}
        Player player = (Player)event.getEntity();
        if(!AGPSetter.isGod(player)) {return;}
        if(player.getFoodLevel() < event.getFoodLevel()) {return;}
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event)
    {
        Player p = event.getPlayer();
        if(AGPSetter.isGod(p) && !p.hasPermission("ArenaGodPlus.toggle"))
            AGPSetter.setGod(p);
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        AGPSetter.addGod(player);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if(AGPConfig.getPersistence()) {return;}
        Player player = event.getPlayer();
        if(AGPSetter.isGod(player))
            AGPSetter.setGod(player);
    }
}