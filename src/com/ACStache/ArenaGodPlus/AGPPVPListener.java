package com.ACStache.ArenaGodPlus;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.slipcor.pvparena.definitions.Arena;
import net.slipcor.pvparena.events.PADeathEvent;
import net.slipcor.pvparena.events.PAJoinEvent;
import net.slipcor.pvparena.events.PALeaveEvent;

public class AGPPVPListener implements Listener
{
    private HashMap<Arena,HashSet<String>> godPVPMap = new HashMap<Arena,HashSet<String>>();
    private ArenaGodPlus plugin;
    
    public AGPPVPListener(ArenaGodPlus plugin)
    {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPvpPlayerJoin(PAJoinEvent event)
    {
        Player p = event.getPlayer();
        if(!AGPSetter.isGod(p)) {return;}
        AGPSetter.setGod(p);
        addPVPGod(event.getArena(), p);
    }
    
    @EventHandler
    public void onPvpPlayerDeath(PADeathEvent event)
    {
        Arena arena = event.getArena();
        Player p = event.getPlayer();
        if(godPVPMap.get(arena) == null) {return;}
        if(godPVPMap.get(arena).contains(p.getName()))
            removePVPGod(arena, p);
    }
    
    @EventHandler
    public void onPvpPlayerLeave(PALeaveEvent event)
    {
        Arena arena = event.getArena();
        Player p = event.getPlayer();
        if(godPVPMap.get(arena) == null) {return;}
        if(godPVPMap.get(arena).contains(p.getName()))
            removePVPGod(arena, p);
    }
    

    /*
     * Extra methods used for tracking PVP Arena participants
     */
    private void addPVPGod(Arena arena, Player p)
    {
        String pName = p.getName(); 
        if(godPVPMap.get(arena) == null)
        {
            godPVPMap.put(arena, new HashSet<String>());
            if(!godPVPMap.get(arena).contains(pName))
                godPVPMap.get(arena).add(pName);
        }
        else
        {
            if(!godPVPMap.get(arena).contains(pName))
                godPVPMap.get(arena).add(pName);
        }
    }
    
    private void removePVPGod(final Arena arena, final Player p)
    {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            public void run()
            {
                godPVPMap.get(arena).remove(p.getName());
                AGPSetter.setGod(p);
            }
        }, 20);
    }
}