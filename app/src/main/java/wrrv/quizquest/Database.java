package wrrv.quizquest;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Database {
    private static ResultSet resultSet = null;
    private static Connection connection = null;
    private static Statement statement = null;
    private static ArrayList<Question> questions;
    private static ArrayList<Player> players;

    public static ArrayList<Player> getPlayers() throws Exception {
        if (establishConnection()){
            players = new ArrayList<>();
            resultSet = statement.executeQuery("SELECT * FROM player");
            while (resultSet.next()){
                String userName = resultSet.getString(1);
                String password = resultSet.getString(2);
                int playerScore = resultSet.getInt(3);
                int playerCoins = resultSet.getInt(4);
                int playerLevel = resultSet.getInt(5);
                int playerHints = resultSet.getInt(6);
                int leaderboardID = resultSet.getInt(7);
                int gamesPlayed = resultSet.getInt(8);
                players.add(new Player(userName,password,null,playerScore,playerCoins,playerLevel,playerHints,leaderboardID,gamesPlayed));
            }
            disconnect();
            return players;
        }
        return null;
    }
    public static Administrator getAdmin(String username, String password) throws Exception{
        if (establishConnection()) {
            Administrator admin = null;
            resultSet = statement.executeQuery("SELECT * FROM administrator WHERE userName = '" + username + "' AND userPassword = '" + password + "'");
            while (resultSet.next()) {
                String userName = resultSet.getString(1);
                String passWord = resultSet.getString(2);
                admin = new Administrator(userName, passWord);
            }
            disconnect();
            return admin;
        }
        return null;
    }

    public static Player getPlayer(String username, String passWord) throws Exception {
        if (establishConnection()){
            Player player = null;
            resultSet = statement.executeQuery("SELECT * FROM player WHERE userName = '" + username + "' AND userPassword = '" + passWord + "'");
            while (resultSet.next()){
                String userName = resultSet.getString(1);
                String password = resultSet.getString(2);
                int playerScore = resultSet.getInt(3);
                int playerCoins = resultSet.getInt(4);
                int playerLevel = resultSet.getInt(5);
                int playerHints = resultSet.getInt(6);
                int leaderboardID = resultSet.getInt(7);
                int gamesPlayed = resultSet.getInt(8);
                player = new Player(userName,password,null,playerScore,playerCoins,playerLevel,playerHints,leaderboardID,gamesPlayed);
            }
            disconnect();
            return player;
        }
        return null;
    }
    public static ArrayList<Question> LoadQuestions() throws Exception {
        if (establishConnection()){
            questions = new ArrayList<>();
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM question");
            resultSet.next();
            int numQuestions = resultSet.getInt(1);
            Set<Integer> selectedIndices = new HashSet<>();
            Random random = new Random();
            while (selectedIndices.size() < 10){selectedIndices.add(random.nextInt(numQuestions)+ 1);}
            String selectedIndicesStr = selectedIndices.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
            String query = "SELECT * FROM question WHERE questionID IN (" + selectedIndicesStr + ")";
            resultSet = statement.executeQuery(query);
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
    public static void updateHints(String userName, int numHints) throws Exception {
        if (establishConnection()) {
            statement.execute("UPDATE player SET playerHints = playerHints + " + numHints + " WHERE userName = '" + userName + "'");
        }
    }
    public static int getHints(String userName) throws Exception{
        if (establishConnection()){
            resultSet = statement.executeQuery("SELECT playerhints FROM player WHERE userName = '" + userName + "'");
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        }
        return 0;
    }
    public static void updateScoreAndCoins(String userName, int score, int coins) throws Exception {
        if (establishConnection()) {
            statement.execute("UPDATE player SET playerScore = playerScore + " + score + ", playerCoins = playerCoins + " + coins + " WHERE userName = '" + userName + "'");
        }
    }
    public static void updateGamesPlayed(String userName) throws Exception {
        /*
        updates the games played per day
         */
        if (establishConnection()) {
            statement.execute("UPDATE player SET gamesPlayed = gamesPlayed + 1 WHERE userName = '" + userName + "'");
        }
    }
    public static int getGamesPlayed(String userName) throws Exception {
        if (establishConnection()){
            resultSet = statement.executeQuery("SELECT gamesPlayed FROM player WHERE userName = '" + userName + "'");
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        }
        return 0;
    }
    private static boolean establishConnection() throws Exception{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://10.0.0.105:3306/quizquest", "josh", "josh");
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
}
