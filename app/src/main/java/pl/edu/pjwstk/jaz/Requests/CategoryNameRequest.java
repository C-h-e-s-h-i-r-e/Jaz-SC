package pl.edu.pjwstk.jaz.Requests;

public class CategoryNameRequest {

    private final String name;
    private String newName;

    public CategoryNameRequest(String name, String newName) {
        this.name = name;
        this.newName = newName;
    }

    public String getCategoryName() {
        return name;
    }

    public String getCategoryNewName() {
        return newName;
    }

    public void setCategoryNewName(String newName) {
        this.newName = newName;
    }
}
