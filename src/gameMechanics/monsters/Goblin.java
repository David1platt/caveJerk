package gameMechanics.monsters;

//import java.util.ArrayList;
import java.util.List;

public class Goblin extends Monster implements MonsterAction {

	public Goblin() {
		super();
		this.type = "goblin";
		this.startHp = randomNumber.nextInt(10) + 3;
		this.hp = startHp;
		this.combatScore = 13 + (hp / 2);
		this.dmg = 4;
		this.ac = 7;
	}

	protected int goblinSpecial(List<Monster> gang) {
		int check = 0, dmg = 0;
		if (gang.size() > 1) {
			System.out.println("The frantic movements of the goblin make you lose track of the enemies.");
			for (Monster it : gang) {
				check = randomNumber.nextInt(10);
				if (check > 5) {
					dmg += randomNumber.nextInt(it.getDmg()) + 1;
					System.out.println("The " + it.getType()
							+ " steps in and visciously slashes you from a weak point in your guard.");
				}
			}
		} else
			System.out.println("The goblin eyes your weapon nervously...");
		return dmg;
	}

}
