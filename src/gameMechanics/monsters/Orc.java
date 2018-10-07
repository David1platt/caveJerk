package gameMechanics.monsters;

import java.security.SecureRandom;
import java.util.ArrayList;

public class Orc extends Monster implements MonsterAction {

	public Orc(){
		super();
		this.type = "orc";
		this.startHp = randomNumber.nextInt(10) + 3;
        this.hp = startHp;
        this.combatScore = 13 + (hp/2); 
        this.dmg = 6;
        this.ac = 7;
	}

	
	 public ArrayList<Monster> orcSpecial(ArrayList<Monster> gang)
	 {
	    	int addMonsters = 0, check = 0;
	    	SecureRandom rand = new SecureRandom();
	    	rand.setSeed(8);
	    	check = rand.nextInt(100) + 1;
	    	if(this.hp > 0){
		    	if(check > 70 && abilitySave == true)
		    	{
		    		addMonsters = randomNumber.nextInt(3);
		    		Monster[] creatureList = new Monster[addMonsters];
		    		for(int i = 0; i < addMonsters; i++)
		    		{
		    			check = randomNumber.nextInt(3);
		    			switch(check){
		    				case 0:
		    					Monster mon1 = new Orc();
		    					creatureList[i] = mon1;
		    					break;
		    				case 1:
		    					Monster mon2 = new Goblin();
		    					creatureList[i] = mon2;
		    					break;
		    				case 2:
		    					Monster mon3 = new Troll();
		    					creatureList[i] = mon3;
		    					break;
		    				case 3:
		    					Monster mon4 = new Ogre();
		    					creatureList[i] = mon4;
		    					break;
		    			}
		    		}
		    		System.out.print("The orc lets out a wild howl; you hear the clomping of feet as " + addMonsters + " beasts: "); 
		       		for(int i = 0; i < addMonsters; i++)
		       		{
		       			gang.add(creatureList[i]);
		       			System.out.print("a " + creatureList[i].getType() + " ");
		       		}
		    		System.out.print("come forth from the darkness.\n");
		    	}
		    	else {
		    		abilitySave = true;
		    		System.out.println("The orc opens its mouth to shout but is strangely silent");
		    	}
	    	}
	    	return gang;
	    }
}
