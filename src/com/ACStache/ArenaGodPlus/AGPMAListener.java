package com.ACStache.ArenaGodPlus;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.garbagemule.MobArena.events.ArenaPlayerDeathEvent;
import com.garbagemule.MobArena.events.ArenaPlayerJoinEvent;
import com.garbagemule.MobArena.events.ArenaPlayerLeaveEvent;
import com.garbagemule.MobArena.framework.Arena;

public class AGPMAListener implements Listener
{
    private HashMap<Arena,HashSet<String>> godMAMap = new HashMap<Arena,HashSet<String>>();
    private ArenaGodPlus plugin;
    
    public AGPMAListener(ArenaGodPlus plugin)
    {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onMAPlayerJoin(ArenaPlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        if(!AGPSetter.isGod(p)) {return;}
        AGPSetter.setGod(p);
        addMAGod(event.getArena(), p);
    }
    
    @EventHandler
    public void onMAPlayerDeath(ArenaPlayerDeathEvent event)
    {
        Arena arena = event.getArena();
        Player p = event.getPlayer();
        if(godMAMap.get(arena) == null) {return;}
        if(godMAMap.get(arena).contains(p.getName()))
            removeMAGod(arena, p);
    }
    
    @EventHandler
    public void onMAPlayerLeave(ArenaPlayerLeaveEvent event)
    {
        Arena arena = event.getArena();
        Player p = event.getPlayer();
        if(godMAMap.get(arena) == null) {return;}
        if(godMAMap.get(arena).contains(p.getName()))
            removeMAGod(arena, p);
    }
    

    /*
     * Extra methods used for tracking MA Arena participants
     */
    private void addMAGod(Arena arena, Player p)
    {
        String pName = p.getName(); 
        if(godMAMap.get(arena) == null)
        {
            godMAMap.put(arena, new HashSet<String>());
            if(!godMAMap.get(arena).contains(pName))
                godMAMap.get(arena).add(pName);
        }
        else
        {
            if(!godMAMap.get(arena).contains(pName))
                godMAMap.get(arena).add(pName);
        }
    }
    
    private void removeMAGod(final Arena arena, final Player p)
    {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            public void run()
            {
                godMAMap.get(arena).remove(p.getName());
                AGPSetter.setGod(p);
            }
        }, 20);
    }
}