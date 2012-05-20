package com.ACStache.ArenaGodPlus;

import java.util.HashMap;
import java.util.HashSet;
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
        
        if(godSet.isEmpty()) {
            if(!inRestrictedRegion(player)) {
                godSet.add(true);
                ArenaGodPlus.printToPlayer(player, "God mode enabled", false);
                if(AGPConfig.getPersistence())
                    AGPConfig.addPersGod(player);
            }
            else {
                ArenaGodPlus.printToPlayer(player, "God mode not allowed in Restricted Zones or excluded Worlds", true);
            }
        }
        else if(godSet.contains(true)) {
            godSet.remove(true);
            godSet.add(false);
            if(AGPConfig.getPersistence())
                AGPConfig.removePersGod(player);
            
            if(!inRestrictedRegion(player))
                ArenaGodPlus.printToPlayer(player, "God mode disabled", false);
            else
                ArenaGodPlus.printToPlayer(player, "God mode not allowed in Restricted Zones or excluded Worlds", true);
        }
        else {
            if(!inRestrictedRegion(player)) {
                godSet.remove(false);
                godSet.add(true);
                ArenaGodPlus.printToPlayer(player, "God mode enabled", false);
                if(AGPConfig.getPersistence())
                    AGPConfig.addPersGod(player);
            }
            else {
                ArenaGodPlus.printToPlayer(player, "God mode not allowed in Restricted Zones or excluded Worlds", true);
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
        if(godMode.get(playerName) == null) {
            godMode.put(playerName, new HashSet<Boolean>());
            if(AGPConfig.getPersistence() && AGPConfig.getPersGod(player))
                godMode.get(playerName).add(true);
            else
                godMode.get(playerName).add(false);
        }
        else if(godMode.get(playerName).isEmpty()) {
            if(AGPConfig.getPersistence() && AGPConfig.getPersGod(player))
                godMode.get(playerName).add(true);
            else
                godMode.get(playerName).add(false);
        }
    }
    
    private static boolean inRestrictedRegion(Player player)
    {
        if(AGPRegionChecker.isPlayerInMAArena(player))
            return true;
        if(AGPRegionChecker.isPlayerInPVPArena(player))
            return true;
        if(AGPRegionChecker.isPlayerInWarRegion(player))
            return true;
        if(AGPRegionChecker.isPlayerInMobDungeon(player))
            return true;
        if(AGPConfig.getExcludedWorlds().contains(player.getWorld()))
            return true;
        return false;
    }
}