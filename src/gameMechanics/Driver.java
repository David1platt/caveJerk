package gameMechanics;
//northimport gameMechanics.Action.Commands;

import java.io.IOException;

//import gameMechanics.monsters.MonsterHorde;
import gameMechanics.movement.CoordinateManager;
import gameMechanics.movement.LevelStorage;
import gameMechanics.movement.Map;
import gameMechanics.movement.Room;
import UI.ColorInterface;

public class Driver {

	private Action doStuff;
	private static final Inventory roomItem = new Inventory();
	private int levelNum;
	private static Driver game;
	private static LevelStorage allLevels;
	private Map level;
	private Player playerOne;
	private String introText = "*************************\n*WELCOME TO CAVEJERK 2.0*\n*************************\nSo yup our intrepid adventurer is yet again in some nasty \nold cave looking for loot and glory!\nYou have been sent out on a quest to retrieve the Scepter of Argos, a legendary artifact that is said to give immense power and wisdom to those that know how to wield it!\nType help in the command window to get a full list of commands.\nNow into the depths with yeee!\n\n";

	public Driver() throws IOException, InterruptedException {
		levelNum = 1;
		allLevels = new LevelStorage();
		try {
			level = new Map();// initialize level one
		} catch (IOException e) {
			System.out.println("Exception thrown: " + e.getMessage());
		}
		playerOne = new Player();
		playerOne.setPlayer();
		doStuff = new Action();
		ColorInterface.txtColorRm("orange", introText);
	}

	public static void main(String[] args) throws InterruptedException {

		try {
			game = new Driver();
			game.gameLoop();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (StringIndexOutOfBoundsException x) {
			System.out.println(x.getMessage());
		}
	}

	public static LevelStorage getAllLevels() {
		return allLevels;
	}

	public int gameLoop()
			throws StringIndexOutOfBoundsException, IndexOutOfBoundsException, IOException, InterruptedException {
		CoordinateManager startLoc = level.getLocation();// sets start location
		Room locals = level.getRoom(startLoc);
		locals.format_PrintRm();
		// UI.ColorInterface.txtColorRm("green", locals);
		String noGo = "You can't go that way!";
		String resultCheck = "hi";

		try {
			while (!resultCheck.equals("quit") && playerOne.getHp() > 0) {
				doStuff.commands(startLoc, game, levelNum, roomItem, playerOne, level, noGo);
				resultCheck = doStuff.getCommand();
			}
			System.out.println(
					"Your grand adventure has come to a brutal sad end as the beasts from the deep tear you limb from limb......");

		} catch (StringIndexOutOfBoundsException x) {
			System.out.println("Please provide a valid command.\nType help for info.");
			doStuff.commands(startLoc, game, levelNum, roomItem, playerOne, level, noGo);
		} catch (IOException e) {
			System.out.println("Please provide a valid command.\nType help for info.");
			doStuff.commands(startLoc, game, levelNum, roomItem, playerOne, level, noGo);
		} catch (IndexOutOfBoundsException y) {
			System.out.println("Exception thrown: " + y.getMessage());
			doStuff.commands(startLoc, game, levelNum, roomItem, playerOne, level, noGo);
		}
		String gameEnd = "\nThanks for playing Cave Jerk!!!\n\n Programming/Design/Creative Writing - David Platt\n Creative Writing - Merlin J. Reynolds\n Creative Writing - Ivan Sorenson\n TESTING:\n David Platt\n John Platt\n Merlin Reynolds\n Michael Janicki\n Ivan Sorensen\n An updated version of the game will be coming soon! copyright 2016\n";
		System.out.print(gameEnd);
		return 0;
	}

}
