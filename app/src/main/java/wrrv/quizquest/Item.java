package wrrv.quizquest;

import android.media.Image;

import java.io.Serializable;

public class Item implements Serializable {
    private int itemID;
    private String itemName;
    private int itemPrice;

    //these are used to access the image in the Sprite Generator
    private String array;
    private int position;

    public Item(int itemID, String itemName, int itemPrice, String array, int position) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.array = array;
        this.position = position;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemImage() {
        SpriteGenerator generator = new SpriteGenerator();

        return generator.getImage(array, position);

    }
}
