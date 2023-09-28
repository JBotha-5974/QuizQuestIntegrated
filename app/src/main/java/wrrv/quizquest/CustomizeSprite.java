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
    private String sCode;

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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", player.getUserName());
        editor.putString("password", player.getUserPassword());
        editor.putString("code", player.getPlayerSprite());
        editor.apply();
        sCode = player.getPlayerSprite();
        custom = new GenerateSprite(this, sCode);
        imgHolder = findViewById(R.id.imgSpriteHolder);
    }

    public void btnFeetBackClick(View view) {
        custom.PrevFeet();
    }

    public void btnHairBackClick(View view) {
        custom.PrevHair();
    }

    public void btnShirtBackClick(View view) {
        custom.PrevShirt();
    }

    public void btnPantsBackClick(View view) {
        custom.PrevPants();
    }

    public void btnHairForwardClick(View view) {
        custom.NextHair();
    }

    public void btnCustomizeCancelClick(View view) {

    }

    public void btnCustomizeConfirmClick(View view) throws Exception {
        Database.updatePlayerSprite(player.getUserName(), custom.getCode());
    }

    public void btnShirtForwardClick(View view) {
        custom.NextShirt();
    }

    public void btnPantsForwardClick(View view) {
        custom.NextPants();
    }

    public void btnFeetForwardClick(View view) {
        custom.NextFeet();
    }
}