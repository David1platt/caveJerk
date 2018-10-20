package gameMechanics.movement;

import java.security.SecureRandom;

import UI.ColorInterface;
import gameMechanics.Inventory;
import gameMechanics.Player;
import gameMechanics.items.Items;
import gameMechanics.monsters.MonsterHorde;
/**********
 **
 **@author (your name) 
 **@version (a version number or a date)
 *********/
public class Room
{
	private SecureRandom numbGen;
    private String descript;
    private String[] exit;
    private int xCoord, yCoord;
    private MonsterHorde enemies;
    private Items gear;
    private int currLev;

    public Room()
    {
    	numbGen = new SecureRandom();
    	this.descript = null;
    	exit = new String[4];
    	xCoord = 0;
    	yCoord = 0;
     	enemies = null;
    	gear = null;
    	currLev = 0;
    }
    public Room(String descript, String north, String south, String east, String west, int x, int y, int currLev)
    {
        numbGen = new SecureRandom();
        this.descript = descript;
        exit = new String[4];
        exit[0] = north;
        exit[1] = south;
        exit[2] = east;
        exit[3] = west;
        this.xCoord = x;
        this.yCoord = y;
        enemies = null;
        gear = null;
        this.currLev = currLev;
    }
   
    public String getRoom()
    {
        return descript;
    }
    public String[] checkExit()
    {
    	return this.exit;
    }
    public String[] getExit()
    {
        return exit;
    }
    
    public int getX()
    {
    	return xCoord;
    }
    
    public int getY()
    {
    	return yCoord;
    }
    
    public MonsterHorde getMonsters()
    {
    	return enemies;
    }
       
    public Items getThing()
    {
    	return gear;
    }
    
    public void setRoomDescript(String description)
    {
    	this.descript = description; 
    }
    
    public void setX(int x)
    {
    	this.xCoord = x;
    }
    
    public void setY(int y)
    {
    	this.yCoord = y;
    }
    
    public void setItem(Items thing)
    {
    	this.gear = thing;
    }
    
    public void setMonsters(MonsterHorde enemies)
    {
    	this.enemies = enemies;
    }
    
    
	public void randSetRoom(int lev, Player playerOne)
    {;
    	Inventory loot = new Inventory();
    	if(this.enemies == null){
    		encounter();
    	}
    	if(lev == 3 && playerOne.getArtifactFound() == false)
    	{
    		SecureRandom rand = new SecureRandom();
    		int num = rand.nextInt(10);
    		if(num > 8){
    			this.gear = Inventory.getItem(11);
    			System.out.println("You have found " + this.gear.getItemType() + ", take it! Now you must get back to the"
    			+ " entrance of the dungeon alive to succeed in your quest! ");
    		}
    	}
    	if(this.gear == null)
    		gear = loot.randomItemCheck();  	
    }
    
    /******
     ** this function takes no arguments creates an encounter including number and types of creatures to be printed on the page
     **
     *****/
        public boolean encounter()
        {
            int chance = 0, lvl = 0;
            switch(currLev)
            {
            	case 1:
            		lvl = currLev;
            	case 2:
            		lvl = currLev;
            	case 3:
            		lvl = currLev;
            }
            numbGen.generateSeed(8);
            chance = numbGen.nextInt(10);
            if(chance > 7)
            {
            	this.enemies = new MonsterHorde(lvl);
                this.enemies.setNumbCreaturesRand();
                this.enemies.chooseCreatures();
                //this.enemies.printCreatures();
              
               // System.out.println("\nWould you like to attack the enemy or run? ");
                return true;
            }      
            return false;
        }

    
    public void format_PrintRm() throws InterruptedException
    {
    	String place = this.descript;
    	ColorInterface.txtColorRm("green", place);
    	String exGroup = "\nExits: ";
    	String beasties = null, item = null;
    	for(int i = 0; i < this.exit.length; i++)
    	{
    		if(this.exit[i] != "")
    			exGroup = exGroup + exit[i] + " ";
    	}
    	ColorInterface.txtColorRm("blue", exGroup);
    	/*if(this.gear != null && this.enemies != null)
    	{
    		item ="\nYou have found a " + this.gear.getItemType();
    		ColorInterface.txtColorRm("yellow", item);
    		beasties ="\n\nYou still see the following group" + enemies.toString() + "moving in for the kill.";
    		ColorInterface.txtColorRm("red", beasties);
    		//fullRoom = place + exGroup + item + beasties;
    	}*/
    	if(this.gear != null)
    	{
    		item ="\nYou have found a " + this.gear.getItemType();
    		ColorInterface.txtColorRm("yellow", item);
    		//fullRoom = place + exGroup + item;
    	}
    	if(this.enemies != null)
    	{
    			beasties = this.enemies.printCreatures() + "moving in for the kill.\n";
    			ColorInterface.txtColorRm("red", beasties);    		
    	}

    }
}
