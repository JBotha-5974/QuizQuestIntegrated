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
import android.util.Log;
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

        Log.d("Buy item check", "The database got player");
        // get Item that was selected
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("Item");
        Log.d("Buy item check", "Item :" + item.getItemName());

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
            Log.d("Buy item check", "Database got items");
            for(Item i : items){
                Log.d("Buy item check", "Items name -> " + i.getItemName());
            }

        }catch(Exception e){
            System.out.println("Error checking inventory: " + e.getMessage());
            Log.d("Buy item check", "Error getting items from database");
            e.printStackTrace();
        }

        // check if the item is already in the player's inventory
        checkInventory();

        // set view values
        description.setText(item.getItemName());
        Log.d("Buy item check", "Buy Item color -> " + color);
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

        color = item.getColorsString();

        if(color.equals("default")){
            colors.setVisibility(View.GONE);
        }
        else{
            options = item.getColors();
            ArrayList<String> colorsList = new ArrayList<>(options);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, colorsList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            colors.setAdapter(adapter);
            colors.setSelection(1);
        }

    }
    public void checkInventory(){
        boolean inInventory = false;
        for(Item i : items){
            if(i.getItemID() == item.getItemID()){
                inInventory = true;
                Log.d("Buy item check", "item in inventory: " + i.getItemName());
            }

        }
        Log.d("Buy item check", "in Inventory -> " + inInventory);

        if(inInventory){
            Log.d("Buy item check", "Item in inventory if statement");
            buy.setEnabled(false);
            int color = ContextCompat.getColor(this, R.color.grayed);
            buy.setBackgroundColor(color);
            txtInfo.setVisibility(View.VISIBLE);
        }else{
            Log.d("Buy item check", "Not in iventory");
            txtInfo.setVisibility(View.GONE);
        }
    }

    public void checkItemLayer(){
        int layer = item.getLayer();
        Item current = null;
        Log.d("Buy item check", "Checking layers");
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
                Log.d("Buy item check", "changed item in use");

            }catch(Exception e){
                System.out.println("Error adding to inventory: " + e.getMessage());
                Log.d("Buy item check", "couldn't change item in use");
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