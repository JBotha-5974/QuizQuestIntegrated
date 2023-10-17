package wrrv.quizquest;

import android.media.Image;

import java.io.Serializable;

public class Player implements Serializable {
    private String userName;
    private String userPassword;
    private String playerSprite;
    private int playerScore;
    private int playerCoins;
    private int playerLevel;
    private int playerHints;
    private int leaderboardID;
    private int gamesPlayed;
    private int submissions;

    public Player(String userName, String userPassword, String playerSprite, int playerScore, int playerCoins, int playerLevel, int playerHints, int leaderboardID, int gamesPlayed, int submissions) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.playerSprite = playerSprite;
        this.playerScore = playerScore;
        this.playerCoins = playerCoins;
        this.playerLevel = playerLevel;
        this.playerHints = playerHints;
        this.leaderboardID = leaderboardID;
        this.gamesPlayed = gamesPlayed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(String playerSprite) {
        this.playerSprite = playerSprite;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getPlayerCoins() {
        return playerCoins;
    }

    public void setPlayerCoins(String username,int playerCoins) {
        this.playerCoins = playerCoins;

        //update in db
        try {
            Database.updateCoins(username, playerCoins);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
    }

    public int getPlayerHints() {
        return playerHints;
    }

    public void setPlayerHints(int playerHints) {
        this.playerHints = playerHints;
    }

    public int getLeaderboardID() {
        return leaderboardID;
    }

    public void setLeaderboardID(int leaderboardID) {
        this.leaderboardID = leaderboardID;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getSubmissions() {
        return submissions;
    }

    public void setSubmissions(String username, int subDate){
        this.submissions = subDate;

        //update in db
        try {
            Database.updateSubmission(username, subDate);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
