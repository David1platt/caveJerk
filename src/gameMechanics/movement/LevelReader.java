package gameMechanics.movement;
import java.io.*;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

public class LevelReader 
{
	private List<Room> levelRooms;
	
	public LevelReader()
	{	}
	
	public List<Room> getRooms()
	{
		return levelRooms;
	}
	
	public List<Room> readFileBuildRoom(String inputDir)throws IOException
	{
		levelRooms = new ArrayList<Room>();
		InputStream txt = LevelReader.class.getClassLoader().getResourceAsStream(inputDir);
		byte[] inBuffer = new byte[txt.available()]; 
		new DataInputStream(txt).readFully(inBuffer); 
		txt.close();
		String levelData = new String(inBuffer, "UTF-8");
		roomBuilder(levelData);
		return levelRooms;
	}
	
	private void roomBuilder(String lvlText)
	{
		String description = null, north = null, south = null, east = null, west = null, xStr = null, yStr = null, lvlS = null;
		int x = 0, y = 0, lvl = 0;
		StringTokenizer splitData = new StringTokenizer(lvlText, "#\n"); 
		while(splitData.hasMoreTokens())
		{
			description = splitData.nextToken().toString();
			north = splitData.nextToken().toString();
			south = splitData.nextToken().toString();
			east = splitData.nextToken().toString();
			west = splitData.nextToken().toString();
			xStr = splitData.nextToken().toString();
			yStr = splitData.nextToken().toString();
			lvlS = splitData.nextToken().toString();
			
			x = Integer.parseInt(xStr);
			y = Integer.parseInt(yStr);
			lvl = Integer.parseInt(lvlS);
			Room aRoom = new Room(description, north, south, east, west, x, y, lvl);
			levelRooms.add(aRoom);
		}
	}
}
