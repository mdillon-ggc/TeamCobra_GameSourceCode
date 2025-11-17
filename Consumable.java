public class Consumable extends Item{
	private int hP;

	//constructor
	public Consumable(String itemID, String itemName, String itemDesc,
			int itemValue, int maxStack) {
		super(itemID, itemName, itemDesc, itemValue, maxStack);
		this.hP = itemValue;
		this.itemType = "consumable";
	}
	//getters
	public int getHP() {
		return hP;
	}

	@Override
	public boolean isConsumable(){
		return true;
	}

	@Override
    public void use() {
        if (currentStack > 0) {
            currentStack--;
            System.out.println("Consumed " + itemName + ", restoring " 
            + hP + " HP. Remaining: " + currentStack);
        } else {
            System.out.println(itemName + " is out of stock!");
        }
    }
}
