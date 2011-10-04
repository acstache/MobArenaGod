package com.ACStache.MobArenaGod;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

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
        
        //if a player isn't a god
        if(!MAGSetter.isGod(player)) {return;}
        
        //if the player gets lit on fire, extinguish them
        DamageCause cause = event.getCause();
        if(cause == DamageCause.FIRE || cause == DamageCause.LAVA || cause == DamageCause.FIRE_TICK)
            player.setFireTicks(0);
        
        event.setCancelled(true);
    }
    
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        Entity entity = event.getEntity();
        
        //if entity is not a player, ignore
        if(entity instanceof Player)
        {
            Player player = (Player)entity;
            
            //if the player is not a god, ignore
            if(!MAGSetter.isGod(player)) {return;}
            
            //if a player is god and would lose hunger, cancel the event
            if(player.getFoodLevel() > event.getFoodLevel())
            {
                //if a player's hunger isn't full, fill it
                if(player.getFoodLevel() != 20)
                {
                    player.setFoodLevel(20);
                }
                
                event.setCancelled(true);
            }
        }
    }
}