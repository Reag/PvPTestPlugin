package com.dgco.pvptest;

import org.bukkit.plugin.java.JavaPlugin;


public class PvPTestPlugin_Main extends JavaPlugin {

	public PvPEventHandler handler;
	public PvPDatabase database;
	
	
	/*
	 * This is the On Enable function for the plugin. When the plugin is run,
	 * this is where it all starts TODO: Everything T_T
	 */
	@Override
	public void onEnable() {
		
		getLogger().info("Starting up the PvP Test Plugin"); //Debug output
		
		//Setup Database
		database = new PvPDatabase();
		
		//Setup listener
		handler = new PvPEventHandler(this);
		getServer().getPluginManager().registerEvents(handler, this);
		
		//Setup Executor
		getCommand("pvp").setExecutor(new PvPCommandExecutor(this));
		
		

	}

	/*
	 * This is the On Disable function for the plugin. TODO: Save Player Data
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */

	@Override
	public void onDisable() {

	}
}
