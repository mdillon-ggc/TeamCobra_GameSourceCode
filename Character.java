public class Character
{
    private String iD;
    private String name;
    private String charType;    // Monster or NPC
    private String preExisting; // Text when already in room
    private String spawn;       // Text when the alert system goes off
    private String monsterDies; // Text for defeating the monster
    private String playerDies;  // Text for the player dying by monster
    private int damage;
    private int health;

    // Constructor
    public Character(String id, String name, String preExisting,
                     String spawn, String monsterDies, String playerDies,
                     int damage, int health)
    {
        this.iD = id;
        this.name = name;
        this.preExisting = preExisting;
        this.spawn = spawn;
        this.monsterDies = monsterDies;
        this.playerDies = playerDies;
        this.damage = damage;
        this.health = health;
    }

    // Getters
    public String getiD()
    {
        return iD;
    }

    public String getName()
    {
        return name;
    }

    public String getCharType()
    {
        return charType;
    }

    public String getPreExisting()
    {
        return preExisting;
    }

    public String getSpawn()
    {
        return spawn;
    }

    public String getMonsterDies()
    {
        return monsterDies;
    }

    public String getPlayerDies()
    {
        return playerDies;
    }

    public int getDamage()
    {
        return damage;
    }

    public int getHealth()
    {
        return health;
    }

    // Setters
    public void setName(String name)
    {
        this.name = name;
    }

    public void setCharType(String charType)
    {
        this.charType = charType;
    }

    public void setPreExisting(String preExisting)
    {
        this.preExisting = preExisting;
    }

    public void setPlayerDies(String playerDies)
    {
        this.playerDies = playerDies;
    }

    public void setDamage(int damage)
    {
        this.damage = damage;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    
    public boolean isNPC()
    {
        return this instanceof NPC; 
    }

    public boolean isMonster()
    {
       return this instanceof Monster;
    }

    public boolean isAlive()
    {
        return health > 0;
    }
    
    public boolean canBeAttacked()
    {
        return isMonster();  
    }
    
    @Override
    public String toString() {
        return this.getName(); // or any property you want to display
    }

}
