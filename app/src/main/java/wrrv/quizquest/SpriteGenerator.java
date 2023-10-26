package wrrv.quizquest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class SpriteGenerator {

   ArrayList<Item> itemsInUse;

   Context context;

   //Player player;
   String userName;

    ArrayList<Bitmap> tiles;

   public SpriteGenerator(Context context, String userName){
       this.context = context;
       this.userName = userName;

       getItems();
       sortItems();
       ArrayList<Bitmap> images = getImages();
       Bitmap image = createLayered(images);
       tiles = splitImage(image);
   }

   private ArrayList<Bitmap> splitImage(Bitmap image){

       int columns = 13;
       int rows = 21;

       ArrayList<Bitmap> subImages = new ArrayList<>();
       int subImageWidth = image.getWidth() / columns;
       int subImageHeight = image.getHeight() / rows;

       for (int i = 0; i < rows; i++) {
           for (int j = 0; j < columns; j++) {
               int x = j * subImageWidth;
               int y = i * subImageHeight;
               subImages.add(Bitmap.createBitmap(image, x, y, subImageWidth, subImageHeight));
           }
       }

       return subImages;
   }

   private Bitmap createLayered(ArrayList<Bitmap> images){
       int width = images.get(0).getWidth();
       int height = images.get(0).getHeight();

       // Create a new bitmap with the calculated width and height
       Bitmap resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
       Canvas canvas = new Canvas(resultBitmap);

       // Draw individual bitmaps onto the resultBitmap
       int currentHeight = 0;
       for (Bitmap bitmap : images) {
           canvas.drawBitmap(bitmap, 0, currentHeight, null);
           currentHeight += bitmap.getHeight();
       }

       return resultBitmap;

   }

   private ArrayList<Bitmap> getImages(){

       ArrayList<Bitmap> images = new ArrayList<>();

       for(Item i :itemsInUse){
           images.add(i.getItemImage(context));
       }
       return images;
   }

   private void sortItems(){
       //This is used to sort the arraylist in the order they will be layered
       Collections.sort(itemsInUse, new ItemLayerComparator());

       //0 body
       //1 eyes
       //2 hair
       //3 shoes
       //4 lower
       //5 torso
       //6 jacket
       //7 accessories
   }

   private void getItems(){
        itemsInUse = new ArrayList<>();

        try{
            itemsInUse = Database.getItemsInUse(userName);

        }catch (Exception e) {
            e.printStackTrace();
        }
   }

}
