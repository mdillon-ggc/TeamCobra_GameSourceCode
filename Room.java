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

    private boolean puzzleSolved = false;
    private String defeatedMonsterName;

    private Map<String, Integer> exits;
    private List<Item> items;
    private Puzzle puzzle;
    private Character monster;

    
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
    public boolean isVisited() 
    { 
    	return visited; 
    }
    
    public String getDefeatedMonsterName() 
    {
        return defeatedMonsterName;
    }

    public void setVisited(boolean visited) 
    { 
    	this.visited = visited; 
    }
   
    public void setDefeatedMonsterName(String name) 
    {
        this.defeatedMonsterName = name;
    }
    
    public void setRoomDescr(String descr) 
    { 
    	this.roomDescr = descr; 
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

        if (puzzle != null && puzzle.isFailed()) 
        {
            puzzle.resetPuzzle();
        }

        if (puzzleSolved) 
        {
            System.out.println("Puzzle is solved in this room.");
        }
        
        if (defeatedMonsterName != null) 
        {
            System.out.println("Monster defeated in this room: " + defeatedMonsterName);
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

    public void triggerPuzzle() 
    {
        if (puzzle == null) return;

        if (puzzle.isSolved()) 
        {
            System.out.println("You have already solved this puzzle.");
            puzzleSolved = true;
            return;
        }

        puzzle.start(new Scanner(System.in));
    }

    public void setPuzzleSolved(boolean solved) 
    {
        this.puzzleSolved = solved;
    }

    
    public Character getMonster() 
    {
        return monster;
    }

    public void setMonster(Character monster) 
    {
        this.monster = monster;
    }

    public void triggerMonster() 
    {
        if (monster == null) return;

        if (monster.isDead()) 
        {
            System.out.println("Monster defeated: " + monster.getMonsterName());
        } 
        else 
        {
            System.out.println("Monster here: " + monster.getMonsterName());
            System.out.println(monster.getMonsterDescr());
        }
    }
}


