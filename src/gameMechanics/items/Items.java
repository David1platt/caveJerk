package gameMechanics.items;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import gameMechanics.Inventory;
import gameMechanics.Player;
import gameMechanics.monsters.Monster;
/********
 **
 **@author (your name) 
 **@version (a version number or a date)
 *******/

public class Items{
    private String itemType;
    private int score;
    
    public Items()
    {
    	itemType = null;
    	score = 0;
    }
    public Items(String itemType, int score)
    {
        this.score = score;
        this.itemType = itemType;
        
    }

    public String getItemType()
    {
        return itemType;    
    }
    
    public int getScore()
    {
            return score;    
    }
    
    public Items confirmItem(String type)
    {
    	
    	Inventory stuff = new Inventory();
    	Items tool = null;
    	int invSize = stuff.getInventorySize();
    	for(int i = 0; i < invSize; i++)
    	{
    		if(type == Inventory.getItem(i).getItemType())
    		{	
    			return tool =  Inventory.getItem(i);
    		}
    	}
    	return tool;
    }
    
    public Monster applyDamage(Player plyr, Monster enemy)
    {
    	Items weapon = plyr.getWeapon();
		int dmg = doDamage(enemy, weapon);
		enemy.setHp(dmg);
        if(dmg > (enemy.getHp()/3) && enemy.getHp() > 2)
        	enemy.setCombatScore();
        return enemy;
    }
    
    public ArrayList<Monster> applyDamage(Items weapon, List<Integer> eHit, ArrayList<Monster> hord)
    {
    	for(Integer index : eHit) {
    		Monster badGuy = hord.get(index);
    		if(badGuy.getHp() > 0) {
    			int hitPower = doDamage(badGuy, weapon);
    			badGuy.setHp(hitPower);
    			hord.set(index, badGuy);
    			if(hitPower > (badGuy.getHp()/3) && badGuy.getHp() > 2)
    				hord.get(index).setCombatScore();
    		}
    		else {
    			hord.remove(index);
    		}    			
    	}
        return hord;
    }
    
    private int doDamage(Monster badGuy, Items weapon)
    {
    	SecureRandom chance = new SecureRandom();
    	int dmg = 0, dmgDone = 0, badHp = badGuy.getHp();
    	String weap = weapon.getItemType(), hitType ;
    	switch(weap)
    	{
    		case "sword":
    			hitType = "SHNICKT!";
    			break;
    		case "mace":
    			hitType = "CRUNCH!";
    			break;
    		case "staff":
    			hitType = "THUNK!";
    			break;
    		case "hammer":
    			hitType = "CRASH!";
    			break;
    		default:
    			hitType = " ";
    			break;
    	}
    	if(badHp > 0)
    	{
			dmg = chance.nextInt(weapon.score) + 1;
			dmgDone = badHp - dmg;//damage done to enemy
	        System.out.println(hitType + " you do " + dmg + " points of damage to your opponent!");
    	}
        return dmgDone;
    }
    
    @Override
    public String toString()
    {
    	String itemVals = this.itemType + " " + String.valueOf(this.score);
    	return itemVals;
    }
}



















