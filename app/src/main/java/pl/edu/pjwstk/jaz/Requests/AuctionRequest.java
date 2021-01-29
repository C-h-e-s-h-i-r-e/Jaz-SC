package pl.edu.pjwstk.jaz.Requests;

import java.util.List;

public class AuctionRequest {

    private String category;
    private String description;
    private String title;
    private int price;
    private List<String> photos;
    private List<String> parameters;


    public AuctionRequest(String description, String title, int price, String category, List<String> photos, List<String> parameters) {
        this.description = description;
        this.title = title;
        this.price = price;
        this.category = category;
        this.photos = photos;
        this.parameters = parameters;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}
