package wrrv.quizquest;
import android.provider.ContactsContract;

import java.sql.Date;

public class Submission {
    private int submissionID;
    private String question;
    private String answer;
    private String categoryID;
    private String incorrect1;
    private String incorrect2;
    private String incorrect3;

    private String userName;

    //user limited to 1 submission per day

    private String state;
    //state will be pending, accepted or rejected

    public Submission(String question, String answer, String categoryID, String incorrect1, String incorrect2, String incorrect3, String userName, String state) {
        this.question = question;
        this.answer = answer;
        this.categoryID = categoryID;
        this.incorrect1 = incorrect1;
        this.incorrect2 = incorrect2;
        this.incorrect3 = incorrect3;
        this.userName = userName;
        this.state = state;
    }

    //region Getters & Setters

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCategory() {
        return categoryID;
    }

    public String getIncorrect1() {
        return incorrect1;
    }

    public String getIncorrect2() {
        return incorrect2;
    }

    public String getIncorrect3() {
        return incorrect3;
    }

    public String getUserName() {
        return userName;
    }


    public void setState(String state) {
        this.state = state;

        try {
            Database.setState(state, submissionID);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getState() {
        return state;
    }

    //endregion



    public boolean moveToQuestions(Submission s){
        // Submission remains in submission list, so can be retrieved for previous submissions.
        // Gets copied into questions list

        boolean moved = Database.moveSubmission(s);


        return moved;
    }

}