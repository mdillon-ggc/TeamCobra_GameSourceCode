public class Useable extends Item{
    private int maxUseAmount;
    //constructor
    public Useable(int itemID, String itemName, String itemDesc, String itemType, int itemValue, int maxStack) {
        super(itemID, itemName, itemDesc, itemType, itemValue, maxStack);
        this.maxUseAmount = itemValue;
    }

    @Override
    public boolean isUseable(){
        return true;
    }
}
