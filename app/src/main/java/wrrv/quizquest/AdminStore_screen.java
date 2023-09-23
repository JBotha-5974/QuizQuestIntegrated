package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

public class AdminStore_screen extends AppCompatActivity {

    String type;

    RecyclerView store;
    ImageButton leave;

    List<Item> upper;
    List<Item> lower;
    List<Item> accessories;

    adminStoreAdapter uAdapter;
    adminStoreAdapter lAdapter;
    adminStoreAdapter aAdapter;

    RadioGroup storeGroup;
    RadioButton rbUpper;
    RadioButton rbLower;
    RadioButton rbAccessories;

    // need to add onclicklistener to an item
    // intent with type and item should determine which screen should show
    // update screen or delete screen

    //figure out the images for store

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_store_screen);

        store = findViewById(R.id.rvAdminStore);
        leave = findViewById(R.id.btnLeaveAdminStore);
        storeGroup = findViewById(R.id.storeGroups);
        rbUpper = findViewById(R.id.rbUpper);
        rbLower = findViewById(R.id.rbLower);
        rbAccessories = findViewById(R.id.rbAccessores);


        uAdapter = new adminStoreAdapter(upper);
        lAdapter = new adminStoreAdapter(lower);
        aAdapter = new adminStoreAdapter(accessories);

        RecyclerView.LayoutManager layoutManager;
        layoutManager = new GridLayoutManager(getApplicationContext(),2);

        store.setLayoutManager(layoutManager);
        store.setAdapter(uAdapter);

        storeGroup.clearCheck();

        storeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId){
                    case R.id.rbUpper:
                        store.setAdapter(uAdapter);
                        break;
                    case R.id.rbLower:
                        store.setAdapter(lAdapter);
                        break;
                    case R.id.rbAccessores:
                        store.setAdapter(aAdapter);
                        break;

                }

            }
        });

    }

    public void backClick(View view){
        Intent intent = new Intent(this,AdminStore_screen.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }


}