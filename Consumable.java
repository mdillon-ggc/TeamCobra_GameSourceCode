public class Consumable extends Item{
    private int HP;

    //constructor
    public Consumable(int itemID, String itemName, String itemDesc, String itemType, int itemValue, int maxStack, int roomID, int HP) {
        super(itemID, itemName, itemDesc, itemType, itemValue, maxStack, roomID);
        this.HP = HP;
    }
    //getters
    public int getHP() {
        return HP;
    }

    @Override
    public void useItem(Item item) {
        if(item.getItemType().equalsIgnoreCase("consumable")){
            System.out.println(item.getItemName() + "is now used");
        }

    }
    @Override
    public void equip(Item item){
        System.out.println("This is a consumable item. You can not equip it");
    }//end equip method

    @Override
    public void unequip(Item item) {
        System.out.println("This is a consumable item. You can not unequip it");
    }


}
