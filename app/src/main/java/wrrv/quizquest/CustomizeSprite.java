package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

public class CustomizeSprite extends AppCompatActivity {
    private ImageView imgHolder;
    private GenerateSprite custom;
    private SharedPreferences sharedPreferences;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_sprite);
        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            player = (Player) extras.getSerializable("player");
        }
        imgHolder = findViewById(R.id.imgSpriteHolder);
    }

    public void btnFeetBackClick(View view) {
    }

    public void btnHairBackClick(View view) {
    }

    public void btnShirtBackClick(View view) {
    }

    public void btnPantsBackClick(View view) {
    }

    public void btnHairForwardClick(View view) {
    }

    public void btnCustomizeCancelClick(View view) {
    }

    public void btnCustomizeConfirmClick(View view) {
    }

    public void btnShirtForwardClick(View view) {
    }

    public void btnPantsForwardClick(View view) {
    }

    public void btnFeetForwardClick(View view) {
    }
}