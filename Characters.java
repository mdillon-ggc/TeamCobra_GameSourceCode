public ckass Character{
  private String id;
  private String name;
  private String CharType; //Monster or NPC
  private String PreExisting;  //Text when already in room
  private String Spawn; // Text when the alart system goes off
  private String MonsterDies; // Text for defeating the monster
  private String PlayerDies; // Text for the player dying by monster
  private int Damage; 
  private int Health;
  private String Location; //location format R4 (R+ROOMID)

public Character)String id, String name, String charType, String preExisting, 
  String spawn, String monsterDies, String playerDies, int damage, int health, String location);
  {
    this.id = id;
    this.name = name;
    this.charType = charType;
    this.preExisting = preExisting;
    this.spawn = spawn;
    this.monster Dies = monsterDies;
    this.playerDies = playerDies;
    this.damage = damage;
    this.health = health;
    this.location = location;
  }
//getters
public String getId()
  {
  return id;
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

public String getLocation()
  {
  return location;
}

//setters
 public void setHealth(int health) 
    {
        this.health = health;
    }

    public void setLocation(String location) 
    {
        this.location = location;
    }


//Checker for NPC + MONSTER

 public boolean isNPC()
    {
        return charType != null && charType.equalsIgnoreCase("NPC");
    }

    public boolean isMonster()
    {
        return charType != null && charType.equalsIgnoreCase("Monster");
    }
}
