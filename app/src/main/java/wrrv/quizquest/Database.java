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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Database {
    private static ResultSet resultSet = null;
    private static Connection connection = null;
    private static Statement statement = null;
    private static ArrayList<Question> questions;
    private static ArrayList<Player> players;
    public static void addGame(int gameID, String userName, String gameDate){
        try {
            if (establishConnection()){
                String sql = "INSERT INTO Game (gameID,userName,gameDate) VALUES (?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,gameID);
                preparedStatement.setString(2,userName);
                preparedStatement.setString(3,gameDate);
                preparedStatement.execute();
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
    }
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
    public static int getLeaderBoardID(){
        int leaderBoardID = 0;
        try {
            if(establishConnection()){
                resultSet = statement.executeQuery("SELECT COUNT(*) FROM Leaderboard");
                resultSet.next();
                leaderBoardID = resultSet.getInt(1);
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return leaderBoardID;
    }
    public static int getLatestGameID(){
        int gameID = 0;
        try {
            if (establishConnection()){
                resultSet = statement.executeQuery("SELECT COUNT(*) FROM Game");
                resultSet.next();
                gameID =  resultSet.getInt(1);
            }
        }
        catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return gameID;
    }
    public static void addGameDetails(int gameID, int questionID, int gameScore, long timeTaken){
        try {
            if (establishConnection()){
                String sql = "INSERT INTO GameDetails (gameID,questionID,gameScore,timeTaken) VALUES (?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,gameID);
                preparedStatement.setInt(2,questionID);
                preparedStatement.setInt(3,gameScore);
                preparedStatement.setInt(4,(int)timeTaken/1000);
                preparedStatement.execute();
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
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

    public static ArrayList<Item> inUseIDs(String userName){
        ArrayList<Item> items = new ArrayList<>();

        try {
            if (establishConnection()) {
                String query = "SELECT Item.* " +
                        "FROM Item " +
                        "JOIN Inventory ON Item.itemID = Inventory.itemID " +
                        "WHERE Inventory.userName = '" + userName + "' AND Inventory.itemInUse = 'true';";

                resultSet = statement.executeQuery(query);

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

            }
            disconnect();
            return items;
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

    public static Item getGender(String userName) {

        Item temp = null;
        try {
            if (establishConnection()) {
                resultSet = statement.executeQuery("SELECT Item.*\n" +
                        "FROM Item\n" +
                        "JOIN Inventory ON Item.itemID = Inventory.itemID\n" +
                        "WHERE Item.itemLayer = 0 AND Inventory.userName = '" + userName +
                        "' AND Inventory.itemInUse = 'true';");

                while (resultSet.next()) {
                    int itemID = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    int price = resultSet.getInt(3);
                    String g = resultSet.getString(4);
                    int layer = resultSet.getInt(5);
                    String colors = resultSet.getString(6);

                    temp = new Item(itemID, name, price, g, layer, colors);

                }
                disconnect();
                return temp;
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
    public static void updatePlayerLevel(String userName, int iLevel) {
        try {
            if (establishConnection()) {
                statement.execute("UPDATE Player SET  playerLevel = " + iLevel + " WHERE userName = '" + userName + "'");
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
    }

    public static ArrayList<Map<Item,String>> getItemsInUse(String userName) {
        ArrayList<Map<Item,String>> items;
        try {
            if (establishConnection()) {
                items = new ArrayList<>();
                resultSet = statement.executeQuery("SELECT Item.*, Inventory.color " +
                        "FROM Item " +
                        "JOIN Inventory ON Item.itemID = Inventory.itemID " +
                        "WHERE Inventory.userName = '" + userName + "' AND Inventory.itemInUse = 'true';");
                Log.i("info",resultSet.getFetchSize()+"");
                while (resultSet.next()) {
                    int itemID = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    int price = resultSet.getInt(3);
                    String gender = resultSet.getString(4);
                    int layer = resultSet.getInt(5);
                    String colors = resultSet.getString(6);
                    String curColor = resultSet.getString(7);

                    Item temp = new Item(itemID, name, price, gender, layer, colors);

                    Map<Item, String> map = new HashMap<>();
                    map.put(temp, curColor);

                    items.add(map);
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

    public static void itemNotInUse(String userName, int itemID) {
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
    public static void switchItemUsageValue(String userName, int iItemNum, boolean bCur) {
        try {
            if (establishConnection()) {
                if(bCur)
                    statement.execute("UPDATE inventory SET  itemInUse = false"+ " WHERE userName = '" + userName + "' AND itemID ='" + iItemNum +"'");
                else
                    statement.execute("UPDATE inventory SET  itemInUse = true"+ " WHERE userName = '" + userName + "' AND itemID ='" + iItemNum +"'");
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

    public static ArrayList<Item> getItemsGender(int layerWanted, String gender) {

        ArrayList<Item> items;

        String remove;

        if(gender.equals("m")) {
            remove = "f";
        }else{
            remove = "m";
        }

        try {
            if (establishConnection()) {
                items = new ArrayList<>();


                resultSet = statement.executeQuery("SELECT * " +
                        "FROM Item " +
                        "WHERE itemLayer = '" + layerWanted + "' " +
                        "AND itemGender NOT LIKE '%" + remove + "%';");
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
    private static int getSubmissionID(){
        int ID = 0;
        try {
            if (establishConnection()){
                resultSet = statement.executeQuery("SELECT COUNT(*) FROM Submission");
                resultSet.next();
                ID = resultSet.getInt(1);
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return ID;
    }
    public static boolean insertSubmission(Submission s){
        boolean inserted = true;
        try {
            if(establishConnection())
            {
                int id = getSubmissionID();
                id++;
                String sqlString = "INSERT INTO Submission (submissionID, userName, categoryID, questionText, correctAnswer,incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
                preparedStatement.setInt(1,id);
                preparedStatement.setString(2, s.getUserName());
                preparedStatement.setString(3,s.getCategory());
                preparedStatement.setString(4, s.getQuestion());
                preparedStatement.setString(5, s.getAnswer());
                preparedStatement.setString(6, s.getIncorrect1());
                preparedStatement.setString(7, s.getIncorrect2());
                preparedStatement.setString(8, s.getIncorrect3());
                preparedStatement.setString(9, s.getState());
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
                String query = "SELECT * FROM Submission WHERE userName = '" + username +"'";
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    // Retrieve submission data from the result set
                    int submissionID = resultSet.getInt(1);
                    String resultUserName = resultSet.getString(2);
                    String categoryID = resultSet.getString(3);
                    String questionText = resultSet.getString(4);
                    String correctAnswer = resultSet.getString(5);
                    String incorrectAnswer1 = resultSet.getString(6);
                    String incorrectAnswer2 = resultSet.getString(7);
                    String incorrectAnswer3 = resultSet.getString(8);
                    String state = resultSet.getString(9);

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

        ArrayList<Submission> submissions;
        String query = "SELECT * FROM Submission WHERE state = 'pending';";

        try {
            if (establishConnection()) {

                submissions = new ArrayList<>();
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int submissionID = resultSet.getInt(1);
                    String resultUserName = resultSet.getString(2);
                    String categoryID = resultSet.getString(3);
                    String questionText = resultSet.getString(4);
                    String correctAnswer = resultSet.getString(5);
                    String incorrectAnswer1 = resultSet.getString(6);
                    String incorrectAnswer2 = resultSet.getString(7);
                    String incorrectAnswer3 = resultSet.getString(8);
                    String state = resultSet.getString(9);

                    // Create Submission objects and add them to the list
                    Submission submission = new Submission(questionText,correctAnswer,categoryID, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, resultUserName, state);
                    submissions.add(submission);
                }
                disconnect();
                return submissions;
            }
        }catch (Exception e){
            Log.e("DATABASE",e.getMessage());
            e.printStackTrace();
        }
        return null;
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
