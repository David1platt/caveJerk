package gameMechanics.items;

import java.util.List;
import java.util.Random;

import gameMechanics.monsters.Monster;

public class SilenceScroll extends Items {
	
	public SilenceScroll() {
		super("silence scroll", 0);
	}
	
	public List<Monster> itemAbility(List<Monster> enemies) {
		for(Monster monster : enemies) {
			if(monster.getType().equals("orc")) {
				Random check = new Random();
				int successCheck = check.nextInt(10);
				if(successCheck < 7) {
					monster.setAbilitySave(false);
					System.out.println("One of the orc opens its mouth and attempts to roar, but no sound comes forth!");
					return enemies;
				}	
				else
					System.out.println("The scroll magically burns to ash, yet nothing has happened");
			}
		}
		return enemies;
	}

}
