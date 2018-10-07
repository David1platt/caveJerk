package gameMechanics;

import java.util.List;
import java.util.Scanner;

import gameMechanics.items.Items;
import gameMechanics.items.SilenceScroll;
import gameMechanics.monsters.Monster;
import gameMechanics.monsters.MonsterHorde;
import gameMechanics.movement.CoordinateManager;
import gameMechanics.movement.Map;
import gameMechanics.movement.Room;

import java.util.ArrayList;
import java.security.SecureRandom;
import java.io.IOException;
public class Action
{
    private String keyWord, help = "Command descriptions:\nn, s, e, w- move character into the next room in the selected cardinal direction\n"
    									+	"[a]ttack- start combat with enemies in the room\n[g]et- get usable objects that are found in the room\n[i]nventory- check player inventory\n"
    									+	"use- use an item in player inventory for healing items syntax is: use light heal, use medium heal, use critical heal.\n[o]utfit- check on what items are currently equipped"
    									+   "\n[q]uick items- there are 3 slots available that let players use an item stored in the slot at any time during game play."
    									+	"\n[l]ook- display a room description again\n[e]quip- equip an item in inventory, syntax \"equip sword\"\ns[t]ats- check player stats\n[r]un- flee from enemies in the room\n" 
    									+   "quit- quits the game at any time";   
    private Items pickedUp;
    //private Inventory takenItem = new Inventory(); 
    private Combat battle;
    @SuppressWarnings("unused")
	private List<Items> equipList;
    private Scanner userIn;
    
    public enum Commands 
    {     	 
    	HELP("help"), 
    	ATTACK("attack"),
    	GET("get"), 
    	RUN("run"), 
    	INVENTORY("inventory"), 
    	USE("use"), 
    	OUTFIT("outfit"),
    	LOOK("look"),
    	LOAD("load"),
    	EQUIP("equip"),
    	SAVE("save"),
    	QUICKSLOTS("quick"),
    	STATS("stats"),
    	QUIT("quit"),
    	NORTH("n"), 
    	SOUTH("s"), 
    	EAST("e"), 
    	WEST("w"),
    	DOWN("down"),
    	UP("up");
    	public final String word;
    	Commands(final String words)
    	{
    		word = words;
    	}
    };
    
    public Action()
    {
    	battle = new Combat();
    	equipList = new ArrayList<Items>();
    	//userIn = new Scanner(System.in);
    }
       
    public String makeCommand()
    {
    	//keyWord = null;
        System.out.print("\nWhat is your next action:");
 	   userIn = new Scanner(System.in);
    	keyWord = userIn.nextLine().toLowerCase().trim();
    	userIn.close();
        return keyWord;
    }
    
    public String getCommand()
    {
        return keyWord;    
    }
    
   public void commands(CoordinateManager currLoc, Driver game, int levelNum, Inventory stuff, Player playerOne, Map level, MonsterHorde enemies, String noGo)throws IOException, StringIndexOutOfBoundsException, IndexOutOfBoundsException, InterruptedException
   {
	   Action doStuff = new Action();
	   doStuff.keyWord = " "; 
	   while(!doStuff.keyWord.equals("quit")) 
	   {
		   String error = "Please provide a valide command.\nType \"help\" for further assistance.";
		   if(playerOne.getHp() < 1)
			   break;
		   if(winCheck(playerOne, levelNum, currLoc))
			   break;
		   keyWord = doStuff.makeCommand();
		   
		   if((level.getRoom(currLoc).getMonsters() != null)  && (doStuff.keyWord.equals(Commands.GET.word) || doStuff.keyWord.contains(Commands.EQUIP.word) || doStuff.keyWord.contains(Commands.USE.word)))
			   battle.enemyFreeAttack(playerOne, level.getRoom(currLoc).getMonsters());
           if((doStuff.keyWord.equals(Commands.NORTH.word))
           	|| (doStuff.keyWord.equals(Commands.SOUTH.word))
           	|| (doStuff.keyWord.equals(Commands.EAST.word))
           	|| (doStuff.keyWord.equals(Commands.WEST.word)))
           {	
           	String cmd = doStuff.keyWord;
           	switch(cmd){
           		case "n":
           			cmd = "north";
           			break;
           		case "s":
           			cmd = "south";
           			break;
           		case "e":
           			cmd = "east";
           			break;
           		case "w":
           			cmd = "west";
           			break;
       			default:
       				System.out.println("invalid command");
       				break;
           	}
           	System.out.println("You decide to head " + cmd + ".");
           	goPlaces(cmd, level, game, stuff, currLoc, noGo, playerOne);
           }

		   else if(this.keyWord.equals(Commands.GET.word) || this.keyWord.charAt(0) == 'g')
		   	{
            	Room place = level.getRoom(currLoc);
            	if(place.getThing() != null)
                {
                    aquiredItem(place, playerOne);
                }
                else 
                {
                    System.out.println("There are no items available to pickup in the room");
                }
            }
		   	else if(doStuff.keyWord.equals(Commands.DOWN.word))
		   	{
               	levelNum += 1;
            	level = levelChange(levelNum, doStuff.keyWord);
            	currLoc = level.getLvlStartPt(levelNum, doStuff.keyWord);
            	level.getRoom(currLoc).format_PrintRm();
		   	}	
            else if((doStuff.keyWord.equals(Commands.UP.word)))
            {
            	levelNum -= 1;
            	level = levelChange(levelNum, doStuff.keyWord);
            	currLoc = level.getLvlStartPt(levelNum, doStuff.keyWord);
            	level.getRoom(currLoc).format_PrintRm();
            }
            else if((doStuff.keyWord.length() > 5) && (doStuff.keyWord.substring(0, 5).equals(Commands.EQUIP.word)))
            {
            	boolean check = plyrEquip(playerOne, doStuff.keyWord);// the command string is included to get the name of the item to be equipped
                if(check == false)
                	System.out.println("Item is not in your current inventory.");
            }
            else if(doStuff.keyWord.equals(Commands.INVENTORY.word) || doStuff.keyWord.charAt(0) == 'i')
            {
                checkInv(playerOne);
            }
            else if(doStuff.keyWord.equals(Commands.QUICKSLOTS.word) || doStuff.keyWord.equals("q"))
            {
            	System.out.println("What item would you like to put into a quick slot?");
            	userIn = new Scanner(System.in);
            	String thing = userIn.nextLine();
            	System.out.println("Which slot would you like to put the item into (1-3)?");
            	int slotNum = userIn.nextInt();
            	try {
            	playerOne.playerSetQuick(thing, slotNum);
            	}
            	catch(Exception e) {
            		System.out.println("Please provide the full proper name of the item you would like to save in a quick slot.");
            	}
            	userIn.close();
            }
            else if(doStuff.keyWord.equals("1") || doStuff.keyWord.equals("2") || doStuff.keyWord.equals("3"))
            {
            	try {
            		useItem(doStuff.keyWord, playerOne, enemies);
            	}
            	catch(Exception e) {
            		System.out.println("You do not have any items in your quick slots!");
            	}
            }
            else if((doStuff.keyWord.equals(Commands.OUTFIT.word)) || (doStuff.keyWord.charAt(0) == 'o'))
            {
                checkEq(playerOne);
            }
            else if(doStuff.keyWord.length() > 2 && doStuff.keyWord.substring(0, 3).equals(Commands.USE.word)) 
            {                 
                useItem(doStuff.keyWord, playerOne, enemies);
            }
            else if((doStuff.keyWord.equals(Commands.ATTACK.word)) || doStuff.keyWord.charAt(0) == 'a')
            {
            	enemies = level.getRoom(currLoc).getMonsters();
            	if(level.getRoom(currLoc).getMonsters() != null) {
            		enemies = level.getRoom(currLoc).getMonsters();
	            	try{
	            		enemies = battle.fight(playerOne, enemies, doStuff);
	            		level.getRoom(currLoc).setMonsters(enemies);
	            	}
	            	catch(Exception x){
	            		System.out.println("You must be hallucinating imaginery monsters!!!");
	            		System.out.println(x.getMessage());
	            	}
            	}
            	else
                	look(level, currLoc);
                	
            }
            else if(doStuff.keyWord.equals(Commands.RUN.word) || doStuff.keyWord.charAt(0) == 'r')
            {
            	String action = "You scramble away from the bloodthirsty beasts in a blind panic. ", warning = "You are cornered by these terrors from the deep! ";
            	boolean run = false;
            	enemies = level.getRoom(currLoc).getMonsters();
            	run = level.flee(playerOne, enemies);
            	if(run)
            	{
            		String cmd = doStuff.keyWord;
            		System.out.println(action);
            		goPlaces(cmd, level, game, stuff, currLoc, noGo, playerOne);
            	}
            	else
            		System.out.println(warning);
            }
            else if(doStuff.keyWord.equals(Commands.STATS.word) || doStuff.keyWord.charAt(0) == 't')
            {
                playerOne.printStats();
            }
            else if(doStuff.keyWord.equals(Commands.SAVE.word))
            {
            	System.out.println("Please provide a name for your save file: ");
            	userIn = new Scanner(System.in);
            	playerOne.setName(userIn.nextLine().trim());
            	userIn.close();
            	System.out.print(SaveState.assembleRecord(playerOne, currLoc, levelNum, level.getRoom(currLoc)));
            	
            }
            else if(doStuff.keyWord.equals(Commands.LOOK.word) || doStuff.keyWord.charAt(0) == 'l')
            {
            	look(level, currLoc);
            }
            else if(doStuff.keyWord.equals(Commands.HELP.word) || doStuff.keyWord.charAt(0) == 'h')
            {
            	System.out.println(help);
            }
            else if(doStuff.keyWord.equals(Commands.QUIT.word))
            {
            	break;
            }
            else
            {
            	System.out.println(error);
            }
	}
		  
    }
   /*******
    ** function takes Action, Map, Driver, Inventory, CoordinateManager, String, String args
    ** it checks if the player can go a direction then calls the movement function to change the 
    ** position of the player on the map by one
    ** checks if an encounter check has been run, if not then runs encounter check and saves any successful encounter check
    ** with enemies in a new MonserHorde object
 * @throws InterruptedException 
    ******/
   public boolean goPlaces(String doStuff, Map level, Driver game, Inventory stuff, CoordinateManager currLoc, String noGo, Player playerOne) throws InterruptedException
   {
		String monsterWarning = "Monsters are engaging you must run at random!!";
		boolean canGo = true;
		Room currPlace = level.getRoom(currLoc);
		
		if(doStuff.equals("r") || doStuff.equals("run"))//player attempts to flee
		{
			SecureRandom randDirection = new SecureRandom();
			randDirection.generateSeed(8);
			String warning = "Noo you've run right into a corner. But try escaping yet again! ";
			int direct = randDirection.nextInt(3);
			String directions[] = {"north", "south", "east", "west"}; 
			canGo = level.checkExits(directions[direct]);
			if(canGo)
			{	
				level.movement(directions[direct]);
				currPlace = level.getRoom(currLoc);
				currPlace.randSetRoom(level.getLevelNum(), playerOne);
				currPlace.format_PrintRm(); 
				return canGo;
			}
			else
			{
				System.out.println(warning);
				return canGo;
			}
		}
		else if(currPlace.getMonsters() != null && playerOne.getHp() > 0)
		{	
			System.out.println(monsterWarning);
			canGo = false;
		}	
		else //normal player movement
		{
			canGo = level.checkExits(doStuff); //confirms exit available
			if(canGo && currPlace.getMonsters() == null) 
			{
				level.movement(doStuff);
				currPlace = level.getRoom(currLoc);
				currPlace.randSetRoom(level.getLevelNum(), playerOne);
				currPlace.format_PrintRm();
				if(currPlace.getMonsters() != null)
				{		
					boolean fightCheck = false;
					MonsterHorde them = currPlace.getMonsters();
					fightCheck = them.monstersAttack(playerOne);
					if(fightCheck){
						this.keyWord = "enemyAttck";
						System.out.println("As soon as you enter the chamber you are surrounded and attacked!");
						them = battle.fight(playerOne, them, this);
						currPlace.setMonsters(them);
						if(playerOne.getHp() > 0)
							canGo = true;
						else
							canGo = false;
					}
				}
			}

		}
		return canGo;
   }
   
   public Map levelChange(int levlNum, String direct)throws NullPointerException, ArrayIndexOutOfBoundsException
   {
	   try{
		   	Map nextLvl = new Map(levlNum, direct);
		   	return nextLvl;
	   }
	   catch(IOException e)
	   {
	   	   System.out.println("Exception thrown: " + e.getMessage());
	   }
	   catch(ArrayIndexOutOfBoundsException z)
	   {
		   System.out.println("There isn't a staircase or passage that changes levels in this room.");
	   }
	   return null;
	}
    /*
     * 
      */
    private void aquiredItem(Room currRoom, Player playerOne)
    {
        pickedUp = currRoom.getThing();
        System.out.println("You have aquired " + pickedUp.getItemType());
        playerOne.putItemInventory(pickedUp);
        if(pickedUp.getItemType().equals(Inventory.getItem(10).getItemType()))
        	playerOne.setArtifactFound(true);
        pickedUp = null;
        currRoom.setItem(pickedUp);
    }
    
    private boolean plyrEquip(Player ply, String itName)
    {
        String item = itName.substring(6);//strips the word equip and the space out of the string
        List<Items> list = new ArrayList<Items>();
        list = ply.getEquipment();
        int eqSize = list.size();
        for(int i = 0; i < eqSize; i++)
        {
             if(item.equals(list.get(i).getItemType()))
             {
                 ply.equipItem(list.get(i));
                 System.out.println("You have equipped: " + item);
                 return true;
             }
        }   
        return false;        
    }
    
    private void look(Map area, CoordinateManager location) throws InterruptedException
    {
    	Room place = area.getRoom(location);
    	place.format_PrintRm();
    	//System.out.print(situation);
    }
    
    private void checkInv(Player check)
    {   
        List<Items> list = new ArrayList<Items>();
        list = check.getEquipment();
        int invSize = list.size();
        for (int i = 0; i < invSize; i++)
        {
            System.out.println("You have: " + list.get(i).getItemType() );
        }
    }
    private void checkEq(Player plyr)
    {
        Items[] stuff = null;
        stuff = plyr.getEquipped();
        int eqSz = stuff.length;
        for(int i = 0; i < eqSz; i++)
        {
            System.out.println("currently equipped: " + stuff[i].getItemType());
        }
    }
    public int useItem(String itemType, Player plyrOne, MonsterHorde monster) throws InterruptedException
    {
    	int result = 0;
    	if(itemType.equals("1") || itemType.equals("2") || itemType.equals("3"))
    	{
    		int index = Integer.valueOf(itemType) - 1;
    		Items[] qItems = plyrOne.getQuickS(); 
    		Items tool = qItems[index];
    		result = useItemType(tool, plyrOne, monster);
    		plyrOne.playerSetQuick("remove", index);
    		return result;
    	}
    	else
    	{
    		itemType = itemType.substring(4);
    		Items it = plyrOne.getItem(itemType);
    		if(it != null)
    		{
    			result = useItemType(it, plyrOne, monster);
    			return 0;
    		}
    		return 1;
    	}
    }
    
    
    private int useItemType(Items item, Player plyr, MonsterHorde monster)
    {
		if(item.getItemType().contains("heal"))
		{
			healType(item, plyr);
			return 0;
		}
		else if(item.getItemType().contains("bomb"))
		{
			int damage = 0;
			return damage;
		}
		else if(item.getItemType().contains("scroll"))
		{
			scrollType(item, monster);
			return 0;
		}
		return 0;
    }
    private void healType(Items typeHeal, Player playerOne)
    {   
            if((typeHeal.getItemType().equals("light healing potion")) || (typeHeal.getItemType().equals("medium healing potion")) || (typeHeal.getItemType().equals("critical healing potion")))
            {
            	int fix = typeHeal.getScore();
            	System.out.println("You have used a " + typeHeal.getItemType());
            	heal(fix, playerOne);    
            	//list.remove(i);
            }
    }
    
    private MonsterHorde scrollType(Items scroll, MonsterHorde monster) {
    	if(scroll.getItemType().contains("silence")) {
    		((SilenceScroll)scroll).itemAbility(monster);
    		return monster;
    	}	
    	else
    		return monster;
    }
    
    private void heal(int heal, Player playerOne)
    {
        int addHP;
        addHP = playerOne.getHp() + heal;
        playerOne.setHp(addHP);
         System.out.println("Your hitpoints are now at " + playerOne.getHp());
    }
    
    private boolean winCheck(Player playerOne, int levNum, CoordinateManager currLoc)
    {
    	//System.out.println(levNum + " " + currLoc.getRow() + " " + currLoc.getCol());
    	boolean hasItem = false;
    	//String artefactName = Inventory.getItem(10).getItemType();
    	hasItem = playerOne.getArtifactFound();
    	if((hasItem) && (levNum == 1) && (currLoc.getRow() == 6) && (currLoc.getCol() == 4))
    	{
    		System.out.println("You have braved the depths of these dark dungeons and come out victorious!");
    		return true;
    	}
    	else
    		return false;
    }
}


