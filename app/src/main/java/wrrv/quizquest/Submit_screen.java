package wrrv.quizquest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Submit_screen extends AppCompatActivity {
    String username;

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

        //player = sql statement
        username="marisha";
    }

    public void submitClick(View view){
        //Submission s = getSubmission();

        Submission s = new Submission("Which planet has the most moons?","Saturn","Space","Jupiter","Neptune","Mars","marisha","pending");

        if(s != null){
            boolean inserted = s.insertSubmission(s);

            if(inserted){
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
            intent.putExtra("userName",username);
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
        boolean valid = true;

        String q = question.getText().toString().trim();
        String a = answer.getText().toString().trim();
        String c = (String) category.getSelectedItem();
        String i1 = incorrect1.getText().toString().trim();
        String i2 = incorrect2.getText().toString().trim();
        String i3 = incorrect3.getText().toString().trim();
        Submission s = new Submission(q,a,c,i1,i2,i3, username,"pending");

//        String[] inputs = {q,a,c,i1,i2,i3};
//        for(String input : inputs){
//            if(input.isEmpty()){
//                valid = false;
//            }
//        }

        //if(valid){
        if(!q.isEmpty() && !a.isEmpty() && !c.isEmpty() && !i1.isEmpty() && !i2.isEmpty() && !i3.isEmpty()){
            return s;
        }
        else return null;

    }

    public void exitClick(View view){
        Intent intent = new Intent(this,Submissions_screen.class);
        intent.putExtra("userName",username);
        startActivity(intent);
    }
}