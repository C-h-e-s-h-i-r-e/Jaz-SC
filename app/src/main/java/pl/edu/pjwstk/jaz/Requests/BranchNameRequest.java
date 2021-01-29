package pl.edu.pjwstk.jaz.Requests;


public class BranchNameRequest {

    private final String name;
    private String newName;

    public BranchNameRequest(String name, String newName) {
        this.name = name;
        this.newName = newName;
    }

    public String getName() {
        return name;
    }

    public String getNewBranchName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}