package pl.edu.pjwstk.jaz.Requests;

import java.util.List;

public class AuctionNameRequest {

    private String newCategory;
    private String newDescription;
    private String title;
    private String newTitle;
    private int newPrice;
    private List<String> newPhotos;
    private List<String> newParameters;
    private List<String> deletePhotos;
    private List<String> deleteParameters;


    public AuctionNameRequest(String newDescription, String newTitle, String title, int newPrice, String newCategory, List<String> newPhotos, List<String> newParameters) {
        this.newDescription = newDescription;
        this.newTitle = newTitle;
        this.title = title;
        this.newPrice = newPrice;
        this.newCategory = newCategory;
        this.newPhotos = newPhotos;
        this.newParameters = newParameters;
    }
    
    public AuctionNameRequest(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewCategory() {
        return newCategory;
    }

    public void setNewCategory(String newCategory) {
        this.newCategory = newCategory;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(int newPrice) {
        this.newPrice = newPrice;
    }

    public List<String> getNewPhotos() {
        return newPhotos;
    }

    public void setNewPhotos(List<String> newPhotos) {
        this.newPhotos = newPhotos;
    }

    public List<String> getNewParameters() {
        return newParameters;
    }

    public void setNewParameters(List<String> newParameters) {
        this.newParameters = newParameters;
    }
}