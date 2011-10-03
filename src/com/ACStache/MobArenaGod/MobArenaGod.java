package com.ACStache.MobArenaGod;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.garbagemule.MobArena.MobArenaHandler;

public class MobArenaGod extends JavaPlugin
{
    private Logger log = Logger.getLogger("Minecraft");
    private PluginDescriptionFile info;
    public static MobArenaHandler maHandler;
    private final MAGEntityListener entityListener = new MAGEntityListener(this);
    private final MAGPlayerListener playerListener = new MAGPlayerListener(this);
    
    public void onEnable()
    {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.FOOD_LEVEL_CHANGE, entityListener, Priority.Highest, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
        
        info = getDescription();
        log.info("[" + info.getName() + "] v" + info.getVersion() + " Successfully Enabled! By: " + info.getAuthors());
    }
    
    public void onDisable()
    {
        log.info("[" + info.getName() + "] Sucessfully Disabled");
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("mag"))
        {
            if(args.length >= 1)
            {
                if(args[0].equalsIgnoreCase("toggle"))
                {
                    if(sender instanceof Player && ((Player)sender).hasPermission("MobArenaGod.toggle"))
                    {
                        MAGSetter.setGod((Player)sender);
                    }
                    else
                    {
                        if(sender instanceof Player)
                        {
                            ((Player)sender).sendMessage("MAG: You don't have permission to do that.");
                        }
                        else
                        {
                            log.info("[" + info.getName() + "] You can't use that command from the console");
                        }
                    }
                }
                else if(args[0].equalsIgnoreCase("status"))
                {
                    if(sender instanceof Player && ((Player)sender).hasPermission("MobArenaGod.status"))
                    {
                        Player player = (Player)sender;
                        if(MAGSetter.isGod(player))
                        {
                            player.sendMessage("MAG: You are a God");
                        }
                        else
                        {
                            player.sendMessage("MAG: You are not a God");
                        }
                    }
                    else
                    {
                        if(sender instanceof Player)
                        {
                            ((Player)sender).sendMessage("MAG: You don't have permission to do that.");
                        }
                        else
                        {
                            log.info("[" + info.getName() + "] You can't use that command from the console");
                        }
                    }
                }
                else
                {
                    if(sender instanceof Player)
                    {
                        ((Player)sender).sendMessage("MAG: Please type in '/mag toggle' or '/mag status'");
                    }
                    else
                    {
                        log.info("[" + info.getName() + "] You can't use that command from the console");
                    }
                }
            }
            else
            {
                if(sender instanceof Player)
                {
                    ((Player)sender).sendMessage("MAG: Please type in '/mag toggle' or '/mag status'");
                }
                else
                {
                    log.info("[" + info.getName() + "] You can't use that command from the console");
                }
            }
        }
        else
        {
            if(sender instanceof Player)
                ((Player)sender).sendMessage("MAG: Please type in '/mag toggle' or '/mag status'");
            else
                log.info("[" + info.getName() + "] You can't use that command from the console");
        }
        return true;
    }
}