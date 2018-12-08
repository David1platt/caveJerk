package gameMechanics;

/**
 * Write a description of class Combat here.
 * PROBLEM WITH MONSTER SELECTOR!!!
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.security.SecureRandom;
import java.util.Scanner;

import gameMechanics.items.Armor;
import gameMechanics.items.Hammer;
import gameMechanics.items.Items;
import gameMechanics.items.Mace;
import gameMechanics.items.Staff;
import gameMechanics.items.Sword;
import gameMechanics.monsters.Monster;
import gameMechanics.monsters.MonsterHorde;
import gameMechanics.monsters.Ogre;
import gameMechanics.monsters.Orc;
import gameMechanics.monsters.Troll;

import java.util.List;
import java.util.ArrayList;

public class Combat {

	private SecureRandom initiative = new SecureRandom();

	/**********
	 ** Constructor for objects of class Combat
	 *********/
	public Combat() {
	}

	/**********
	 ** 
	 ** @param monster group
	 ** @return monster selection number
	 **********/
	public int selectMonster(List<Monster> gang) {
		int select = 0;
		select = initiative.nextInt(gang.size());
		return select;
	}

	/**********
	 ** 
	 ** @param playerOne
	 ** @param monster
	 ** @param gang
	 ** @return
	 *********/
	private final List<Monster> battleResults(Player playerOne, List<Monster> gang) throws InterruptedException {
		Monster enemy;
		int pFight = 0, eFight = 0, monsterOption = 0, deadEnemyIndex = -1, index = 0;
		monsterOption = selectMonster(gang);
		boolean enemyDied = false;
		Items weapon = playerOne.getEquipped()[1];

		enemy = gang.get(monsterOption);
		String enemyFighting = "A " + enemy.getType() + " steps up to attack!";
		UI.ColorInterface.txtColorRm("orange", enemyFighting);

		int[] initResults = initiative(playerOne, enemy);
		pFight = initResults[0];
		eFight = initResults[1];

		if (pFight > eFight)// player hits enemy
		{

			// playerOne.setNumAttcks(1);
			if (playerOne.getNumAttcks() == 0) {
				System.out.println(
						"You are too winded to land a hit that does damage. You crouch defensively, and take a deep breath.");
				playerOne.setNumAttcks(1);
				return gang;
			}

			boolean crit = critCheck(pFight, eFight);
			if (crit)
				enemy = criticalHit(pFight, eFight, weapon, playerOne, enemy);
			gang = weaponDamage(weapon, enemy, playerOne, gang, crit, monsterOption);
		} else// enemy hits player
		{
			enemyDoDamage(enemy, gang, playerOne);

		}
		for (Monster beast : gang) {
			if (beast.getHp() <= 0)// enemy killed
			{
				System.out.println("You have defeated the " + enemy.getType());
				deadEnemyIndex = index;
				enemyDied = true;
			}
			index++;
		}
		if (enemyDied) {
			gang.remove(deadEnemyIndex);
			if (gang.size() > 0) {
				System.out.print("You still have the following enemies: ");
				for (Monster thing : gang)
					if (thing.getHp() > 0)
						System.out.print(thing.getType() + ", ");
				System.out.print(" bearing down on you!\n");
				enemyDied = false;
			}
		}
		return gang;
	}

	private void enemyDoDamage(Monster enemy, List<Monster> gang, Player playerOne) throws InterruptedException {
		int dmg = 0;
		int dmgDone = 0;
		if (playerOne.getArmor().getItemType().equals("leather armor")) {
			Armor currArmor = (Armor) playerOne.getArmor();
			enemy = currArmor.itemAbility(enemy, playerOne);
		}
		if (enemy.getNmattacks() == 0) {
			System.out.println("Your opponent is shocked and misses a clear opening in your defense.");
			enemy.setNmattacks(1);
			return;// enemy hit by player weapon special, can't counter this round
		}
		dmg = enemy.monsterDamage(gang, enemy);
		dmgDone = playerOne.getHp() - dmg;// damage done to player
		playerOne.setHp(dmgDone);
		String eHit = "THRASH! your opponent deals you " + dmg + " points of damage!";
		UI.ColorInterface.txtColorRm("gray", eHit);
		System.out.println("You now have " + dmgDone + " hit points.");
		if (enemy.getType().equals("ogre"))
			playerOne = ((Ogre) enemy).ogreSpecial(playerOne);
		if (dmgDone <= 0)
			return;

		if (enemy.getHp() > 0) {
			switch (enemy.getType()) {
			case "orc":
				gang = ((Orc) enemy).orcSpecial(gang);
				break;
			case "troll":
				((Troll) enemy).trollSpecial();
				break;
			default:
				break;
			}
		}
		for (Monster beast : gang) {
			if (beast.getHp() <= 0)// enemy killed
			{
				System.out.println("You have defeated the " + enemy.getType());
				gang.remove(beast);
				if (gang.size() > 0) {
					System.out.print("You still have the following enemies: ");
					for (Monster thing : gang)
						System.out.print(thing.getType() + ", ");
					System.out.print(" bearing down on you!\n");
				}
			}
		}
	}

	private int[] initiative(Player playerOne, Monster enemy) throws InterruptedException {
		int pFight = 0, eFight = 0;

		pFight = playerOne.getCombatScore();
		eFight = enemy.getCombatScore();

		initiative.setSeed(8);
		pFight += initiative.nextInt(10);
		initiative.setSeed(8);
		eFight += initiative.nextInt(10);
		int[] initResults = { pFight, eFight };
		String fightR = "\nYou rolled a " + pFight + " versus the enemy attack roll of " + eFight;

		if (pFight > eFight) {
			UI.ColorInterface.txtColorRm("yellow", fightR);
			return initResults;
		} else {
			UI.ColorInterface.txtColorRm("red", fightR);
			return initResults;
		}
	}

	/**********
	 ** checks combat conditions when fight ends, if player won or lost if enemy will
	 * take a free attack
	 ** 
	 * @param playerOne
	 ** @param monster
	 ** @param melee
	 ** @return
	 * @throws InterruptedException
	 **********/
	public MonsterHorde fight(Player playerOne, MonsterHorde monster, Action melee)
			throws NullPointerException, InterruptedException {
		String currCommand = melee.getCommand();
		List<Monster> gang = monster.getHorde();
		while (((currCommand.charAt(0) == 'a' || currCommand.equals("enemyAttck")
				|| currCommand.toUpperCase().equals("Y")) || currCommand.equals("1") || currCommand.equals("2")
				|| currCommand.equals("3")) && (gang.size() > 0 && playerOne.getHp() > 0)) {
			currCommand = melee.getCommand();

			gang = battleResults(playerOne, gang);

			if (gang.size() <= 0) {
				String vict = "YOU ARE VICTORIOUS!";
				UI.ColorInterface.txtColorRm("green", vict);
				monster = null;
				gang = null;
				return monster;
			}
			if (playerOne.getHp() <= 0) {
				monster = null;
				return monster;
			} else {
				System.out.println(
						"Do you want to keep fighting?: (Y)es/(N)o?\n You can also type 1, 2, 3 to use a quick item.");
				Scanner input = new Scanner(System.in);
				currCommand = input.next().trim().toUpperCase();
				if (currCommand.equals("N")) {
					enemyFreeAttack(playerOne, monster);
					if (playerOne.getHp() > 0)
						System.out.println("You withdraw from combat.\n What action would you like to take?");
					else {
						gang.removeAll(gang);
						input = null;
						monster.setMonsterGroup(gang);
						return monster;// monster.setNumbCreatures(gang.size() - 1);
					}
					input = null;
					return monster;
				} else if (currCommand.equals("1") || currCommand.equals("2") || currCommand.equals("3")) {
					melee.useItem(currCommand, playerOne, monster);
				}
				input = null;
			}
		}
		return monster;
	}

	private Monster criticalHit(int pFight, int eFight, Items weapon, Player playerOne, Monster enemy)
			throws InterruptedException {
		int hp = enemy.getHp();
		String epicStrike = criticalHit(weapon);
		int dmg = (initiative.nextInt(playerOne.getDmg()) + 1) * 2;
		int dmgDone = hp - dmg;
		String critHit = epicStrike + "You do " + dmg + " points of damage to the attacking " + enemy.getType() + ".";
		UI.ColorInterface.txtColorRm("cyan", critHit);
		enemy.setHp(dmgDone);
		if (dmg > (dmgDone / 3) && dmgDone > 2)
			enemy.setCombatScore();
		return enemy;
	}

	/*********
	 * * monster attacks, if player wins the roll the monster doesn't do any damage
	 * *@param playerOne *@param monster arrayList of monsters
	 *********/
	public void enemyFreeAttack(Player playerOne, MonsterHorde monster) {
		Monster enemy;
		List<Monster> gang = monster.getHorde();
		int pFight = 0;
		int eFight = 0;
		int monsterOption = selectMonster(gang);

		pFight = playerOne.getCombatScore() + playerOne.getAc();// player doubles AC when taking a noncombat action, in
																// defensive mode.
		enemy = gang.get(monsterOption);
		System.out.println("a terrible " + enemy.getType() + " steps up to attack!");
		eFight = enemy.getCombatScore();
		pFight = pFight + initiative.nextInt(10);
		eFight = eFight + initiative.nextInt(10);
		// System.out.println("You rolled a " + pFight + " versus the enemy attack roll
		// of " + eFight);
		if (pFight > eFight)// player hits enemy
		{
			System.out.println("You fend off the viscious " + enemy.getType() + " as it presses the attack.");
		} else// enemy hits player
		{
			int dmg = 0;
			int dmgDone = 0;
			dmg = initiative.nextInt(enemy.getDmg()) + 1;
			dmgDone = playerOne.getHp() - dmg;// damage done to player
			playerOne.setHp(dmgDone);
			System.out.println(
					"CRUNCH! your opponent continues their assault and deals you " + dmg + " points of damage!");
			System.out.println("You now have " + dmgDone + " hit points.");
			playerOne.setCombatScore();
		}
	}

	/********************************
	 * *function chooses critical hit description * no parameters *@return String
	 *******************************/
	public String criticalHit(Items weapon) {
		String[] weaps = { "mace", "staff", "sword", "hammer" };
		String[] mace = { "mace", "crushing", "slam" };
		String[] staff = { "staff", "slamming", "slam" };
		String[] sword = { "blade", "slashing", "stab" };
		String[] hammer = { "war hammer", "smashing", "pound" };
		String[] options = new String[3];
		for (String myWep : weaps) {
			if (weapon.getItemType().equals(myWep)) {
				switch (myWep) {
				case "mace":
					options = mace;
					break;
				case "staff":
					options = staff;
					break;
				case "sword":
					options = sword;
					break;
				case "hammer":
					options = hammer;
					break;
				default:
					return " ";
				}
			}

		}

		String[] descriptions = {
				"You desperately charge headlong at your opponent, and blindly strike with all your might.",
				"You shoulder your opponent into the wall and bring your " + options[0]
						+ " around to drive it into the beasts short ribs.",
				"You duck your opponent's strike and counter, shredding its lower leg.",
				"The monster swings down and you sidestep, " + options[1] + " your opponents neck.",
				"You step in under your opponent's attack and " + options[2] + " the beast in the armpit.",
				"You deflect the beast's attack, it stumbles forward and you smash its knee with the pommel of your weapon.",
				"The beast grabs you by the shoulders, you drive your head up into its jaw and drive your weapon into its abdomen.",
				"The monster charges, you sidestep and swing as hard as you can tearing your enemies shoulder to a pulp.",
				"The enemy deflects your blow and you deftly redirect the swing into its thigh.",
				"The  beast misteps, you carve a deep gash into the enemies forehead blinding it with blood.",
				"Your desparate flurry of blows catches your enemy off guard you smite it in the mouth." };
		int index = initiative.nextInt(10);
		String strike = descriptions[index];
		return strike;
	}

	private boolean critCheck(int pFight, int eFight) {
		if (pFight > (eFight + 9))// critical hit
			return true;
		else
			return false;
	}

	private List<Monster> weaponDamage(Items weapon, Monster enemy, Player playerOne, List<Monster> gang, boolean crit,
			int monsterOption) throws InterruptedException {
		String weap = weapon.getItemType();
		List<Integer> attackVals;
		switch (weap) {
		case "mace":
			enemy = ((Mace) weapon).itemAbility(enemy);
			enemy = ((Mace) weapon).applyDamage(playerOne, enemy);
			break;
		case "sword":
			attackVals = new ArrayList<Integer>();
			attackVals.add(((Sword) weapon).itemAbility());
			if (crit)// critical hit
			{
				break;
			}
			for (int i = 0; i < attackVals.get(0); i++) {
				enemy = ((Sword) weapon).applyDamage(playerOne, enemy);
			}
			break;
		case "staff":
			attackVals = new ArrayList<Integer>();
			if (crit)// critical hit
			{
				break;
			}
			attackVals = ((Staff) weapon).itemAbility(gang);
			if (attackVals.size() > 1) {
				gang = ((Staff) weapon).applyDamage(weapon, attackVals, gang);
			} else
				enemy = ((Staff) weapon).applyDamage(playerOne, enemy);

			attackVals.clear();
			break;
		case "warhammer":
			attackVals = new ArrayList<Integer>();
			if (crit)// critical hit
			{
				break;
			}
			attackVals.add(((Hammer) weapon).itemAbility(enemy));
			if (attackVals.get(0) > 0)
				enemy.setCombatScore(attackVals.get(0));
			enemy = ((Hammer) weapon).applyDamage(playerOne, enemy);
			break;
		default:
			System.out.println("Error: no valid weapon for ability check, inventory error.");
			break;
		}
		if (!playerOne.getWeapon().getItemType().equals("staff")) {

			gang.remove(monsterOption);
			enemy.monsterHealthCheck(enemy);
			gang.add(enemy);
			return gang;
		} else
			enemy.monsterHealthCheck(enemy);
		return gang;

	}

}
