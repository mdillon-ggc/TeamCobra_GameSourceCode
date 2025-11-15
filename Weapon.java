public class Weapon extends Item {
    private int dealDamage;

    //constructor
    public Weapon(String itemID, String itemName, String itemDesc, int itemValue) {
        super(itemID, itemName, itemDesc, itemValue, 1); //weapons not stackable
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
