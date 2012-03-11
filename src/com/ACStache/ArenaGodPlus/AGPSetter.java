package com.ACStache.ArenaGodPlus;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AGPSetter
{
    private static HashMap<String,HashSet<Boolean>> godMode = new HashMap<String,HashSet<Boolean>>();
    
    public static void setGod(Player player)
    {
        String playerName = player.getName();
        if(godMode.get(playerName) == null)
            godMode.put(playerName, new HashSet<Boolean>());
        
        HashSet<Boolean> godSet = godMode.get(playerName);
        
        if(godSet.isEmpty())
        {
            if(!AGPArenaChecker.isPlayerInMAArena(player) && !AGPArenaChecker.isPlayerInPVPArena(player) && !AGPConfig.getExcludedWorlds().contains(player.getWorld()))
            {
                godSet.add(true);
                player.sendMessage(ChatColor.AQUA + "AGP: God mode enabled");
                if(AGPConfig.getPersistence())
                    AGPConfig.addPersGod(player);
            }
            else
            {
                player.sendMessage(ChatColor.RED + "AGP: God mode not allowed in Arenas or excluded Worlds");
            }
        }
        else if(godSet.contains(true))
        {
            godSet.remove(true);
            godSet.add(false);
            if(AGPConfig.getPersistence())
                AGPConfig.removePersGod(player);
            
            if(!AGPArenaChecker.isPlayerInMAArena(player) && !AGPArenaChecker.isPlayerInPVPArena(player) && !AGPConfig.getExcludedWorlds().contains(player.getWorld()))
                player.sendMessage(ChatColor.AQUA + "AGP: God mode disabled");
            else
                player.sendMessage(ChatColor.RED + "AGP: God mode not allowed in Arenas or excluded Worlds");
        }
        else
        {
            if(!AGPArenaChecker.isPlayerInMAArena(player) && !AGPArenaChecker.isPlayerInPVPArena(player) && !AGPConfig.getExcludedWorlds().contains(player.getWorld()))
            {
                godSet.remove(false);
                godSet.add(true);
                player.sendMessage(ChatColor.AQUA + "AGP: God mode enabled");
                if(AGPConfig.getPersistence())
                    AGPConfig.addPersGod(player);
            }
            else
            {
                player.sendMessage(ChatColor.RED + "AGP: God mode not allowed in Arenas or excluded Worlds");
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
            if(AGPConfig.getPersistence() && AGPConfig.getPersGod(player))
                godMode.get(playerName).add(true);
            else
                godMode.get(playerName).add(false);
        }
        else if(godMode.get(playerName).isEmpty())
        {
            if(AGPConfig.getPersistence() && AGPConfig.getPersGod(player))
                godMode.get(playerName).add(true);
            else
                godMode.get(playerName).add(false);
        }
    }
}