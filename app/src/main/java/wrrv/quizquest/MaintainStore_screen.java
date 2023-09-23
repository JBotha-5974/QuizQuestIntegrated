package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MaintainStore_screen extends AppCompatActivity {

    // with intent so can see if its update or delete
    String type;

    Button update;
    Button delete;
    ImageButton leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_store_screen);

        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        leave = findViewById(R.id.btnLeaveMaintenance);
    }

    public void updateClick(View view){
        Intent intent = new Intent(this,AdminStore_screen.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    public void deleteClick(View view){
        Intent intent = new Intent(this,AdminStore_screen.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    public void leaveClick(View view){
        Intent intent = new Intent(this,Admin_screen.class);
        startActivity(intent);
    }
}