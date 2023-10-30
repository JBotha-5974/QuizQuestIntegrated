package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ViewSpriteAfterCustomize extends AppCompatActivity {
    private Bitmap imgPreview;
    private ImageView imgHolder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sprite_after_customize);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            imgPreview = (Bitmap) extras.getParcelable("img");
        }
        imgHolder = findViewById(R.id.imgPreviewSpritePostCustomize);
        imgHolder.setImageBitmap(imgPreview);
    }

    public void btnBackToCustomizeClick(View view) {
        Intent intent = new Intent(this, Customize.class);
        startActivity(intent);
        finish();
    }

    public void btnConfirmCustomizeClick(View view) {
        Intent intent = new Intent(this, Profile_screen.class);
        startActivity(intent);
        finish();
    }
}