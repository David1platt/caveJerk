package gameMechanics;

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.List;
import java.util.ListIterator;

import gameMechanics.items.Items;

import java.util.ArrayList;
import java.security.SecureRandom;

public class Player {
	private String name;
	private int hp;
	private int ac;
	private int combatScore;
	private int initiative;
	private SecureRandom hitPoints = new SecureRandom();
	private List<Items> equipment;
	private Items[] equipped;
	private Items[] quickSlots;
	private int numAttacks;
	private boolean artifactFound = false;
//private Action doStuff;

	public Player(int hp, int ac, int combatScore, int initiative) {
		this.hp = hp;
		this.ac = ac;
		this.combatScore = combatScore;
		this.initiative = initiative;
		equipment = new ArrayList<Items>();
		equipped = new Items[2];
		quickSlots = new Items[3];
		equipment.add(Inventory.getItem(1));
		equipment.add(Inventory.getItem(6));
		equipment.add(Inventory.getItem(7));
		equipped[0] = Inventory.getItem(1);
		equipped[1] = Inventory.getItem(6);
		numAttacks = 1;
		artifactFound = false;

	}

	public Player() {
		this.name = null;
		this.hp = 0;
		this.ac = 0;
		this.combatScore = 0;
		this.initiative = 0;
		equipment = new ArrayList<Items>();
		equipped = new Items[2];
		quickSlots = new Items[3];
		equipment.add(Inventory.itemFactory("leather armor"));
		equipment.add(Inventory.itemFactory("mace"));
		equipment.add(Inventory.itemFactory("light healing potion"));
		equipment.add(Inventory.itemFactory("light healing potion"));
		equipment.add(Inventory.itemFactory("medium healing potion"));
		equipped[0] = equipment.get(0);
		equipped[1] = equipment.get(1);

		numAttacks = 1;
		artifactFound = false;
	}

	public void setStartHp() {
		hp = hitPoints.nextInt(20) + 30;
	}

	public void setHp(int update) {
		hp = update;
	}

	public int getHp() {
		return hp;
	}

	public void setInitiative() {
		initiative = hitPoints.nextInt(10) + 15;
	}

	public int getInitiative() {
		return initiative;
	}

	public void setAc() {
		ac = 10 + equipped[1].getScore();
	}

	public int getAc() {
		return ac;
	}

	public void setCombatScore() {
		combatScore = equipped[1].getScore() + equipped[0].getScore() + (hp / 4);
	}

	public int getCombatScore() {
		return combatScore;
	}

	public List<Items> getEquipment() {
		return equipment;
	}

	public Items[] getEquipped() {
		return equipped;
	}

	public int getDmg() {
		int dmg = equipped[1].getScore();
		return dmg;
	}

	public Items getItem(String name) {
		ListIterator<Items> thing = equipment.listIterator();
		// int index = 0;
		while (thing.hasNext()) {
			Items it = thing.next();
			// System.out.println(it.getItemType());
			if (it.getItemType().contains(name)) {
				try {
					Items foundI = it;
					thing.remove();
					return foundI;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return null;
	}

	public boolean getArtifactFound() {
		return artifactFound;
	}

	public void setPlayer() {
		setAc();
		setStartHp();
		setInitiative();
		setCombatScore();
	}

	public int getNumAttcks() {

		return numAttacks;
	}

	public void setNumAttcks(int attck) {
		this.numAttacks = attck;
	}

	public void putItemInventory(Items newThing) {
		equipment.add(newThing);
	}

	public void equipItem(Items aItem) {
		int inventorySize = 10;
		for (int i = 0; i < inventorySize; i++) {

			if (aItem.getItemType().equals(Inventory.getItem(i).getItemType())) {
				if (i < 3)// this is a piece of armor on the range of inventory
				{
					equipped[0] = aItem;
					setCombatScore();
				}
				if (i >= 3 && i < 8)// this is a weapon on the range of inventory
				{
					equipped[1] = aItem;
					setCombatScore();
				}
			}
		}
	}

	public void printStats() {
		System.out.println("current HP: " + this.hp);
		System.out.println("current combat score: " + this.combatScore);
		System.out.println("current initiative: " + this.initiative);
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		name = aName;
	}

	public void setArtifactFound(boolean check) {
		artifactFound = check;
	}

	public Items getWeapon() {
		int eqWeap = 1;
		Items aItem = equipped[eqWeap];
		return aItem;
	}

	private void setQuickSlots(int i, Items usable) {
		if (quickSlots[i - 1] == null)
			quickSlots[i - 1] = usable;
		else
			System.out.println("This slot already holds: " + quickSlots[i - 1].getItemType());
	}

	private void QuickSlRemove(int index) {
		quickSlots[index] = null;
	}

	public boolean playerSetQuick(String itemName, int index) throws InterruptedException {
		if (itemName.equals("remove")) {
			QuickSlRemove(index);
			return false;
		}
		int len = this.equipment.size();
		for (int i = 0; i < len; i++) {
			if (equipment.get(i).getItemType().equals(itemName)) {
				setQuickSlots(index, equipment.get(i));
				String output = "You have put " + equipment.get(i).getItemType() + " into quickslot " + index + ".";
				UI.ColorInterface.txtColorRm("cyan", output);
				equipment.remove(i);
				return true;
			}

		}
		UI.ColorInterface.txtColorRm("red", "This item is not currently in your inventory.");
		return false;
	}

	public Items[] getQuickS() {
		return quickSlots;
	}

	public Items getArmor() {
		int eqArm = 0;
		Items aItem = equipped[eqArm];
		return aItem;
	}
}
