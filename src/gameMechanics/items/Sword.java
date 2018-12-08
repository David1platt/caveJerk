package gameMechanics.items;

import java.security.SecureRandom;

public class Sword extends Items {

	public Sword() {
		super("sword", 8);
	}

	public int itemAbility() throws InterruptedException {
		SecureRandom chance = new SecureRandom();
		int hits = 1;
		chance.generateSeed(8);
		if (chance.nextInt(100) < 51) {
			hits = chance.nextInt(3) + 1;
			String spec = "You are able to outmaneuver your opponent in a furious combination of " + hits + " slashes.";
			UI.ColorInterface.txtColorRm("cyan", spec);
			return hits;
		}
		return hits;
	}
}
