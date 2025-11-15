public abstract class Item implements Cloneable {
    protected String itemID;
    protected String itemName;
    protected String itemDesc;
    protected String itemType;
    protected int itemValue;
    protected int maxStack;
    protected int currentStack = 1; //default 1


    //constructor
    public Item(String itemID, String itemName, String itemDesc, int itemValue, int maxStack) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemValue = itemValue;
        this.maxStack = maxStack;

    }
    
    @Override
    public Item clone() {
        try {
            return (Item) super.clone(); // shallow copy is fine for primitives/Strings
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }    
    
    //getters
    public String getItemID(){
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public int getItemValue() {
        return itemValue;
    }

    public String getItemType() {
        return itemType;
    }
    
    public int getMaxStack(){
        return maxStack;
    }
    
    public int getCurrentStack(){
        return currentStack;
    }
    
    public void setMaxStack(int maxStack) {
    	this.maxStack = maxStack;
    }
    
    public void setCurrentStack(int currentStack) {
    	this.currentStack = currentStack;
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

    public void use() {
        System.out.println(itemName + "cannot be used.");
    }
    
    @Override
    public String toString() {
        return this.getItemName(); // or any property you want to display
    }


}//end class
