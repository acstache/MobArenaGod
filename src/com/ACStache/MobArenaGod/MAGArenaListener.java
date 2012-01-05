package com.ACStache.MobArenaGod;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.entity.Player;

import com.garbagemule.MobArena.Arena;
import com.garbagemule.MobArena.MobArenaListener;

public class MAGArenaListener extends MobArenaListener
{
    private HashMap<Arena,HashSet<String>> godMap = new HashMap<Arena,HashSet<String>>();
    
    /*public void onArenaStart(final Arena arena)
    {
        //set god mode to false for players joining the arena
        for(Player p : arena.getAllPlayers())
        {
            if(MAGSetter.isGod(p))
            {
                MAGSetter.setGod(p);
                addGod(arena, p);
            }
        }
    }*/
    
    public void onPlayerJoin(Arena arena, Player p)
    {
        if(MAGSetter.isGod(p))
        {
            MAGSetter.setGod(p);
            addGod(arena, p);
        }
    }
    
    public void onPlayerDeath(Arena arena, Player p)
    {
        //remove a God from the arena on death
        if(godMap.get(arena).contains(p.getName()))
            removeGod(arena, p);
    }
    
    public void onPlayerLeave(Arena arena, Player p)
    {
        //remove a God from the arena on leave
        if(godMap.get(arena).contains(p.getName()))
            removeGod(arena, p);
    }
    
    private void addGod(Arena arena, Player p)
    {
        String pName = p.getName(); 
        //map of Arenas and what Gods joined them
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
        //remove a God from the map and give them back Godmode
        godMap.get(arena).remove(p.getName());
        MAGSetter.setGod(p);
    }
}