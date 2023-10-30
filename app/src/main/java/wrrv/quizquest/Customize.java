package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class Customize extends AppCompatActivity {
    private ImageView imgHead, imgTorso, imgJacket, imgLower, imgShoes;
    private ArrayList arrItemsInUse;
    private ArrayList arrHead, arrTorso, arrJacket, arrLower, arrShoes;
    private int iPosHead, iPosTorso, iPosJacket, iPosLower, iPosShoes;
    private int iOGPosHead, iOGPosTorso, iOGPosJacket, iOGPosLower, iOGPosShoes;
    Item head,torso,jacket,lower,shoe;
    private Player player;
    private Bitmap finalImage;
    String savedUsername;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        try {
            player = Database.getPlayer(savedUsername,savedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgHead = findViewById(R.id.imgHead);
        imgTorso = findViewById(R.id.imgTorso);
        imgJacket = findViewById(R.id.imgJacket);
        imgLower = findViewById(R.id.imgLower);
        imgShoes = findViewById(R.id.imgShoes);
        arrHead = new ArrayList<>(Database.getItems(1));
        arrTorso = new ArrayList<>(Database.getItems(6));
        arrJacket = new ArrayList<>(Database.getItems(7));
        arrLower = new ArrayList<>(Database.getItems(5));
        arrShoes = new ArrayList<>(Database.getItems(4));
        SpriteGenerator sg = new SpriteGenerator(this, savedUsername);
        arrItemsInUse = sg.getItemsInUse();
        head = (Item) arrItemsInUse.get(1);
        torso = (Item) arrItemsInUse.get(6);
        jacket = (Item) arrItemsInUse.get(7);
        lower = (Item) arrItemsInUse.get(5);
        shoe = (Item) arrItemsInUse.get(4);
        iOGPosHead = getItemID(head);
        iOGPosTorso = getItemID(shoe);
        iOGPosJacket = getItemID(jacket);
        iOGPosLower = getItemID(lower);
        iOGPosShoes = getItemID(torso);
        iPosHead = iSearchForPos(getItemID(head),arrHead);
        iPosShoes = iSearchForPos(getItemID(shoe),arrHead);
        iPosJacket = iSearchForPos(getItemID(jacket),arrHead);
        iPosLower = iSearchForPos(getItemID(lower),arrHead);
        iPosTorso = iSearchForPos(getItemID(torso),arrHead);
        imgShoes.setImageBitmap((Bitmap) arrShoes.get(iPosShoes));
        imgLower.setImageBitmap((Bitmap) arrLower.get(iPosLower));
        imgJacket.setImageBitmap((Bitmap) arrJacket.get(iPosJacket));
        imgTorso.setImageBitmap((Bitmap) arrTorso.get(iPosTorso));
        imgHead.setImageBitmap((Bitmap) arrHead.get(iPosHead));
    }

    public void btnRegisterSpriteContinueClick(View view) {
        getFinalImage();
        Intent intent = new Intent(this, Profile_screen.class);
        intent.putExtra("img", finalImage);
        startActivity(intent);
        finish();
    }

    public void btnCancelCustomizeSpriteClick(View view) {
        Intent intent = new Intent(this, Profile_screen.class);
        startActivity(intent);
        finish();

    }

    public void btnPrevHeadClick(View view) {
        if(iPosHead == 0)
            iPosHead = arrHead.size()-1;
        else
            iPosHead--;
        imgHead.setImageBitmap((Bitmap) arrHead.get(iPosHead));
        changeInUse(iPosHead, (Inventory) arrHead.get(iPosHead));
    }

    public void btnNextHeadClick(View view)
    {
        if(iPosHead == arrHead.size()-1)
            iPosHead = 0;
        else
            iPosHead++;
        imgHead.setImageBitmap((Bitmap) arrHead.get(iPosHead));
        changeInUse(iPosHead, (Inventory) arrHead.get(iPosHead));
    }

    public void btnPrevTorsoClick(View view)
    {
        if(iPosTorso == 0)
            iPosTorso = arrTorso.size()-1;
        else
            iPosTorso--;
        imgTorso.setImageBitmap((Bitmap) arrTorso.get(iPosTorso));
        changeInUse(iPosTorso, (Inventory) arrTorso.get(iPosTorso));
    }

    public void btnNextTorsoClick(View view)
    {
        if(iPosTorso == arrTorso.size()-1)
            iPosTorso = 0;
        else
            iPosTorso++;
        imgTorso.setImageBitmap((Bitmap) arrTorso.get(iPosTorso));
        changeInUse(iPosTorso, (Inventory) arrTorso.get(iPosTorso));
    }

    public void btnPrevJacketClick(View view)
    {
        if(iPosJacket == 0)
            iPosJacket = arrTorso.size()-1;
        else
            iPosJacket--;
        imgJacket.setImageBitmap((Bitmap) arrJacket.get(iPosJacket));
        changeInUse(iPosJacket, (Inventory) arrJacket.get(iPosJacket));
    }

    public void btnNextJacketClick(View view)
    {
        if(iPosJacket == arrJacket.size()-1)
            iPosJacket = 0;
        else
            iPosJacket++;
        imgJacket.setImageBitmap((Bitmap) arrJacket.get(iPosJacket));
        changeInUse(iPosJacket, (Inventory) arrJacket.get(iPosJacket));
    }

    public void btnPrevLowerClick(View view)
    {
        if(iPosLower == 0)
            iPosLower = arrLower.size()-1;
        else
            iPosLower--;
        imgLower.setImageBitmap((Bitmap) arrLower.get(iPosLower));
        changeInUse(iPosLower, (Inventory) arrLower.get(iPosLower));
    }

    public void btnNextLowerClick(View view)
    {
        if(iPosLower == arrLower.size()-1)
            iPosLower = 0;
        else
            iPosLower++;
        imgLower.setImageBitmap((Bitmap) arrLower.get(iPosLower));
        changeInUse(iPosLower, (Inventory) arrLower.get(iPosLower));
    }

    public void btnPrevShoesClick(View view)
    {
        if(iPosShoes == 0)
            iPosShoes = arrShoes.size()-1;
        else
            iPosShoes--;
        imgShoes.setImageBitmap((Bitmap) arrShoes.get(iPosShoes));
        changeInUse(iPosShoes, (Inventory) arrShoes.get(iPosShoes));
    }

    public void btnNextShoesClick(View view)
    {
        if(iPosShoes == arrShoes.size()-1)
            iPosShoes = 0;
        else
            iPosShoes++;
        imgShoes.setImageBitmap((Bitmap) arrShoes.get(iPosShoes));
        changeInUse(iPosShoes, (Inventory) arrShoes.get(iPosShoes));
    }

    public void btnCustomizeFloatingHelpClick(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.sprite_desc);
        alertDialogBuilder.setMessage(R.string.dialog_title);
        alertDialogBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Close the app or perform an exit action
                finish(); // This will close the current activity
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private int getItemID(Item item)
    {
        return item.getItemID();
    }
    private int iSearchForPos(int iID, ArrayList arrIn)
    {
        int iTemp = 0;
        for(int k = 0; k<arrIn.size();k++)
        {
            Item temp = (Item) arrIn.get(k);
            if (getItemID(temp) == iID)
                iTemp = k;
        }
        return iTemp;
    }
    private void getFinalImage()
    {
        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add((Drawable) arrHead.get(iPosHead));
        drawables.add((Drawable) arrShoes.get(iPosHead));
        drawables.add((Drawable) arrLower.get(iPosHead));
        drawables.add((Drawable) arrTorso.get(iPosHead));
        drawables.add((Drawable) arrJacket.get(iPosHead));
        Drawable[] layers = new Drawable[drawables.size()];
        layers = drawables.toArray(layers);
        LayerDrawable layered =  new LayerDrawable(layers);
        int width = layered.getIntrinsicWidth();
        int height = layered.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layered.setBounds(0, 0, width, height);
        layered.draw(canvas);
        finalImage = bitmap;
    }
    private void changeInUse(int ID, Inventory curPolarity)
    {
        boolean bFlag = curPolarity.isItemInUse();
        Database.switchItemUsageValue(savedUsername,ID,bFlag);
    }
}