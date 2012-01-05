package com.ACStache.MobArenaGod;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.configuration.file.YamlConfiguration;

public class MAConfig
{
    private static YamlConfiguration config = new YamlConfiguration();
    private static Boolean persist;
    private static HashMap<String,HashSet<Boolean>> persistGods = new HashMap<String,HashSet<Boolean>>();
    
    public static void loadConfig(File file)
    {
        try
        {
            config.load(file);
        }
        catch(FileNotFoundException e)
        {
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
                    
                }
            }
        }
    }
    
    public static Boolean getPersistence()
    {
        return persist;
    }
}