package wrrv.quizquest;

public class LeaderBoard {
    private int leaderboardID;
    private String leaderboardDate;
    private int playerRank;
    /*
    to comply with UML notation, I had to add a playerRank attribute. I don't know
    if we will actually use this in implementation though
    */
    public LeaderBoard(int leaderboardID, String leaderboardDate, int playerRank) {
        this.leaderboardID = leaderboardID;
        this.leaderboardDate = leaderboardDate;
        this.playerRank = playerRank;
    }

    public int getLeaderboardID() {
        return leaderboardID;
    }

    public void setLeaderboardID(int leaderboardID) {
        this.leaderboardID = leaderboardID;
    }

    public String getLeaderboardDate() {
        return leaderboardDate;
    }

    public void setLeaderboardDate(String leaderboardDate) {
        this.leaderboardDate = leaderboardDate;
    }

    public int getPlayerRank() {
        return playerRank;
    }

    public void setPlayerRank(int playerRank) {
        this.playerRank = playerRank;
    }
}
