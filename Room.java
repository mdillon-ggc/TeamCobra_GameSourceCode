import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Room 
{
    private int roomId;
    private String roomName;
    private String roomDescr;
    private boolean visited;

    private int itemId1;
    private int itemId2;
    private int itemId3;
    private int puzzleId1;
    private int puzzleId2;
    private int monsterId1;
    private int monsterId2;
    private boolean puzzleSolved = false;
    private String defeatedMonsterName;

    private Map<String, Integer> exits;
    private List<Item> items;
    private Puzzle puzzle;
    private Monster monster;

    
    public Room(int roomId, String roomName, String roomDescr,
                int itemId1, int itemId2, int itemId3, int puzzleId1, int puzzleId2, int monsterId1, int monsterId2) 
    {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescr = roomDescr;
        this.visited = false;
        this.itemId1 = itemId1;
        this.itemId2 = itemId2;
        this.itemId3 = itemId3;
        this.puzzleId1 = puzzleId1;
        this.puzzleId2 = puzzleId2;
        this.monsterId1 = monsterId1;
        this.monsterId2 = monsterId2;

        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public int getRoomId() 
    { 
    	return roomId; 
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

    public int getItemId1() 
    { 
    	return itemId1; 
    }
    
    public int getItemId2() 
    { 
    	return itemId2; 
    }
    
    public int getItemId3() 
    { 
    	return itemId3; 
    }
    
    public int getPuzzleId1() 
    { 
    	return puzzleId1; 
    }
    
    public int getPuzzleId2() 
    { 
    	return puzzleId2; 
    }
    
    public int getMonsterId1() 
    { 
    	return monsterId1; 
    }
    
    public int getMonsterId2() 
    { 
    	return monsterId2; 
    }
    
    public String getDefeatedMonsterName() 
    {
        return defeatedMonsterName;
    }

    public void setVisited(boolean visited) 
    { 
    	this.visited = visited; 
    }
    public void setItemId1(int itemId) 
    { 
    	this.itemId1 = itemId1; 
    }
    
    public void setItemId2(int itemId) 
    { 
    	this.itemId2 = itemId2; 
    }
    
    public void setItemId3(int itemId) 
    { 
    	this.itemId3 = itemId3; 
    }
    
    public void setPuzzleId1(int puzzleId) 
    { 
    	this.puzzleId1 = puzzleId1; 
    }
    
    public void setPuzzleId2(int puzzleId) 
    { 
    	this.puzzleId2 = puzzleId2; 
    }
    
    public void setMonsterId1(int monsterId) 
    { 
    	this.monsterId1 = monsterId1; 
    }
    
    public void setMonsterId2(int monsterId) 
    { 
    	this.monsterId2 = monsterId2; 
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

    public void addExit(String direction, int roomId) 
    {
        if (roomId != 0)
            exits.put(direction.toUpperCase(), roomId);
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

    
    public Monster getMonster() 
    {
        return monster;
    }

    public void setMonster(Monster monster) 
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
