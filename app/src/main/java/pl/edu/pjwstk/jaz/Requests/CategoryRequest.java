package pl.edu.pjwstk.jaz.Requests;

import pl.edu.pjwstk.jaz.Entities.BranchEntity;

public class CategoryRequest {
    private String name;
    private BranchEntity branchEntity;

    public CategoryRequest(String name, BranchEntity branchEntity) {
        this.name = name;
        this.branchEntity = branchEntity;
    }

    public BranchEntity getBranchEntity() {
        return branchEntity;
    }

    public void setBranchEntity(BranchEntity branchEntity) {
        this.branchEntity = branchEntity;
    }

    public String getCategoryName() {
        return name;
    }

    public void setCategoryName(String name) {
        this.name = name;
    }

    public String getBranchName() {
        return branchEntity.getName();
    }

    public void setBranchName(String branchName) {
        this.branchEntity.setName(branchName);
    }
}