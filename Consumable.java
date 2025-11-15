public class Consumable extends Item{
	private int HP;

	//constructor
	public Consumable(String itemID, String itemName, String itemDesc, int itemValue, int maxStack) {
		super(itemID, itemName, itemDesc, itemValue, maxStack);
		this.HP = itemValue;
	}
	//getters
	public int getHP() {
		return HP;
	}

	@Override
	public boolean isConsumable(){
		return true;
	}

	@Override
    public void use() {
        if (currentStack > 0) {
            currentStack--;
            System.out.println("Consumed " + itemName + ", restoring " + HP + " HP. Remaining: " + currentStack);
        } else {
            System.out.println(itemName + " is out of stock!");
        }
    }
}
