/* The FileLoader parses all textfiles and loads them into the game
 *    Edited. Will make more edits once updated txt files are uploaded.
 */

import java.io.*;
import java.util.*;

public class FileLoader
{
	private Map<String, Room> gameMap = new HashMap<>();
	private ArrayList<Item> itemAL = new ArrayList<>();
	private ArrayList<Puzzle> puzzleAL = new ArrayList<>();
	private ArrayList<Character> charAL = new ArrayList<>();
	private ArrayList<Player> playerAL = new ArrayList<>();

	public Map<String, Room> loadRooms(String fileName)
	{
		try(Scanner scan = new Scanner(new File(fileName)))
		{
			while(scan.hasNextLine())
			{
				String line = scan.nextLine().trim();
				try(Scanner scan2 = new Scanner(line))
				{
					scan2.useDelimiter("%");

					String roomID = scan2.next();
					String roomFloorID = scan2.next();
					String roomName = scan2.next();
					String roomDescr = scan2.next();
					String [] roomExits = scan2.next().split(",");

					Room room = new Room(roomID, roomFloorID, roomName, roomDescr);


					//add exits for each room
					String[] directions = {"north", "south", "west", "east"};

					for (int i = 0; i < 4; i++) 
					{
					    String exitRoomID = roomExits[i].trim();
					    if (!exitRoomID.equals("-1")) 
					    { // Only add valid exits
					        room.addExit(directions[i], exitRoomID);
					    }
					}

					//add rooms to game map
					gameMap.put(roomID, room);
				}

				catch(Exception e)
				{
					System.out.println("Error parsing line: " + line);
					e.printStackTrace();
				}
			}
		}

		catch(FileNotFoundException fnfe)
		{
			System.out.println("Rooms file not found.");
			fnfe.printStackTrace();
		}

		return gameMap;
	}

	public ArrayList<Item> loadItems(String fileName)
	{
		try(Scanner scan = new Scanner(new File(fileName)))
		{
			while(scan.hasNextLine())
			{
				String line = scan.nextLine().trim();
				if (line.isEmpty()) continue;
				try
				{
					String[] parts = line.split("%");

			        if (parts.length < 6)
			        {
			            System.out.println("Invalid item line: " + line);
			            continue;
			        }

					String itemID     = parts[0].trim();
			        String itemName   = parts[1].trim();
			        String itemDesc   = parts[2].trim();
			        String itemType   = parts[3].trim();
			        int itemValue     = Integer.parseInt(parts[4].trim());
					int maxStack      = (parts.length > 5 && !parts[5].trim().isEmpty()) ? Integer.parseInt(parts[5].trim()) : 1;
					String itemRoomID = (parts.length > 6) ? parts[6].trim() : "";

					Item item;

					switch(itemType)
					{
						case "weapon" ->
							item = new Weapon(itemID, itemName, itemDesc, itemValue);
						case "consumable" ->
							item = new Consumable(itemID, itemName, itemDesc, itemValue, maxStack);
						case "useable" ->
							item = new Useable(itemID, itemName, itemDesc, itemValue, maxStack);

							default ->
							{
								System.out.println("Unknown item type.");
								continue;
							}
					}

					if(itemRoomID.contains(","))
					{
						String [] itemRoomIDs = itemRoomID.split(",");
						for(String entry : itemRoomIDs)
						{
							entry = entry.trim();
							String[] parts2 = entry.split(":");
							String roomID = parts2[0].trim();
							int quantity = (parts.length > 1) ? Integer.parseInt(parts2[1]) : 1;

							Room room = gameMap.get(roomID);
							if(room != null)
							{
								Item itemCopy = item.clone();
								itemCopy.currentStack = quantity;
								room.addItem(itemCopy);
								itemAL.add(itemCopy);
							}

							else
							{
								//System.out.println("Room " + roomID + 
								//"not found for this item" + itemName);
							}
						}
					}

					else
					{
						Room room = gameMap.get(itemRoomID);
						if (room != null)
						{
							room.addItem(item);
							itemAL.add(item);
						}
					}
				}
				catch(Exception e)
				{
					System.out.println("Error parsing line: " + line);
					e.printStackTrace();
				}
			}
		}

		catch(FileNotFoundException fnfe)
		{
			System.out.println("Items file not found.");
			fnfe.printStackTrace();
		}

		return itemAL;
	}


	public ArrayList<Puzzle> loadPuzzles(String fileName)
	{
		try (Scanner scan = new Scanner(new File(fileName)))
		{
			while (scan.hasNextLine())
			{
				String line = scan.nextLine().trim();
				if (line.isEmpty()) continue;

				try 
				{
					String[] parts = line.split("%");
					
					if (parts.length < 6) {
	                    System.out.println("Invalid puzzle line: " + line);
	                    continue;
	                }

					String puzzleID    = parts[0].trim();
					String name        = parts[1].trim();
					String location    = parts[2].trim();
					String description = parts[3].trim();
					String answer      = parts[4].trim();
					String passMessage = parts[5].trim();
					String failMessage = parts[6].trim();
					int maxAttempts    = Integer.parseInt(parts[7].trim());

					Puzzle puzzle = new Puzzle(
							puzzleID,
							name,
							location,
							description,
							answer,
							maxAttempts,
							passMessage,
							failMessage
							);

					puzzleAL.add(puzzle);

					Room room = gameMap.get(location);

					// room exists
					if (room != null)
					{
						room.setPuzzle(puzzle);
					}
					// room does not exist
					else
					{
						System.out.println("Room " + location +
								" not found for puzzle " + puzzleID);
					}
				}
				catch (Exception e)
				{
					System.out.println("Error parsing line: " + line);
					e.printStackTrace();
				}
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Puzzles file not found.");
			fnfe.printStackTrace();
		}

		return puzzleAL;
	}


	public ArrayList<Character> loadChars(String fileName)
	{
		try(Scanner scan = new Scanner(new File(fileName)))
		{
			while(scan.hasNextLine())
			{
				String line = scan.nextLine().trim();
				if (line.isEmpty()) continue;

				try 
				{
					String[] parts = line.split("%");
					
					if (parts.length < 6) 
					{
	                    System.out.println("Invalid character line: " + line);
	                    continue;
					}

					String iD = parts[0].trim();
					String name = parts[1].trim();
					String charType = parts[2].trim();
					String preExisting = parts[3].trim();
					String spawn = parts[4].trim();
					String monsterDies = parts[5].trim();
					String playerDies = parts[6].trim();
					int damage = Integer.parseInt(parts[7].trim());
					int health = Integer.parseInt(parts[8].trim());
					String location = parts[9].trim();

						Character character;

						switch(charType)
						{
						case "NPC" ->
						character = new NPC(iD, name, preExisting, spawn, monsterDies, playerDies);
						case "Monster" ->
						character = new Monster(iD, name, preExisting, spawn, monsterDies,
								playerDies, damage, health);

						default ->
						{
							System.out.println("Unknown character type.");
							continue;
						}
						}

						if(location.contains(","))
						{
							String [] locationRoomID = location.split(",");
							for(String entry : locationRoomID)
							{
								entry = entry.trim();
								Room room = gameMap.get(entry);
								if(room != null)
								{
									room.setCharacter(character);
									charAL.add(character);
								}

								else
								{
									//System.out.println("Room " + location + 
									//"not found for this character" + name);
								}
							}
						}

						else
						{
							Room room = gameMap.get(location);
							if (room != null)
							{
								room.setCharacter(character);
								charAL.add(character);
							}
						}
					}
				

				catch(Exception e)
				{
					System.out.println("Error parsing line: " + line);
					e.printStackTrace();
				}
			}
		}

		catch(FileNotFoundException fnfe)
		{
			System.out.println("Characters file not found.");
			fnfe.printStackTrace();
		}

		return charAL;
	}


	public ArrayList<Player> loadPlayers(String fileName)
	{
		try(Scanner scan = new Scanner(new File(fileName)))
		{
			while(scan.hasNextLine())
			{
				String line = scan.nextLine().trim();
				try(Scanner scan2 = new Scanner(line))
				{
					scan2.useDelimiter("%");

					String playerName = scan2.next();
					int playerHP = scan2.nextInt();
					int playerDamage = scan2.nextInt();

					Player player = new Player(playerName, playerHP, playerDamage);

					playerAL.add(player);
				}

				catch(Exception e)
				{
					System.out.println("Error parsing line: " + line);
					e.printStackTrace();
				}
			}
		}

		catch(FileNotFoundException fnfe)
		{
			System.out.println("Players file not found.");
			fnfe.printStackTrace();
		}

		return playerAL;
	}

	public Map<String, Room> getRoomMap() 
	{
		return gameMap;
	}

	public ArrayList<Item> getItemList() 
	{
		return itemAL;
	}

	public ArrayList<Puzzle> getPuzzleList() 
	{
		return puzzleAL;
	}

	public ArrayList<Character> getCharList() 
	{
		return charAL;
	}

	public ArrayList<Player> getPlayerList() 
	{
		return playerAL;
	}
}
