package wrrv.quizquest;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class DatabaseCode extends AppCompatActivity {
    //This code does not currently work, just posting it here for reference
    private TextView txtTemp;
    private Connection con = null;
    private ResultSet resultSet = null;
    private Statement stmt = null;
    private static final String ConnectionString = "jdbc:sqlserver://jmandj.database.windows.net:1433;database=QuizQuestDB;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    private static final String DeviceDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String username = "CloudSAee1d035f";
    private static final String password = "JM&J2023";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            public void run() {
                connectToDB();
            }
        }).start();
    }
    private void connectToDB(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(DeviceDriver);
            con = DriverManager.getConnection(ConnectionString, username, password);
            stmt = con.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM ADMINISTRATOR");
            ResultSetMetaData metadata = resultSet.getMetaData();
            txtTemp.setText("test");
            while (resultSet.next()) {
                txtTemp.setText(resultSet.getString("userName"));
            }
        }catch (Exception e){
            Log.e("database",""+e.getMessage());
            e.printStackTrace();
        }
        finally{
            disconnectDB();
        }
    }
    private void disconnectDB(){
        try{
            if (con != null){
                con.close();
                con = null;
                stmt.close();
                resultSet.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
