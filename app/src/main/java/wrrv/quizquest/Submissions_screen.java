package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Submissions_screen extends AppCompatActivity {

    String username;

    ImageButton info;
    Button submit;
    Button previous;
    ImageButton leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submissions_screen);

        info = findViewById(R.id.btnSubInfo);
        submit = findViewById(R.id.btnSubmitAQ);
        previous = findViewById(R.id.btnPrevSubs);
        leave = findViewById(R.id.btnLeaveSubs);

        //player = sql statement
        username = "marisha";

    }

    public void infoClick(View view){
        AlertDialog ad = new AlertDialog.Builder (this).create();
        ad.setTitle("Submissions Info");
        ad.setMessage("You can be part of our quizzes, all you have to do is submit a question! " +
                "Your submission will then be reviewed by one of our administrators," +
                "and if they like it -it will get added to our database of questions!");
        ad.setIcon(R.drawable.info); // icon stored in [[resources]]
        ad.setButton(AlertDialog.BUTTON_POSITIVE, "ok", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        ad.show();
    }

    public void submitClick(View view){
        //get submission for today
        Submission todaySub = null;

        todaySub = new Submission("Which planet has the most moons?","Saturn","Space","Jupiter","Neptune","Mars","marisha","pending");

        if(todaySub != null){
            Intent intent = new Intent(this,Submit_screen.class);
            intent.putExtra("userName",username);
            startActivity(intent);
        }
        else{
            AlertDialog ad = new AlertDialog.Builder (this).create();
            ad.setTitle("Submitted");
            ad.setMessage("You are only allowed 1 submission per day. Unfortunately you have already submitted yours! See you tomorrow!");
            ad.setButton(AlertDialog.BUTTON_POSITIVE, "ok", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });

            ad.show();
        }

    }

    public void previousClick(View view){
        Intent intent = new Intent(this,PrevSubs_screen.class);
        intent.putExtra("userName",username);
        startActivity(intent);
    }

    public void leaveClick(View view){
        Intent intent = new Intent(this,Profile_screen.class);
        intent.putExtra("userName",username);
        startActivity(intent);
    }
}