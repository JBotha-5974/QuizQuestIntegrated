package wrrv.quizquest;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.graphics.Bitmap;
import android.os.Bundle;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Item implements Serializable {
    private int itemID;
    private String itemName;
    private int itemPrice;

    //these are used to access the image in the Sprite Generator
    private String array;
    private int position;

    //making it so the player just buys 1 item and can have all the colours
    String colors;
    Set<String> options;
    String curColor;

    public Item(int itemID, String itemName, int itemPrice, String array, int position, String colors, String curColor) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.array = array;
        this.position = position;
        this.colors = colors;
        this.curColor = curColor;

        setColors();
    }



    private void setColors(){
        String[] c = colors.split(",");

        options = new HashSet<>();

        for (String option : c) {
            options.add(option);
        }
    }

    public int getItemImage() {
        SpriteGenerator generator = new SpriteGenerator();

        return generator.getImage(array, position);

    }

    public Bitmap getItemBitmap(){

    }

    private Image[] splitImage(int imageResourceId){

        int rows = 21;
        int columns = 13;

        Bitmap image = BitmapFactory.decodeResource(getResources(), imageResourceId);

        int subimage_Width = image.getWidth() / columns;
        int subimage_Height = image.getHeight() / rows;

    }

    public void setUpdate(int id, String desc, int price){
        setItemName(desc);
        setItemPrice(price);

        // update in db
        Database.updateItem(id,desc,price);

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
}
