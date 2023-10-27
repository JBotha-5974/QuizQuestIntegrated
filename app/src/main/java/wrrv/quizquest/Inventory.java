package wrrv.quizquest;

public class Inventory {
    private String userName;
    private int itemID;
    private String color; //this data type could change, currently thinking the string could be 20230811, etc.
    private boolean itemInUse;

    public Inventory(String userName, int itemID, String color, boolean itemInUse) {
        this.userName = userName;
        this.itemID = itemID;
        this.color = color;
        this.itemInUse = itemInUse;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getPurchaseDate() {
        return color;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.color = purchaseDate;
    }

    public boolean isItemInUse() {
        return itemInUse;
    }

    public void setItemInUse(boolean itemInUse) {
        this.itemInUse = itemInUse;
    }
}
