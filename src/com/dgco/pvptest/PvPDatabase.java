package com.dgco.pvptest;

import java.util.HashMap;
import org.bukkit.entity.Player;

public class PvPDatabase {

	public HashMap<Player, PvPCharacterSheet> sheetlist;
	
	public PvPDatabase()
	{
		sheetlist = new HashMap<Player, PvPCharacterSheet>();
	}
}
