package com.dgco.pvptest;

import net.lordofthecraft.arche.ArcheCore;
import net.lordofthecraft.arche.interfaces.Persona;
import net.lordofthecraft.arche.interfaces.PersonaHandler;

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
		
		PersonaHandler phandler = ArcheCore.getControls().getPersonaHandler();
        Player p = (Player) sender; 
        if(phandler == null) //Handler check
        {
        	sender.sendMessage("persona Handler not init?");
        	return true;
        }
        if(!phandler.hasPersona(p)) //Check for active persona
        {
            sender.sendMessage("You do not have an active persona! This plugin requires one to work.");
            return true;
        }
        
        Persona persona = null;
        try
        {
        	persona = phandler.getPersona(p); //Get the persona
        } catch(Exception e)
        {
        	sender.sendMessage("NULL ERROR! INFORM DGCO OF THIS! THIS IS VERY BAD!");
        	return false;
        }
		
		if(cmd.getName().equalsIgnoreCase("pvp")) // Main logic.
        {
        	if(args.length < 1) // check for modifiers
        	{
        		sender.sendMessage("This command requires a modifier to use");
        		return false;
        	}
        	if(args[0].equalsIgnoreCase("create")) // if it has the create modifier
        	{
        		//create a sheet for the player
        		plugin.database.sheetlist.put(persona,new PvPCharacterSheet());
        		
        		sender.sendMessage("A basic sheet has been created for you!");
        		//TODO: Character Creation methods and stuff go here
        		return true;
        	}
        	
        	
        	
        	
        	/*
        	 * ALL COMMANDS BELOW HERE REQUIRE A CHARACTER SHEET TO USE. BE SURE TO REMEMBER THAT!
        	 */
        	
        	PvPCharacterSheet sheet = plugin.database.sheetlist.get(persona); //get the character sheet
    		if(sheet == null)
    		{
    			sender.sendMessage("This command requires a character sheet to use. Type /pvp create to make one");
    			return true;
    		}
    		/*
    		 * COMMANDS START HERE:
    		 */
    		
    		
        	if(args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("infodump"))
        	{
        		sender.sendMessage(persona.getName()+"'s Stat Sheet:");
        		sender.sendMessage("Earth: "+ sheet.getEarthRing() + " (Stamina: "+ sheet.stamina + ", Willpower: "+ sheet.willpower + ")");
        		sender.sendMessage("Air: "+ sheet.getAirRing() + " (Reflexes: "+ sheet.reflexes + ", Awareness: "+ sheet.awareness + ")");
        		sender.sendMessage("Water: "+ sheet.getWaterRing() + " (Strength: "+ sheet.strength + ", Perception: "+ sheet.perception + ")");
        		sender.sendMessage("Fire: "+ sheet.getFireRing() + " (Agility: "+ sheet.agility + ", Intelligence: "+ sheet.intelligence + ")");
        		sender.sendMessage("Void: "+ sheet.getVoidRing());
        		sender.sendMessage("Initiative: " + sheet.getInitiative() + ", Armor Level: " + sheet.getBaseArmorTN()
        				+ ", Wounds: " + sheet.wounds + "/" + sheet.getMaxWounds() + " (-"+ sheet.getWoundPenalty() + " Penalty)"); 
        		PvPRoll dmg = sheet.getBaseDamage();
        		dmg.add(new PvPRoll(3,2));
        		sender.sendMessage("Attack: "+ sheet.getBaseAttack() + ", Damage: "+ dmg);
        		sender.sendMessage("Rank: "+ sheet.getRank() + ", Insight: " + sheet.getInsight() + ", XP: "+ sheet.getXp() +"/"+ sheet.getTotalXp());
        		//TODO: Change to use real armor and weapons, rather than base
        		
        		
        		return true;
        	}
        	if(args[0].equalsIgnoreCase("buy") || args[0].equalsIgnoreCase("purchase") || args[0].equalsIgnoreCase("level"))
        	{
        		if(args.length < 2)
        		{
        			sender.sendMessage("This command requires a specified attribute to work.");
        			return true;
        		}
        		Rings attrib = null;
        		if(args[1].equalsIgnoreCase("Strength") || args[1].equalsIgnoreCase("str"))
        		{
        			attrib = Rings.STRENGTH;
        			
        			
        			/*if(sheet.purchaseAttribute(Rings.STRENGTH))
        			{
        				sender.sendMessage("You have gained a point in your Strength attribute!");
        				return true;
        			} else {
        				sender.sendMessage("You do not have enough XP for this action.");
        			return true;
        			}*/
        		} else if(args[1].equalsIgnoreCase("perception") || args[1].equalsIgnoreCase("per"))
        		{
        			attrib = Rings.PERCEPTION;
        		} else if(args[1].equalsIgnoreCase("stamina") || args[1].equalsIgnoreCase("sta") || args[1].equalsIgnoreCase("stm"))
        		{
        			attrib = Rings.STAMINA;
        		} else if(args[1].equalsIgnoreCase("Willpower") || args[1].equalsIgnoreCase("will"))
        		{
        			attrib = Rings.WILLPOWER;
        		}  else if(args[1].equalsIgnoreCase("reflexes") || args[1].equalsIgnoreCase("ref"))
        		{
        			attrib = Rings.REFLEXES;
        		}  else if(args[1].equalsIgnoreCase("awarness") || args[1].equalsIgnoreCase("aware"))
        		{
        			attrib = Rings.AWARENESS;
        		}  else if(args[1].equalsIgnoreCase("agility") || args[1].equalsIgnoreCase("agl") || args[1].equalsIgnoreCase("agil"))
        		{
        			attrib = Rings.AGILITY;
        		}  else if(args[1].equalsIgnoreCase("intelligence") || args[1].equalsIgnoreCase("int"))
        		{
        			attrib = Rings.INTELLIGENCE;
        		}  else if(args[1].equalsIgnoreCase("void") || args[1].equalsIgnoreCase("vd"))
        		{
        			attrib = Rings.VOID;
        		}
        		
        		if(sheet.purchaseAttribute(attrib))
    			{
    				sender.sendMessage("You have gained a point in your "+ attrib +" attribute!");
    				return true;
    			} else {
    				sender.sendMessage("You do not have enough XP for this action.");
    				return false;
    			}
        	}
        	if(args[0].equalsIgnoreCase("damage"))
        	{
        		if(args.length < 2)
        		{
        			sender.sendMessage("This command requires an number to specifiy how much damage you've taken.");
        			return true;
        		}
        		int damage =0;
        		try
        		{
        			damage = Integer.parseInt(args[1]);
        		} catch (NumberFormatException e)
        		{
        			sender.sendMessage("Damage argument must be a whole number!");
        			return false;
        		}
        		sheet.wounds += damage;
        		sender.sendMessage("You have taken "+damage+ " wounds! Your total is now "+sheet.wounds+". You suffer a "+ sheet.getWoundPenalty() + " penalty on all rolls");
        		return true;
        		
        	}
        	if(args[0].equalsIgnoreCase("xp"))
        	{
        		if(args.length < 2)
        		{
        			sender.sendMessage("This command requires an number to specifiy how much xp to recieve.");
        			return true;
        		}
        		int xp =0;
        		try
        		{
        			xp = Integer.parseInt(args[1]);
        		} catch (NumberFormatException e)
        		{
        			sender.sendMessage("XP argument must be a whole number!");
        			return false;
        		}
        		sheet.recieveXp(xp);
        		sender.sendMessage("You have gained "+xp+" XP. Your totals are now "+sheet.getXp()+"/"+sheet.getTotalXp()+".");
        		return true;
        		
        	}
        	if(args[0].equalsIgnoreCase("heal"))
        	{
        		sheet.wounds = 0;
        		return true;
        	}
        	if(args[0].equalsIgnoreCase("roll"))
        	{
        		if(args.length < 2) // check for modifiers
            	{
            		sender.sendMessage("This command requires an additional modifier to use");
            		return false;
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
        			sendMessageToAllPlayers(p.getDisplayName()+" has rolled a "+roll.Resolve()+" for their Damage Roll!");
        			return true;
        		}
        		
        		return false;
        		
        	}
        }
		return false;
	}
	
	private void sendMessageToAllPlayers(String str) //TODO: Change to send to local players only
	{
		for(World w : plugin.getServer().getWorlds()){
		    for(Player p : w.getPlayers()){
		    	p.sendMessage(str);
		    }
		}
	}

}
