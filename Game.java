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
//		ArrayList<ItemInstance> items = loader.parseItemLine("item.txt");
//		ArrayList<Puzzle> puzzles = loader.loadPuzzles("puzzles.txt");
//		ArrayList<Character> characters = loader.loadChars("Characters.txt");
		
		player = new Player("R05");
		
		Room currentRoom = roomMap.get("R05");
		currentRoom.setVisited(true);
		
		System.out.println("Welcome to Master Jewel Thief!");
		System.out.println("Type 'quit' to exit the game.");
		System.out.println("Type 'help' to open the help menu.");
		System.out.println("Type 'read  map' to open the map.\n");
		
		
		while(true)
		{
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
			// combat mode code
			 if (currentMonster != null)
            {
                // attack while in combat
                if (input.equals("attack") || input.startsWith("attack "))
                {
                    player.attack(currentMonster);

                    if (!currentMonster.isAlive())
                    {
                        System.out.println(currentMonster.getMonsterDies());
                        currentRoom.getCharacterList().remove(currentMonster);
                        currentMonster = null;
                    }
                    else
                    {
                        monsterCounterAttack();
                    }
                    continue;
                }

                // flee in combat mode
                if (input.equals("flee"))
                {
                    player.flee();  
                    currentRoom = roomMap.get(player.getCurrentRoomID());
                    currentMonster = null;  
                    System.out.println("You fled the battle!");
                    continue;
                }

                // view stats of monster in battle mode
                if (input.equals("check monster stats"))
                {
                    System.out.println("Monster: " + currentMonster.getName());
                    System.out.println("HP: " + currentMonster.getHealth());
                    System.out.println("ATK: " + currentMonster.getDamage());
                    continue;
                }
            }
				
			
			if(currentRoom.getRoomID().equals("R00"))
			{
				System.out.println("Where would you like to start?: 'window' or 'backdoor'");
				String input = scan.nextLine().trim().toLowerCase();
				
				if(input.startsWith("choose "))
				{
					String path = input.substring(7).trim();

					if(path.equals("window"))
					{
						System.out.println("You climb through the garden window...\n");

						player.setCurrentRoomID("R02");
						currentRoom = roomMap.get("R02);

						System.out.println(currentRoom.getRoomName());
						System.out.println(currentRoom.getExits().keySet());
						player.startPuzzle;

						continue;
					}

					else if (path.equals("backdoor"))
					{
						System.out.println("You slip through the backdoor quietly...\n");

						player.setCurrentRoomID("R01");
						currentRoom = roomMap.get("R01");

						System.out.println(currentRoom.getRoomName());
						System.out.println(currentRoom.getExits().keySet());
						player.startPuzzle;

						continue;
					}

					else
					{
						System.out.println("That path does not exist.");
						continue;
					}
				}

				continue;
			}
			
			
			if(input.startsWith("go "))
			{
				String direction = input.substring(3).trim();
				currentRoom = roomMap.get(player.getCurrentRoomID());
				String nextRoomID = currentRoom.getExit(direction);
				if(nextRoomID!= null)
				{
					player.setCurrentRoomID(nextRoomID);
					currentRoom = roomMap.get(nextRoomID);
					System.out.println("Moved " + direction + " to " + nextRoomID);
					System.out.println(currentRoom.getRoomName());
					System.out.println(currentRoom.getExits().keySet());
				}
				
				else
				{
					System.out.println("invalid command. Try again.");
				}
			}
			
			if(input.equals("explore"))
			{
			
				player.exploreRoom();
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
				player.pickUpItem(item);
				continue;
			}
			
			if(input.startsWith("drop "))
			{
				String item = input.substring(5).trim();
				player.dropItem(item);
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
				Character monster = null;
				for (Character character : currentRoom.getCharacterList())
   			 {
					if (character.isMonster() && character.isAlive())
      					  	{

							monster = character;
							break;
							}
				}

				if (monster != null)
				{
						currentMonster = monster;
						System.out.println("Combat started with " + monster.getName() + "!");
						player.attack(currentMonster);

						if (!currentMonster.isAlive())
						{
							System.out.println(currentMonster.getMonsterDies());
							currentRoom.getCharacterList().remove(currentMonster);
							currentMonster = null;
						}	
						else
						{	
							monsterCounterAttack();
						}
					}
					else
					{
						System.out.println("There is no monster to attack here.");
					}
				continue;
					}
			if (input.equals("flee")) {
                if (currentMonster != null) {
                    player.flee();
                    currentRoom = roomMap.get(player.getCurrentRoomID());
                    currentMonster = null;
                    System.out.println("You fled the battle!");
                	} 
						else 
					{
                    System.out.println("There is nothing to flee from.");
                }
                continue;
            }
			
			if(input.equals("check status"))
			{
				player.checkStatus();
				continue;
			}
			
			if(input.equals("save"))
			{
				player.saveGame(input);
				continue;
			}
			
			if(input.equals("load"))
			{
				player.loadGame(input, characters, items);
				continue;
			}
			
			if(input.equals("read map"))
			{
				player.readMap();
				continue;
			}
		}
	}

	private void monsterCounterAttack() 
		{
        if (currentMonster == null) 
		{
            return;
        }
        int dmg = currentMonster.getDamage();
        System.out.println(currentMonster.getName() + " attacks you for " + dmg + " damage!");
        player.takeDamage(dmg);
			
        try {
            if (player.getPlayerHP() <= 0) 
			{
                System.out.println(currentMonster.getPlayerDies());
                System.out.println("Game Over.");
                System.exit(0);
            }
        }
		catch (Exception ignored) 
		{
			
        }
    }
	
	public void help()
	{
		;
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.playGame();
	}
}
