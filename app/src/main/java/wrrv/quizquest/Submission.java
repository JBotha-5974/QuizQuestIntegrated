package wrrv.quizquest;
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
    private Date date;
    //user limited to 1 submission per day

    private String state;
    //state will be pending, accepted or rejected

    public Submission(String question, String answer, String categoryID, String incorrect1, String incorrect2, String incorrect3, String userName) {
        this.question = question;
        this.answer = answer;
        this.categoryID = categoryID;
        this.incorrect1 = incorrect1;
        this.incorrect2 = incorrect2;
        this.incorrect3 = incorrect3;
        this.userName = userName;

        //date = today's date
        state ="pending";
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

    public Date getDate() {
        return date;
    }

    public void setState(String state) {

        // will have to change in db
        this.state = state;
    }

    public String getState() {
        return state;
    }

    //endregion

    public boolean insertSubmission(Submission s){
        boolean inserted = true;

        //try catch: if inserts- true else catches error false;
//        try{
//            inserted = true;
//
//        }catch(Error e){
//             inserted = false;
//        }

        return inserted;
    }

    public boolean moveToQuestions(Submission s){
        // Submission remains in submission list, so can be retrieved for previous submissions.
        // Gets copied into questions list

        //how is questionID determined?

        //convert submission into question
        Question q = new Question(24,s.categoryID, s.question,s.answer,s.incorrect1,s.incorrect2,s.incorrect3);

        boolean copied = true;
//        //try catch: if inserts- true else catches error false;
//        try{
//
//        }catch(Error e){
//            copied = false;
//        }

        return copied;
    }

}