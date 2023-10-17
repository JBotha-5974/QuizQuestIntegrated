package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Submit_screen extends AppCompatActivity {

    Player player;

    EditText question;
    EditText answer;
    Spinner category;
    EditText incorrect1;
    EditText incorrect2;
    EditText incorrect3;

    Button submit;
    ImageButton exit;

    Spinner spinnerCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_screen);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        try {
            player = Database.getPlayer(savedUsername,savedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        spinnerCategories =findViewById(R.id.spnCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCategories.setAdapter(adapter);

        question = findViewById(R.id.txtQuestion);
        answer = findViewById(R.id.txtAnswer);
        incorrect1 = findViewById(R.id.txtIncorrect1);
        incorrect2 = findViewById(R.id.txtIncorrect2);
        incorrect3 = findViewById(R.id.txtIncorrect3);

        submit = findViewById(R.id.btnSubmit);
        exit = findViewById(R.id.btnLeaveSubmit);

    }

    public void submitClick(View view){
        Submission s = getSubmission();

        if(s != null){
            boolean inserted = Database.insertSubmission(s);

            if(inserted){

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                int date = Integer.parseInt(currentDate.format(formatter));

                player.setSubmissions(player.getUserName(), date);

                Toast.makeText(getApplicationContext(),"Your question has been submitted!",Toast.LENGTH_SHORT).show();

            }
            else{
                AlertDialog ad = new AlertDialog.Builder (this).create();
                ad.setTitle("Error");
                ad.setMessage("Your submission has not been submitted due to an error. Please try again later.");
                ad.setButton(AlertDialog.BUTTON_POSITIVE, "ok", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });

                ad.show();
            }

            Intent intent = new Intent(this,Submissions_screen.class);
            startActivity(intent);
        }
        else{
            AlertDialog ad = new AlertDialog.Builder (this).create();
            ad.setTitle("Invalid");
            ad.setMessage("Please fill in all fields correctly");
            ad.setButton(AlertDialog.BUTTON_POSITIVE, "ok", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });

            ad.show();
        }

    }

    private Submission getSubmission() {

        String q = question.getText().toString().trim();
        String a = answer.getText().toString().trim();
        String c = (String) category.getSelectedItem();
        String i1 = incorrect1.getText().toString().trim();
        String i2 = incorrect2.getText().toString().trim();
        String i3 = incorrect3.getText().toString().trim();

        if(!q.isEmpty() && !a.isEmpty() && !c.isEmpty() && !i1.isEmpty() && !i2.isEmpty() && !i3.isEmpty()){
            Submission s = new Submission(q,a,c,i1,i2,i3, player.getUserName(),"pending");
            return s;
        }
        else return null;

    }

    public void exitClick(View view){
        Intent intent = new Intent(this,Submissions_screen.class);
        startActivity(intent);
    }

    public void test(){

    }

}