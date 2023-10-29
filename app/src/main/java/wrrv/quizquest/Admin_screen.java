package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin_screen extends AppCompatActivity {

    Button submissions;
    Button store;
    Button logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        submissions = findViewById(R.id.btnViewSubmissions);
        store = findViewById(R.id.btnMaintainStore);
        logOut = findViewById(R.id.btnAdminLogOut);

    }
    public void submissionsClick(View view){
        Intent intent = new Intent(this,AssessSubs_screen.class);
        startActivity(intent);
    }

    public void storeClick(View view){
        Intent intent = new Intent(this,Admin_Store.class);
        startActivity(intent);
    }

    public void logOutClick(View view){
        Intent intent = new Intent(this,LogIn_screen.class);
        startActivity(intent);
        finish();
    }
}