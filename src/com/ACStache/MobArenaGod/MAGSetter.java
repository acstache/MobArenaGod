package com.ACStache.MobArenaGod;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MAGSetter
{
    private static HashMap<String,HashSet<Boolean>> godMode = new HashMap<String,HashSet<Boolean>>();
    
    public static void setGod(Player player)
    {
        String playerName = player.getName();
        if(godMode.get(playerName) == null)
        {
            godMode.put(playerName, new HashSet<Boolean>());
        }
        HashSet<Boolean> godSet = godMode.get(playerName);
        
        if(godSet.isEmpty())
        {
            if(!MAGArenaChecker.isPlayerInArena(player))
            {
                godSet.add(true);
                player.sendMessage(ChatColor.AQUA + "MAG: God mode enabled");
                if(MAGConfig.getPersistence())
                {
                    MAGConfig.addPersGod(player);
                }
            }
            else
            {
                player.sendMessage(ChatColor.AQUA + "MAG: God mode not allowed in an Arena");
            }
        }
        else if(godSet.contains(true))
        {
            godSet.remove(true);
            godSet.add(false);
            if(!MAGArenaChecker.isPlayerInArena(player))
            {
                player.sendMessage(ChatColor.AQUA + "MAG: God mode disabled");
                if(MAGConfig.getPersistence())
                {
                    MAGConfig.removePersGod(player);
                }
            }
            else
            {
                player.sendMessage(ChatColor.AQUA + "MAG: God mode not allowed in an Arena");
            }
        }
        else
        {
            godSet.remove(false);
            godSet.add(true);
            if(!MAGArenaChecker.isPlayerInArena(player))
            {
                player.sendMessage(ChatColor.AQUA + "MAG: God mode enabled");
                if(MAGConfig.getPersistence())
                {
                    MAGConfig.addPersGod(player);
                }
            }
            else
            {
                player.sendMessage(ChatColor.AQUA + "MAG: God mode not allowed in an Arena!");
            }
        }
    }
    
    public static boolean isGod(Player player)
    {
        if(godMode.containsKey(player.getName()))
            return godMode.get(player.getName()).contains(true);
        else
            return false;
    }
    
    public static void addGod(Player player)
    {
        String playerName = player.getName();
        if(godMode.get(playerName) == null)
        {
            godMode.put(playerName, new HashSet<Boolean>());
            
            if(MAGConfig.getPersistence() && MAGConfig.getPersGod(player))
            {
                godMode.get(playerName).add(true);
            }
            else
            {
                godMode.get(playerName).add(false);
            }
        }
        else if(godMode.get(playerName).isEmpty())
        {
            if(MAGConfig.getPersistence() && MAGConfig.getPersGod(player))
            {
                godMode.get(playerName).add(true);
            }
            else
            {
                godMode.get(playerName).add(false);
            }
        }
    }
}