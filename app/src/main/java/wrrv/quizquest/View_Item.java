package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class View_Item extends AppCompatActivity {

    Player player;
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item2);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        try {
            player = Database.getPlayer(savedUsername,savedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("Item");


    }
}