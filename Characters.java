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
  String spawn, String monsterDies, String playerDies, int damage, int health);
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

//setters
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
    this.playerDies = player.Dies;
    }
  public void setDamage(int damgae)
    {
    this.damage = damage;
    }
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
  public boolean isAlive()
    {
     return isMonster() && health >0;
  }
}

