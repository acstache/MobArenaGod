package com.ACStache.ArenaGodPlus.PluginListeners;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ACStache.ArenaGodPlus.AGPSetter;
import com.ACStache.ArenaGodPlus.ArenaGodPlus;
import de.kumpelblase2.mobdungeon.events.player.PlayerJoinEvent;
import de.kumpelblase2.mobdungeon.events.player.PlayerLeaveEvent;
import de.kumpelblase2.mobdungeon.api.Dungeon;

public class MobDungeonListener implements Listener
{
    private HashMap<Dungeon,HashSet<String>> godMDMap = new HashMap<Dungeon,HashSet<String>>();
    private ArenaGodPlus plugin;
    
    public MobDungeonListener(ArenaGodPlus plugin)
    {
        this.plugin = plugin;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        if(!AGPSetter.isGod(p)) {return;}
        AGPSetter.setGod(p);
        addMDGod(event.getDungeon(), p);
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerLeave(PlayerLeaveEvent event)
    {
        Dungeon dungeon = event.getDungeon();
        Player p = event.getPlayer();
        if(godMDMap.get(dungeon) == null) {return;}
        if(godMDMap.get(dungeon).contains(p.getName()))
            removeMDGod(dungeon, p);
    }
    

    /*
     * Extra methods used for tracking MD Dungeon participants
     */
    private void addMDGod(Dungeon dungeon, Player p)
    {
        String pName = p.getName(); 
        if(godMDMap.get(dungeon) == null) {
            godMDMap.put(dungeon, new HashSet<String>());
            if(!godMDMap.get(dungeon).contains(pName))
                godMDMap.get(dungeon).add(pName);
        }
        else {
            if(!godMDMap.get(dungeon).contains(pName))
                godMDMap.get(dungeon).add(pName);
        }
    }
    
    private void removeMDGod(final Dungeon dungeon, final Player p)
    {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                godMDMap.get(dungeon).remove(p.getName());
                AGPSetter.setGod(p);
            }
        }, 20);
    }
}