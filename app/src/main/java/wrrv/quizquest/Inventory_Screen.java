package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

public class Inventory_Screen extends AppCompatActivity {

    Player player;

    RecyclerView rvInventory;
    StoreAdapter adapter;

    ImageButton leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_screen);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        try {
            player = Database.getPlayer(savedUsername,savedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rvInventory = findViewById(R.id.rvInventoryScreen);
        leave = findViewById(R.id.leaveInventory);

        rvInventory.setLayoutManager(
                new GridLayoutManager(this,2));


    }

    public void leaveClick(View view){
        Intent intent = new Intent(this,Profile_screen.class);
        startActivity(intent);
    }
}