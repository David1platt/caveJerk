package gameMechanics.items;

import java.security.SecureRandom;

import gameMechanics.monsters.Monster;

public class Hammer extends Items {

	public Hammer() {
		super("warhammer", 7);
	};

	public int itemAbility(Monster enemy) throws InterruptedException {
		SecureRandom chance = new SecureRandom();
		chance.generateSeed(8);
		int specialCheck = chance.nextInt(100);
		if (specialCheck < 36) {
			int dmg = chance.nextInt(5);
			int modifiedenemyCs = enemy.getCombatScore() - (dmg + 2);

			// group.get(enemy).setCombatScore(modifiedenemyCs);
			String spec = "The spiked end of your warhammer visciously tears into your opponents armor leaving a large bloody tear.\nYour opponents abillity to defend is severely impeded!";
			UI.ColorInterface.txtColorRm("cyan", spec);
			return modifiedenemyCs;
		}

		return -1;
	}

}
