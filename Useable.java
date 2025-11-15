public class Useable extends Item{
    private int maxUseAmount;
    //constructor
    public Useable(String itemID, String itemName, String itemDesc, int itemValue, int maxStack) {
        super(itemID, itemName, itemDesc, itemValue, maxStack);
        this.maxUseAmount = itemValue;
    }

    @Override
    public boolean isUseable(){
        return true;
    }
    
    public int getMaxUseAmount()
    {
    	return maxUseAmount;
    }
    
    public void use() {
        if (currentStack > 0) {
            currentStack--;
            System.out.println("Used " + itemName + ". Remaining: " + currentStack);
        } else {
            System.out.println(itemName + " cannot be used, none left!");
        }
    }
}
