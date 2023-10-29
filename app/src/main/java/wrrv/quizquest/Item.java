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

    //0 body
    //1 eyes
    //2 hair
    //3 shoes
    //4 lower
    //5 torso
    //6 jacket
    //7 accessories

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

    public Bitmap getItemImage(Context context, String curColor) {

        AssetManager assetManager = context.getAssets();

        String fileName = "";
        if(Objects.equals(gender, "m")){
            fileName = "Male/";

            if(layer == 5){
                //if lower item (pants/skirt)

                switch(itemName){
                    case "pantaloons":
                        fileName = fileName + "lower/pantaloons/" + curColor + ".png";
                        break;
                    case "pants":
                        fileName = fileName + "lower/pants/" + curColor + ".png";
                        break;
                }


            }else{
                //if torso item (shirt etc)

                switch(itemName){
                    case "longsleeve":
                        fileName = fileName + "upper/longsleeve/" + curColor + ".png";
                        break;
                    case "shortsleeve":
                        fileName = fileName + "upper/shortsleeve/" + curColor + ".png";
                        break;
                    case "sleeveless":
                        fileName = fileName + "upper/sleeveless/" + curColor + ".png";
                        break;
                    case "vest":
                        fileName = fileName + "upper/vest/" + curColor + ".png";
                        break;
                }
            }

        }else if(Objects.equals(gender, "f")){
            fileName = "Female/";
            if(layer == 5){
                //if lower item (pants/skirt)

                switch(itemName){
                    case "leggings":
                        fileName = fileName + "lower/leggings/" + curColor + ".png";
                        break;
                    case "pants":
                        fileName = fileName + "lower/pants/" + curColor + ".png";
                        break;
                    case "skirt":
                        fileName = fileName + "lower/skirt/" + curColor + ".png";
                        break;
                }

            }else{
                //if torso item (shirt etc)

                switch(itemName){
                    case "blouse":
                        fileName = fileName + "upper/blouse/" + curColor + ".png";
                        break;
                    case "corset":
                        fileName = fileName + "upper/corset/" + curColor + ".png";
                        break;
                    case "longsleeve":
                        fileName = fileName + "upper/longsleeve/" + curColor + ".png";
                        break;
                    case "shortsleeve":
                        fileName = fileName + "upper/shortsleeve/" + curColor + ".png";
                        break;
                    case "sleeveless":
                        fileName = fileName + "upper/sleeveless/" + curColor + ".png";
                        break;
                    case "tanktop":
                        fileName = fileName + "upper/tanktop/" + curColor + ".png";
                        break;
                    case "tunic":
                        fileName = fileName + "upper/tunic/" + curColor + ".png";
                        break;
                }

            }

        }else{
            fileName = "Unisex/";

            switch(itemName){
                case "collared jacket":
                    fileName = fileName + "jackets/collared/" + curColor + ".png";
                    break;
                case "trench coat":
                    fileName = fileName + "jackets/trench/" + curColor + ".png";
                    break;
                case "boots":
                    fileName = fileName + "shoes/boots/" + curColor + ".png";
                    break;
                case "sandals":
                    fileName = fileName + "shoes/sandals/" + curColor + ".png";
                    break;
                case "shoes":
                    fileName = fileName + "shoes/shoes/" + curColor + ".png";
                    break;
                case "crown":
                    fileName = fileName + "accessories/crown.png";
                    break;
                case "formal hat":
                    fileName = fileName + "accessories/formal_hat.png";
                    break;
                case "glasses":
                    fileName = fileName + "accessories/glasses.png";
                    break;
                case "pirate hat":
                    fileName = fileName + "accessories/pirate_hat.png";
                    break;
                case "round hat":
                    fileName = fileName + "accessories/round_hat.png";
                    break;
                case "sunglasses":
                    fileName = fileName + "accessories/sunglasses.png";
                    break;
                case "tiara":
                    fileName = fileName + "accessories/tiara.png";
                    break;
                case "tricorne":
                    fileName = fileName + "accessories/tricorne.png";
                    break;
            }
        }
        try {
            InputStream inputStream = assetManager.open(fileName);
            Bitmap temp =  BitmapFactory.decodeStream(inputStream);
            return splitImage(temp);

        } catch (IOException e) {
            System.out.println("Error getting item image: "  + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public Bitmap getItemSheet(Context context, String curColor) {

        AssetManager assetManager = context.getAssets();

        String fileName = "";
        if(gender == "m"){
            fileName = "Male/";

            if(layer == 4){
                //if lower item (pants/skirt)

                switch(itemName){
                    case "pantaloons":
                        fileName = fileName + "lower/pantaloons/" + curColor + ".png";
                        break;
                    case "pants":
                        fileName = fileName + "lower/pants/" + curColor + ".png";
                        break;
                }


            }else{
                //if torso item (shirt etc)

                switch(itemName){
                    case "longsleeve":
                        fileName = fileName + "upper/longsleeve/" + curColor + ".png";
                        break;
                    case "shortsleeve":
                        fileName = fileName + "upper/shortsleeve/" + curColor + ".png";
                        break;
                    case "sleeveless":
                        fileName = fileName + "upper/sleeveless/" + curColor + ".png";
                        break;
                    case "vest":
                        fileName = fileName + "upper/vest/" + curColor + ".png";
                        break;
                }
            }

        }else if(gender == "f"){
            fileName = "Female/";

            if(layer == 4){
                //if lower item (pants/skirt)

                switch(itemName){
                    case "leggings":
                        fileName = fileName + "lower/leggings/" + curColor + ".png";
                        break;
                    case "pants":
                        fileName = fileName + "lower/pants/" + curColor + ".png";
                        break;
                    case "skirt":
                        fileName = fileName + "lower/skirt/" + curColor + ".png";
                        break;
                }

            }else{
                //if torso item (shirt etc)

                switch(itemName){
                    case "blouse":
                        fileName = fileName + "upper/blouse/" + curColor + ".png";
                        break;
                    case "corset":
                        fileName = fileName + "upper/corset/" + curColor + ".png";
                        break;
                    case "longsleeve":
                        fileName = fileName + "upper/longsleeve/" + curColor + ".png";
                        break;
                    case "shortsleeve":
                        fileName = fileName + "upper/shortsleeve/" + curColor + ".png";
                        break;
                    case "sleeveless":
                        fileName = fileName + "upper/sleeveless/" + curColor + ".png";
                        break;
                    case "tanktop":
                        fileName = fileName + "upper/tanktop/" + curColor + ".png";
                        break;
                    case "tunic":
                        fileName = fileName + "upper/tunic/" + curColor + ".png";
                        break;
                }

            }

        }else{
            fileName = "Unisex/";

            switch(itemName){
                case "glasses":
                    fileName = fileName + "accessories/glasses.png";
                    break;
                case "sunglasses":
                    fileName = fileName + "accessories/sunglasses.png";
                    break;
                case "collared jacket":
                    fileName = fileName + "jackets/collared/" + curColor + ".png";
                    break;
                case "trench coat":
                    fileName = fileName + "jackets/trench/" + curColor + ".png";
                    break;
                case "boots":
                    fileName = fileName + "shoes/boots/" + curColor + ".png";
                    break;
                case "sandals":
                    fileName = fileName + "shoes/sandals/" + curColor + ".png";
                    break;
                case "shoes":
                    fileName = fileName + "shoes/shoes/" + curColor + ".png";
                    break;
            }
        }


        try {
            InputStream inputStream = assetManager.open(fileName);
            return BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {
            System.out.println("Error getting item sheet: "  + e.getMessage());
            e.printStackTrace();
        }

        return null;
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
