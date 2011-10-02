package com.ACStache.MobArenaGod;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class MAGPlayerListener extends PlayerListener
{
    @SuppressWarnings("unused")
    private MobArenaGod plugin;
    
    public MAGPlayerListener(MobArenaGod instance)
    {
        plugin = instance;
    }
    
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        MAGSetter.addGod(event.getPlayer());
    }
    
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        if(MAGSetter.isGod(player))
            MAGSetter.setGod(player);
    }
}