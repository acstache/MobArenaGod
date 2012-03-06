package com.ACStache.ArenaGodPlus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class AGPConfig
{
    private static YamlConfiguration config = new YamlConfiguration();
    private static File file;
    private static Boolean persist;
    private static HashMap<String,HashSet<Boolean>> persistGods = new HashMap<String,HashSet<Boolean>>();
    
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
            System.out.println("[MobArenaGod] No config found, creating one for you");
            config.set("persistence", false);
        }
        catch(Exception e)
        {
            System.out.println("[MobArenaGod] An Error has occured. Try deleting your config and reloading MobArenaGod");
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
                        if(persistGods.get(s).contains(true)) {return;}
                        else
                        {
                            persistGods.get(s).add(true);
                        }
                    }
                }
            }
        }
        
        try
        {
            config.save(file);
        }
        catch (Exception e)
        {
            System.out.println("[MobArenaGod] An Error has occured. Try deleting your config and reloading MobArenaGod");
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
            System.out.println("[MobArenaGod] An Error has occured. Try deleting your config and reloading MobArenaGod");
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
                System.out.println("[MobArenaGod] An Error has occured. Try deleting your config and reloading MobArenaGod");
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
            System.out.println("[MobArenaGod] An Error has occured. Try deleting your config and reloading MobArenaGod");
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
                System.out.println("[MobArenaGod] An Error has occured. Try deleting your config and reloading MobArenaGod");
            }
        }
    }
    
    public static Boolean getPersistence()
    {
        return persist;
    }
}