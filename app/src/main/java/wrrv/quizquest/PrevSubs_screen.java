package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class PrevSubs_screen extends AppCompatActivity {

    String username;

    private List<Submission> submissions;
    private submissionAdapter adapter;
    ImageButton btnBack;
    ImageButton info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_subs_screen);

        submissions = getSubmissionList(username);
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
        intent.putExtra("userName",username);
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

    public List<Submission> getSubmissionList(String userName){

        //connect to db and retrieve submissions
        //have parameter for username to filter by
        Submission sub1 = new Submission("Which planet has the most moons?","Saturn","Space","Jupiter","Neptune","Mars","marisha","pending");
        Submission sub2 = new Submission("What character have both Robert Downey Jr. and Benedict Cumberbatch played?","Sherlock","Entertainment","Dr Strange","IronMan","Willy Wonka","josh","pending");
        Submission sub3 = new Submission("What Renaissance artist is buried in Rome's Pantheon?","Raphael","Art","Donatello","MichealAngelo","Leonardo","joshua","pending");
        Submission sub4 = new Submission("What is the largest animal?","Blue Whale","Animal","Elephant","Hippo","Giraffe","robin","pending");
        Submission sub5 = new Submission("How many valves does a heart have?","4","Biology","2","6","3","Steve","pending");
        Submission sub6 = new Submission("What is the largest country?", "Russia","Geography","USA","Greenland","South Africa","Bob","pending");
        Submission sub7 = new Submission("Whats the fastest car?","Porche","General","kjfhka","kjhsfdj","kjshfdk","Amy","pending");
        Submission sub8 = new Submission("What is the capital of Brazil", "Brasilia", "Geography","sjhfskdn","skjfdhkjsd","jkfdhkjsf","Dave","pending");
        sub1.setState("accepted");
        sub2.setState("rejected");
        sub5.setState("accepted");
        sub7.setState("rejected");
        List<Submission> subs = new ArrayList<Submission>();

        subs.add(sub1);
        subs.add(sub2);
        subs.add(sub3);
        subs.add(sub4);
        subs.add(sub5);
        subs.add(sub6);
        subs.add(sub7);
        subs.add(sub8);


        return subs;
    }
}