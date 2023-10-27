package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;
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

    TabLayout tabLayout;
    ViewPager2 viewpager2;
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

        viewpager2 = findViewById(R.id.viewPager2Inventory);
        leave = findViewById(R.id.leaveInventory);

    }

    public void leaveClick(View view){
        Intent intent = new Intent(this,Profile_screen.class);
        startActivity(intent);
    }
}