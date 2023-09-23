package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogIn_screen extends AppCompatActivity {

    Button admin;
    Button player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);

        admin = findViewById(R.id.btnAdmin);
        player = findViewById(R.id.btnPlayer);
    }

    public void adminClick(View view){
        Intent intent = new Intent(this,Admin_screen.class);
        startActivity(intent);
    }

    public void playerClick(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}