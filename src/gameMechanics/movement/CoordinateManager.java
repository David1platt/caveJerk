package gameMechanics.movement;

public class CoordinateManager 
{
	private int column = 0;
	private int row = 0;
	public CoordinateManager()
	{
		column = 0;
		row = 0;
	}
	
	public CoordinateManager(int c, int r)
	{
		this.column = c;
		this.row = r;
	}
	
	public void setCol(int c)
	{
		column = c;
	}
	
	public void setRow(int r)
	{
		row = r;
	}
	
	public int getCol()
	{
		return column;
	}
	
	public int getRow()
	{
		return row;
	}
}
