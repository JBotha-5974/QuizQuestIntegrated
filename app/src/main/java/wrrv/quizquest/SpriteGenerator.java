package wrrv.quizquest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;

public class SpriteGenerator {
    // Marisha's version

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

   public Bitmap stand(){
       return tiles.get(26);
   }

   public AnimationDrawable handUp(Context context){

       Bitmap[] images = new Bitmap[6];
       int pos = 0;

       for(int i = 26; i < 32; i++){

           images[pos] = tiles.get(i);
           pos++;
       }

       return getAnimation(images, context, 40);
   }

    public AnimationDrawable allRound(Context context){

        Bitmap[] images = new Bitmap[4];

        images[0] = tiles.get(0);
        images[1] = tiles.get(13);
        images[2] = tiles.get(26);
        images[3] = tiles.get(39);

        return getAnimation(images, context,60);
    }

   private AnimationDrawable getAnimation(Bitmap[] images, Context context, int duration){

       AnimationDrawable animationDrawable = new AnimationDrawable();

       Resources resources = context.getResources();

       for(Bitmap bitmap: images){
           Drawable drawable = new BitmapDrawable(resources, bitmap);
           animationDrawable.addFrame(drawable, duration);
       }

       return animationDrawable;

       // start and stop animation
       // animationDrawable.start();
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
       try{

       int currentHeight = 0;
       for (Bitmap bitmap : images) {
           canvas.drawBitmap(bitmap, 0, currentHeight, null);
           currentHeight += bitmap.getHeight();
       }
       }catch(Exception e){
           System.out.println("Error layering: " + e.getMessage());
           e.printStackTrace();
       }

       return resultBitmap;

   }

   private ArrayList<Bitmap> getImages(){

       ArrayList<Bitmap> images = new ArrayList<>();

       try{

       for(Item i :itemsInUse){
           images.add(i.getItemImage(context,"blue"));
       }

       }catch(Exception e){
           System.out.println("Error getting item images: " + e.getMessage());
           e.printStackTrace();
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

        }catch(Exception e){
            System.out.println("Database error (getting items in use): " + e.getMessage());
            e.printStackTrace();
        }
   }

}
