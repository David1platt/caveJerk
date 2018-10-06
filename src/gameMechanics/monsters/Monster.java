package gameMechanics.monsters;

import java.util.ArrayList;
/**
 * Write a description of class Monsters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Random;

public class Monster implements MonsterAction{

    //monster hitpoints variable
    protected int startHp;
	protected int hp;
    //monster damage variable
    protected int dmg;
    //monster ac variable
    protected int ac;
    //combat score var
    protected int combatScore;
    //random number
    protected String type;
    protected Random randomNumber;
    protected int numberattcks;
    
   public Monster()
   {
	   hp = 0;
	   startHp = 0;
	   dmg = 0;
	   ac = 0;
	   combatScore = 0;
	   type = null;
	   randomNumber = new Random();
	   numberattcks = 1;
	   
   }
    
   public Monster(String type, int hp, int dmg, int ac, int combatScore)
   {
	   randomNumber = new Random();
       setMonster(type, hp, dmg, ac, combatScore);
       numberattcks = 1;
   }
   
   protected void setMonster(String type, int hp, int dmg, int ac, int combatScore)
   {
       this.type = type;
       this.startHp = hp;
       this.hp = hp;
       this.dmg = dmg;
       this.ac = ac;
       this.combatScore = combatScore;
       
   }
    
    

   //protected void special() {};

    /*public void setQueen()
    {
        int comScore = 15 + (hp/3);
        setMonster("Queen Gobbleblargh", 65, 10, 5, comScore);    
    
    }*/
    

    

    
    
   public String getType()
    {
        
        return this.type;    
    }
    public int getHp()
    {
        return this.hp;    
    }
    
    public void setHp(int update)
    {
        hp = update;    
    }
    public int getDmg()
    {
        return dmg;    
    }
    protected int getAc()
    {
        return this.ac;    
    }
    
    public void setCombatScore()
    {
        combatScore = ac + dmg + (hp/3);
    }
    public void setCombatScore(int score)
    {
    	this.combatScore = score;
    }
    public int getCombatScore()
    {
        return combatScore;    
    }
    
    public int getNmattacks()
    {
    	return numberattcks;
    }
    
    public void setNmattacks(int num)
    {
    	this.numberattcks = num;
    }
    
    @Override
    public String toString()
    {
    	String monVals = this.type + ";" + String.valueOf(this.hp) + ";" + String.valueOf(this.dmg) + ";" + String.valueOf(this.ac) + ";" + String.valueOf(this.combatScore) + ";" + String.valueOf(this.numberattcks); 
    	return monVals;
    }
    
      
}
