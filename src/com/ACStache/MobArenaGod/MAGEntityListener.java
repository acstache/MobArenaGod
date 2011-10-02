package com.ACStache.MobArenaGod;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

public class MAGEntityListener extends EntityListener
{
    @SuppressWarnings("unused")
    private MobArenaGod plugin;
    
    public MAGEntityListener(MobArenaGod instance)
    {
        plugin = instance;
    }
    
    public void onEntityDamage(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();
        
        //if entity is not a player, ignore
        if(!(entity instanceof Player)) {return;}
        
        Player player = (Player)entity;
        
        //if player is in an arena
        if(MAGArenaChecker.isPlayerInArena(player)) {return;}
        
        //if a player isn't a god
        if(!MAGSetter.isGod(player)) {return;}
        
        //if the player gets lit on fire, extinguish them
        if(event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.LAVA)
            player.setFireTicks(0);
        
        event.setCancelled(true);
    }
}