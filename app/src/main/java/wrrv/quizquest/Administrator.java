package wrrv.quizquest;

public class Administrator {
    private String userName;
    private String userPassword;
    public Administrator(String userName, String userPassword){
        this.userName = userName;
        this.userPassword = userPassword;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
