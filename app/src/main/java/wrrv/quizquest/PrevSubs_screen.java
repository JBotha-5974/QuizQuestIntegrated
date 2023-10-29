package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class PrevSubs_screen extends AppCompatActivity {

    Player player;

    private List<Submission> submissions;
    private submissionAdapter adapter;
    ImageButton btnBack;
    ImageButton info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_subs_screen);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        try {
            player = Database.getPlayer(savedUsername,savedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        submissions = Database.getSubmissionList(player.getUserName());
        adapter = new submissionAdapter(submissions);
        RecyclerView listSubmissions = findViewById(R.id.RVlistSubmissions);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getApplicationContext());

        listSubmissions.setLayoutManager(layoutManager);
        listSubmissions.setAdapter(adapter);
        btnBack = findViewById(R.id.btnBackSubmissions);
    }

    public void backClick(View view){
        Intent intent = new Intent(this,Submissions_screen.class);
        startActivity(intent);
    }

    public void infoClick(View view){
        AlertDialog ad = new AlertDialog.Builder (this).create();
        ad.setTitle("What do the symbols mean?");
        ad.setMessage("The tick means it's been accepted! :) \n" +
                "The cross means it's been rejected. :( \n" +
                "The little clipboard means its pending - this means our administrators still have to review your submission");
        ad.setIcon(R.drawable.info); // icon stored in [[resources]]
        ad.setButton(AlertDialog.BUTTON_POSITIVE, "ok", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        ad.show();
    }
}