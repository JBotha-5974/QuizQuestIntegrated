package wrrv.quizquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView txtCoinsNum;
    private TextView txtHintsNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Player Josh = new Player("Josh","Josh123",null,10,100,12,5,1);
        txtCoinsNum = findViewById(R.id.txtCoinsNum);
        txtCoinsNum.setText(""+Josh.getPlayerCoins());
        txtHintsNum = findViewById(R.id.txtHintsNum);
        txtHintsNum.setText(""+Josh.getPlayerHints());
    }
    public void startGame(View view) {
        Intent intent = new Intent(this,gameScreen.class);
//        intent.putExtra("player",new Player());
        startActivity(intent);
    }
}
