package com.ACStache.MobArenaGod;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.garbagemule.MobArena.Arena;
import com.garbagemule.MobArena.events.ArenaPlayerDeathEvent;
import com.garbagemule.MobArena.events.ArenaPlayerJoinEvent;
import com.garbagemule.MobArena.events.ArenaPlayerLeaveEvent;

public class MAGListener implements Listener
{
    private HashMap<Arena,HashSet<String>> godMap = new HashMap<Arena,HashSet<String>>();
    
    public MAGListener(MobArenaGod plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    /*
     * Entity Listeners
     */
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();
        
        //if entity is not a player, ignore
        if(!(entity instanceof Player)) {return;}
        
        Player player = (Player)entity;
        
        //if a player isn't a god
        if(!MAGSetter.isGod(player)) {return;}
        
        //if the player gets lit on fire, extinguish them
        DamageCause cause = event.getCause();
        if(cause == DamageCause.FIRE || cause == DamageCause.LAVA || cause == DamageCause.FIRE_TICK)
            player.setFireTicks(0);
        
        event.setCancelled(true);
    }
    
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        Entity entity = event.getEntity();
        
        //if entity is not a player, ignore
        if(entity instanceof Player)
        {
            Player player = (Player)entity;
            
            //if the player is not a god, ignore
            if(!MAGSetter.isGod(player)) {return;}
            
            //if a player is god and would lose hunger, cancel the event
            if(player.getFoodLevel() > event.getFoodLevel())
            {
                //if a player's hunger isn't full, fill it
                if(player.getFoodLevel() != 20)
                {
                    player.setFoodLevel(20);
                }
                
                event.setCancelled(true);
            }
        }
    }
    
    
    /*
     * Player Listeners
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        MAGSetter.addGod(player);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if(MAGConfig.getPersistence()) {return;}
        
        Player player = event.getPlayer();
        if(MAGSetter.isGod(player))
            MAGSetter.setGod(player);
    }
    
    
    /*
     * Arena Listeners
     */
    @EventHandler
    public void onPlayerJoin(ArenaPlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        if(MAGSetter.isGod(p))
        {
            MAGSetter.setGod(p);
            addGod(event.getArena(), p);
        }
    }
    
    @EventHandler
    public void onPlayerDeath(ArenaPlayerDeathEvent event)
    {
        Arena arena = event.getArena();
        Player p = event.getPlayer();
        if(godMap.get(arena) == null) {return;}
        
        if(godMap.get(arena).contains(p.getName()))
            removeGod(arena, p);
    }
    
    @EventHandler
    public void onPlayerLeave(ArenaPlayerLeaveEvent event)
    {
        Arena arena = event.getArena();
        Player p = event.getPlayer();
        if(godMap.get(arena) == null) {return;}
        
        if(godMap.get(arena).contains(p.getName()))
            removeGod(arena, p);
    }
    
    
    /*
     * Extra methods used for tracking arena participants
     */
    private void addGod(Arena arena, Player p)
    {
        String pName = p.getName(); 
        
        if(godMap.get(arena) == null)
        {
            godMap.put(arena, new HashSet<String>());
            if(!godMap.get(arena).contains(pName))
            {
                godMap.get(arena).add(pName);
            }
        }
        else
        {
            if(!godMap.get(arena).contains(pName))
            {
                godMap.get(arena).add(pName);
            }
        }
    }
    
    private void removeGod(Arena arena, Player p)
    {
        godMap.get(arena).remove(p.getName());
        MAGSetter.setGod(p);
    }
}