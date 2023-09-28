package wrrv.quizquest;

import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenerateSprite {
    private Map<String, Integer> genderResourceMap = new HashMap<>();
    private ArrayList<Drawable>

    public GenerateSprite(String sCode)
    {
        genderResourceMap.put("g0", R.drawable.f);
        genderResourceMap.put("g1", R.drawable.m);
        for (int k = 0; k < 2; k++) {
            String sTempGender = "g" + k;
            Integer resourceId = genderResourceMap.get(sTempGender);
            if (resourceId != null) {
                Gender.add(AppCompatResources.getDrawable(this, resourceId));
            } else {
                // Handle the case where the resource is not found.
            }
        }
    }



}
