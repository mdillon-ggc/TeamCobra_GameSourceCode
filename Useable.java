public class Useable extends Item{

    //constructor
    public Useable(int itemID, String itemName, String itemDesc, String itemType, int itemValue, int maxStack, int roomID) {
        super(itemID, itemName, itemDesc, itemType, itemValue, maxStack);
    }

    @Override
    public boolean isUseable(){
        return true;
    }
}
