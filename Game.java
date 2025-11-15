import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Game
{
	private Player player;

	public void playGame()
	{
		Scanner scan = new Scanner(System.in);
		FileLoader loader = new FileLoader();
		Map<String, Room> roomMap = loader.loadRooms("Room.txt");
		ArrayList<Item> items = loader.loadItems("item.txt");
		ArrayList<Puzzle> puzzles = loader.loadPuzzles("puzzles.txt");
		ArrayList<Character> characters = loader.loadChars("Character.txt");

		player = new Player("R00");

		Room currentRoom = roomMap.get("R00");
		currentRoom.setVisited(true);

		Character eliteMerc = null;

		for(Character monster: characters)
		{
			if(monster.getName().equalsIgnoreCase("Elite Mercenary"))
			{
				eliteMerc = monster;
				break;
			}
		}

		for(Room room : roomMap.values())
		{
			room.setEliteMercernary(eliteMerc);
		}


		System.out.println("Welcome to Master Jewel Thief!");
		System.out.println("Type 'quit' to exit the game.");
		System.out.println("Type 'help' to open the help menu.");
		System.out.println("Type 'read  map' to open the map.\n");


		while(true)
		{
			if(currentRoom.getRoomID().equals("R00"))
			{
				System.out.println("Where would you like to start?: 'window' or 'backdoor'");
				String input = scan.nextLine().trim().toLowerCase();

				if(input.startsWith("choose "))
				{
					String path = input.substring(7).trim();
					
					String startRoomID = null;
			        switch (path) {
			            case "window":
			                startRoomID = "R02";
			                System.out.println("You climb through the garden window...\n");
			                break;
			            case "backdoor":
			                startRoomID = "R01";
			                System.out.println("You slip through the backdoor quietly...\n");
			                break;
			            default:
			                System.out.println("That path does not exist.");
			                continue;
			        }

			     // set current room and player
			        currentRoom = roomMap.get(startRoomID);
			        player.setCurrentRoomID(startRoomID);
			        player.setCurrentRoom(currentRoom);

			        // print room info
			        System.out.println(currentRoom.getRoomName());
			        System.out.println(currentRoom.getRoomDescr());
			        System.out.println("Exits: " + currentRoom.getExits().keySet());

			        player.startPuzzle();
			        continue;
				}
			}

			System.out.println("Enter a command: ");
			String input = scan.nextLine().trim().toLowerCase();

			if(input.equals("quit"))
			{
				System.out.println("Thanks for playing. Bye!");
				break;
			}

			if(input.equals("help"))
			{
				help();
				continue;
			}

			if(input.startsWith("go "))
			{
				String direction = input.substring(3).trim();
				player.move(direction, roomMap);
				continue;
			}

			if(input.equals("explore"))
			{

				player.exploreRoom(roomMap);
				continue;
			}

			if(input.equals("inventory"))
			{
				player.accessInventory();
				continue;
			}

			if(input.startsWith("pickup "))
			{
				String item = input.substring(7).trim();
				player.pickUpItem(item, roomMap);
				continue;
			}

			if(input.startsWith("drop "))
			{
				String item = input.substring(5).trim();
				player.dropItem(item, roomMap);
				continue;
			}

			if(input.startsWith("use "))
			{
				String item = input.substring(4).trim();
				player.useItem(item);
				continue;
			}

			if(input.startsWith("inspect "))
			{
				String item = input.substring(8).trim();
				player.inspectItem(item);
				continue;
			}

			if(input.startsWith("equip "))
			{
				String item = input.substring(6).trim();
				player.equipItem(item);
				continue;
			}

			if(input.startsWith("unequip "))
			{
				String item = input.substring(8).trim();
				player.unequipItem(item);
				continue;
			}

			if(input.startsWith("attack "))
			{
				String monsterName = input.substring(7).trim();
				Character monster = null;
				currentRoom = roomMap.get(player.getCurrentRoomID());

				for(Character character : currentRoom.getCharacterList())
				{
					if(character.getName().equalsIgnoreCase(monsterName))
					{
						monster = character;
						break;
					}
				}

				if (monster == null)
				{
					System.out.println("No monster with that name");
					continue;
				}

				player.attack(monster); //go to combat mode
				continue;
			}
			if (input.startsWith("flee"))
			{
				
				String arg = input.length() > 4 ? input.substring(5).trim() : "";

				if (arg.isEmpty())
				{
					System.out.println("Which direction do you want to flee? (N/S/E/W or north/east/south/west)");
					continue;
				}
				
				String direction = arg.toLowerCase();
				if (direction.equals("n")) direction = "north";
				else if (direction.equals("s")) direction = "south";
				else if (direction.equals("e")) direction = "east";
				else if (direction.equals("w")) direction = "west";

				player.move(direction, roomMap);
				currentRoom = roomMap.get(player.getCurrentRoomID());
				continue;
			}

			if(input.equals("check status"))
			{
				player.checkStatus();
				continue;
			}

			if(input.equals("save"))
			{
				player.saveGame(input, roomMap);
				continue;
			}

			if(input.equals("load "))
			{
				String fileName = input.substring(5).trim();
				player.loadGame(fileName, characters, items, roomMap);
				continue;
			}

			if(input.equals("read map"))
			{
				player.readMap();
				continue;
			}
		}
	}

	public void help()
	{
		System.out.println("choose <window> or <backdoor>: Decide which path to take initially.");
		System.out.println("go <direction>: Allows the user to move rooms.");
		System.out.println("inspect <item>: Allows the user to inspect an artifact/item in order to see its description");
		System.out.println("pick up <item>: Allows the user to pickup an item.");
		System.out.println("attack: Allows the user to attack a monster.");
		System.out.println("save: Allows the user to save.");
		System.out.println("load: Allows the user to load.");
		System.out.println("inventory: Open the inventory.");
		System.out.println("quit: Allows the user to quit/exit");
		System.out.println("use <item>: Allows the user to use whatever inventory &  artifacts and tools they have.");
		System.out.println("equip <weapon>: Allows the user to equip a weapon, which will be used for attacks");
		System.out.println("explore: Allows the player to view the room snapshot (room description, npcs, monsters, artifacts, etc.)");
		System.out.println("drop <item>: Allows the player to drop an item from their inventory.");
		System.out.println("status: Allows the player to view their detection level");
		System.out.println("flee <N|S|E|W: Allows the player to flee to the previous room. If no direction is provided with flee command, the player flees to the previous room they were in.\n");
	}

	public static void main(String[] args)
	{
		Game game = new Game();
		game.playGame();
	}
}
