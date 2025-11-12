public class Useable extends Item{

    //constructor
    public Useable(int itemID, String itemName, String itemDesc, String itemType, int itemValue, int maxStack, int roomID) {
        super(itemID, itemName, itemDesc, itemType, itemValue, maxStack, roomID);
    }

    @Override
    public void useItem(Item item) {
        if(item.getItemType().equalsIgnoreCase("useable")){
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
