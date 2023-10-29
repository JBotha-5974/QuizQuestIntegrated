package wrrv.quizquest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class SpriteGenerator {
    // Marisha's version

    ArrayList<Map<Item,String>> itemsInUse;

    Context context;

    String userName;

    public SpriteGenerator(Context context, String userName){
       this.context = context;
       this.userName = userName;

       getItems();
       sortItems();
       ArrayList<Bitmap> images = getImages();
       Bitmap image = createLayered(images);

    }

    public Bitmap generate(){
       return createLayered(getImages());
    }


    private Bitmap createLayered(ArrayList<Bitmap> images){

       ArrayList<Drawable> drawables = new ArrayList<>();

       try {
           for (Bitmap bitmap : images) {
               Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
               drawables.add(drawable);
           }
       }catch(Exception e){
           System.out.println("Error converting Bitmap > Drawable: \n" + e.getMessage());
           e.printStackTrace();
       }

       Drawable[] layers = new Drawable[drawables.size()];
       layers = drawables.toArray(layers);

       LayerDrawable layered =  new LayerDrawable(layers);

       int width = layered.getIntrinsicWidth();
       int height = layered.getIntrinsicHeight();


       Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

       Canvas canvas = new Canvas(bitmap);

       layered.setBounds(0, 0, width, height);
       layered.draw(canvas);

       return bitmap;
    }

    private ArrayList<Bitmap> getImages(){

       ArrayList<Bitmap> images = new ArrayList<>();

       try{

           for (Map<Item, String> map : itemsInUse) {
               for (Map.Entry<Item, String> entry : map.entrySet()) {
                   Item item = entry.getKey();
                   String value = entry.getValue();

                   Log.d("Inv Items in use: ", "item -> "+ value + " " + item.getItemName());
                   images.add(item.getItemImage(context, value));
               }
           }

       }catch(Exception e){
           System.out.println("Error getting item images: " + e.getMessage());
           e.printStackTrace();
       }
       return images;
   }

    private void sortItems(){
       //This is used to sort the arraylist in the order they will be layered

       itemsInUse.sort(Comparator.comparingInt(map -> map.keySet().iterator().next().getLayer()));

       //0 body
       //1 head
       //2 eyes
       //3 hair
       //4 shoes
       //5 lower
       //6 torso
       //7 jacket
       //8 accessories
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
