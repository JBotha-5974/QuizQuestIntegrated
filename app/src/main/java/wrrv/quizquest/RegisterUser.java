package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterUser extends AppCompatActivity {
    private TextView tbxName;
    private TextView tbxPassword;
    private TextView tbxConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        tbxName = findViewById(R.id.tbxRegisterUserName);
        tbxPassword = findViewById(R.id.tbxCreateUserPassword);
        tbxConfirmPassword = findViewById(R.id.tbxUserConfirmPassword);
    }

    public void btnRegisterUserContinueClick(View view) {
        String sName =  tbxName.getText().toString();
        String sPassword = tbxPassword.getText().toString();
        String sPasswordConfirm = tbxConfirmPassword.getText().toString();
        if(sPassword.equals(sPasswordConfirm) && !sPassword.isEmpty() && !sName.isEmpty())
        {
            Player newPlayer = new Player(sName, sPassword, null, 0, 0, 0, 0, 0, 0);
            Intent intent = new Intent(this, RegisterSprite.class);
            intent.putExtra("player", newPlayer);
            startActivity(intent);
        }
        else
        {
            tbxConfirmPassword.setText("");
            CharSequence cMessage = "Please ensure that all fields complete and passwords match.";
            int iLength = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, cMessage, iLength);
            toast.show();
        }
    }

    public void btnLoginClick(View view) {
        Intent intent = new Intent(this, LogIn_screen.class);
        startActivity(intent);
    }
}