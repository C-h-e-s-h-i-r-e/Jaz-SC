package pl.edu.pjwstk.jaz.Requests;

import java.util.List;

public class BranchRequest {

    private String name;
    private String category;


    public BranchRequest(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public BranchRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

}