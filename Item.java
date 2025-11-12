public abstract class Item {
    private int itemID;
    private String itemName;
    private String itemDesc;
    private String itemType;
    private int itemValue;
    private int maxStack;


    //constructor
    public Item(int itemID, String itemName, String itemDesc, String itemType, int itemValue, int maxStack) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemType = itemType;
        this.itemValue = itemValue;
        this.maxStack = maxStack;

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

    public boolean isEquipable(){
        return false;
    }
    public boolean isConsumable(){
        return false;
    }
    public boolean isUseable(){
        return false;
    }


}//end class
