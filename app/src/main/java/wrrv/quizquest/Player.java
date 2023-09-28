package wrrv.quizquest;

import android.media.Image;

import java.io.Serializable;

public class Player implements Serializable {
    private String userName;
    private String userPassword;
    private Image playerSprite;
    private int playerScore;
    private int playerCoins;
    private int playerLevel;
    private int playerHints;
    private int leaderboardID;
    private int gamesPlayed;

    public Player(String userName, String userPassword, Image playerSprite, int playerScore, int playerCoins, int playerLevel, int playerHints, int leaderboardID, int gamesPlayed) {
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

    public Image getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Image playerSprite) {
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

    public void setPlayerCoins(int playerCoins) {
        this.playerCoins = playerCoins;
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
}
