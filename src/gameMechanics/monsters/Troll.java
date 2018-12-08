package gameMechanics.monsters;

public class Troll extends Monster implements MonsterAction {

	public Troll() {
		super();
		this.type = "troll";
		this.startHp = randomNumber.nextInt(10) + 16;
		;
		this.hp = startHp;
		this.combatScore = 12 + (hp / 3);
		;
		this.dmg = 6;
		this.ac = 6;
	}

	public void trollSpecial() {
		int regen = 0, check = 0;
		check = randomNumber.nextInt(10);
		if (check > 4) {
			if (this.hp > 0) {
				regen = randomNumber.nextInt(4) + 1;
				this.hp += regen;
				System.out.println(
						"You here a strange crunching sound as the troll's wounds magically begin to close up, forming scaled ridges. (+"
								+ regen + ")");
			}
		}
	}

}
