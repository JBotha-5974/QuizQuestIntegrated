package wrrv.quizquest;

import android.media.Image;
import android.os.StrictMode;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private static ResultSet resultSet = null;
    private static Connection connection = null;
    private static Statement statement = null;
    private static ArrayList<Question> questions;

    public static ArrayList<Question> LoadQuestions() throws Exception {
        if (establishConnection()){
            questions = new ArrayList<>();
            resultSet = statement.executeQuery("SELECT * FROM question");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String category = resultSet.getString(2);
                String questionText = resultSet.getString(3);
                String correctAnswer = resultSet.getString(4);
                String incorrectAnswer1 = resultSet.getString(5);
                String incorrectAnswer2 = resultSet.getString(6);
                String incorrectAnswer3 = resultSet.getString(7);
                questions.add( new Question(id,category,questionText,correctAnswer,incorrectAnswer1,incorrectAnswer2,incorrectAnswer3));
            }
            disconnect();
            return questions;
        }
        return null;
    }
    private static boolean establishConnection() throws Exception{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://192.168.0.107:3306:3306/android", "Joshua Botha", "Merc.5974");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private static void disconnect() throws Exception {
        try {
            connection.close();
            connection = null;
            statement.close();
            statement = null;
            resultSet.close();
            resultSet = null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void CreateUser(Player player) throws Exception{
        String sName = player.getUserName();
        String sPassword = player.getUserPassword();
        Image imgSprite = player.getPlayerSprite();
        int iScore = player.getPlayerScore();
        int iCoins = player.getPlayerCoins();
        int iLevel = player.getPlayerLevel();
        int iHints = player.getPlayerHints();
        int iLeaderboard = player.getLeaderboardID();
        if(establishConnection())
        {
            String sqlString = "INSERT INTO Player (userName, userPassword , playerSprite, playerScore, playerCoins, playerLevel, playerHints, leaderboardID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setString(1, sName);
            preparedStatement.setString(2,sPassword);
            preparedStatement.setBlob(3, (Blob) imgSprite);
            preparedStatement.setInt(4, iScore);
            preparedStatement.setInt(5, iCoins);
            preparedStatement.setInt(6, iLevel);
            preparedStatement.setInt(7, iHints);
            preparedStatement.setInt(8, iLeaderboard);
            preparedStatement.executeUpdate();
        }
    }
}
