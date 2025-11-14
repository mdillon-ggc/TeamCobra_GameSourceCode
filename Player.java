import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class Player
{
	private String currentRoomID;
	private String previousRoomID; //for flee 
//(I don't think this is needed since you aren't going
//to the prev room but just leaving combat mode? unless
//their SRS says to.)
	private Map<String, Room> gameMap;
	private String playerName;
	private int playerHP;
	private int playerDamage;
	private double detectionLvl;
	private boolean triggered;
	private Item equippedItem; // Stores currently equipped item
	private ArrayList<String> equippedItems = new ArrayList<>(); //could we use this instead?
	private ArrayList<Item> playerInventory = new ArrayList<>();
	
	private int health = 100; //was not mentioned
	private int baseDamage = 10; //was not mentioned 
	private Weapon equippedWeapon;

	public Player(String startingRoomID)
	{
		currentRoomID = startingRoomID;
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

	public void exploreRoom()
	{
		Room currentRoom = gameMap.get(currentRoomID);
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

	public void pickUpItem(String item)
	{
		// TODO Auto-generated method stub
		Room currentRoom = gameMap.get(currentRoomID);
        Item itemToPickUp = null;
    	for (Item itemInRoom : currentRoom.getItems()) {
	        if (itemInRoom.getItemName().equalsIgnoreCase(item)) {
	            itemToPickUp = itemInRoom;
	            break;
	        }
    	}

	    if (itemToPickUp != null) {
	        inventory.add(itemToPickUp);
	        currentRoom.removeItem(itemToPickUp);
	        System.out.println("Picked up: " + itemToPickUp.getItemName() + " and added to inventory");
	    } else {
	        System.out.println("Item is not found in this room.");
	    }	
	}//end pickUpItem method

	public void dropItem(String item)
	{
		// TODO Auto-generated method stub
		Room currentRoom = gameMap.get(currentRoomID);
        Item itemToDrop = null;
		for(Item itemInInv: inventory){
			if(itemInInv.getItemNam().equalsIgnoreCase(item){
				itemToDrop = item;
				break;
			}
		}
		if (itemToDrop != null) {
	        inventory.remove(itemToDrop);
	        currentRoom.addItem(itemToDrop);
	        System.out.println("Dropped: " + itemToDrop.getItemName() + " from this room");
	    } else {
	        System.out.println("Item can not be dropped.");
	    }			
	}//end dropItem method

	public void useItem(String item)
	{
		Item itemToUse = null;

	    // Search for the item in the player's inventory
	    for (Item invInInv : inventory) {
	        if (invInInv.getItemName().equalsIgnoreCase(item)) {
	            itemToUse = invInInv;
	            break;
	        }
	    }
		if (itemToUse == null) {
	        System.out.println("You don't have an item named '" + item + "' in your inventory.");
	        return;
	    }

		// If it's consumable, remove it after use
		if (itemToUse.getItemType.equals("consumable"){
			itemToUse.isConsumable();
	        inventory.remove(itemToUse);
	        System.out.println(itemToUse.getItemName() + " has been consumed.");
			health += itemToUse.getHP;
	        System.out.println("Current health point is: " + health);
	    }if else (itemToUse.getItemType.equals("useable"){
			itemToUse.isUseable();
			System.out.println(itemToUse.getItemName() + " has been used.");
		}	
		
	}//end useItem method

	public void inspectItem(String item)
	{
		// TODO Auto-generated method stub
		Item itemToInspect = null;
		for(Item itemInInv: inventory){
			if(itemInInv.getItemName().equalsIgnoreCase(item){
				ItemtoInspect = itemInInv;
				System.out.println("Inspecting: " + itemToInspect.getItemName() + ": " + itemToInspect.getItemDesc());
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
    if (!itemToEquip.isEquippable())
    {
        System.out.println("You cannot equip this item.");
        return;
    }

    // If the player already has an item equipped, unequip it first (optional)
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

	public void attack(Character monster)
	{
		if (monster == null && !monster.isMonster() && !monster.isAlive())
		{
			System.out.println("There is no monster to attack.");
			return;
		}
	int dmg = getPlayerDamage();
	int newHP = Math.max(0, monster.getHealth() - dmg);
	monster.setHealth(newHP);
	System.out.println("You attacked " + monster.getName() + " for " + dmg + " damage.");

	if (newHP = 0)
		{
		System.out.println(monster.getMonsterDies());
		}	
		
	}
	public void takeDamage(int amount)
	{
		health = Math.max(0, health - amount);
		System.out.println("You took " + amount + " damage!");
	}

	public void flee()
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
		System.out.println(getPlayerHP());
		System.out.println(getPlayerDamage());
		System.out.println(getDetectionLvl());
		System.out.println();
	}

	public void saveGame(String fileName)
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
			for(String item : playerInventory)
			{
				writer.println(item);
			}

			writer.println("End Inventory");

			writer.println("Equipped Items:");
			for(String item : equippedItems)
			{
				writer.println(item);
			}

			writer.println("End Equipped");

			writer.println("Rooms:");
			for(Room room : gameMap.values())
			{
				StringBuilder sb = new StringBuilder();
				sb.append(room.getRoomID()).append(",").append(room.isVisited()).append(",");

				for(Character character : room.getCharacterList())
				{
					sb.append(character.getID()).append("|");
				}
				sb.append(",");

				for (Item item : room.getItems())
				{
					sb.append(item.getItemID()).append("|");
				}

				writer.println(sb.toString());
			}
			writer.println("End Room");

			System.out.println("Game saved.");
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void loadGame(String fileName, ArrayList<Character> characters, ArrayList<Item> items)
	{
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
		{
			String line;

			while(!(line = reader.readLine()).equals("Player:"))
			{
				currentRoomID = reader.readLine();
				detectionLvl = Double.parseDouble(reader.readLine());
				playerHP = Integer.parseInt(reader.readLine());
				playerDamage = Integer.parseInt(reader.readLine());
			}

			while (!(line = reader.readLine()).equals("Inventory:")) 
			{
				playerInventory.clear();
			}

			while (!(line = reader.readLine()).equals("End Inventory")) 
			{
				playerInventory.add(line);
			}

			while (!(line = reader.readLine()).equals("Equipped Items:")) 
			{
				equippedItems.clear();
			}

			while (!(line = reader.readLine()).equals("End Equipped")) 
			{
				equippedItems.add(line);
			}

			while (!(line = reader.readLine()).equals("Rooms:")) 
			{
				while (!(line = reader.readLine()).equals("End Room")) 
				{
					String[] parts = line.split(",", 4); // roomID, visited, characters, items
					String roomID = parts[0];
					boolean visited = Boolean.parseBoolean(parts[1]);
					Room room = gameMap.get(roomID);
					if (room != null) 
					{
						room.setVisited(visited);

						room.getCharacterList().clear();
						if (parts.length > 2 && !parts[2].isEmpty()) 
						{
							String[] charIDs = parts[2].split("\\|");
							for (String id : charIDs) 
							{
								for (Character ch : characters) 
								{
									if (ch.getID().equals(id)) 
									{
										room.setCharacter(ch);
										break;
									}
								}
							}
						}

						room.getItems().clear();
						if (parts.length > 3 && !parts[3].isEmpty()) 
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

	
}
