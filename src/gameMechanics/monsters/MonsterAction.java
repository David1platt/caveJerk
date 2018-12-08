package gameMechanics.monsters;

//import java.util.ArrayList;
import java.util.List;
/**
 * Write a description of class Monsters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Random;

public interface MonsterAction {

	// monster hitpoints variable
	public Random randomNum = new Random();

	// protected void special() {};

	/*
	 * public void setQueen() { int comScore = 15 + (hp/3);
	 * setMonster("Queen Gobbleblargh", 65, 10, 5, comScore);
	 * 
	 * }
	 */

	default int monsterDamage(List<Monster> gang, Monster beast) {
		int dmg = 0;
		switch (beast.getType()) {
		case "goblin":
			dmg = (((Goblin) beast).goblinSpecial(gang));
			break;
		case "orc":
			dmg = randomNum.nextInt(beast.getDmg()) + 1;
			break;
		case "troll":
			dmg = randomNum.nextInt(beast.getDmg()) + 1;
			break;
		case "ogre":
			dmg = randomNum.nextInt(beast.getDmg()) + 1;

			break;
		default:
			return -1;
		}
		return dmg;
	}

	default void monsterHealthCheck(Monster beast) throws InterruptedException {
		String[] badWoundMssgs = {
				" stumbles and takes a deep breath. You see blood flowing freely from deep wounds. It cannot stand up to you much longer. ",
				" glares at you with madness in its eyes as it clutches a brutal wound. ",
				" shakes in a terrible trembling fit as it coughs on its own blood. " };
		String lghtWound = "\nThe " + beast.getType()
				+ " is bruised and battered, but stares you down, roaring in fury.";
		double half = 0.6, quarter = 0.30, currPercent = 0;
		currPercent = (beast.hp / (double) beast.startHp);
		if (currPercent <= half && currPercent > quarter)
			UI.ColorInterface.txtColorRm("yellow", lghtWound);
		else if (currPercent <= quarter && currPercent > 0) {
			int check;
			check = randomNum.nextInt(3);
			String badWound = "\nThe " + beast.getType() + badWoundMssgs[check];
			UI.ColorInterface.txtColorRm("orange", badWound);
		} else if (currPercent <= 0) {
			String death = "The " + beast.getType() + " collapses to the ground, clutching at a mortal wound. ";
			UI.ColorInterface.txtColorRm("red", death);
		}
	}

}
