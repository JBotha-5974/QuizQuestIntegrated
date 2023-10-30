package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

public class CustomizeSprite extends AppCompatActivity {
    private ImageView imgHolder;
    private SpriteGenerator generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("username", "");
        generator = new SpriteGenerator(this,savedUsername);
        setContentView(R.layout.activity_customize_sprite);
        imgHolder = findViewById(R.id.imgSpriteHolder);
        imgHolder.setImageBitmap(generator.generate());
    }

    public void btnFeetBackClick(View view) {
        custom.PrevFeet();
        imgHolder.setBackground(custom.getImage());
    }

    public void btnHairBackClick(View view) {
        custom.PrevHair();
        imgHolder.setBackground(custom.getImage());
    }

    public void btnShirtBackClick(View view) {
        custom.PrevShirt();
        imgHolder.setBackground(custom.getImage());
    }

    public void btnPantsBackClick(View view) {
        custom.PrevPants();
        imgHolder.setBackground(custom.getImage());
    }

    public void btnHairForwardClick(View view) {
        custom.NextHair();
        imgHolder.setBackground(custom.getImage());
    }

    public void btnCustomizeCancelClick(View view) {
        Intent intent = new Intent(this, Profile_screen.class);
        startActivity(intent);
        finish();
    }

    public void btnCustomizeConfirmClick(View view) throws Exception {
        Database.updatePlayerSprite(player.getUserName(), custom.getCode());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void btnShirtForwardClick(View view) {
        custom.NextShirt();
        imgHolder.setBackground(custom.getImage());
    }

    public void btnPantsForwardClick(View view) {
        custom.NextPants();
        imgHolder.setBackground(custom.getImage());
    }

    public void btnFeetForwardClick(View view) {
        custom.NextFeet();
        imgHolder.setBackground(custom.getImage());
    }
}