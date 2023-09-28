package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RegisterSprite extends AppCompatActivity {
    private ImageView imgHolder;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sprite);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            player = (Player) extras.getSerializable("player");
        }
        imgHolder = findViewById(R.id.imgvSprite);
        //player.setPlayerSprite(getDrawable(R.drawable.base_male));
    }

    public void btnRegisterSpriteContinueClick(View view) throws Exception {
        Database.CreateUser(player);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void rgbRegisterSpriteFemaleClick(View view) {
        imgHolder.setImageResource(R.drawable.base_female);
        //player.setPlayerSprite(imgHolder.getDrawable());
    }

    public void rgbRegisterSpriteMaleClick(View view) {
        imgHolder.setImageResource(R.drawable.base_male);
        //player.setPlayerSprite(imgHolder.getDrawable());
    }
}