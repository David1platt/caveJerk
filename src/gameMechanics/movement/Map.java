package gameMechanics.movement;

import java.util.ArrayList;
import java.util.List;

import gameMechanics.Driver;
import gameMechanics.Player;
import gameMechanics.monsters.MonsterHorde;

import java.io.IOException;
import java.security.SecureRandom;
public class Map 
{
	private int levelNum;
	private CoordinateManager location, lvlOneStartPt = new CoordinateManager(4, 6), lvlTwoStartPt = new CoordinateManager(4, 2), lvlThreeStartPt = new CoordinateManager(0, 0);
	private CoordinateManager  lvlOneUpPt = new CoordinateManager(2, 0), lvlTowUpPt = new CoordinateManager(7, 8);
	private CoordinateManager stairCoordsDown[] = {lvlOneStartPt, lvlTwoStartPt, lvlThreeStartPt};
	private CoordinateManager stairCoordsUp[] = {lvlOneUpPt, lvlTowUpPt};
	private Room navPts[][];
	private List<Room> rooms;
	private String directions[] = {"north", "south", "east", "west", "up", "down"};
	private LevelStorage currLevel = new LevelStorage();
	private enum Directions
	{
		NORTH(0),
		SOUTH(1), 
		EAST(2), 
		WEST(3);
		public final int num;
		Directions(final int number){num = number;}
	}
	/*******
	 * *default map constructor loads level one
	 * *@throws IOException
	 *******/
	public Map()throws IOException
	{
		levelNum = 1;
		rooms = new ArrayList<Room>();
		location = currLevel.getLevelDimensions(levelNum);
		navPts = new Room[location.getCol()][location.getRow()];
		currLevel = new LevelStorage();
		rooms = currLevel.getLevel(levelNum);
		mapBuilder(rooms);
		location = new CoordinateManager(lvlOneStartPt.getCol(), lvlOneStartPt.getRow());
	}
	/**********
	 * *constructor sets up different levels
	 * *@param lvlNum 
	 * *@throws IOException
	 **********/
	public Map(int lvlNum, String direct)throws IOException
	{
		if(rooms != null)
			rooms = null;
		rooms = new ArrayList<Room>();
		rooms = Driver.getAllLevels().getLevel(lvlNum);
		location = currLevel.getLevelDimensions(lvlNum);
		navPts = new Room[location.getCol()][location.getRow()];
		levelNum = lvlNum;
		mapBuilder(rooms);
		location = getLvlStartPt(lvlNum, direct);
	}
	/********
	 * * get current location in the array
	 * *@return Room 2d array
	 ********/
	public Room[][] getNav()
	{
		return navPts;
	}
	public int getLevelNum()
	{
		return levelNum;
	}
	/********
	 * *
	 * *@param levelNum
	 *******/
	public void setLevelNum(int levelNum)
	{
		this.levelNum = levelNum;
	}
	
	public Room getRoom(CoordinateManager local)
	{
		Room currPlace;
		currPlace = navPts[local.getCol()][local.getRow()];
		return currPlace;
	}
	
	public CoordinateManager getLvlStartPt(int lvlNum, String direction)
	{
		lvlNum -= 1;
		CoordinateManager startPt = new CoordinateManager(); 
		if(direction.equals(directions[4])) //4 is up 
			startPt = stairCoordsUp[lvlNum];
		else if(direction.equals(directions[5])) //5 is down
			startPt = stairCoordsDown[lvlNum];
		return startPt;
	}
	public List<Room> getMap()
	{
		return rooms;
	}
	
	public boolean checkExits(String direction)
	{
		String []exitPts; 
        	exitPts = navPts[location.getCol()][location.getRow()].checkExit();//checks if the exit is available
        	for(int i = 0; i < exitPts.length; i++)
        	{
        		if(direction.equals(exitPts[i].toString()))
        			return true;
        			
        	}   
        return false;
    }
	
	public void movement(String direction)
	{
		if(direction.equals(directions[Directions.NORTH.num]))
			location.setRow(location.getRow() - 1);
		
		else if(direction.equals(directions[Directions.SOUTH.num]))
			location.setRow(location.getRow() + 1);
		
		else if(direction.equals(directions[Directions.EAST.num]))
			location.setCol(location.getCol() + 1);

		
		else if(direction.equals(directions[Directions.WEST.num]))
			location.setCol(location.getCol() - 1);
		
		else
			System.out.println("You can't go that way.");
		
	}
	/******
	 * checks if player initiative check is better than monsters so player can flee
	 * @param playerOne
	 * @param monsters
	 * @return boolean to confirm flight attempt true is success false is failure
	 */
	public boolean flee(Player playerOne, MonsterHorde monsters)
	{
		SecureRandom d10 = new SecureRandom();
		//List<Monster> gang = monsters.getHorde(monsters);
		boolean chckRun = true;
		
		int pl = playerOne.getInitiative(), numMonsters = monsters.getNumbCreatures(), monInitiative = 0;
		if(numMonsters > 0)
		{
			for(int i = 0; i < numMonsters; i++)
			{
				monInitiative = 10;
				d10.generateSeed(8);
				pl = pl + d10.nextInt(10);
				d10.generateSeed(8);
				monInitiative += d10.nextInt(10);
				if(pl < monInitiative)
				{
					chckRun = false;
				}
				else
				{
					pl = playerOne.getInitiative() - 5;
					monInitiative += 2;
				}				
			}
		}
		return chckRun;
	}
	
	/*public String toString(CoordinateManager nums)
	{
		Room place;
		place = navPts[nums.getCol()][nums.getRow()];
		place.toString();
		
		return placeDescript;
	}*/
	
	public CoordinateManager getLocation()
	{
		return location;
	}
	
	private void mapBuilder(List<Room> rooms)
	{ 
		int size = rooms.size(), currX = 0, currY =0;//, north = 0, south = 1, east = 2, west = 3;
		for(int i = 0; i < size; i++)
		{
			currX = rooms.get(i).getX();
			currY = rooms.get(i).getY();
			navPts[currX][currY] = rooms.get(i);
		}
	}
}
