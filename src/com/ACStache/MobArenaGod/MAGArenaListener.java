package com.ACStache.MobArenaGod;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.entity.Player;

import com.garbagemule.MobArena.Arena;
import com.garbagemule.MobArena.MobArenaListener;

public class MAGArenaListener extends MobArenaListener
{
    private HashMap<Arena,HashSet<String>> godMap = new HashMap<Arena,HashSet<String>>();
    
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
        if(godMap.get(arena) == null) {return;}
        
        if(godMap.get(arena).contains(p.getName()))
            removeGod(arena, p);
    }
    
    public void onPlayerLeave(Arena arena, Player p)
    {
        if(godMap.get(arena) == null) {return;}
        
        if(godMap.get(arena).contains(p.getName()))
            removeGod(arena, p);
    }
    
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