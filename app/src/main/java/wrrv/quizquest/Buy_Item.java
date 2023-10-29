package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

public class Buy_Item extends AppCompatActivity {

    Player player;
    Item item;

    TextView description;
    ImageView image;
    TextView price;
    TextView coins;
    TextView txtInfo;

    Button buy;
    ImageButton leave;
    Spinner colors;

    Set<String> options;
    String color;

    ArrayList<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_item);

        // get player
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        try {
            player = Database.getPlayer(savedUsername,savedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // get Item that was selected
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("Item");

        // set views
        description = findViewById(R.id.lblBuyDesc);
        image = findViewById(R.id.imgBuyItem);
        price = findViewById(R.id.lblBuyPrice);
        coins = findViewById(R.id.lblBuyCoins);
        txtInfo = findViewById(R.id.lblInfo);
        buy = findViewById(R.id.btnBuy);
        leave = findViewById(R.id.btnLeaveBuy);
        colors = findViewById(R.id.spinner);

        // get Items in use
        try{
            items = Database.inUseIDs(player.getUserName());

        }catch(Exception e){
            System.out.println("Error checking inventory: " + e.getMessage());
            e.printStackTrace();
        }

        // check if the item is already in the player's inventory
        checkInventory();

        // set view values
        description.setText(item.getItemName());
        image.setImageBitmap(item.getItemImage(this, color));
        price.setText(String.valueOf(item.getItemPrice()));
        coins.setText(String.valueOf(player.getPlayerCoins()));

        setSpinner();

        if(options.size() == 1){
            color = "default";
        }

        colors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                color = (String) parentView.getItemAtPosition(position);

                updateImage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                colors.setSelection(1);
            }
        });
    }

    public void buyItem(View view){

        //check if player has enough coins
        if(player.getPlayerCoins() < item.getItemPrice()){

            //display "can't afford this" alert dialog
            AlertDialog alertDialog = cantAfford();
            alertDialog.show();

        }else{
            //update player coins
            player.setPlayerCoins(player.getUserName(),player.getPlayerCoins() - item.getItemPrice());

            //check if there is already an item in that layer
            checkItemLayer();

            //add to player inventory
            try{
                Database.addToInventory(player.getUserName(),item.getItemID(),color,true);

            }catch(Exception e){
                System.out.println("Error adding to inventory: " + e.getMessage());
                e.printStackTrace();
            }

            //alert dialog with updated sprite
            AlertDialog alertDialog = changeItemImage();
            alertDialog.show();

        }

    }

    public void updateImage(){
        Bitmap newImage = item.getItemImage(this, color);
        image.setImageBitmap(item.getItemImage(this, color));
    }

    public void setSpinner(){

        options = item.getColors();
        if(options.size() == 1){
            colors.setVisibility(View.INVISIBLE);
        }

        ArrayList<String> colorsList = new ArrayList<>(options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colorsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colors.setAdapter(adapter);
        colors.setSelection(1);

    }

    public void checkInventory(){
        boolean inInventory = false;

        for(Item i : items){
            if(i.getItemID() == item.getItemID()){
                inInventory = true;
            }
        }

        if(inInventory){
            buy.setEnabled(false);
            int color = ContextCompat.getColor(this, R.color.grayed);
            buy.setBackgroundColor(color);
            txtInfo.setVisibility(View.VISIBLE);
        }else{
            txtInfo.setVisibility(View.GONE);
        }
    }

    public void checkItemLayer(){
        int layer = item.getLayer();
        Item current = null;

        for(Item i: items){
            if(i.getLayer() == layer){

                current = i;
                break;
            }
        }

        if(current != null){
            //change current to not be in use
            try{
                Database.itemNotInUse(player.getUserName(),item.getItemID());

            }catch(Exception e){
                System.out.println("Error adding to inventory: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public AlertDialog changeItemImage(){
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_alert, null);

        ImageView imageView = customView.findViewById(R.id.alertImageView);
        SpriteGenerator sg = new SpriteGenerator(this, player.getUserName());
        imageView.setImageBitmap(sg.generate());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Sprite updated!");
        alertDialogBuilder.setView(customView);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent(Buy_Item.this,Store_screen.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

    public AlertDialog cantAfford(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Insufficient coins");
        alertDialogBuilder.setMessage("Sorry, it looks like you don't have enough coins to buy this.");
        alertDialogBuilder.setIcon(R.drawable.coins);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();

        return alertDialog;
    }

    public void leaveClick(View view){
        Intent intent = new Intent(this,Store_screen.class);
        startActivity(intent);
    }
}