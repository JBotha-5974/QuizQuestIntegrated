package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

public class Admin_Store extends AppCompatActivity {

    //Marisha

    TabLayout tabLayout;
    ViewPager2 viewpager2;
    StoreAdapter adapter;

    ImageButton leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_store);

        leave = findViewById(R.id.btnLeaveStoreAdmin);

        tabLayout = findViewById(R.id.tabLayoutAdmin);
        viewpager2 = findViewById(R.id.viewPager2Admin);

        adapter = new StoreAdapter(this);
        viewpager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

    }

    public void leaveClick(View view){
        Intent intent = new Intent(this,Admin_screen.class);
        startActivity(intent);
        finish();
    }
}