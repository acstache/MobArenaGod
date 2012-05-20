package com.ACStache.ArenaGodPlus;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ACStache.ArenaGodPlus.PluginListeners.HeroesListener;
import com.ACStache.ArenaGodPlus.PluginListeners.MobArenaListener;
import com.ACStache.ArenaGodPlus.PluginListeners.MobDungeonListener;
import com.ACStache.ArenaGodPlus.PluginListeners.PvPArenaListener;
import com.ACStache.ArenaGodPlus.PluginListeners.WarListener;
import com.garbagemule.MobArena.framework.ArenaMaster;

import com.garbagemule.MobArena.MobArena;
import com.herocraftonline.heroes.Heroes;
import net.slipcor.pvparena.PVPArena;
import com.tommytony.war.War;
import de.kumpelblase2.mobdungeon.MobDungeonMain;

public class ArenaGodPlus extends JavaPlugin
{
    private static Logger log = Logger.getLogger("Minecraft");
    private static PluginDescriptionFile info;
    private static AGPCommandExecutor agpExecutor;
    private static File dir, file;
    public static ArenaMaster am;
    private static boolean foundMA = false, foundPVP = false, foundWar = false, foundMD = false;
    
    public void onEnable()
    {
        info = getDescription();
        
        agpExecutor = new AGPCommandExecutor(this);
        getCommand("god").setExecutor(agpExecutor);
        getCommand("agp").setExecutor(agpExecutor);
        
        dir = getDataFolder();
        file = new File(dir, "config.yml");
        if(!dir.exists()) {
            dir.mkdir();
            AGPConfig.loadConfig(file);
        }
        else {
            AGPConfig.loadConfig(file);
        }
        
        for(World w : Bukkit.getWorlds())
            for(Player p : w.getPlayers())
                if(AGPConfig.getPersGod(p))
                    AGPSetter.addGod(p);

        setupListeners();
        printToConsole("v" + info.getVersion() + " Successfully Enabled! By: " + info.getAuthors(), false);
    }
    
    public void onDisable()
    {
        printToConsole("Sucessfully Disabled", false);
    }
    
    private void setupListeners()
    {
        PluginManager pm = this.getServer().getPluginManager();
        Heroes heroPlugin = (Heroes)pm.getPlugin("Heroes");
        MobArena maPlugin = (MobArena)pm.getPlugin("MobArena");
        PVPArena pvpPlugin = (PVPArena)pm.getPlugin("pvparena");
        MobDungeonMain mdPlugin = (MobDungeonMain)pm.getPlugin("MobDungeon");
        War warPlugin = (War)pm.getPlugin("War");

        pm.registerEvents(new AGPListener(), this);
        
        if(heroPlugin != null && heroPlugin.isEnabled()) {
            pm.registerEvents(new HeroesListener(), this);
            printToConsole("Found Heroes!", false);
        }
        if(maPlugin != null && maPlugin.isEnabled()) {
            am = maPlugin.getArenaMaster();
            pm.registerEvents(new MobArenaListener(this), this);
            foundMA = true;
            printToConsole("Found Mob Arena!", false);
        }
        if(pvpPlugin != null && pvpPlugin.isEnabled()) {
            pm.registerEvents(new PvPArenaListener(this), this);
            foundPVP = true;
            printToConsole("Found PvP Arena!", false);
        }
        if(warPlugin != null && warPlugin.isEnabled()) {
            pm.registerEvents(new WarListener(), this);
            foundWar = true;
            printToConsole("Found War!", false);
        }
        if(mdPlugin != null && mdPlugin.isEnabled()) {
            pm.registerEvents(new MobDungeonListener(this), this);
            foundMD = true;
            printToConsole("Found Mob Dungeon!", false);
        }
    }
    
    public static void printToConsole(String msg, boolean warn)
    {
        if(warn)
            log.warning("[" + info.getName() + "] " + msg);
        else
            log.info("[" + info.getName() + "] " + msg);
    }
    public static void printToPlayer(Player p, String msg, boolean warn)
    {
        String color = "";
        if(warn)
            color += ChatColor.RED + "";
        else
            color += ChatColor.AQUA + "";
        color += "[ArenaGodPlus]";
        p.sendMessage(color + ChatColor.WHITE + msg);
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
}