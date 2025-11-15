import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room 
{
    private String roomID;
    private String roomFloorID;
    private String roomName;
    private String roomDescr;
    private boolean visited;

    private Map<String, String> exits = new HashMap<>();;
    private Puzzle puzzle;
    private ArrayList<Item> roomInventory = new ArrayList<>(); 
    private ArrayList<Character> characterList =new ArrayList<>();
    private ArrayList<Character> CharacterInRoom = new ArrayList<>();
	private Character eliteMercernary;

    public Room(String roomID, String roomFloorID, String roomName, String roomDescr) 
    {
        this.roomID = roomID;
        this.roomFloorID = roomFloorID;
        this.roomName = roomName;
        this.roomDescr = roomDescr;
        this.visited = false;
        this.exits = new HashMap<>();
    }

    public String getRoomID() 
    { 
        return roomID; 
    }

    public String getRoomFloorID() 
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

    public ArrayList<Character> getCharacterList() 
    {
        return characterList;
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
        characterList.add(character);
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

        System.out.println("Puzzle is solved in this room.");
    }

    public void addExit(String direction, String targetRoomID) 
    {
        if (targetRoomID != null && !targetRoomID.isEmpty() && !targetRoomID.equals("-1")) 
        {
            exits.put(direction.toLowerCase(), targetRoomID);
        }
    }

    public String getExit(String direction) 
    {
    	return exits.get(direction.toLowerCase());
    }

    public Map<String, String> getExits() 
    {
        return exits;
    }

    public void setExits(Map<String, String> exits) 
    {
        if (exits != null)
            this.exits.putAll(exits);
    }

    public ArrayList<Item> getItems() 
    {
        return roomInventory;
    }

    public void addItem(Item item) 
    {
        if (item != null) roomInventory.add(item);
    }

    public boolean removeItem(Item item) 
    {
        return roomInventory.remove(item);
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

    public void resetPuzzle()
    {
        if (puzzle != null)
        {
            puzzle.resetPuzzle();
        }
    }

	public boolean hasAlert()
	{
		return false;
	}
	
	public void checkAlert(Player player)
	{
		if(player.getDetectionLvl() >= 3)
		{
			if(eliteMercernary != null && !CharacterInRoom.contains(eliteMercernary))
			{
				System.out.println(((NPC)eliteMercernary).getAlt());
				CharacterInRoom.add(eliteMercernary);
				System.out.println("Alert triggered!" + eliteMercernary.getName() 
				+ "has appeared!");
			}
		}
	}

	public void setEliteMercernary(Character eliteMerc)
	{
		this.eliteMercernary = eliteMerc;
	}
}
