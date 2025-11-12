import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Room 
{
    private int roomID;
    private String roomName;
    private String roomDescr;
    private boolean visited;

    private Map<String, Integer> exits;
    private List<Item> items;
    private Puzzle puzzle;
    private Character character;

    
    public Room(int roomId, String roomName, String roomDescr,) 
    {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDescr = roomDescr;
        this.visited = false;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public int getRoomID() 
    { 
    	return roomID; 
    }
    
    public String getRoomName() 
    { 
    	return roomName; 
    }
    
    public String getRoomDescr() 
    { 
    	return roomDescr; 
    }
    
    public Character getCharacter() 
    {
        return character;
    }
    
    public boolean isVisited() 
    { 
    	return visited; 
    }
    
    public void setVisited(boolean visited) 
    { 
    	this.visited = visited; 
    }  
    
    public void setCharacter(Character character) 
    {
        this.character = character;
    }
    
    public void trackVisit() 
    {
        if (!visited) 
        {
            visited = true;
        } 
        else 
        {
            System.out.println("You have visited this room.");
        }

        if (puzzle != null && puzzle.isSolved()) 
        {
            puzzle.resetPuzzle();
        }

        if (puzzleSolved) 
        {
            System.out.println("Puzzle is solved in this room.");
        }
    }

    public void addExit(String direction, int roomID) 
    {
        if (roomID != -1)
            exits.put(direction.toUpperCase(), roomID);
    }

    public Integer getExit(String direction) 
    {
        return exits.get(direction.toUpperCase());
    }

    public Map<String, Integer> getExits() 
    {
        return exits;
    }

    public void setExits(Map<String, Integer> exits) 
    {
        if (exits != null)
            this.exits.putAll(exits);
    }


    public List<Item> getItems() 
    {
        return items;
    }

    public void addItem(Item item) 
    {
        if (item != null) items.add(item);
    }

    public Item removeItemByName(String itemName) 
    {
        for (Iterator<Item> it = items.iterator(); it.hasNext();) 
        {
            Item i = it.next();
            if (i.getItemName().equalsIgnoreCase(itemName)) 
            {
                it.remove();
                return i;
            }
        }
        return null;
    }

    public Puzzle getPuzzle() 
    {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) 
    {
        this.puzzle = puzzle;
    }

    public boolean hasPuzzle() 
    {
        return puzzle != null && !puzzle.isSolved();
    }
    
}

