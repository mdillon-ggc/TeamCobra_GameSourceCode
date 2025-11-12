public class Weapon extends Item {
    private int dealDamage;

    //constructor
    public Weapon(int itemID, String itemName, String itemDesc, String itemType, int itemValue, int maxStack, int roomID, int dealDamage) {
        super(itemID, itemName, itemDesc, itemType, itemValue, maxStack);
        this.dealDamage = dealDamage;
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
