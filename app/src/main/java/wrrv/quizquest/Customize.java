package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
    private ArrayList arrAllItems;
    private ArrayList arrHead, arrTorso, arrJacket, arrLower, arrShoes;
    private Player player;
    private Bitmap finalImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
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
        arrAllItems = new ArrayList<>(Database.getItemsInUse(savedUsername));
        arrHead = new ArrayList<>(Database.getItems(1));
        arrTorso = new ArrayList<>(Database.getItems(6));
        arrJacket = new ArrayList<>(Database.getItems(7));
        arrLower = new ArrayList<>(Database.getItems(5));
        arrShoes = new ArrayList<>(Database.getItems(4));
        Set<String> options;
        Spinner colors;

        ArrayList<String> colorsList = new ArrayList<>(options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, colorsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colors.setAdapter(adapter);
        colors.setSelection(1);

        SpriteGenerator sg = new SpriteGenerator(this, savedUsername);
        ArrayList usedItems = sg.getItemsInUse();
    }

    public void btnRegisterSpriteContinueClick(View view) {
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
        imgHead.setImageBitmap((Bitmap) arrHead.get(0));
    }

    public void btnNextHeadClick(View view)
    {
        imgHead.setImageBitmap((Bitmap) arrHead.get(0));
    }

    public void btnPrevTorsoClick(View view)
    {
        imgTorso.setImageBitmap((Bitmap) arrTorso.get(0));
    }

    public void btnNextTorsoClick(View view)
    {
        imgTorso.setImageBitmap((Bitmap) arrTorso.get(0));
    }

    public void btnPrevJacketClick(View view) {
        imgJacket.setImageBitmap((Bitmap) arrJacket.get(0));
    }

    public void btnNextJacketClick(View view) {
        imgJacket.setImageBitmap((Bitmap) arrJacket.get(0));
    }

    public void btnPrevLowerClick(View view)
    {
        imgLower.setImageBitmap((Bitmap) arrLower.get(0));
    }

    public void btnNextLowerClick(View view)
    {
        imgLower.setImageBitmap((Bitmap) arrLower.get(0));
    }

    public void btnPrevShoesClick(View view)
    {
        imgShoes.setImageBitmap((Bitmap) arrShoes.get(0));
    }

    public void btnNextShoesClick(View view)
    {
        imgShoes.setImageBitmap((Bitmap) arrShoes.get(0));
    }

    public void btnCustomizeFloatingHelpClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.sprite_desc)
                .setTitle(R.string.dialog_title);
        AlertDialog dialog = builder.create();
    }
    public int getItemID(Item item)
    {
        return item.getItemID();
    }
}