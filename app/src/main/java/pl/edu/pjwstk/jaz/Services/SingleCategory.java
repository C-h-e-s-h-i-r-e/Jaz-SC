package pl.edu.pjwstk.jaz.Services;

public class SingleCategory {
    public String name;
    public String branch;

    public SingleCategory(String name, String branch){
        this.name=name;
        this.branch=branch;
    }

    SingleCategory(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
