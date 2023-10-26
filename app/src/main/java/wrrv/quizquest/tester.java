package wrrv.quizquest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class tester extends AppCompatActivity {

    TextView position;
    ImageView image;
    Button prev;
    Button next;

    int i = 0;
    ArrayList<Bitmap> subImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

        position = findViewById(R.id.textView);
        image = findViewById(R.id.imageView2);
        prev = findViewById(R.id.button);
        next = findViewById(R.id.button2);

        int drawableResourceId = R.drawable.testerimage;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableResourceId);

        subImages = splitImage(bitmap);

        position.setText(i);
        image.setImageBitmap(subImages.get(i));
    }

    private ArrayList<Bitmap> splitImage(Bitmap image){

        int columns = 13;
        int rows = 21;

        ArrayList<Bitmap> subImages = new ArrayList<>();
        int subImageWidth = image.getWidth() / columns;
        int subImageHeight = image.getHeight() / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int x = j * subImageWidth;
                int y = i * subImageHeight;
                subImages.add(Bitmap.createBitmap(image, x, y, subImageWidth, subImageHeight));
            }
        }

        return subImages;
    }

    public void prev(View view){
        if(i == 0){
            i = subImages.size();
        }else{
            i--;
        }

        position.setText(i);
        image.setImageBitmap(subImages.get(i));
    }

    public void next(View view){
        if(i == subImages.size()){
            i = 0;
        }else{
            i++;
        }

        position.setText(i);
        image.setImageBitmap(subImages.get(i));
    }
}