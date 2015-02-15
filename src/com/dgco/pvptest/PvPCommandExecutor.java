package com.dgco.pvptest;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvPCommandExecutor implements CommandExecutor {

	PvPTestPlugin_Main plugin;
	
	public PvPCommandExecutor(PvPTestPlugin_Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable,
			String[] args) {
		
		if(! (sender instanceof Player)) // player check
        {
            sender.sendMessage("This command is for players only");
            return false;
        }
		
		Player p = (Player) sender; 
		
		if(cmd.getName().equalsIgnoreCase("pvp"))
        {
        	if(args.length < 1) // check for modifiers
        	{
        		sender.sendMessage("This command requires a modifier to use");
        		return false;
        	}
        	if(args[0].equalsIgnoreCase("create")) // if it has the create modifier
        	{
        		//create a sheet for the player
        		plugin.database.sheetlist.put(p,new PvPCharacterSheet());
        		
        		sender.sendMessage("A basic sheet has been created for you!");
        		return true;
        	}
        	if(args[0].equalsIgnoreCase("roll"))
        	{
        		if(args.length < 2) // check for modifiers
            	{
            		sender.sendMessage("This command requires an additional modifier to use");
            		return false;
            	}
        		
        		PvPCharacterSheet sheet = plugin.database.sheetlist.get(p);
        		if(sheet == null) //sheet check
        		{
        			sender.sendMessage("You need to create a sheet first!");
        			return true;
        		}
        		PvPRoll roll;
        		
        		if(args[1].equalsIgnoreCase("Initiative") || args[1].equalsIgnoreCase("init"))
        		{
        			roll = sheet.getInitiative();
        			sendMessageToAllPlayers(p.getDisplayName()+" has rolled a "+roll.Resolve()+" for their Initiative!");
        			return true;
        		}
        		if(args[1].equalsIgnoreCase("Attack") || args[1].equalsIgnoreCase("att")|| args[1].equalsIgnoreCase("tohit"))
        		{
        			roll = sheet.getBaseAttack();
        			sendMessageToAllPlayers(p.getDisplayName()+" has rolled a "+roll.Resolve()+" for their Attack Roll!");
        			return true;
        		}
        		if(args[1].equalsIgnoreCase("Damage") || args[1].equalsIgnoreCase("dmg"))
        		{
        			roll = sheet.getBaseAttack();
        			roll.add(new PvPRoll(3,2));
        			sendMessageToAllPlayers(p.getDisplayName()+" has rolled a "+roll.Resolve()+" for their Attack Roll!");
        			return true;
        		}
        		return false;
        		
        	}
        }
		return false;
	}
	
	private void sendMessageToAllPlayers(String str)
	{
		for(World w : plugin.getServer().getWorlds()){
		    for(Player p : w.getPlayers()){
		    	p.sendMessage(str);
		    }
		}
	}

}
