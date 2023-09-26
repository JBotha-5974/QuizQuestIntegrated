package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AssessSubs_screen extends AppCompatActivity {

    List<Submission> subs;

    ImageButton previous;
    ImageButton next;
    TextView index;
    Button accept;
    Button reject;
    ImageButton leave;

    TextView question;
    TextView answer;
    TextView category;
    TextView incorrect1;
    TextView incorrect2;
    TextView incorrect3;

    int cur = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_subs_screen);

        previous = findViewById(R.id.btnPrev);
        next = findViewById(R.id.btnNext);
        index = findViewById(R.id.txtIndex);
        accept = findViewById(R.id.btnAccept);
        reject = findViewById(R.id.btnReject);
        leave = findViewById(R.id.btnLeaveAssess);

        question = findViewById(R.id.txtQuestionView);
        answer = findViewById(R.id.txtAnswerView);
        category =findViewById(R.id.txtCategoryView);
        incorrect1 = findViewById(R.id.txtI1View);
        incorrect2 = findViewById(R.id.txtI2View);
        incorrect3 = findViewById(R.id.txtI3View);

        subs = getSubmissionList();

        updateIndex();
        displaySub(subs.get(cur));

    }

    public void prevClick(View view){
        if(cur == 0){
            cur = subs.size()-1;
        }
        else{
            cur--;
        }
        updateIndex();
        displaySub(subs.get(cur));
    }

    public void nextClick(View view){
        if(cur == subs.size()-1){
            cur = 0;
        }
        else{
            cur++;
        }
        updateIndex();
        displaySub(subs.get(cur));
    }

    public void acceptClick(View view){
        Submission s = subs.get(cur);
        s.setState("accept");
        s.moveToQuestions(s);
        getSubmissionList();
        updateIndex();
        displaySub(subs.get(cur));
    }

    public void rejectClick(View view){
        Submission s = subs.get(cur);
        s.setState("reject");
        getSubmissionList();
        updateIndex();
        displaySub(subs.get(cur));
    }

    public void backClick(View view){
        Intent intent = new Intent(this,Admin_screen.class);
        startActivity(intent);
    }

    public void displaySub(Submission s){
        question.setText(s.getQuestion());
        answer.setText(s.getAnswer());
        category.setText(s.getCategory());
        incorrect1.setText(s.getIncorrect1());
        incorrect2.setText(s.getIncorrect2());
        incorrect3.setText(s.getIncorrect3());
    }

    public void updateIndex(){

        int total = subs.size();
        int temp = cur + 1;
        String text = temp + " of " + total;

        index.setText(text);
    }

    public List<Submission> getSubmissionList(){

        //connect to db and retrieve submissions
        //filter by if state = pending
        Submission sub1 = new Submission("Which planet has the most moons?","Saturn","Space","Jupiter","Neptune","Mars","marisha","pending");
        Submission sub2 = new Submission("What character have both Robert Downey Jr. and Benedict Cumberbatch played?","Sherlock","Entertainment","Dr Strange","IronMan","Willy Wonka","josh","pending");
        Submission sub3 = new Submission("What Renaissance artist is buried in Rome's Pantheon?","Raphael","Art","Donatello","MichealAngelo","Leonardo","joshua","pending");
        Submission sub4 = new Submission("What is the largest animal?","Blue Whale","Animal","Elephant","Hippo","Giraffe","robin","pending");
        Submission sub5 = new Submission("How many valves does a heart have?","4","Biology","2","6","3","Steve","pending");
        Submission sub6 = new Submission("What is the largest country?", "Russia","Geography","USA","Greenland","South Africa","Bob","pending");
        Submission sub7 = new Submission("Whats the fastest car?","Porche","General","kjfhka","kjhsfdj","kjshfdk","Amy","pending");
        Submission sub8 = new Submission("What is the capital of Brazil", "Brasilia", "Geography","sjhfskdn","skjfdhkjsd","jkfdhkjsf","Dave","pending");

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