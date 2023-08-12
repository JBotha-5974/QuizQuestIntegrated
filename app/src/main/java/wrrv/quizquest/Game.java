package wrrv.quizquest;

public class Game {
    private int gameID;
    private String userName;
    private String gameDate;

    public Game(int gameID, String userName, String gameDate) {
        this.gameID = gameID;
        this.userName = userName;
        this.gameDate = gameDate;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }
}
