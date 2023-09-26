package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn_screen extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private Button logIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        userName = findViewById(R.id.editTextUserName);
        password = findViewById(R.id.editTextPassword);
        logIn = findViewById(R.id.logInBtn);
    }

    public void logIn(View view) throws Exception {
        Administrator admin = Database.getAdmin(userName.getText().toString(),password.getText().toString());
        if (admin != null){
            Intent intent = new Intent(this, Admin_screen.class);
            startActivity(intent);
            finish();
        }else{
            Player player = Database.getPlayer(userName.getText().toString(),password.getText().toString());
            if (player != null){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this,"No user with these details found!",Toast.LENGTH_LONG).show();
            }
        }
    }
}