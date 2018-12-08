package gameMechanics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import gameMechanics.Player;
import gameMechanics.items.Items;
import gameMechanics.monsters.Monster;
import gameMechanics.monsters.MonsterHorde;
import gameMechanics.movement.CoordinateManager;
import gameMechanics.movement.Room;

public class SaveState {

	public SaveState() {
	}

	static String assembleRecord(Player plyrOne, CoordinateManager playerLoc, int lvlNum, Room currRoom)
			throws IOException {
		// Charset charSet = Charset.forName("UTF-8");
		String plyrRecord = plyrOne.getName() + ";" + plyrOne.getHp() + ","
				+ String.valueOf(plyrOne.getArtifactFound());
		String plyrInv = ";";
		String x = String.valueOf(playerLoc.getCol());
		String y = String.valueOf(playerLoc.getRow());
		String level = String.valueOf(lvlNum);
		String monsterRecords = null;
		String foundItem = null;
		List<Items> playerEq = plyrOne.getEquipment();
		Iterator<Items> it = playerEq.iterator();
		while (it.hasNext()) {
			plyrInv += it.next().toString() + ";";
		}
		String gameRecord = plyrRecord + ";" + x + ";" + y + ";" + level + plyrInv;

		MonsterHorde enemies;
		if (currRoom.getMonsters() != null) {
			enemies = currRoom.getMonsters();
			List<Monster> foes = enemies.getHorde();
			Iterator<Monster> iter = foes.iterator();
			while (iter.hasNext()) {
				monsterRecords += ";" + iter.next().toString();
			}
			gameRecord += monsterRecords;
		}

		Items foundI;
		if (currRoom.getThing() != null) {
			foundI = currRoom.getThing();
			foundItem = foundI.getItemType();
			gameRecord += foundItem;
		}
		saveFileWriter(gameRecord);
		return gameRecord;

	}

	public static void saveFileWriter(String saveFile) throws IOException {
		FileOutputStream save = new FileOutputStream("resources/documents/save.dat");
		byte[] outBuffer = saveFile.getBytes();
		try {
			save.write(outBuffer);
		} catch (Exception e) {
			System.out.print(e);
		}
		save.flush();
		save.close();
	}

}
