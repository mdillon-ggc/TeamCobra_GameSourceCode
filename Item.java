public abstract class Item {
    private int itemID;
    private String itemName;
    private String itemDesc;
    private String itemType;
    private int itemValue;
    private int maxStack;
    private int roomID;

    //constructor
    public Item(int itemID, String itemName, String itemDesc, String itemType, int itemValue, int maxStack, int roomID) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemType = itemType;
        this.itemValue = itemValue;
        this.maxStack = maxStack;
        this.roomID = roomID;
    }
    //getters
    public int getItemID(){
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public String getItemType() {
        return itemType;
    }

    public int getItemValue() {
        return itemValue;
    }

    public int getMaxStack(){
        return maxStack;
    }

    public int getRoomID(){
        return roomID;
    }

    //abstract methods
    public abstract void equip(Item item);
    public abstract void unequip(Item item);
    public abstract void useItem(Item item);


}//end class
