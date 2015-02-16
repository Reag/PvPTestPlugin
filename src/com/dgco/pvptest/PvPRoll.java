package com.dgco.pvptest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PvPRoll {

	public int dicenum; //Dice to roll
	public int keepnum; //dice to keep
	public int modifier; //modifier to add
	public int explodeNumber;
	public Random rn;
	
	//Sets up the number of dice, how many to keep, modifiers to add, and the number to explode the dice on
	public PvPRoll(int dice, int keep, int modifier, int explodeNumber) 
	{
		dicenum = dice;
		keepnum = keep;
		this.modifier = modifier;
		this.explodeNumber = explodeNumber;
		rn = new Random();
	}
	public PvPRoll(int dice, int keep, int modifier) 
	{
		dicenum = dice;
		keepnum = keep;
		this.modifier = modifier;
		explodeNumber = 10;
		rn = new Random();
	}
	public PvPRoll(int dice, int keep) 
	{
		dicenum = dice;
		keepnum = keep;
		modifier = 0;
		explodeNumber = 10;
		rn = new Random();
	}
	
	public void add(PvPRoll roll) 
	{
		dicenum += roll.dicenum;
		keepnum += roll.keepnum;
	}
	public String toString()
	{
		String ret = "";
		
		ret += dicenum;
		ret += "k";
		ret += keepnum;
		
		return ret;
	}
	public int Resolve()
	{
		List<Integer> dice = new ArrayList<Integer>(); //list of rolls
		
		for(int i =0; i < dicenum; i++) //Fill list
		{
			dice.add(RollDice(0));
		}
		
		Collections.sort(dice); // Sort into ascending order
		
		int ret = 0; // Result of the dicerolls
		
		// Start at the last index, and loop keepnum times from the end
		for(int i = dicenum-1; i > dicenum - keepnum - 1; i--)
		{
			ret += dice.get(i);
		}
		
		ret += modifier;
		
		if(ret < 0)
			ret = 0;
		
		return ret;
	}
	
	private int RollDice(int loopbreak) //Rolls the dice and returns the number. ALWAYS pass the value of 0 when called.
	{
		if(loopbreak >= 10 || loopbreak < 0) // loopbreak to stop infinite regress
		{
			return 0;
		}
		int result = rn.nextInt(10)+1; // gen a random number between 1-10
		if(result >= explodeNumber) // if we are at or above the explosion number
			return result + RollDice(loopbreak+1); // we explode
		
		return result; // otherwise we return our diceroll
	}
	
}
