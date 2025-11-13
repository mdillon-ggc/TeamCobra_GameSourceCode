public class Consumable extends Item{
    private int HP;

    //constructor
    public Consumable(String itemID, String itemName, String itemDesc, String itemType, int itemValue, int maxStack, int HP) {
        super(itemID, itemName, itemDesc, itemType, itemValue, maxStack);
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


}
