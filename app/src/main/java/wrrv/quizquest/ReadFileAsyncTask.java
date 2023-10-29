package wrrv.quizquest;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFileAsyncTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private TextView textView;
    private int resourceId;

    public ReadFileAsyncTask(Context context, TextView textView, int resourceId) {
        this.context = context;
        this.textView = textView;
        this.resourceId = resourceId;
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder content = new StringBuilder();
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return content.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        textView.setText(result);
    }
}

