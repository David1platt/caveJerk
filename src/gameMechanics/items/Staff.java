package gameMechanics.items;

import java.util.ArrayList;
import java.util.Random;

import gameMechanics.monsters.Monster;

public class Staff extends Items {
	
	public Staff(){
		super("staff", 5);
	}
	
	public ArrayList<Integer> itemAbility(ArrayList<Monster> group)
	{
		int groupSize = group.size(), numHit = 0, hitMonster = 0;
		Random choice = new Random();
		ArrayList<Integer> enemiesHit; 
		if(groupSize > 2){
			numHit = choice.nextInt(groupSize);
			if(numHit == 0)
				numHit += 1;
			System.out.println("You push through the group of monsters striking " + numHit + " enemies." );
			enemiesHit = new ArrayList<Integer>();
			for(int i = 0; i < numHit; i++)
			{
				hitMonster = choice.nextInt(groupSize - 1);
				if(group.get(hitMonster).getHp() > 0)
					enemiesHit.add(hitMonster);
				else {
					hitMonster = choice.nextInt(groupSize - 1);
					numHit += 1;
				}
			}	
		}
		else
		{
			enemiesHit = new ArrayList<Integer>();
			int enemy = 0;
			enemiesHit.add(enemy);
		}
		return enemiesHit;
	}	
}
