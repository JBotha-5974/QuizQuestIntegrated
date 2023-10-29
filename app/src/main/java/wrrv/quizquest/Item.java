package wrrv.quizquest;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Item implements Serializable {
    private int itemID;
    private String itemName;
    private int itemPrice;

    // gender item is for, either male(m), female(f), or unisex(u)
    private String gender;

    //layer is for the order in which items are layered to make the final sprite
    //there is also a ItemLayerComparator that sorts by layer
    private int layer;

    //0 body, 1 head, 2 eyes, 3 hair, 4 shoes, 5 lower, 6 torso, 7 jacket, 8 accessories

    //making it so the player just buys 1 item and can have all the colours
    String colors;
    Set<String> options;

    public Item(int itemID, String itemName, int itemPrice, String gender, int layer, String colors) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.gender = gender;
        this.layer = layer;
        this.colors = colors;

        setColors();
    }



    private void setColors(){
        String[] c = colors.split(",");

        options = new HashSet<>();

        for (String option : c) {
            options.add(option);
        }
    }

    private Bitmap getMale(Context context, String curColor){
        String fileName = "";

        if(layer == 0){
            // body
            fileName = "Male/body/" + curColor;

        }else if(layer == 1){
            //head
            fileName = "Male/head/" + curColor;

        }else if(layer == 5){
            //lower item (pants/skirt)

            switch(itemName){
                case "pantaloons":
                    fileName = "Male/lower/pantaloons/" + curColor;
                    break;
                case "pants":
                    fileName = "Male/lower/pants/" + curColor;
                    break;
            }
        }else{
            //torso item (shirt etc)

            switch(itemName){
                case "longsleeve":
                    fileName = "Male/upper/longsleeve/" + curColor;
                    break;
                case "shortsleeve":
                    fileName = "Male/upper/shortsleeve/" + curColor;
                    break;
                case "sleeveless":
                    fileName = "Male/upper/sleeveless/" + curColor;
                    break;
                case "vest":
                    fileName = "Male/upper/vest/" + curColor;
                    break;
            }
        }

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            Bitmap temp =  BitmapFactory.decodeStream(inputStream);
            return splitImage(temp);

        } catch (IOException e) {
            System.out.println("Error getting item image: "  + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private Bitmap getFemale(Context context, String curColor){
        String fileName = "";

        if(layer == 0){
            // body
            fileName = "Female/body/" + curColor;

        }else if(layer == 1){
            //head
            fileName = "Female/head/" + curColor;

        }else if(layer == 5){
            //lower item (pants/skirt)

            switch(itemName){
                case "leggings":
                    fileName = "Female/lower/leggings/" + curColor;
                    break;
                case "pants":
                    fileName = "Female/lower/pants/" + curColor;
                    break;
                case "skirt":
                    fileName = "Female/lower/skirt/" + curColor;
                    break;
            }

        }else{
            switch(itemName){
                case "blouse":
                    fileName= "Female/upper/blouse/" + curColor;
                    break;
                case "corset":
                    fileName = "Female/upper/corset/" + curColor;
                    break;
                case "longsleeve":
                    fileName = "Female/upper/longsleeve/" + curColor;
                    break;
                case "shortsleeve":
                    fileName = "Female/upper/shortsleeve/" + curColor;
                    break;
                case "sleeveless":
                    fileName = "Female/upper/sleeveless/" + curColor;
                    break;
                case "tanktop":
                    fileName = "Female/upper/tanktop/" + curColor;
                    break;
                case "tunic":
                    fileName = "Female/upper/tunic/" + curColor;
                    break;
            }
        }

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            Bitmap temp =  BitmapFactory.decodeStream(inputStream);
            return splitImage(temp);

        } catch (IOException e) {
            System.out.println("Error getting item image: "  + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private Bitmap getUnisex(Context context, String curColor){
        String fileName = "";

        switch(itemName){
            case "eyes":
                fileName = "Unisex/eyes/" + curColor;
                break;
            case "collared jacket":
                fileName = "Unisex/jackets/collared/" + curColor;
                break;
            case "trench coat":
                fileName = "Unisex/jackets/trench/" + curColor;
                break;
            case "boots":
                fileName = "Unisex/shoes/boots/" + curColor;
                break;
            case "sandals":
                fileName = "Unisex/shoes/sandals/" + curColor;
                break;
            case "shoes":
                fileName = "Unisex/shoes/shoes/" + curColor;
                break;
            case "crown":
                fileName = "Unisex/accessories/crown.png";
                break;
            case "formal hat":
                fileName = "Unisex/accessories/formal_hat.png";
                break;
            case "glasses":
                fileName = "Unisex/accessories/glasses.png";
                break;
            case "pirate hat":
                fileName = "Unisex/accessories/pirate_hat.png";
                break;
            case "round hat":
                fileName = "Unisex/accessories/round_hat.png";
                break;
            case "sunglasses":
                fileName = "Unisex/accessories/sunglasses.png";
                break;
            case "tiara":
                fileName = "Unisex/accessories/tiara.png";
                break;
            case "tricorne":
                fileName = "Unisex/accessories/tricorne.png";
                break;
        }

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            Bitmap temp =  BitmapFactory.decodeStream(inputStream);
            return splitImage(temp);

        } catch (IOException e) {
            System.out.println("Error getting item image: "  + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public Bitmap getItemImage(Context context, String color) {

        String curColor = color.replace(" ", "_");
        curColor += ".png";

        String fileName = "";
        if(Objects.equals(gender, "m")){

            return getMale(context, curColor);

        }else if(Objects.equals(gender, "f")){

           return getFemale(context, curColor);

        }else{

            return getUnisex(context, curColor);

        }

    }

    private Bitmap splitImage(Bitmap image){

        int columns = 13;
        int rows = 21;

        int subImageWidth = image.getWidth() / columns;
        int subImageHeight = image.getHeight() / rows;

        int x = (1 - 1) * subImageWidth;
        int y = (3 - 1) * subImageHeight;

        return Bitmap.createBitmap(image, x, y, subImageWidth, subImageHeight);
    }

    public void setUpdate(int id, String desc, int price){
        // update in db
        try {

            Database.updateItem(id, desc, price);

        }catch(Exception e){
            System.out.println("Error updating item: " + e.getMessage());
            e.printStackTrace();
        }

        //update in class
        this.itemName = desc;
        this.itemPrice = price;

    }

    public Set<String> getColors(){
        return options;
    }

    public int getItemID() {
        return itemID;
    }

    public String getGender(){return gender;}

    public String getItemName() {
        return itemName;
    }

    public int getLayer() {
        return layer;
    }

    public int getItemPrice() {
        return itemPrice;
    }


}
