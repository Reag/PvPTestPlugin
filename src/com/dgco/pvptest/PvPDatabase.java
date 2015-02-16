package com.dgco.pvptest;

import java.util.HashMap;

import net.lordofthecraft.arche.interfaces.Persona;

public class PvPDatabase {

	public HashMap<Persona, PvPCharacterSheet> sheetlist;
	
	public PvPDatabase()
	{
		sheetlist = new HashMap<Persona, PvPCharacterSheet>();
	}
}
