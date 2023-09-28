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

        updateList();

    }

    public void prevClick(View view){
        if(cur == 0){
            cur = subs.size()-1;
        }
        else{
            cur--;
        }
        updateDisplay();
    }

    public void nextClick(View view){
        if(cur == subs.size()-1){
            cur = 0;
        }
        else{
            cur++;
        }
        updateDisplay();
    }

    public void acceptClick(View view){
        Submission s = subs.get(cur);
        s.setState("accept");
        s.moveToQuestions(s);
        updateList();
    }

    public void rejectClick(View view){
        Submission s = subs.get(cur);
        s.setState("reject");
        updateList();
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

    public void updateList(){

        subs = Database.getPending();

        int total = subs.size();
        int temp = cur + 1;
        String text = temp + " of " + total;

        index.setText(text);

        displaySub(subs.get(cur));

    }

    public void updateDisplay(){

        int total = subs.size();
        int temp = cur + 1;
        String text = temp + " of " + total;

        index.setText(text);

        displaySub(subs.get(cur));

    }

}