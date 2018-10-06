package gameMechanics.items;

import java.util.Random;

import gameMechanics.Player;
import gameMechanics.monsters.Monster;

//import java.util.ArrayList;

public class Armor extends Items {
	
	public Armor(String name, int score)
	{
		super(name, score);
	}
	
	public Monster itemAbility(Monster enemy, Player playerOne) throws InterruptedException
	{
		String armorType = playerOne.getArmor().getItemType();
		Random chance = new Random();

		int specialCheck = chance.nextInt(100);
		switch(armorType) {
			case "leather armor":
				if(specialCheck < 21)
				{
					enemy.setNmattacks(0);
					String spec = "You are light on your feet, and able to outmanuver the " + enemy.getType() + "!";
					UI.ColorInterface.txtColorRm("cyan", spec);
				}
				return enemy;
		
			case "chain mail":
				if(specialCheck < 36)
				{
					enemy.setNmattacks(0);
					String spec = "The " + enemy.getType() + " strikes with teeth rattle force, but its weapon is caught in rings of your armor!";
					UI.ColorInterface.txtColorRm("cyan", spec);
				}
				return enemy;
			case "plate mail":	
				if(specialCheck < 51)
				{
					enemy.setNmattacks(0);
					String spec = "The " + enemy.getType() + " strikes with teeth rattle force, but cannot pierce the steel plate!";
					UI.ColorInterface.txtColorRm("cyan", spec);
				}
				return enemy;
			default:
				System.out.println("");
				return enemy;
		}
	}
	
}
