public class Characters{
  private String iD;
  private String name;
  private String charType; //Monster or NPC
  private String preExisting;  //Text when already in room
  private String spawn; // Text when the alart system goes off
  private String monsterDies; // Text for defeating the monster
  private String playerDies; // Text for the player dying by monster
  private int damage; 
  private int health;
 

public Character(String id, String name, String charType, String preExisting, 
  String spawn, String monsterDies, String playerDies, int damage, int health, String location);
  {
    this.iD = iD;
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
