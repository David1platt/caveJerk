package UI;
import java.awt.Color;
import enigma.console.*;
import enigma.core.Enigma;

public class ColorInterface {
	
	public ColorInterface()
	{}
	
	public static void txtColorRm(String color, String output) throws InterruptedException
	{
		Console s_console = Enigma.getConsole("*CAVE JERK*");
		TextAttributes attrs = new TextAttributes(Color.WHITE);
		long wait = 500; 
		switch(color)
		{
			case "green":
				attrs = new TextAttributes(Color.green);
				s_console.setTextAttributes(attrs);
				System.out.println(output);

				break;
			case "gray":
				attrs = new TextAttributes(Color.lightGray);
				s_console.setTextAttributes(attrs);
				System.out.println(output);
				Thread.sleep(wait);
				break;
			case "cyan":
				attrs = new TextAttributes(Color.cyan);
				s_console.setTextAttributes(attrs);
				Thread.sleep(wait);
				System.out.println(output);
				break;
			case "blue":
				attrs = new TextAttributes(Color.blue);
				s_console.setTextAttributes(attrs);

				System.out.println(output);
				break;
			case "orange":
				attrs = new TextAttributes(Color.orange);
				s_console.setTextAttributes(attrs);
			
				System.out.println(output);
				break;
			case "red":
				attrs = new TextAttributes(Color.red);
				s_console.setTextAttributes(attrs);
				Thread.sleep(wait);
				System.out.println(output);
				break;
			case "yellow":
				attrs = new TextAttributes(Color.yellow);
				s_console.setTextAttributes(attrs);
				Thread.sleep(wait);
				System.out.println(output);
				break;
			default:
				attrs = new TextAttributes(Color.green);
				s_console.setTextAttributes(attrs);
				Thread.sleep(wait);
				break;
		}
		attrs = new TextAttributes(Color.WHITE);
		s_console.setTextAttributes(attrs);
	}
}
