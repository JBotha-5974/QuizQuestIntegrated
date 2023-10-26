package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class View_Item extends AppCompatActivity {

    Player player;
    Item item;

    TextView description;
    ImageView image;
    TextView price;
    TextView coins;

    Button buy;
    ImageButton leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        try {
            player = Database.getPlayer(savedUsername,savedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        description = findViewById(R.id.lblBuyDesc);
        image = findViewById(R.id.imgBuyItem);
        price = findViewById(R.id.lblBuyPrice);
        coins = findViewById(R.id.lblBuyCoins);

        buy = findViewById(R.id.btnBuy);
        leave = findViewById(R.id.btnLeaveBuy);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("Item");

        // check if player already bought this item
        // ie in inventory
        // if they did - replace price with "already bought" or maybe just gray out buy button
        // another option is to set visibility

        description.setText(item.getItemName());
        image.setImageBitmap(item.getItemImage(this));
        price.setText(String.valueOf(item.getItemPrice()));
        coins.setText(String.valueOf(player.getPlayerCoins()));

    }

    public void buyItem(View view){
        //check if player has enough coins
        if(player.getPlayerCoins() < item.getItemPrice()){
            //cant afford this alert dialog

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
            alertDialog.show();

        }else{
            //update player coins
            player.setPlayerCoins(player.getUserName(),player.getPlayerCoins() - item.getItemPrice());

            //add to player inventory

            //alert dialog with updated sprite
            LayoutInflater inflater = getLayoutInflater();
            View customView = inflater.inflate(R.layout.custom_alert, null);

            ImageView imageView = customView.findViewById(R.id.alertImageView);
            // get player sprite
            imageView.setImageResource(R.drawable.base_female);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Sprite updated!");
            alertDialogBuilder.setView(customView);

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent intent = new Intent(View_Item.this,Store_screen.class);
                    startActivity(intent);

                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }


    }

    public void leaveClick(View view){
        Intent intent = new Intent(this,Store_screen.class);
        startActivity(intent);
    }
}