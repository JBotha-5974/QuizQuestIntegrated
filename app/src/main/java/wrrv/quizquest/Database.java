package wrrv.quizquest;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Database {
    private static ResultSet resultSet = null;
    private static Connection connection = null;
    private static Statement statement = null;
    private static ArrayList<Question> questions;
    private static ArrayList<Player> players;

    public static ArrayList<Player> getPlayers() {
        try {
            if (establishConnection()) {
                players = new ArrayList<>();
                resultSet = statement.executeQuery("SELECT * FROM player");
                while (resultSet.next()) {
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
                    players.add(new Player(userName, password, playerSprite, playerScore, playerCoins, playerLevel, playerHints, leaderboardID, gamesPlayed, submissions));
                }
                disconnect();
                return players;
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static Administrator getAdmin(String username, String password) {
        try {
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
        }
        catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static Player getPlayer(String username, String passWord) {
        try {

            if (establishConnection()) {
                Player player = null;
                resultSet = statement.executeQuery("SELECT * FROM player WHERE userName = '" + username + "' AND userPassword = '" + passWord + "'");
                while (resultSet.next()) {
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
                    player = new Player(userName, password, playerSprite, playerScore, playerCoins, playerLevel, playerHints, leaderboardID, gamesPlayed, submissions);
                }
                disconnect();
                return player;
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Question> LoadQuestions() {
        try {

            if (establishConnection()) {
                questions = new ArrayList<>();
                resultSet = statement.executeQuery("SELECT COUNT(*) FROM question");
                resultSet.next();
                int numQuestions = resultSet.getInt(1);
                Set<Integer> selectedIndices = new HashSet<>();
                Random random = new Random();
                while (selectedIndices.size() < 10) {
                    selectedIndices.add(random.nextInt(numQuestions) + 1);
                }
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
                    questions.add(new Question(id, category, questionText, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3));
                }
                disconnect();
                return questions;
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void updateHints(String userName, int numHints) {
        try {
            if (establishConnection()) {
                statement.execute("UPDATE player SET playerHints = playerHints + " + numHints + " WHERE userName = '" + userName + "'");
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }

    }

    public static int getHints(String userName) {
        try {
            if (establishConnection()) {
                resultSet = statement.executeQuery("SELECT playerHints FROM player WHERE userName = '" + userName + "'");
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public static void updateScoreAndCoins(String userName, int score, int coins) {
        try {
            if (establishConnection()) {
                statement.execute("UPDATE player SET playerScore = playerScore + " + score + ", playerCoins = playerCoins + " + coins + " WHERE userName = '" + userName + "'");
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateGamesPlayed(String userName) {
        //updates the games played per day
        try {
            if (establishConnection()) {
                statement.execute("UPDATE player SET gamesPlayed = gamesPlayed + 1 WHERE userName = '" + userName + "'");
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
    }

    public static int getGamesPlayed(String userName) {
        try {
            if (establishConnection()){
                resultSet = statement.executeQuery("SELECT gamesPlayed FROM player WHERE userName = '" + userName + "'");
                if (resultSet.next()){
                    return resultSet.getInt(1);
                }
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public static void updatePlayerSprite(String userName, String sCode) {
        //updates the player sprite String
        try {
            if (establishConnection()) {
                statement.execute("UPDATE player SET playerSprite = '"+ sCode+ "' WHERE userName = '" + userName + "'");
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getPlayerSprite(String userName) {
        try {
            if (establishConnection()){
                resultSet = statement.executeQuery("SELECT playerSprite FROM player WHERE userName = '" + userName + "'");
                if (resultSet.next()){
                    return resultSet.getString(1);
                }
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return "m,h0,s0,p0,f0";
    }
    public static void CreateUser(Player player) {
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
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        disconnect();
    }

    //region Marisha

    public static boolean inInventory(String userName, int itemID){
        try {
            if (establishConnection()) {
                resultSet = statement.executeQuery("SELECT * " +
                        "FROM Inventory " +
                        "WHERE userName = '" + userName + "' " +
                        "AND itemID = " + itemID + ";");

                }
                disconnect();
            if (!resultSet.next()) {
                // No rows were returned, the item is not in the inventory
                return true;
            } else {
                // Rows were returned, the item is in the inventory
                return false;
            }

        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

    public static void addToInventory(String userName, int itemID, String color, boolean inUse){

        String inUseString = "";
        if(inUse){
            inUseString = "true";
        }
        else{
            inUseString = "false";
        }
        try{
            String sqlString = "INSERT INTO Inventory (userName, itemID , color, itemInUse) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2,itemID);
            preparedStatement.setString(3,color);
            preparedStatement.setString(4, inUseString);

            preparedStatement.execute();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getGender(String username) {

        String gender = "";

        try {
            if (establishConnection()) {
                resultSet = statement.executeQuery("SELECT * " +
                        "FROM Inventory" +
                        "WHERE itemID IN (SELECT itemID FROM Item WHERE itemLayer = 1)" +
                        "AND userName = '" + username + "'" +
                        "AND itemInUse = 'true';");

                while (resultSet.next()) {
                    gender = resultSet.getString(3);

                }
                disconnect();
                return gender;
            }

        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Item> getItemsInUse(String userName) {
        ArrayList<Item> items;
        try {
            if (establishConnection()) {
                items = new ArrayList<>();
                resultSet = statement.executeQuery("SELECT Item.itemID, Item.itemName, Item.itemPrice, Item.itemGender, Item.itemLayer, Item.itemColors, Item.itemCurColor " +
                        "FROM Item " +
                        "JOIN Inventory ON Item.itemID = Inventory.itemID " +
                        "WHERE Inventory.userName = '" + userName +
                        "' AND Inventory.itemInUse = '1';");
                while (resultSet.next()) {
                    int itemID = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    int price = resultSet.getInt(3);
                    String gender = resultSet.getString(4);
                    int layer = resultSet.getInt(5);
                    String colors = resultSet.getString(6);

                    Item temp = new Item(itemID, name, price, gender, layer, colors);
                    items.add(temp);
                }
                disconnect();
                return items;
            }

        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void updateItemInventory(String userName, int itemID) {
        try {
            if (establishConnection()) {
                statement.execute("UPDATE Inventory " +
                        "SET itemInUse = 'false'" +
                        "WHERE itemID = " + itemID + " AND userName = '" + userName + "';");
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateCoins(String userName, int coins) {
        try {
            if (establishConnection()) {
                statement.execute("UPDATE player SET  playerCoins = " + coins + " WHERE userName = '" + userName + "'");
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateSubmission(String userName, int submission) {
        try {
            if (establishConnection()) {
                statement.execute("UPDATE player SET  submissions = " + submission + " WHERE userName = '" + userName + "'");
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateItem(int itemID, String desc, int price) {
        try {
            if (establishConnection()) {
                statement.execute("UPDATE item SET itemName = " + desc + ", itemPrice = " + price + " WHERE itemID = '" + itemID + "'");
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
    }

    public static ArrayList<Item> getItems(int layerWanted) {

        ArrayList<Item> items;
        try {
            if (establishConnection()) {
                items = new ArrayList<>();


                resultSet = statement.executeQuery("SELECT * " +
                        "FROM Item " +
                        "WHERE itemLayer = '" + layerWanted + "';");
                while (resultSet.next()) {
                    int itemID = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    int price = resultSet.getInt(3);
                    String g = resultSet.getString(4);
                    int layer = resultSet.getInt(5);
                    String colors = resultSet.getString(6);

                    Item temp = new Item(itemID, name, price, g, layer, colors);
                    items.add(temp);
                }
                disconnect();
                return items;
            }

        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return null;

        //for reference
        //0 body, 1 head, 2 eyes, 3 hair, 4 shoes
        //5 lower, 6 torso, 7 jacket, 8 accessories

    }

    public static boolean setState(String state, int submissionID) {

        try {
            if (establishConnection()) {
                statement.execute("UPDATE Submission SET itemName = " + state + ", WHERE submissionID = '" + submissionID + "'");
                return true;
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
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
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return inserted;
    }

    public static List<Submission> getSubmissionList(String username) {
        List<Submission> submissions = new ArrayList<>();

        if (establishConnection()) {
            try {
                String query = "SELECT * FROM Submission WHERE userName = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    // Retrieve submission data from the result set
                    int submissionID = resultSet.getInt("submissionID");
                    String categoryID = resultSet.getString("categoryID");
                    String questionText = resultSet.getString("questionText");
                    String correctAnswer = resultSet.getString("correctAnswer");
                    String incorrectAnswer1 = resultSet.getString("incorrectAnswer1");
                    String incorrectAnswer2 = resultSet.getString("incorrectAnswer2");
                    String incorrectAnswer3 = resultSet.getString("incorrectAnswer3");
                    String state = resultSet.getString("state");

                    // Create Submission objects and add them to the list
                    Submission submission = new Submission(questionText,correctAnswer,categoryID, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, username, state);
                    submissions.add(submission);
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception properly
            } finally {
                disconnect(); // Ensure proper disconnection after usage
            }
        } else {
            // Handle connection failure if needed
        }

        return submissions;
    }

    public static ArrayList<Submission> getPending() {
        ArrayList<Submission> submissions = new ArrayList<>(); // Initialize the ArrayList

        return submissions;
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
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }

        return moved;
    }
    //endregion

    //region Connection
    private static boolean establishConnection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://10.0.0.101:3306/quizquest", "josh", "josh");
            connection = DriverManager.getConnection("jdbc:mysql://192.168.3.3:3306/quizquest", "marisha", "marisha");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            return true;
        } catch (Exception e) {
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    private static void disconnect() {
        try {
            connection.close();
            connection = null;
            statement.close();
            statement = null;
            resultSet.close();
            resultSet = null;
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
    }

    //endregion
}
