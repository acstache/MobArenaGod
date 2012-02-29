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
        return (MobArenaGod.am.getArenaWithPlayer(player) != null && MobArenaGod.am.getArenaWithPlayer(player).inArena(player));
    }
}