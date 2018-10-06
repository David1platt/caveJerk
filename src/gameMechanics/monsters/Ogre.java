package gameMechanics.monsters;

import gameMechanics.Player;

public class Ogre extends Monster implements MonsterAction {

	public Ogre(){
		super();
		type = "ogre";
        startHp = randomNumber.nextInt(10) + 24;
        hp = startHp;
        combatScore = 14 + (hp/3); 
        dmg = 7;
        ac = 7;
	}
	
    public Player ogreSpecial(Player playerOne)
    {
    	int check = 0;
    	check = randomNumber.nextInt(100);
    	if(check > 70)
    	{
    		playerOne.setNumAttcks(0);
    		System.out.println("Your enemy strikes youn with so much force the wind is knocked out of you. You barely "
    				+ "have the energy to defend yourself. It feels like a couple ribs might be broken...");	
    	}
    	return playerOne;
    }
	
}
