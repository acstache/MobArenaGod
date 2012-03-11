package com.ACStache.ArenaGodPlus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class AGPConfig
{
    private static YamlConfiguration config = new YamlConfiguration();
    private static File file;
    private static Boolean persist;
    private static HashMap<String,HashSet<Boolean>> persistGods = new HashMap<String,HashSet<Boolean>>();
    private static HashSet<World> excludedWorlds = new HashSet<World>();
    
    public static void loadConfig(File f)
    {
        file = f;
        loadConfig();
    }
    public static void loadConfig()
    {
        try
        {
            config.load(file);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("[ArenaGodPlus] No config found, creating one for you");
            config.set("persistence", false);
            config.set("excluded-worlds", Arrays.asList("someWorld", "someWorld_nether", "someWorld_the_end"));
        }
        catch(Exception e)
        {
            System.out.println("[ArenaGodPlus] An Error has occured. Try deleting your config and reloading ArenaGodPlus");
        }
        finally
        {
            persist = config.getBoolean("persistence");
            if(persist)
            {
                if(config.getStringList("gods") == null) {return;}
                    
                for(String s : config.getStringList("gods"))
                {
                    if(persistGods.get(s) == null)
                    {
                        persistGods.put(s, new HashSet<Boolean>());
                        persistGods.get(s).add(true);
                    }
                    else
                    {
                        if(persistGods.get(s).contains(true))
                            continue;
                        else
                            persistGods.get(s).add(true);
                    }
                }
            }
            if(config.contains("excluded-worlds"))
            {
                for(String s : config.getStringList("excluded-worlds"))
                {
                    if(Bukkit.getServer().getWorld(s) != null)
                        excludedWorlds.add(Bukkit.getServer().getWorld(s));
                    else
                        System.out.println("[ArenaGodPlus] The world " + s + " is not found for AGP Exclusion");
                }
            }
        }
        
        try
        {
            config.save(file);
        }
        catch (Exception e)
        {
            System.out.println("[ArenaGodPlus] An Error has occured. Try deleting your config and reloading ArenaGodPlus");
        }
    }
    
    public static void addPersGod(Player p)
    {
        try
        {
            config.load(file);
        }
        catch(Exception e)
        {
            System.out.println("[ArenaGodPlus] An Error has occured. Try deleting your config and reloading ArenaGodPlus");
        }
        finally
        {
            String[] playerName = {p.getName()};
            config.set("gods", Arrays.asList(playerName));
            try
            {
                config.save(file);
            }
            catch (Exception e)
            {
                System.out.println("[ArenaGodPlus] An Error has occured. Try deleting your config and reloading ArenaGodPlus");
            }
        }
    }
    
    public static boolean getPersGod(Player p)
    {
        boolean b = false;
        if(!getPersistence() || config.getStringList("gods") == null)
        {
            return b;
        }
        for(String s : config.getStringList("gods"))
        {
            if(s.equals(p.getName()))
                b = true;
        }
        return b;
    }
    
    public static void removePersGod(Player p)
    {
        try
        {
            config.load(file);
        }
        catch(Exception e)
        {
            System.out.println("[ArenaGodPlus] An Error has occured. Try deleting your config and reloading ArenaGodPlus");
        }
        finally
        {
            config.set("gods." + p.getName(), null);
            try
            {
                config.save(file);
            }
            catch (Exception e)
            {
                System.out.println("[ArenaGodPlus] An Error has occured. Try deleting your config and reloading ArenaGodPlus");
            }
        }
    }
    
    public static HashSet<World> getExcludedWorlds()
    {
        return excludedWorlds;
    }
    
    public static Boolean getPersistence()
    {
        return persist;
    }
}