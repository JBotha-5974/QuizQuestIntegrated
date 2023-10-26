package wrrv.quizquest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class SpriteGenerator {

    //private Map<String, Set<String>> item;

    public int getImage(String array, int position){
//        switch(array){
//            case "shoes":
//                return shoes[position];
//            default:
//                return shoes[position];
//
//
//        }
        return 1;
    }

    public SpriteGenerator(){

    }


    public static Bitmap getItemBitmap(Context context, int resourceId) {
        try {
            // Open an input stream to the asset
            InputStream inputStream = getAssets().open("image.jpg");

            // Decode the InputStream into a Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Now 'bitmap' contains the loaded image
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
