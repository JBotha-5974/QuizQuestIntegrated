package wrrv.quizquest;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;

public class SpriteHolder {

    private ArrayList<Drawable> Hair;
    private ArrayList<Drawable> Shirts;
    private ArrayList<Drawable> Pants;
    private ArrayList<Drawable> Feet;
    private ArrayList<Drawable> Gender;
    private String sGender, sHair, sShirt, sPants, sFeet;
    private LayerDrawable finalImage;

    public SpriteHolder(String sInput)
    {
        String[] strings = sInput.split(",");
        sGender = strings[0];
        sHair = strings[1];
        sShirt = strings[2];
        sPants = strings[3];
        sFeet = strings[4];
        FillArrays();
    }
    private void FillArrays()
    {
        /*int[] genderDrawableIds = {R.drawable.g0, R.drawable.g1};

        for (int k = 0; k < 2; k++) {
            Gender.add(AppCompatResources.getDrawable(this, genderDrawableIds[k]));
        }
        for (int k = 0; k < 2; k++)
        {
            String sTempGender = "g"+ k;
            Gender.add(AppCompatResources.getDrawable(this, R.drawable.));
        }
        Hair = new Drawable[48];
        Shirts = new Drawable[48];
        Pants = new Drawable[48];
        Feet = new Drawable[48];*/
    }
}
