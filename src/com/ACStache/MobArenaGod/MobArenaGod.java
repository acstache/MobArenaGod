package com.ACStache.MobArenaGod;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.garbagemule.MobArena.ArenaMaster;
import com.garbagemule.MobArena.MobArena;
import com.garbagemule.MobArena.MobArenaHandler;

public class MobArenaGod extends JavaPlugin
{
    private Logger log = Logger.getLogger("Minecraft");
    private PluginDescriptionFile info;
    private static File dir, file;
    public static MobArenaHandler maHandler;
    public static ArenaMaster am;
    @SuppressWarnings("unused")
    private MAGListener listener;
    
    public void onEnable()
    {
        this.getServer().getPluginManager().registerEvents(new MAGListener(), this);
        
        Plugin mobArena = Bukkit.getPluginManager().getPlugin("MobArena");
        if(mobArena != null && mobArena.isEnabled())
            setupMobArena();
        
        info = getDescription();
        
        dir = getDataFolder();
        file = new File(dir, "config.yml");
        if(!dir.exists())
        {
            dir.mkdir();
            MAGConfig.loadConfig(file);
        }
        else
        {
            MAGConfig.loadConfig(file);
        }
        for(World w : Bukkit.getWorlds())
        {
            for(Player p : w.getPlayers())
            {
                if(MAGConfig.getPersGod(p))
                {
                    MAGSetter.addGod(p);
                }
            }
        }
        
        log.info("[" + info.getName() + "] v" + info.getVersion() + " Successfully Enabled! By: " + info.getAuthors());
    }
    
    public void onDisable()
    {
        log.info("[" + info.getName() + "] Sucessfully Disabled");
    }
    
    private void setupMobArena()
    {
        Plugin maPlugin = (MobArena)Bukkit.getServer().getPluginManager().getPlugin("MobArena");
        
        if(maPlugin == null) {return;}
        
        maHandler = new MobArenaHandler();
        am = ((MobArena)maPlugin).getArenaMaster();
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("mag"))
        {
            if(args.length >= 1)
            {
                if(args[0].equalsIgnoreCase("status"))
                {
                    if(sender instanceof Player && ((Player)sender).hasPermission("MobArenaGod.status"))
                    {
                        Player player = (Player)sender;
                        if(MAGSetter.isGod(player))
                        {
                            player.sendMessage(ChatColor.AQUA + "MAG: You are a God");
                            MAGConfig.addPersGod(player);
                        }
                        else
                        {
                            player.sendMessage(ChatColor.AQUA + "MAG: You are not a God");
                            MAGConfig.removePersGod(player);
                        }
                    }
                    else
                    {
                        if(sender instanceof Player)
                        {
                            ((Player)sender).sendMessage(ChatColor.AQUA + "MAG: You don't have permission to do that.");
                        }
                        else
                        {
                            log.info("[" + info.getName() + "] You can't use that command from the console");
                        }
                    }
                }
                else if(args[0].equalsIgnoreCase("reload"))
                {
                    if(sender instanceof Player && ((Player)sender).hasPermission("MobArenaGod.reload") || !(sender instanceof Player))
                    {
                        MAGConfig.loadConfig(file);
                        if(sender instanceof Player)
                        {
                            ((Player)sender).sendMessage(ChatColor.AQUA + "MAG: Config reloaded");
                        }
                        else
                        {
                            log.info("[" + info.getName() + "] Config reloaded");
                        }
                    }
                    else
                    {
                        ((Player)sender).sendMessage(ChatColor.AQUA + "MAG: You don't have permission to do that.");
                    }
                }
                else
                {
                    if(sender instanceof Player)
                    {
                        ((Player)sender).sendMessage(ChatColor.AQUA + "MAG: Please type in '/mag', '/mag status', or '/mag reload'");
                    }
                    else
                    {
                        log.info("[" + info.getName() + "] You can't use that command from the console");
                    }
                }
            }
            else
            {
                if(sender instanceof Player && ((Player)sender).hasPermission("MobArenaGod.toggle"))
                {
                    MAGSetter.setGod((Player)sender);
                }
                else
                {
                    if(sender instanceof Player)
                    {
                        ((Player)sender).sendMessage(ChatColor.AQUA + "MAG: You don't have permission to do that.");
                    }
                    else
                    {
                        log.info("[" + info.getName() + "] You can't use that command from the console");
                    }
                }
            }
        }
        return true;
    }
}