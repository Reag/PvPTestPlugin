package com.dgco.pvptest;

public class PvPCharacterSheet {
	
	
	public int xp; //Characters XP
	private int insight; //Characters Insight, a shorthand for total level. This value should NEVER be changed directly
	
	private int earthRing; //This is the characters Endurance ring attribute. This value should NEVER be changed directly
	public int stamina; // characters stamina. Part of the Earth Ring
	public int willpower; // characters willpower. Part of the Earth Ring
	private int airRing; //This is the characters Intuition ring attribute. This value should NEVER be changed directly
	public int awareness; // characters awareness. Part of the Air Ring
	public int reflexes; // characters reflexes. Part of the Air Ring
	private int fireRing; // this is the characters Coordination Ring. This value should NEVER be changed directly
	public int intelligence; // characters int. Part of the Fire Ring
	public int agility; // characters agility. Part of the Fire Ring
	private int waterRing; // this is the characters Force Ring. This value should NEVER be changed directly
	public int strength; // characters strength. part of the Water Ring
	public int perception; // characters perception. part of the Water Ring
	private int voidRing; // this is the characters Void Ring. It represents the 'Spark of Life' and other intangibles.
	
	private int maxWounds; // this is the maximum damage a player can take
	public int wounds; // Characters current wound level
	
	
	
	// simple sheet assumed for no bonuses.
	public PvPCharacterSheet()
	{
		xp = 40; //Starting XP is 40.
		
		insight = 0; // sets to zero. Will be updated in the updateRings() method
		
		wounds = 0;
		
		//Sets up all rings
		stamina = willpower = awareness = reflexes = intelligence = agility = strength = perception = 2; //sets ring attributes to 2
		updateRings(); //updates rings. Also sets up maxWounds
		voidRing = 2; //manually set void ring, due to its unusual mechanics

		
		
	}
	
	public void updateRings() //This method updates the rings. Call this after changing a stat value
	{
		earthRing = Math.min(stamina,willpower);
		airRing = Math.min(awareness,reflexes);
		fireRing = Math.min(intelligence,agility);
		waterRing = Math.min(strength,perception);
		
		updateInsight();
		updateMaxWounds();
	}
	
	private void updateInsight() // This method updates the insight value for the character
	{
		insight = 10 * (earthRing + airRing + fireRing + waterRing + voidRing) + getTotalSkillRanks();
	}
	private void updateMaxWounds()
	{
		// sets up the max health. 5 for the first health level, and 7 additional damage levels
		int emulti = PvPConstants.getEarthMulti();
		maxWounds = 5*earthRing + 7*earthRing*emulti; 
				
	}
	
	// Getters for the rings.
	public int getEarthRing() {
		return earthRing;
	}
	public int getAirRing() {
		return airRing;
	}
	public int getFireRing() {
		return fireRing;
	}
	public int getWaterRing() {
		return waterRing;
	}
	public int getVoidRing() {
		return voidRing;
	}
	public int getMaxWounds()
	{
		return maxWounds;
	}
	public int getRank()
	{
		if(insight <= 149)
			return 1;
		return 0; //TODO: Fix math to return the real insight
	}
	
	public int getWoundPenalty()
	{
		int ret = 0; //No penalty by default
		int emulti = PvPConstants.getEarthMulti();
		
		if(wounds > 5*earthRing && wounds <= 5*earthRing + 1*earthRing*emulti) // filling the healthy points
		{
			ret = 0;	
		} else if(wounds > 5*earthRing + 1*earthRing*emulti && wounds <= 5*earthRing + 2*earthRing*emulti) 
		{
			ret = 3; // character is nicked
		} else if(wounds > 5*earthRing + 2*earthRing*emulti && wounds <= 5*earthRing + 3*earthRing*emulti)
		{
			ret = 5; // character is grazed
		} else if(wounds > 5*earthRing + 3*earthRing*emulti && wounds <= 5*earthRing + 4*earthRing*emulti)
		{
			ret = 10; // character is hurt
		} else if(wounds > 5*earthRing + 4*earthRing*emulti && wounds <= 5*earthRing + 5*earthRing*emulti)
		{
			ret = 15; // character is injured
		} else if(wounds > 5*earthRing + 5*earthRing*emulti && wounds <= 5*earthRing + 6*earthRing*emulti)
		{
			ret = 20; // character is crippled
		} else if(wounds > 5*earthRing + 6*earthRing*emulti && wounds <= 5*earthRing + 7*earthRing*emulti)
		{
			ret = 40; // character is down
		} else if(wounds > 5*earthRing + 7*earthRing*emulti)
		{
			ret = 9999; // character is out (KOed)
		}
			
		return ret;
	}
	
	public int getBaseArmorTN()
	{
		return 5 + reflexes*5;
	}
	//Returns the total amount of skill ranks the sheet has
	public int getTotalSkillRanks()
	{
		return 0;
	}
	
	public PvPRoll getInitiative()
	{
		return new PvPRoll(reflexes+1,reflexes);
	}
	public PvPRoll getBaseAttack()
	{
		return new PvPRoll(agility,agility);
	}
	public PvPRoll getBaseDamage()
	{
		return new PvPRoll(strength, 0);
	}

}
