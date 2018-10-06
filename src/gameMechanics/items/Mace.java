package gameMechanics.items;
import java.util.Random;

import gameMechanics.monsters.Monster;


public class Mace extends Items {
	
	public Mace(){
		super("mace", 6);
	};
	
	public Monster itemAbility(Monster enemy) throws InterruptedException
	{
		Random chance = new Random();

		int specialCheck = chance.nextInt(100);
		if(specialCheck < 36)
		{
			enemy.setNmattacks(0);
			String spec = "You hit your enemy with enough force to make them stumble.\nYour opponent can only defend next round!";
			UI.ColorInterface.txtColorRm("cyan", spec);
		}
		return enemy;
	}
	
}
