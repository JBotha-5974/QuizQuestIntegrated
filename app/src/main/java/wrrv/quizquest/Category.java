package wrrv.quizquest;

public class Category {
    private String categoryID;
    private String categoryDescription;

    public Category(String categoryID, String categoryDescription) {
        this.categoryID = categoryID;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
