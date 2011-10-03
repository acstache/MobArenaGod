package com.ACStache.MobArenaGod;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.entity.Player;

public class MAGSetter
{
    private static HashMap<String,HashSet<Boolean>> godMode = new HashMap<String,HashSet<Boolean>>();
    
    public static void setGod(Player player)
    {
        String playerName = player.getName();
        HashSet<Boolean> godSet = godMode.get(playerName);
        if(godSet.contains(true))
        {
            godSet.remove(true);
            godSet.add(false);
            player.sendMessage("MAG: God mode disabled");
        }
        else
        {
            godSet.remove(false);
            godSet.add(true);
            player.sendMessage("MAG: God mode enabled");
        }
    }
    
    public static boolean isGod(Player player)
    {
        return godMode.get(player.getName()).contains(true);
    }
    
    public static void addGod(Player player)
    {
        String playerName = player.getName();
        if(godMode.get(playerName) == null)
        {
            godMode.put(playerName, new HashSet<Boolean>());
            if(!godMode.get(playerName).contains(false))
            {
                godMode.get(playerName).add(false);
            }
        }
        else
        {
            if(!godMode.get(playerName).contains(false))
            {
                godMode.get(playerName).add(false);
            }
        }
    }
}