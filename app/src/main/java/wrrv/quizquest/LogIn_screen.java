package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn_screen extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private Button logIn;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        userName = findViewById(R.id.editTextUserName);
        password = findViewById(R.id.editTextPassword);
        logIn = findViewById(R.id.logInBtn);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void logIn(View view) throws Exception {
        String enteredUsername = userName.getText().toString();
        String enteredPassword = password.getText().toString();
        Administrator admin = Database.getAdmin(userName.getText().toString(),password.getText().toString());
        if (admin != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", enteredUsername);
            editor.putString("password", enteredPassword);
            editor.apply();
            Intent intent = new Intent(this, Admin_screen.class);
            startActivity(intent);
            finish();
        }else{
            Player player = Database.getPlayer(userName.getText().toString(),password.getText().toString());
            if (player != null){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", enteredUsername);
                editor.putString("password", enteredPassword);
                editor.apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this,"No user with these details found!",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this,RegisterUser.class);
        startActivity(intent);
        finish();
    }
}