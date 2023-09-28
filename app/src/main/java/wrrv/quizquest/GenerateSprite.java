package wrrv.quizquest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenerateSprite {
    private Map<String, Integer> genderResourceMap = new HashMap<>();
    private Map<String, Integer> HairResourceMap = new HashMap<>();
    private Map<String, Integer> ShirtResourceMap = new HashMap<>();
    private Map<String, Integer> PantsResourceMap = new HashMap<>();
    private Map<String, Integer> FeetResourceMap = new HashMap<>();
    private ArrayList<Drawable> Gender;
    private ArrayList<Drawable> Hair;
    private ArrayList<Drawable> Shirts;
    private ArrayList<Drawable> Pants;
    private ArrayList<Drawable> Feet;
    private String sGender, sHair, sShirt, sPants, sFeet;
    private int iPosG, iPosH, iPosS, iPosP, iPosF;
    private LayerDrawable imgToPush;

    public GenerateSprite(Context context, String sCode)
    {
        String[] strings = sCode.split(",");
        sGender = strings[0];
        sHair = strings[1];
        sShirt = strings[2];
        sPants = strings[3];
        sFeet = strings[4];
        if (sGender.equals("f"))
            iPosG = 0;
        else
            iPosG = 1;
        iPosF = Integer.parseInt(sFeet.substring(1));
        iPosH = Integer.parseInt(sHair.substring(1));
        iPosP = Integer.parseInt(sPants.substring(1));
        iPosS = Integer.parseInt(sShirt.substring(1));
        AddToArrays(context);
    }
    public LayerDrawable getImage()
    {
        Drawable layer1 = Gender.get(iPosG);
        Drawable layer2 = Pants.get(iPosP);
        Drawable layer3 = Shirts.get(iPosS);
        Drawable layer4 = Feet.get(iPosF);
        Drawable layer5 = Hair.get(iPosH);
        Drawable[] layers = {layer1, layer2, layer3, layer4, layer5};
        imgToPush = new LayerDrawable(layers);
        return imgToPush;
    }
    public void NextHair()
    {
        if(iPosH == 23)
            iPosH = 0;
        else
            iPosH++;
    }
    public void NextShirt()
    {
        if(iPosS == 23)
            iPosS = 0;
        else
            iPosS++;
    }
    public void NextPants()
    {
        if(iPosP == 23)
            iPosP = 0;
        else
            iPosP++;
    }
    public void NextFeet()
    {
        if(iPosF == 23)
            iPosF = 0;
        else
            iPosF++;
    }
    public void PrevHair()
    {
        if(iPosH == 0)
            iPosH = 23;
        else
            iPosH--;
    }
    public void PrevShirt()
    {
        if(iPosS == 0)
            iPosS = 23;
        else
            iPosS--;
    }
    public void PrevPants()
    {
        if(iPosP == 0)
            iPosP = 23;
        else
            iPosP--;
    }
    public void PrevFeet()
    {
        if(iPosF == 0)
            iPosF = 23;
        else
            iPosF--;
    }
    private void AddToArrays(Context context)
    {
        Gender = new ArrayList<>();
        Hair = new ArrayList<>();
        Shirts = new ArrayList<>();
        Pants = new ArrayList<>();
        Feet = new ArrayList<>();
        genderResourceMap.put("g0", R.drawable.f);
        genderResourceMap.put("g1", R.drawable.m);
        for (int k = 0; k < 2; k++) {
            String sTempGender = "g" + k;
            Integer resourceId = genderResourceMap.get(sTempGender);
            Gender.add(AppCompatResources.getDrawable(context, resourceId));
        }
        FeetResourceMap.put("f0", R.drawable.f0);
        FeetResourceMap.put("f1", R.drawable.f1);
        FeetResourceMap.put("f2", R.drawable.f2);
        FeetResourceMap.put("f3", R.drawable.f3);
        FeetResourceMap.put("f4", R.drawable.f4);
        FeetResourceMap.put("f5", R.drawable.f5);
        FeetResourceMap.put("f6", R.drawable.f6);
        FeetResourceMap.put("f7", R.drawable.f7);
        FeetResourceMap.put("f8", R.drawable.f8);
        FeetResourceMap.put("f9", R.drawable.f9);
        FeetResourceMap.put("f10", R.drawable.f10);
        FeetResourceMap.put("f11", R.drawable.f11);
        FeetResourceMap.put("f12", R.drawable.f12);
        FeetResourceMap.put("f13", R.drawable.f13);
        FeetResourceMap.put("f14", R.drawable.f14);
        FeetResourceMap.put("f15", R.drawable.f15);
        FeetResourceMap.put("f16", R.drawable.f16);
        FeetResourceMap.put("f17", R.drawable.f17);
        FeetResourceMap.put("f18", R.drawable.f18);
        FeetResourceMap.put("f19", R.drawable.f19);
        FeetResourceMap.put("f20", R.drawable.f20);
        FeetResourceMap.put("f21", R.drawable.f21);
        FeetResourceMap.put("f22", R.drawable.f22);
        FeetResourceMap.put("f23", R.drawable.f23);
        for (int k = 0; k < 24; k++) {
            String sTempFeet = "f" + k;
            Integer resourceId = FeetResourceMap.get(sTempFeet);
            Feet.add(AppCompatResources.getDrawable(context, resourceId));
        }
        //HairResourceMap.put("h0",R.drawable.h0)
        for (int k = 0; k < 52; k++) {
            String sTempHair = "h" + k;
            Integer resourceId = HairResourceMap.get(sTempHair);
            //Hair.add(AppCompatResources.getDrawable(context, resourceId));
            Hair.add(null);
        }
        ShirtResourceMap.put("s0",R.drawable.s0);
        ShirtResourceMap.put("s1",R.drawable.s1);
        ShirtResourceMap.put("s2",R.drawable.s2);
        ShirtResourceMap.put("s3",R.drawable.s3);
        ShirtResourceMap.put("s4",R.drawable.s4);
        ShirtResourceMap.put("s5",R.drawable.s5);
        ShirtResourceMap.put("s6",R.drawable.s6);
        ShirtResourceMap.put("s7",R.drawable.s7);
        ShirtResourceMap.put("s8",R.drawable.s8);
        ShirtResourceMap.put("s9",R.drawable.s9);
        ShirtResourceMap.put("s10",R.drawable.s10);
        ShirtResourceMap.put("s11",R.drawable.s11);
        ShirtResourceMap.put("s12",R.drawable.s12);
        ShirtResourceMap.put("s13",R.drawable.s13);
        ShirtResourceMap.put("s14",R.drawable.s14);
        ShirtResourceMap.put("s15",R.drawable.s15);
        ShirtResourceMap.put("s16",R.drawable.s16);
        ShirtResourceMap.put("s17",R.drawable.s17);
        ShirtResourceMap.put("s18",R.drawable.s18);
        ShirtResourceMap.put("s19",R.drawable.s19);
        ShirtResourceMap.put("s20",R.drawable.s20);
        ShirtResourceMap.put("s21",R.drawable.s21);
        ShirtResourceMap.put("s22",R.drawable.s22);
        ShirtResourceMap.put("s23",R.drawable.s23);
        for (int k = 0; k < 24; k++) {
            String sTempShirt = "s" + k;
            Integer resourceId = ShirtResourceMap.get(sTempShirt);
            Shirts.add(AppCompatResources.getDrawable(context, resourceId));
        }
        PantsResourceMap.put("p0",R.drawable.p0);
        PantsResourceMap.put("p1",R.drawable.p1);
        PantsResourceMap.put("p2",R.drawable.p2);
        PantsResourceMap.put("p3",R.drawable.p3);
        PantsResourceMap.put("p4",R.drawable.p4);
        PantsResourceMap.put("p5",R.drawable.p5);
        PantsResourceMap.put("p6",R.drawable.p6);
        PantsResourceMap.put("p7",R.drawable.p7);
        PantsResourceMap.put("p8",R.drawable.p8);
        PantsResourceMap.put("p9",R.drawable.p9);
        PantsResourceMap.put("p10",R.drawable.p10);
        PantsResourceMap.put("p11",R.drawable.p11);
        PantsResourceMap.put("p12",R.drawable.p12);
        PantsResourceMap.put("p13",R.drawable.p13);
        PantsResourceMap.put("p14",R.drawable.p14);
        PantsResourceMap.put("p15",R.drawable.p15);
        PantsResourceMap.put("p16",R.drawable.p16);
        PantsResourceMap.put("p17",R.drawable.p17);
        PantsResourceMap.put("p18",R.drawable.p18);
        PantsResourceMap.put("p19",R.drawable.p19);
        PantsResourceMap.put("p20",R.drawable.p20);
        PantsResourceMap.put("p21",R.drawable.p21);
        PantsResourceMap.put("p22",R.drawable.p22);
        PantsResourceMap.put("p23",R.drawable.p23);
        for (int k = 0; k < 24; k++) {
            String sTempPants = "p" + k;
            Integer resourceId = PantsResourceMap.get(sTempPants);
            Pants.add(AppCompatResources.getDrawable(context, resourceId));
        }
    }
}
