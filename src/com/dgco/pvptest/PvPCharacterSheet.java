package com.dgco.pvptest;

public class PvPCharacterSheet {
	
	
	private int xp; //Characters XP
	private int totalxp; // characters total xp
	
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
	
	private int emulti = 3;
	
	// simple sheet assumed for no bonuses.
	public PvPCharacterSheet()
	{
		xp = 40; //Starting XP is 40.
		totalxp = 40;
		
		insight = 0; // sets to zero. Will be updated in the updateRings() method
		
		wounds = 0;
		
		//Sets up all rings
		stamina = willpower = awareness = reflexes = intelligence = agility = strength = perception = voidRing = 2; //sets ring attributes to 2
		
		
		updateRings(); //updates rings. Also sets up maxWounds	
		
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
	
	private void updateInsight() // This private method updates the insight value for the character
	{
		insight = 10 * (earthRing + airRing + fireRing + waterRing + voidRing) + getTotalSkillRanks();
	}
	private void updateMaxWounds() // this private method updates the max wounds
	{
		// sets up the max health. 5 for the first health level, and 7 additional damage levels
		maxWounds = 5*earthRing + 7*earthRing*emulti; 
				
	}
	
	public boolean purchaseAttribute(Rings attrib) //attempts to purchase an attribute. Returns true on success, false otherwise
	{
		if(attrib.equals(Rings.STRENGTH))
		{
			int xpcost = (strength+1)*4;
			if (xpcost > xp)
			{
				return false;
			} else {
				xp -= xpcost;
				strength++;
				updateRings();
				return true;
			}
		} else if(attrib.equals(Rings.PERCEPTION))
		{
			int xpcost = (perception+1)*4;
			if (xpcost > xp)
			{
				return false;
			} else {
				xp -= xpcost;
				perception++;
				updateRings();
				return true;
			}
		} else if(attrib.equals(Rings.STAMINA))
		{
			int xpcost = (stamina+1)*4;
			if (xpcost > xp)
			{
				return false;
			} else {
				xp -= xpcost;
				stamina++;
				updateRings();
				return true;
			}
		} else if(attrib.equals(Rings.WILLPOWER))
		{
			int xpcost = (willpower+1)*4;
			if (xpcost > xp)
			{
				return false;
			} else {
				xp -= xpcost;
				willpower++;
				updateRings();
				return true;
			}
		} else if(attrib.equals(Rings.REFLEXES))
		{
			int xpcost = (reflexes+1)*4;
			if (xpcost > xp)
			{
				return false;
			} else {
				xp -= xpcost;
				reflexes++;
				updateRings();
				return true;
			}
		} else if(attrib.equals(Rings.AWARENESS))
		{
			int xpcost = (awareness+1)*4;
			if (xpcost > xp)
			{
				return false;
			} else {
				xp -= xpcost;
				awareness++;
				updateRings();
				return true;
			}
		} else if(attrib.equals(Rings.AGILITY))
		{
			int xpcost = (agility+1)*4;
			if (xpcost > xp)
			{
				return false;
			} else {
				xp -= xpcost;
				agility++;
				updateRings();
				return true;
			}
		} else if(attrib.equals(Rings.INTELLIGENCE))
		{
			int xpcost = (intelligence+1)*4;
			if (xpcost > xp)
			{
				return false;
			} else {
				xp -= xpcost;
				intelligence++;
				updateRings();
				return true;
			}
		} else if(attrib.equals(Rings.VOID))
		{
			int xpcost = (voidRing+1)*6;
			if (xpcost > xp)
			{
				return false;
			} else {
				xp -= xpcost;
				voidRing++;
				updateRings();
				return true;
			}
		}
		
		
		
		return false;
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
	public int getXp()
	{
		return xp;
	}
	public int getTotalXp()
	{
		return totalxp;
	}
	public int getMaxWounds()
	{
		return maxWounds;
	}
	public int getInsight()
	{
		return insight;
	}
	public int getRank() // Recursive method for finding the rank
	{
		return rankRecurse(insight-150); 
	}
	
	private int rankRecurse(int i) // output 1 for every 25 insight
	{
		if(i > 999)
			return 35;
		if(i < 0)
			return 1;
		
		return 1 + rankRecurse(i-25);
	}
	
	
	public void recieveXp(int xp)
	{
		this. xp+= xp;
		totalxp += xp;
	}
	public int getWoundPenalty() //Returns the total wound penalty. IS POSITIVE
	{
		int ret = 0; //No penalty by default
		
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
		return new PvPRoll(reflexes+1,reflexes, 0-getWoundPenalty());
	}
	public PvPRoll getBaseAttack()
	{
		return new PvPRoll(agility,agility, 0-getWoundPenalty());
	}
	public PvPRoll getBaseDamage()
	{
		return new PvPRoll(strength, 0, 0-getWoundPenalty());
	}

}
