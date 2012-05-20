package com.ACStache.ArenaGodPlus.PluginListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.ACStache.ArenaGodPlus.AGPSetter;
import com.herocraftonline.heroes.api.events.SkillDamageEvent;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public class HeroesListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHeroesSkillDmg(SkillDamageEvent event) 
    {
        if(!(event.getEntity() instanceof Player)) {return;}
        Player player = (Player)event.getEntity();
        if(!AGPSetter.isGod(player)) {return;}
        event.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHeroesWpnDmg(WeaponDamageEvent event)
    {
        if(!(event.getEntity() instanceof Player)) {return;}
        Player player = (Player)event.getEntity();
        if(!AGPSetter.isGod(player)) {return;}
        event.setCancelled(true);
    }
}