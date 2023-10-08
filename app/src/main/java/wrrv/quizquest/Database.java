package wrrv.quizquest;

import android.media.Image;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
                String playerSprite = resultSet.getString(3);
                int playerScore = resultSet.getInt(4);
                int playerCoins = resultSet.getInt(5);
                int playerLevel = resultSet.getInt(6);
                int playerHints = resultSet.getInt(7);
                int leaderboardID = resultSet.getInt(8);
                int gamesPlayed = resultSet.getInt(9);
                int submissions = resultSet.getInt(10);
                players.add(new Player(userName,password,playerSprite,playerScore,playerCoins,playerLevel,playerHints,leaderboardID,gamesPlayed,submissions));
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
                String playerSprite = resultSet.getString(3);
                int playerScore = resultSet.getInt(4);
                int playerCoins = resultSet.getInt(5);
                int playerLevel = resultSet.getInt(6);
                int playerHints = resultSet.getInt(7);
                int leaderboardID = resultSet.getInt(8);
                int gamesPlayed = resultSet.getInt(9);
                int submissions = resultSet.getInt(10);
                player = new Player(userName,password,playerSprite,playerScore,playerCoins,playerLevel,playerHints,leaderboardID,gamesPlayed,submissions);
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
            resultSet = statement.executeQuery("SELECT playerHints FROM player WHERE userName = '" + userName + "'");
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
    public static void updatePlayerSprite(String userName, String sCode) throws Exception {
        /*
        updates the player sprite String
         */
        if (establishConnection()) {
            statement.execute("UPDATE player SET playerSprite = '"+ sCode+ "' WHERE userName = '" + userName + "'");
        }
    }
    public static String getPlayerSprite(String userName) throws Exception {
        if (establishConnection()){
            resultSet = statement.executeQuery("SELECT playerSprite FROM player WHERE userName = '" + userName + "'");
            if (resultSet.next()){
                return resultSet.getString(1);
            }
        }
        return "m,h0,s0,p0,f0";
    }
    public static void CreateUser(Player player) throws Exception{
        String sName = player.getUserName();
        String sPassword = player.getUserPassword();
        String sSprite = player.getPlayerSprite();
        int iScore = player.getPlayerScore();
        int iCoins = player.getPlayerCoins();
        int iLevel = player.getPlayerLevel();
        int iHints = player.getPlayerHints();
        int iLeaderboard = player.getLeaderboardID();
        int iGamesPlayed = player.getGamesPlayed();
        int iQuestionsSubmitted = player.getSubmissions();
        try {
            if(establishConnection())
            {
                String sqlString = "INSERT INTO Player (userName, userPassword , playerSprite, playerScore, playerCoins, playerLevel, playerHints, leaderboardID, gamesPlayed, submissions) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
                preparedStatement.setString(1, sName);
                preparedStatement.setString(2,sPassword);
                preparedStatement.setString(3,sSprite);
                preparedStatement.setInt(4, iScore);
                preparedStatement.setInt(5, iCoins);
                preparedStatement.setInt(6, iLevel);
                preparedStatement.setInt(7, iHints);
                preparedStatement.setInt(8, iLeaderboard);
                preparedStatement.setInt(9, iGamesPlayed);
                preparedStatement.setInt(10, iQuestionsSubmitted);
                preparedStatement.execute();
            }
        }catch (Exception e){
            Log.i("database",e.getMessage());
        }

        disconnect();
    }

    public static boolean setState(String state, int submissionID){

        try {
            if(establishConnection())
            {
                String updateQuery = "UPDATE Submission SET state = ? WHERE submissionID = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

                preparedStatement.setString(1, state);
                preparedStatement.setInt(2, submissionID);

                int rowsUpdated = preparedStatement.executeUpdate();

                disconnect();

                if (rowsUpdated > 0) {
                    return true;
                } else {
                    return false;
                }

            }
        }catch (Exception e){
            Log.i("database",e.getMessage());
        }

        return false;
    }

    public static boolean insertSubmission(Submission s){
        boolean inserted = true;

        try {
            if(establishConnection())
            {
                String sqlString = "INSERT INTO Submission (question, answer , categoryID, incorrect1, incorrect2, incorrect3, userName, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
                preparedStatement.setString(1, s.getQuestion());
                preparedStatement.setString(2,s.getAnswer());
                preparedStatement.setString(3, s.getCategory());
                preparedStatement.setString(4, s.getIncorrect1());
                preparedStatement.setString(5, s.getIncorrect2());
                preparedStatement.setString(6, s.getIncorrect3());
                preparedStatement.setString(7, s.getUserName());
                preparedStatement.setString(8, s.getState());

                preparedStatement.execute();
                disconnect();
            }
        }catch (Exception e){
            inserted = false;
            Log.i("database",e.getMessage());
        }
        return inserted;
    }
    public static ArrayList<Submission> getSubmissionList(String userName) {
        ArrayList<Submission> subs = new ArrayList<>(); // Initialize the ArrayList

//        try {
//            if (establishConnection()) {
//                String query = "SELECT * FROM Submission WHERE userName = '" + userName + "'";
//                statement = connection.createStatement();
//
//                ResultSet resultSet = statement.executeQuery(query);
//
//
//                while (resultSet.next()) {
//                    int id = resultSet.getInt("id");
//                    String username = resultSet.getString("userName");
//                    String category = resultSet.getString("category");
//                    String questionText = resultSet.getString("questionText");
//                    String correctAnswer = resultSet.getString("correctAnswer");
//                    String incorrectAnswer1 = resultSet.getString("incorrectAnswer1");
//                    String incorrectAnswer2 = resultSet.getString("incorrectAnswer2");
//                    String incorrectAnswer3 = resultSet.getString("incorrectAnswer3");
//                    String state = resultSet.getString("state");
//
//                    Submission s = new Submission(questionText, correctAnswer, category, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, username, state);
//                    subs.add(s);
//
//                }
//
//                disconnect();
//            }
//        } catch (Exception e) {
//            Log.i("database", e.getMessage());
//        }

        if (userName != null && !userName.isEmpty()) {

        } else {
            return null;
        }

        String query = "SELECT * FROM Submission WHERE userName = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("userName");
                    String category = resultSet.getString("category");
                    String questionText = resultSet.getString("questionText");
                    String correctAnswer = resultSet.getString("correctAnswer");
                    String incorrectAnswer1 = resultSet.getString("incorrectAnswer1");
                    String incorrectAnswer2 = resultSet.getString("incorrectAnswer2");
                    String incorrectAnswer3 = resultSet.getString("incorrectAnswer3");
                    String state = resultSet.getString("state");

                    Submission s = new Submission(questionText, correctAnswer, category, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, username, state);
                    subs.add(s);

                }

            }
        } catch (Exception e) {
            Log.i("database", e.getMessage());
        }

        return subs;
    }

    public static ArrayList<Submission> getPending() {
        ArrayList<Submission> subs = new ArrayList<>(); // Initialize the ArrayList

//        try {
//            if (establishConnection()) {
//                String query = "SELECT * FROM Submission WHERE state = 'pending'";
//
//                statement = connection.createStatement();
//
//                ResultSet resultSet = statement.executeQuery(query);
//
//
//                while (resultSet.next()) {
//                    int id = resultSet.getInt("id");
//                    String username = resultSet.getString("userName");
//                    String category = resultSet.getString("category");
//                    String questionText = resultSet.getString("questionText");
//                    String correctAnswer = resultSet.getString("correctAnswer");
//                    String incorrectAnswer1 = resultSet.getString("incorrectAnswer1");
//                    String incorrectAnswer2 = resultSet.getString("incorrectAnswer2");
//                    String incorrectAnswer3 = resultSet.getString("incorrectAnswer3");
//                    String state = resultSet.getString("state");
//
//                    Submission s = new Submission(questionText, correctAnswer, category, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, username, state);
//                    subs.add(s);
//
//                }
//
//                disconnect();
//            }
//        } catch (Exception e) {
//            Log.i("database", e.getMessage());
//        }

        String query = "SELECT * FROM Submission WHERE state = 'pending'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("userName");
                    String category = resultSet.getString("category");
                    String questionText = resultSet.getString("questionText");
                    String correctAnswer = resultSet.getString("correctAnswer");
                    String incorrectAnswer1 = resultSet.getString("incorrectAnswer1");
                    String incorrectAnswer2 = resultSet.getString("incorrectAnswer2");
                    String incorrectAnswer3 = resultSet.getString("incorrectAnswer3");
                    String state = resultSet.getString("state");

                    Submission s = new Submission(questionText, correctAnswer, category, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, username, state);
                    subs.add(s);

                }

            }
        } catch (Exception e) {
            Log.i("database", e.getMessage());
        }

        return subs;
    }

    public static boolean moveSubmission(Submission s){
        boolean moved = true;

        try {
            if(establishConnection())
            {
                String sqlString = "INSERT INTO Question (categoryID, question, answer , incorrect1, incorrect2, incorrect3) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
                preparedStatement.setString(1, s.getCategory());
                preparedStatement.setString(2, s.getQuestion());
                preparedStatement.setString(3,s.getAnswer());
                preparedStatement.setString(4, s.getIncorrect1());
                preparedStatement.setString(5, s.getIncorrect2());
                preparedStatement.setString(6, s.getIncorrect3());

                preparedStatement.execute();
                disconnect();
            }
        }catch (Exception e){
            moved = false;
            Log.i("database",e.getMessage());
        }

        return moved;
    }
    private static boolean establishConnection() throws Exception{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //connection = DriverManager.getConnection("jdbc:mysql://10.0.0.106:3306/quizquest", "josh", "josh");
            connection = DriverManager.getConnection("jdbc:mysql://192.168.3.3:3306/quizquest", "marisha", "marisha");
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
