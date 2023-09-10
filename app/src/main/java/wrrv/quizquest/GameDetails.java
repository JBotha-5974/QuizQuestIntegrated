package wrrv.quizquest;

public class GameDetails {
    private int gameID;
    private int questionID;
    private int gameScore;
    private long timeTaken; //this datatype can change, int cant be used to measure time

    public GameDetails(int gameID, int questionID, int gameScore, long timeTaken) {
        this.gameID = gameID;
        this.questionID = questionID;
        this.gameScore = gameScore;
        this.timeTaken = timeTaken;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }
}
