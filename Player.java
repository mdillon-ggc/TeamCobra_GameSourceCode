import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import javax.swing.*;

public class Player
{
    private Room currentRoom;
    private String currentRoomID;
    private String previousRoomID; //for flee 
    private String playerName;
    private int playerHP;
    private int playerMaxHP;
    private int playerDamage;
    private double detectionLvl;
    private boolean triggered;
    private Item equippedItem;
    private ArrayList<Item> playerInventory;
    private ArrayList<Item> equippedItems;
    private HashSet<String> visitedCheckpointRooms;

    // 🔒 NUEVO: modo puzzle
    private boolean inPuzzleMode = false;

    public Player(String startingRoomID)
    {
        currentRoomID = startingRoomID;
        this.playerHP = 100;
        this.playerMaxHP = 100;
        this.playerDamage = 5;
        this.detectionLvl = 0;
        playerInventory = new ArrayList<>();
        equippedItems = new ArrayList<>();
        visitedCheckpointRooms = new HashSet<>();

        // add starting items
        for (Item template: FileLoader.getItemList()) {
            if (template.getItemName().equalsIgnoreCase("Shipping Label") ||
                    template.getItemName().equalsIgnoreCase("Pocket_Knife") ||
                    template.getItemName().equalsIgnoreCase("Lockpick")) {

                Item starterItem = template.clone();
                starterItem.setCurrentStack(1);
                playerInventory.add(starterItem);
            }
        }
    }

    // 🔒 NUEVOS getters/setters para puzzle mode
    public boolean isInPuzzleMode() {
        return inPuzzleMode;
    }

    public void setInPuzzleMode(boolean inPuzzleMode) {
        this.inPuzzleMode = inPuzzleMode;
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
        System.out.println("Room name: " + currentRoom.getRoomName());
        System.out.println("Desc: " + currentRoom.getRoomDescr());
        System.out.println("Items: " + currentRoom.getItems());
        System.out.println("Characters: " + currentRoom.getCharacterList());
        System.out.println("Exits: " + currentRoom.getExits().keySet());
        System.out.println();
    }

    public void accessInventory()
    {
        if (playerInventory.isEmpty())
        {
            System.out.println("Inventory is empty.\n");
            return;
        }

        // mostrar items
        for (int i = 0; i < playerInventory.size(); i++)
        {
            Item item = playerInventory.get(i);
            System.out.println("Slot " + i + ": " + item.getItemName()
                    + " (x" + item.getCurrentStack() + ")");
        }

        // mostrar slots vacíos hasta 8
        for (int i = playerInventory.size(); i < 8; i++) 
        {
            System.out.println("Slot " + i + ": [Empty]");
        }
        System.out.println();
    }

    public void pickUpItem(String item, Map <String, Room> gameMap)
    {
        // Search for the item in the current room and add it to inventory
        if (playerInventory.size() >= 8) 
        {
            System.out.println("Inventory full!"
                    + " You cannot carry more than 8 stacks of items.\n");
            return;
        }

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
            System.out.println(itemToPickUp.getItemName() + " added to inventory.\n");
        } else {
            System.out.println("Item is not found in this room.\n");
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
            System.out.println("Dropped: " + itemToDrop.getItemName() + " from this room.\n");
        } else {
            System.out.println("Item can not be dropped.\n");
        }           
    }//end dropItem method

    public void useItem(String item, Map<String, Room> gameMap)
    {
        Item itemFound = null;

        for (Item invInInv : playerInventory) {
            if (invInInv.getItemName().equalsIgnoreCase(item)) {
                itemFound = invInInv;
                break;
            }
        }

        if (itemFound == null) {
            System.out.println("You don't have an item named '" + item + "' in your inventory.\n");
            return;
        }

        String type = itemFound.getItemType();

        if (type.equals("consumable"))
        {
            Consumable itemToConsume = (Consumable) itemFound;
            playerInventory.remove(itemFound);
            System.out.println(itemFound.getItemName() + " has been consumed.");
            playerHP += itemToConsume.getHP();
            System.out.println("Current health point is: " + playerHP);
            System.out.println();
        }
        else if(type.equals("useable"))
        {
            Useable itemToUse = (Useable) itemFound;

            // ==== UNLOCK ROOMS BEFORE REMOVING THE KEY ====
            Room current = this.getCurrentRoom();

            for (String direction : current.getExits().keySet())
            {
                String nextRoomID = current.getExits().get(direction);
                Room nextRoom = gameMap.get(nextRoomID);

                if (nextRoom != null && nextRoom.isLocked())
                {
                    if (itemFound.getItemID().equalsIgnoreCase(nextRoom.getRequiredKey()))
                    {
                        nextRoom.setLocked(false);
                        System.out.println("You used " + itemFound.getItemName()
                                + " to unlock " + nextRoom.getRoomName() + "!");
                    }
                }
            }

            // Use item and decrease stack
            itemToUse.use();

            if(itemToUse.getCurrentStack() <= 0 &&
              !itemToUse.getItemID().startsWith("A-")) 
            {
                playerInventory.remove(itemToUse);
                System.out.println(itemToUse.getItemName() 
                        + " has been removed from inventory.\n");
            }
        }
        else
        {
            System.out.println("Item type not valid.\n");
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
        System.out.println("Item is not found in the Inventory.\n");       
    }//end inspectItem method

    public void equipItem(String itemName)
    {
        // Check if the inventory exists and is not empty
        if (playerInventory == null || playerInventory.isEmpty())
        {
            System.out.println("Your inventory is empty.\n");
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
            System.out.println("That item is not in your inventory.\n");
            return;
        }

        // If the item exists but cannot be equipped
        if (!itemToEquip.isEquipable())
        {
            System.out.println("You cannot equip this item.\n");
            return;
        }

        // Equip the new item
        equippedItem = itemToEquip;
        System.out.println("You equipped: " + equippedItem.getItemName());
        System.out.println();
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
        System.out.println();
    }

    // Method to increase detection level (for puzzles/items that affect detection)
    public void increaseDetectionLvl(double amount)
    {
        // Increase the detection level by the given amount
        detectionLvl += amount;

        // Optional: show the new detection level
        System.out.println("Detection level increased to: " + detectionLvl);
    }

    public void attack(Character target) 
    {
        if (target == null)
        {
            System.out.println("There is nothing to attack.");
            return;
        }

        if (!target.canBeAttacked()) //to prevent npc getting attacked 
        {
            System.out.println("You cannot attack " + target.getName() + ". They are not hostile.");
            return;
        }

        if (!target.isAlive())
        {
            System.out.println(target.getName() + " is already defeated.");
            return;
        }

        int dmg = getPlayerDamage();                    
        int newHP = Math.max(0, target.getHealth() - dmg);
        target.setHealth(newHP);
        System.out.println("You attacked " + target.getName() + " for " + dmg + " damage.");
        if (newHP <= 0)
        {
            System.out.println(target.getMonsterDies());   
        }
    }


    //player loses hp
    public void takeDamage(int amount)
    {
        playerHP = Math.max(0, playerHP - amount);
        System.out.println("You took " + amount + " damage!");
        System.out.println("Current HP: " + playerHP); 
        System.out.println();
    }

    public void flee() // need flee <N|E|S|W>
    {
        if (previousRoomID == null)
        {   
            System.out.println("There is nowhere you can flee.\n");
            return;
        }
        System.out.println("You flee back to room " + previousRoomID + ".");
        System.out.println();
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
                writer.println(item.getItemID());
            }

            writer.println("End Equipped");

            writer.println("CheckpointRooms:");

            for (String roomID : visitedCheckpointRooms) 
            {
                writer.println(roomID);
            }

            writer.println("End CheckpointRooms");

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

            System.out.println("Game saved. File saved under name: " + fileName);
            System.out.println();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void loadGame(String fileName, ArrayList<Character> characters, 
            ArrayList<Item> items, Map <String, Room> gameMap)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String line;

            reader.readLine();

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

            reader.readLine();
            visitedCheckpointRooms.clear();

            while (!(line = reader.readLine()).equals("End CheckpointRooms")) 
            {
                visitedCheckpointRooms.add(line.trim());
            }

            reader.readLine();

            while (!(line = reader.readLine()).equals("End Room")) 
            {
                String[] parts = line.split(",", -1);
                String roomID = parts[0];
                boolean visited = Boolean.parseBoolean(parts[1]);
                Room room = gameMap.get(roomID);

                if (room == null) continue;

                room.setVisited(visited);

                room.getCharacterList().clear();
                if (!parts[2].isEmpty()) 
                {
                    String[] charIDs = parts[2].split("\\|");
                    for (String iD : charIDs) 
                    {
                        for (Character character : characters) 
                        {
                            if (character.getiD().equals(iD)) 
                            {
                                room.setCharacter(character);
                                break;
                            }
                        }
                    }
                }

                room.getItems().clear();
                if (!parts[3].isEmpty()) 
                {
                    String[] itemIDs = parts[3].split("\\|");
                    for (String iD : itemIDs) 
                    {
                        for (Item item : items) 
                        {
                            if (item.getItemID().equals(iD)) 
                            {
                                room.addItem(item);
                                break;
                            }
                        }
                    }
                }
            }


            System.out.println("~Loading Game...~");
            System.out.println("Game has loaded!");
            System.out.println();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void readMap()
    {
        String floorID = currentRoom.getRoomFloorID();

        String map = switch(floorID) {
        case "F1" -> "F1.jpg";
        case "F2" -> "F2.jpg";
        case "F3" -> "F3.jpg";
        case "F4" -> "F4.jpg";
        default -> null;
        };

        if (map == null) {
            System.out.println("The file name does not exist. "
                    + "Try a different file name.\n");
            return;
        }

        // Load image
        ImageIcon mapIcon = new ImageIcon(map);

        // Create a frame to display the map
        JFrame frame = new JFrame("Game Map");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // only close this window
        frame.setSize(800, 600); // adjust size as needed

        // Add the image to a label
        JLabel label = new JLabel(mapIcon);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        frame.add(label);

        frame.setLocationRelativeTo(null); // center on screen
        frame.setVisible(true);
    }

    public void startPuzzle()
    {
        // This can be used to manually trigger a puzzle if needed
        // (lo dejo igual que estaba, sin lógica nueva)
    }

    public void move(String direction, Map<String, Room> gameMap) {
        direction = direction.toLowerCase();

        // 🔒 BLOQUEO: si está en modo puzzle, no puede moverse
        if (inPuzzleMode) {
            System.out.println("You are currently solving a puzzle and cannot leave this room.\n");
            return;
        }

        // Check if the current room has an exit in that direction
        if (!currentRoom.getExits().containsKey(direction)) {
            System.out.println("You can't go that way.\n");
            return;
        }

        String nextRoomID = currentRoom.getExits().get(direction);

        if (nextRoomID == null || nextRoomID.equals("-1")) {
            System.out.println("You can't go that way.\n");
            return;
        }

        // Get the next room from the room map
        Room nextRoom = gameMap.get(nextRoomID);

        if (nextRoom.isLocked()) 
        {
            if (!nextRoom.unlockRoom(this)) 
            {   
                System.out.println("The door is locked.");
                return;  
            }
        }


        if (nextRoom == null) {
            System.out.println("That room does not exist.\n");
            return;
        }

        // Move player
        currentRoom = nextRoom;
        currentRoomID = nextRoomID;

        // Mark the room as visited
        if (!currentRoom.isVisited()) {
            currentRoom.setVisited(true);
        }

        if (currentRoom.isCheckpoint()) {
            if (!visitedCheckpointRooms.contains(currentRoomID)) {
                playerHP = playerMaxHP;
                visitedCheckpointRooms.add(currentRoomID);
                System.out.println("Checkpoint reached! HP refilled to max.");
            }
            saveCheckpoint(gameMap);
        }

        // Display room info
        System.out.println(currentRoom.getRoomName());
        System.out.println("Exits: " + currentRoom.getExits().keySet());
        System.out.println();

        // Automatically start puzzle if the room has one
        if (currentRoom.hasPuzzle()) {
            currentRoom.getPuzzle().startPuzzle(this);
        }
    }

    private void saveCheckpoint(Map<String, Room> gameMap) 
    {
        try 
        {
            String fileName = "checkpoint.txt"; //always overwrite
            saveGame(fileName, gameMap);
            System.out.println("Checkpoint saved at " + currentRoom.getRoomName());
            System.out.println();
        } 

        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public boolean hasItem(String requiredKeyID)
    {   
        // lo dejo como estaba
        return playerInventory.contains(requiredKeyID);
    }
}


