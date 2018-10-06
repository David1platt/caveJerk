package gameMechanics;

/**
 *This class creates all items with their numeric value which is applied to different game stats.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

import gameMechanics.items.*;

public class Inventory
{
  private int foundI;
  @SuppressWarnings("unused")
  private int itemIndex;
  
  public static ArrayList<Items> allItems = new ArrayList<Items>(); 
  //below is a list of item objects that are created
  //private Items thing;
  private Items lArmor = new Armor("leather armor", 3);
  private Items cArmor = new Armor("chain mail", 5);
  private Items pArmor = new Armor("plate mail", 7);
  private Items swWeap = new Sword();
  private Items aMace = new Mace();
  private Items aStaff = new Staff();
  private Items aHammer = new Hammer();
  private Items liHeal = new LightHealingPotion();
  private Items meHeal = new MediumHealingPotion();
  private Items crHeal = new CriticalHealingPotion();
  private Items sceptor = new Artifacts("sceptor of Argos the Redeemer", 15);
  
  private Random itemCheck = new Random();//when the object is created we have the ability to choose certain items at random and return them
  /**
   * below all created items are put into the allItems ArrayList
   */
  public Inventory(){
    allItems.add(lArmor);
    allItems.add(cArmor);
    allItems.add(pArmor);
    allItems.add(swWeap);
    allItems.add(aMace);
    allItems.add(aStaff);
    allItems.add(aHammer);
    allItems.add(liHeal);
    allItems.add(meHeal);
    allItems.add(crHeal);
    allItems.add(sceptor);
  }

    /**
     * this method generates index for random item and returns that item object
     */
    public Items randomItemCheck()
    {
        int chance = itemCheck.nextInt(10);
        //String randItem;
        if(chance > 5)
        {
            foundI = itemCheck.nextInt(9);
            //randItem = allItems.get(foundI).getItemType();
            //System.out.println("You have found one " + randItem);
            itemIndex = foundI;
            return allItems.get(foundI);
        }
        else
        {
            return null;    
        }
    }
    /*******
     ** method returns index for random item
     ******/
    public int returnItemIndex()
    {
        return foundI;    
    }
    public static Items itemFactory(String itemType){
    		switch(itemType)
    		{
    		case "leather armor": 
    			return new Armor("leather armor", 3);
    		case "chain mail":
    			return new Armor("chain mail", 5);
    		case "plate mail":
    			return new Armor("plate mail", 7);
    		case "mace":	
				return new Mace();
    		case "warhammer":
    			return new Hammer();
    		case "staff":
    			return new Staff();
    		case "sword":
    			return new Sword();
    		case "light healing potion":
    			return new LightHealingPotion();
    		case "medium healing potion":
    			return new MediumHealingPotion();
    		case "critical healing potion":
    			new CriticalHealingPotion();
			default:
				System.out.println("item name did not match");
				return null;
    		}    			
    }
   /*****
   ** return an item from the arraylist based off its' index
   ******/
  public static Items getItem(int index)
  {
      return allItems.get(index);
  } 
  
  public int getInventorySize()
  {
	  return allItems.size();
  }
    
}

