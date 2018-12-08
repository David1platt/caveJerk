package gameMechanics.movement;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class LevelStorage {
	private List<CoordinateManager> levelDimensions;
	private CoordinateManager lvlOneDimensions = new CoordinateManager(7, 7),
			lvlTwoDimensions = new CoordinateManager(8, 9), lvlThreeDimensions = new CoordinateManager(12, 12);

	private List<List<Room>> gameLevels;
	private List<Room> level = new ArrayList<Room>();
	private List<Room> levelOne, levelTwo, levelThree;

	private String directory = "resources/levels/";
	private String[] levelNames = { "levelOne.lvl", "levelTwo.lvl", "levelThree.lvl" };
	private LevelReader levImport;

	/******
	 * *constructor builds the set of game levels *uses the gameLevels to save a
	 * list of room lists *sets up room lists and stores them in gameLevels *reads
	 * levels into game *@throws IOException
	 ******/
	public LevelStorage() throws IOException {
		gameLevels = new ArrayList<List<Room>>();
		levelOne = new ArrayList<Room>();
		levelTwo = new ArrayList<Room>();
		levelThree = new ArrayList<Room>();
		levelDimensions = new ArrayList<CoordinateManager>();
		levelDimensions.add(lvlOneDimensions);
		levelDimensions.add(lvlTwoDimensions);
		levelDimensions.add(lvlThreeDimensions);
		levImport = new LevelReader();
		storeLevels(0, directory, levelOne);
		storeLevels(1, directory, levelTwo);
		storeLevels(2, directory, levelThree);
	}

	public void storeLevels(int index, String directory, List<Room> currMap) throws IOException {
		String currFile;
		currFile = directory + levelNames[index];
		currMap = levImport.readFileBuildRoom(currFile);
		gameLevels.add(currMap);

	}

	public void setLevel(int index, List<Room> aMap) throws NullPointerException {
		gameLevels.set(index, aMap);
	}

	public void setLevelDimensions(CoordinateManager levDim) {
		levelDimensions.add(levDim);
	}

	public CoordinateManager getLevelDimensions(int lvlNum) {
		lvlNum -= 1;
		return levelDimensions.get(lvlNum);
	}

	public List<Room> getLevel(int levNum) {
		levNum -= 1;
		level = gameLevels.get(levNum);
		return level;
	}
}
