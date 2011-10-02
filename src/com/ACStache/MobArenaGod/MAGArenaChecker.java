package com.ACStache.MobArenaGod;

import org.bukkit.entity.Player;

public class MAGArenaChecker
{
    /**
     * Checks to see if a player is in a MobArena arena
     * @param player the player being checked
     * @return true or false
     */
    public static boolean isPlayerInArena(Player player)
    {
        if(MobArenaGod.maHandler != null && MobArenaGod.maHandler.isPlayerInArena(player))
            return true; //Mob Arena found and player is in an arena
        else
            return false;
    }
}