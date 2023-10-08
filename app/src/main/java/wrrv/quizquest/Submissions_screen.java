package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Submissions_screen extends AppCompatActivity {

    Player player;

    String username;

    ImageButton info;
    Button submit;
    Button previous;
    ImageButton leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submissions_screen);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        try {
            player = Database.getPlayer(savedUsername,savedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        info = findViewById(R.id.btnSubInfo);
        submit = findViewById(R.id.btnSubmitAQ);
        previous = findViewById(R.id.btnPrevSubs);
        leave = findViewById(R.id.btnLeaveSubs);

    }

    public void infoClick(View view){
        AlertDialog ad = new AlertDialog.Builder (this).create();
        ad.setTitle("Submissions Info");
        ad.setMessage("You can be part of our quizzes, all you have to do is submit a question! " +
                "Your submission will then be reviewed by one of our administrators." +
                " If they like it,it will get added to our database of questions!");
        ad.setIcon(R.drawable.info); //
        ad.setButton(AlertDialog.BUTTON_POSITIVE, "ok", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        ad.show();
    }

    public void submitClick(View view){
        int check = player.getSubmissions();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        int date = Integer.parseInt(currentDate.format(formatter));

        if(check != date){
            Intent intent = new Intent(this,Submit_screen.class);
            startActivity(intent);
        }
        else{
            AlertDialog ad = new AlertDialog.Builder (this).create();
            ad.setTitle("Submitted");
            ad.setMessage("You are only allowed 1 submission per day. Unfortunately you have already submitted yours! See you tomorrow!");
            ad.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });

            ad.show();
        }

    }

    public void previousClick(View view){
        Intent intent = new Intent(this,PrevSubs_screen.class);
        startActivity(intent);
    }

    public void leaveClick(View view){
        Intent intent = new Intent(this,Profile_screen.class);
        startActivity(intent);
    }
}