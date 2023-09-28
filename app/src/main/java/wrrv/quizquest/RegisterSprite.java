package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

public class RegisterSprite extends AppCompatActivity {
    private ImageView imgHolder;
    private Player player;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sprite);
        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            player = (Player) extras.getSerializable("player");
        }
        imgHolder = findViewById(R.id.imgvSprite);
        player.setPlayerSprite("m,h0,s0,p0,f0");
    }

    public void btnRegisterSpriteContinueClick(View view) throws Exception {
        Database.CreateUser(player);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", player.getUserName());
        editor.putString("password", player.getUserPassword());
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void rgbRegisterSpriteFemaleClick(View view) {
        imgHolder.setImageResource(R.drawable.base_female);
        player.setPlayerSprite("f,h0,s0,p0,f0");
    }

    public void rgbRegisterSpriteMaleClick(View view) {
        imgHolder.setImageResource(R.drawable.base_male);
        player.setPlayerSprite("m,h0,s0,p0,f0");
    }
}