package gameMechanics.monsters;

/**
 * Write a description of class MonsterHorde here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 * setup a random number gen. & set a switch that calls each monster create n
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import gameMechanics.Player;
public class MonsterHorde
{
    //array for creatures
    private ArrayList<Monster> group;
    //random number
    private Random randNumber;
    //size of group
    private int levelMob;
    //creature type chooser
    private int creatType;
    
    public MonsterHorde(int lvl)
    {
        group = new ArrayList<Monster>();
        randNumber = new Random();
        levelMob = lvl;
        creatType = 0;
    }
    
    public int getNumbCreatures()
    {
    	return levelMob;
    }
    public void setNumbCreatures(int num)
    {
    	this.levelMob = num;
    }
    public int setNumbCreaturesRand()
    {
    	randNumber = new Random();
    	if(levelMob == 1)
    		levelMob = randNumber.nextInt(2)+1 ;
    	else if(levelMob == 2)
    		levelMob = randNumber.nextInt(3)+1 ;
    	else if(levelMob == 3)
    		levelMob = randNumber.nextInt(4)+1;
        
    	return levelMob;
    }
    
    public void chooseCreatures()
    {
    	randNumber = new Random();
    	Monster goblin, orc, troll, ogre;
    	int min = 1, max = 11;
        for(int i = 0; i<= levelMob; i++)
        {
            creatType = randNumber.nextInt(max - min) + 1;
            switch(creatType){
            case 1:
                orc = new Orc();
                group.add(orc);
                break;
            case 2:
                orc = new Orc();
                group.add(orc);
                break;
            case 3:
                goblin = new Goblin();
                group.add(goblin);
                break;
            case 4:
                goblin = new Goblin();
                group.add(goblin);
                break;
            case 5:
                troll = new Troll();
                group.add(troll);
                break;
            case 6:
                troll = new Troll();
                group.add(troll);
                break;
            case 7:
                orc = new Orc();
                group.add(orc);
                break;
            case 8:
                goblin = new Goblin();
                group.add(goblin);
                break;
            case 9:
                goblin = new Goblin();
                group.add(goblin);
                break;
            case 10:
            	ogre = new Ogre();
                group.add(ogre);
                break;
            default:
            	break;
            	
            }
        }
    }
    public boolean monstersAttack(Player playerOne){
    	List<Monster> beasts = this.group;
    	int monInit = 0;
    	for(Monster it : beasts){
    		monInit += it.getCombatScore();
    	}
    	if(playerOne.getCombatScore() < monInit/2)
    		return true;
    	else
    		return false;
    }
    
    public ArrayList<Monster> getHorde()
    {  
        return this.group;    
    }
    public void setMonster(Monster creature)
    {
    	group.add(creature);
    }
    public void setMonsterGroup(ArrayList<Monster> beasts)
    {
    	//level1Mob = beasts.size();
    	this.group = beasts;
    }
    public void removeAll()
    {
    	for(int i = 0; i < group.size(); i++)
    	{
    		group.remove(i);
    	}
    }
    @Override
    public String toString()
    {
    	String monsters = " ";
    	for(Monster it : group)
    	{
    		monsters = monsters + it.getType() + ", "; 
     	}
    	return monsters;
    }
    public String printCreatures()
    {
    	int num = group.size();
        String enems = "You see a group of " + num + " creatures: "; 
        for(Monster it : group)
        {
        	String baddie = it.getType() + " " + it.getHp() + "Hp, ";
        	enems += baddie;
        }
        return enems;
    }
        
}
        

    
    
    
    
    
    
    
    
    
    
    
    

