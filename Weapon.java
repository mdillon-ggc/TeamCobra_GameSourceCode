public class Weapon extends Item {
    private int dealDamage;

    //constructor
    public Weapon(String itemID, String itemName, String itemDesc, String itemType, int itemValue, int maxStack) {
        super(itemID, itemName, itemDesc, itemType, itemValue, maxStack);
        this.dealDamage = itemValue;
    }
    //getter
    public int getDealDamage(){
        return dealDamage;
    }

    @Override
    public boolean isEquipable(){
        return true;
    }

}
