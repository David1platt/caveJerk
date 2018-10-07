package gameMechanics.items;

import java.util.ArrayList;
import java.util.Random;

import gameMechanics.monsters.Monster;
import gameMechanics.monsters.MonsterHorde;

public class SilenceScroll extends Items {
	
	public SilenceScroll() {
		super();
	}
	
	public MonsterHorde itemAbility(MonsterHorde monsters) {
		ArrayList<Monster> enemies = monsters.getHorde();
		for(Monster monster : enemies) {
			if(monster.getType().equals("orc")) {
				Random check = new Random();
				int successCheck = check.nextInt(10);
				if(successCheck < 7) {
					monster.setAbilitySave(false);
					enemies.remove(monster);
					enemies.add(monster);
					monsters.setMonsterGroup(enemies);
					return monsters;
				}	
			}
		}
		return monsters;
	}

}
