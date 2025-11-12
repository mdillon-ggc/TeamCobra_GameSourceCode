public class Weapon extends Item {
    private int dealDamage;

    //constructor
    public Weapon(int itemID, String itemName, String itemDesc, String itemType, int itemValue, int maxStack, int roomID, int dealDamage) {
        super(itemID, itemName, itemDesc, itemType, itemValue, maxStack, roomID);
        this.dealDamage = dealDamage;
    }
    //getter
    public int getDealDamage(){
        return dealDamage;
    }
    @Override
    public void equip(Item item){
        if(item.getItemType().equalsIgnoreCase("weapon")){
            System.out.println("Equipping weapon: " + item.getItemName());
        } else {
            System.out.println("Cannot equip item: " + item.getItemName());
        }
    }//end equip method

    @Override
    public void unequip(Item item) {
        System.out.println("Unequipping weapon: " + item.getItemName());
    }

    @Override
    public void useItem(Item item) {
        System.out.println("Weapons can't be used directly.");
    }

}
