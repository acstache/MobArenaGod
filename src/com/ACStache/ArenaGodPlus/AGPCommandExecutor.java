package com.ACStache.ArenaGodPlus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AGPCommandExecutor implements CommandExecutor
{
    private final String CMDS = "/agp, /agp status, /agp reload, /agp give playername";
    private ArenaGodPlus plugin;
    
    public AGPCommandExecutor(ArenaGodPlus plugin)
    {
        this.plugin = plugin;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("god") || command.getName().equalsIgnoreCase("agp")) {
            Player player = null;
            if(sender instanceof Player)
                player = (Player)sender;
            
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("status")) {
                    if(player != null && player.hasPermission("ArenaGodPlus.status")) {
                        if(AGPSetter.isGod(player))
                            ArenaGodPlus.printToPlayer(player, "You are a God", false);
                        else
                            ArenaGodPlus.printToPlayer(player, "You are not a God", false);
                    }
                    else {
                        if(player != null)
                            ArenaGodPlus.printToPlayer(player, "You don't have permission to do that.", true);
                        else
                            ArenaGodPlus.printToConsole("You can't use that command from the console", true);
                    }
                }
                else if(args[0].equalsIgnoreCase("reload")) {
                    if(player != null && player.hasPermission("ArenaGodPlus.reload") || !(sender instanceof Player)) {
                        AGPConfig.loadConfig();
                        if(player != null)
                            ArenaGodPlus.printToPlayer(player, "Config reloaded", false);
                        else
                            ArenaGodPlus.printToConsole("Config reloaded", false);
                    }
                    else
                        ArenaGodPlus.printToPlayer(player, "You don't have permission to do that.", true);
                }
                else if(args[0].equalsIgnoreCase("give")){
                    if(player != null && player.hasPermission("ArenaGodPlus.give") || !(sender instanceof Player)) {
                        boolean foundPlayer = false;
                        for(Player p : plugin.getServer().getOnlinePlayers()) {
                            if(args[1].equals(p.getName())) {
                                AGPSetter.setGod(p);
                                if(player != null)
                                    ArenaGodPlus.printToPlayer(player, p.getName() + " given God Mode", false);
                                else
                                    ArenaGodPlus.printToConsole(p.getName() + "given God Mode", false);
                                foundPlayer = true;
                                break;
                            }
                        }
                        if(!foundPlayer) {
                            if(player != null)
                                ArenaGodPlus.printToPlayer(player, args[1] + " not found online", true);
                            else
                                ArenaGodPlus.printToConsole(args[1] + " not found online", true);
                        }
                    }
                }
                else {
                    if(player != null)
                        ArenaGodPlus.printToPlayer(player, "Please type in one of " + CMDS, true);
                    else
                        ArenaGodPlus.printToConsole("You can't use that command from the console", true);
                }
            }
            else {
                if(player != null && (player.hasPermission("ArenaGodPlus.toggle"))) {
                    AGPSetter.setGod(player);
                }
                else {
                    if(player != null)
                        ArenaGodPlus.printToPlayer(player, "You don't have permission to do that.", true);
                    else
                        ArenaGodPlus.printToConsole("You can't use that command from the console", true);
                }
            }
        }
        return false;
    }
}