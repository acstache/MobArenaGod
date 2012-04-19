package com.ACStache.ArenaGodPlus;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ACStache.ArenaGodPlus.PluginListeners.MobArenaListener;
import com.ACStache.ArenaGodPlus.PluginListeners.MobDungeonListener;
import com.ACStache.ArenaGodPlus.PluginListeners.PvPArenaListener;
import com.ACStache.ArenaGodPlus.PluginListeners.WarListener;
import com.garbagemule.MobArena.framework.ArenaMaster;
import com.garbagemule.MobArena.MobArena;
import net.slipcor.pvparena.PVPArena;
import com.tommytony.war.War;

import de.kumpelblase2.mobdungeon.MobDungeonMain;

public class ArenaGodPlus extends JavaPlugin
{
    private Logger log = Logger.getLogger("Minecraft");
    private PluginDescriptionFile info;
    private static File dir, file;
    public static ArenaMaster am;
    private static boolean foundMA = false, foundPVP = false, foundWar = false, foundMD = false;
    
    public void onEnable()
    {
        this.getServer().getPluginManager().registerEvents(new AGPListener(), this);
        info = getDescription();
        
        dir = getDataFolder();
        file = new File(dir, "config.yml");
        if(!dir.exists())
        {
            dir.mkdir();
            AGPConfig.loadConfig(file);
        }
        else
        {
            AGPConfig.loadConfig(file);
        }
        
        for(World w : Bukkit.getWorlds())
            for(Player p : w.getPlayers())
                if(AGPConfig.getPersGod(p))
                    AGPSetter.addGod(p);

        setupArenaStuff();
        log.info("[" + info.getName() + "] v" + info.getVersion() + " Successfully Enabled! By: " + info.getAuthors());
    }
    
    public void onDisable()
    {
        log.info("[" + info.getName() + "] Sucessfully Disabled");
    }
    
    private void setupArenaStuff()
    {
        PluginManager pm = this.getServer().getPluginManager();
        MobArena maPlugin = (MobArena)pm.getPlugin("MobArena");
        PVPArena pvpPlugin = (PVPArena)pm.getPlugin("pvparena");
        MobDungeonMain mdPlugin = (MobDungeonMain)pm.getPlugin("MobDungeon");
        War warPlugin = (War)pm.getPlugin("War");
        if(maPlugin != null && maPlugin.isEnabled()) {
            am = maPlugin.getArenaMaster();
            pm.registerEvents(new MobArenaListener(this), this);
            foundMA = true;
            log.info("[" + info.getName() + "] Found MobArena!");
        }
        if(pvpPlugin != null && pvpPlugin.isEnabled()) {
            pm.registerEvents(new PvPArenaListener(this), this);
            foundPVP = true;
            log.info("[" + info.getName() + "] Found PVPArena!");
        }
        if(warPlugin != null && warPlugin.isEnabled()) {
            pm.registerEvents(new WarListener(), this);
            foundWar = true;
            log.info("[" + info.getName() + "] Found War!");
        }
        if(mdPlugin != null && mdPlugin.isEnabled()) {
            pm.registerEvents(new MobDungeonListener(this), this);
            foundMD = true;
            log.info("[" + info.getName() + "] Found MobDungeon!");
        }
    }
    
    public static boolean foundMA()
    {
        return foundMA;
    }
    public static boolean foundPVP()
    {
        return foundPVP;
    }
    public static boolean foundWar()
    {
        return foundWar;
    }
    public static boolean foundMD()
    {
        return foundMD;
    }
    
    //TODO command executor this bad boy
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("god") || command.getName().equalsIgnoreCase("agp"))
        {
            if(args.length >= 1)
            {
                if(args[0].equalsIgnoreCase("status"))
                {
                    if(sender instanceof Player && ((Player)sender).hasPermission("ArenaGodPlus.status"))
                    {
                        Player player = (Player)sender;
                        if(AGPSetter.isGod(player))
                        {
                            player.sendMessage(ChatColor.AQUA + "AGP: You are a God");
                        }
                        else
                        {
                            player.sendMessage(ChatColor.AQUA + "AGP: You are not a God");
                        }
                    }
                    else
                    {
                        if(sender instanceof Player)
                        {
                            ((Player)sender).sendMessage(ChatColor.AQUA + "AGP: You don't have permission to do that.");
                        }
                        else
                        {
                            log.warning("[" + info.getName() + "] You can't use that command from the console");
                        }
                    }
                }
                else if(args[0].equalsIgnoreCase("reload"))
                {
                    if(sender instanceof Player && ((Player)sender).hasPermission("ArenaGodPlus.reload") || !(sender instanceof Player))
                    {
                        AGPConfig.loadConfig(file);
                        if(sender instanceof Player)
                        {
                            ((Player)sender).sendMessage(ChatColor.AQUA + "AGP: Config reloaded");
                        }
                        else
                        {
                            log.info("[" + info.getName() + "] Config reloaded");
                        }
                    }
                    else
                    {
                        ((Player)sender).sendMessage(ChatColor.AQUA + "AGP: You don't have permission to do that.");
                    }
                }
                else
                {
                    if(sender instanceof Player)
                    {
                        ((Player)sender).sendMessage(ChatColor.AQUA + "AGP: Please type in '/" + command.getName()+ "', '/" + command.getName()+ " status', or '/" + command.getName()+ " reload'");
                    }
                    else
                    {
                        log.warning("[" + info.getName() + "] You can't use that command from the console");
                    }
                }
            }
            else
            {
                if(sender instanceof Player && ((Player)sender).hasPermission("ArenaGodPlus.toggle"))
                {
                    //TODO allow for setting God Mode on others, require another permission (would be in above section)
                    AGPSetter.setGod((Player)sender);
                }
                else
                {
                    if(sender instanceof Player)
                    {
                        ((Player)sender).sendMessage(ChatColor.AQUA + "AGP: You don't have permission to do that.");
                    }
                    else
                    {
                        //TODO make usable from console IF an online player is found (would be in above section)
                        log.warning("[" + info.getName() + "] You can't use that command from the console");
                    }
                }
            }
        }
        return true;
    }
}