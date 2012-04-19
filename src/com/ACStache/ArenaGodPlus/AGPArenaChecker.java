package com.ACStache.ArenaGodPlus;

import net.slipcor.pvparena.api.PVPArenaAPI;
import com.tommytony.war.Warzone;
import de.kumpelblase2.mobdungeon.MobDungeonMain;

import org.bukkit.entity.Player;

public class AGPArenaChecker
{
    public static boolean isPlayerInMAArena(Player player)
    {
        if(ArenaGodPlus.foundMA())
            return (ArenaGodPlus.am.getArenaWithPlayer(player) != null && ArenaGodPlus.am.getArenaWithPlayer(player).inArena(player));
        else
            return false;
    }
    
    public static boolean isPlayerInPVPArena(Player player)
    {
        if(ArenaGodPlus.foundPVP())
            return !PVPArenaAPI.getArenaName(player).equals("");
        else
            return false;
    }
    
    public static boolean isPlayerInWarRegion(Player player)
    {
        if(ArenaGodPlus.foundWar())
            return !(Warzone.getZoneByPlayerName(player.getName()) == null);
        else
            return false;
    }
    
    public static boolean isPlayerInMobDungeon(Player player)
    {
        if(ArenaGodPlus.foundMD())
            return MobDungeonMain.getDungeonManager().playerIsInDungeon(player.getName());
        else
            return false;
    }
}