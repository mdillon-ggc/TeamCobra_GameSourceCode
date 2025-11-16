import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player
{
	private Room currentRoom;
	private String currentRoomID;
	private String previousRoomID; //for flee 
	private String playerName;
	private int playerHP = 100;
	private int playerDamage = 5;
	private double detectionLvl;
	private boolean triggered;
	private Item equippedItem;
	private ArrayList<Item> playerInventory = new ArrayList<>();
	private ArrayList<Item> equippedItems = new ArrayList<>();

	public Player(String startingRoomID)
	{
		currentRoomID = startingRoomID;
		this.playerHP = 100;
		this.playerDamage = 5;
	}

	public Player(String playerName, int playerHP, int playerDamage)
	{
		this.playerName = playerName;
		this.playerHP = playerHP;
		this.playerDamage = playerDamage;
		this.detectionLvl = 0;
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public int getPlayerHP()
	{
		return playerHP;
	}

	public int getPlayerDamage()
	{
		return playerDamage;
	}

	public double getDetectionLvl()
	{
		return detectionLvl;
	}

	public boolean getTriggered()
	{
		return triggered;
	}

	public void setTriggered(boolean triggered)
	{
		this.triggered = triggered; 
	}

	public String getCurrentRoomID()
	{
		return currentRoomID;
	}

	public void setCurrentRoomID(String roomID)
	{
		this.currentRoomID = roomID;
	}

	public void setCurrentRoom(Room room)
	{
		this.currentRoom = room;
	}

	public Room getCurrentRoom() 
	{
		return currentRoom;
	}

	public void exploreRoom(Map <String, Room> gameMap)
	{
		Room currentRoom = gameMap.get(currentRoomID);
		System.out.println("Desc:" + currentRoom.getRoomDescr());
		System.out.println("Items: " + currentRoom.getItems());
		System.out.println("Characters: " + currentRoom.getCharacterList());
	}

	public void accessInventory()
	{
		if (playerInventory.isEmpty())
		{
			System.out.println("Inventory is empty.");
			return;
		}
		for(int i = 0; i < playerInventory.size(); i++)
		{
			Item item = playerInventory.get(i);

			System.out.println("Slot " + i + ": " + item.getItemName()
			+ " | Quantity: " + item.getCurrentStack());
		}
	}

	public void pickUpItem(String item, Map <String, Room> gameMap)
	{
		// Search for the item in the current room and add it to inventory
		Room currentRoom = gameMap.get(currentRoomID);
		Item itemToPickUp = null;
		for (Item itemInRoom : currentRoom.getItems()) {
			if (itemInRoom.getItemName().equalsIgnoreCase(item)) {
				itemToPickUp = itemInRoom;
				break;
			}
		}
		if (itemToPickUp != null) {
			playerInventory.add(itemToPickUp);
			currentRoom.removeItem(itemToPickUp);
			System.out.println(itemToPickUp.getItemName() + " added to inventory.");
		} else {
			System.out.println("Item is not found in this room.");
		}	
	}//end pickUpItem method

	public void dropItem(String item, Map <String, Room> gameMap)
	{
		// Drop an item from inventory into the current room
		Room currentRoom = gameMap.get(currentRoomID);
		Item itemToDrop = null;
		for(Item itemInInv: playerInventory){
			if(itemInInv.getItemName().equalsIgnoreCase(item)){
				itemToDrop = itemInInv;
				break;
			}
		}
		if (itemToDrop != null) {
			playerInventory.remove(itemToDrop);
			currentRoom.addItem(itemToDrop);
			System.out.println("Dropped: " + itemToDrop.getItemName() + " from this room");
		} else {
			System.out.println("Item can not be dropped.");
		}			
	}//end dropItem method

	public void useItem(String item)
	{
		Item itemFound = null;

		// Search for the item in the player's inventory
		for (Item invInInv : playerInventory) {
			if (invInInv.getItemName().equalsIgnoreCase(item)) {
				itemFound = invInInv;
				break;
			}
		}
		if (itemFound == null) {
			System.out.println("You don't have an item named '" + item + "' in your inventory.");
			return;
		}

		// If it's consumable, remove it after use
		if (itemFound.getItemType().equals("consumable"))
		{
			Consumable itemToConsume = (Consumable) itemFound;
			playerInventory.remove(itemFound);
			System.out.println(itemFound.getItemName() + " has been consumed.");
			playerHP += itemToConsume.getHP();
			System.out.println("Current health point is: " + playerHP);
		}
		else if(itemFound.getItemType().equals("useable"))
		{
			System.out.println(itemFound.getItemName() + " has been used.");
			Useable itemToUse = null;
		}	
		else
		{
			System.out.println("Item type not valid.");
		}

	}//end useItem method

	public void inspectItem(String item)
	{
		// Inspect an item in the inventory
		Item itemToInspect = null;
		for(Item itemInInv: playerInventory){
			if(itemInInv.getItemName().equalsIgnoreCase(item)){
				itemToInspect = itemInInv;
				System.out.println(itemToInspect.getItemName() 
				+ ": " + itemToInspect.getItemDesc());
				return;
			}
		}
		System.out.println("Item is not found in the Inventory");		
	}//end inspectItem method

	public void equipItem(String itemName)
	{
		// Check if the inventory exists and is not empty
		if (playerInventory == null || playerInventory.isEmpty())
		{
			System.out.println("Your inventory is empty.");
			return;
		}

		// Search for the item inside the player's inventory
		Item itemToEquip = null;
		for (Item item : playerInventory)
		{
			if (item.getItemName().equalsIgnoreCase(itemName))
			{
				itemToEquip = item;
				break;
			}
		}

		// If the item does not exist in the inventory
		if (itemToEquip == null)
		{
			System.out.println("That item is not in your inventory.");
			return;
		}

		// If the item exists but cannot be equipped
		if (!itemToEquip.isEquipable())
		{
			System.out.println("You cannot equip this item.");
			return;
		}

		// If the player already has an item equipped, unequip it first
		if (equippedItem != null)
		{
			System.out.println("You unequipped: " + equippedItem.getItemName());
		}

		// Equip the new item
		equippedItem = itemToEquip;
		System.out.println("You equipped: " + equippedItem.getItemName());
	}

	public void unequipItem(String itemName)
	{
		// Check if the player has an equipped item
		if (equippedItem == null)
		{
			System.out.println("You have no item equipped.");
			return;
		}

		// Unequip the currently equipped item
		System.out.println("You unequipped: " + equippedItem.getItemName());
		equippedItem = null;
	}

	// Method to increase detection level (for puzzles/items that affect detection)
	public void increaseDetectionLvl(double amount)
	{
		// Increase the detection level by the given amount
		detectionLvl += amount;

		// Optional: show the new detection level
		System.out.println("Detection level increased to: " + detectionLvl);
	}

	//monster loses hp
	public void attack(Character monster)
	{
		// Do not attack if target is null, not a monster, or already dead
		if (monster == null || !monster.isMonster() || !monster.isAlive())
		{
			System.out.println("You cannot attack that.");
			return;
		}
		int dmg = getPlayerDamage();
		int newHP = Math.max(0, monster.getHealth() - dmg);
		monster.setHealth(newHP);
		System.out.println("You attacked " + monster.getName() + " for " + dmg + " damage.");

		if (newHP <= 0)
		{
			System.out.println(monster.getMonsterDies());
			//remove monster after dead
		}	

	}

	//player loses hp
	public void takeDamage(int amount)
	{
		playerHP = Math.max(0, playerHP - amount);
		System.out.println("You took " + amount + " damage!");
		System.out.println("Current HP: " + playerHP); 
	}

	public void flee() // need flee <N|E|S|W>
	{
		if (previousRoomID == null)
		{	
			System.out.println("There is nowhere you can flee.");
			return;
		}
		System.out.println("You flee back to room " + previousRoomID + ".");
		currentRoomID = previousRoomID;
	}

	public void checkStatus()
	{
		System.out.println("~Player stats~");
		System.out.println("HP: " + playerHP);
		System.out.println("Attack: " + playerDamage); 
		System.out.println("Detection: " + detectionLvl);
		System.out.println("Current Room: " + currentRoomID);
		System.out.println();
	}

	public void saveGame(String fileName, Map <String, Room> gameMap)
	{
		try(PrintWriter writer = new PrintWriter(new FileWriter(fileName)))
		{
			writer.println("Player:");
			writer.println(currentRoomID);
			writer.println(detectionLvl);
			writer.println(playerHP);
			writer.println(playerDamage);


			writer.println("Inventory:");
			//items in inventory
			for(Item item : playerInventory)
			{
				writer.println(item.getItemID());
			}

			writer.println("End Inventory");

			writer.println("Equipped Items:");
			for(Item item : equippedItems)
			{
				writer.println(item);
			}

			writer.println("End Equipped");

			writer.println("Rooms:");
			for(Room room : gameMap.values())
			{
				StringBuilder sb = new StringBuilder();
				sb.append(room.getRoomID()).append(",");
				sb.append(room.isVisited()).append(",");

				for(Character character : room.getCharacterList())
				{
					sb.append(character.getiD()).append("|");
				}
				sb.append(",");

				for (Item item : room.getItems())
				{
					sb.append(item.getItemID()).append("|");
				}

				writer.println(sb.toString());
			}
			writer.println("End Room");

			System.out.println("Game saved. File saved under name: ");
			System.out.println();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void loadGame(String fileName, ArrayList<Character> characters, ArrayList<Item> items, Map <String, Room> gameMap)
	{
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
		{
			String line;

			line = reader.readLine();

			currentRoomID = reader.readLine();
			detectionLvl = Double.parseDouble(reader.readLine());
			playerHP = Integer.parseInt(reader.readLine());
			playerDamage = Integer.parseInt(reader.readLine());

			reader.readLine();
			playerInventory.clear();

			while (!(line = reader.readLine()).equals("End Inventory")) 
			{
				for (Item item : items)
				{
					if(item.getItemID().equals(line))
					{
						playerInventory.add(item);
						break;
					}
				}
			}

			reader.readLine();
			equippedItems.clear();


			while (!(line = reader.readLine()).equals("End Equipped")) 
			{
				for (Item item : items)
				{
					if(item.getItemID().equals(line))
					{
						equippedItems.add(item);
						break;
					}
				}
			}

			line = reader.readLine();
			{
				while (!(line = reader.readLine()).equals("End Room")) 
				{
					String[] parts = line.split(",", 4); // roomID, visited, characters, items
					String roomID = parts[0];
					boolean visited = Boolean.parseBoolean(parts[1]);
					Room room = gameMap.get(roomID);

					if (room == null) continue;

					room.setVisited(visited);

					room.getCharacterList().clear();
					if (!parts[2].isEmpty()) 
					{
						String[] charIDs = parts[2].split("\\|");
						for (String id : charIDs) 
						{
							for (Character ch : characters) 
							{
								if (ch.getiD().equals(id)) 
								{
									room.setCharacter(ch);
									break;
								}
							}
						}
					}

					room.getItems().clear();
					if (!parts[3].isEmpty()) 
					{
						String[] itemIDs = parts[3].split("\\|");
						for (String id : itemIDs) 
						{
							for (Item item : items) 
							{
								if (item.getItemID().equals(id)) 
								{
									room.addItem(item);
									break;
								}
							}
						}
					}
				}

			}

			System.out.println("Loaded game.");
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void readMap()
	{
		// TODO Auto-generated method stub

	}

	public void startPuzzle()
	{
		// TODO Auto-generated method stub
		// This can be used to manually trigger a puzzle if needed
	}

	public void move(String direction, Map<String, Room> roomMap) {
		direction = direction.toLowerCase();

		// Check if the current room has an exit in that direction
		if (!currentRoom.getExits().containsKey(direction)) {
			System.out.println("You can't go that way.");
			return;
		}

		String nextRoomID = currentRoom.getExits().get(direction);

		if (nextRoomID == null || nextRoomID.equals("-1")) {
			System.out.println("You can't go that way.");
			return;
		}

		// Get the next room from the room map
		Room nextRoom = roomMap.get(nextRoomID);

		
		if (nextRoom == null) {
			System.out.println("That room does not exist.");
			return;
		}

		// Move player
		currentRoom = nextRoom;
		currentRoomID = nextRoomID;

		// Mark the room as visited
		if (!currentRoom.isVisited()) {
			currentRoom.setVisited(true);
		}

		// Display room info
		System.out.println(currentRoom.getRoomName());
		System.out.println("Exits: " + currentRoom.getExits().keySet());

		// Automatically start puzzle if the room has one
		// NOTE: adjust method names if your Room/Puzzle classes are different
		if (currentRoom.hasPuzzle()) {
			currentRoom.getPuzzle().startPuzzle(this);
		}
	}
}
