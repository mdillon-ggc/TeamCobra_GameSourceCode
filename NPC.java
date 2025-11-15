public class NPC extends Character {
	private String alt;
	
	public NPC(String iD, String name, String preExisting, String spawn, String monsterDies, String playerDies) {
		super(iD, name, preExisting, spawn, monsterDies, playerDies, 0, 0);
		this.alt = spawn;
	}
	
	public String getAlt()
	{
		return alt;
	}
}
