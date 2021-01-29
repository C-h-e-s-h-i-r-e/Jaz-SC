package pl.edu.pjwstk.jaz.Services;

public class SingleAuction {
    public String title;
    public String description;
    public int price;
    public String photo;



    public String category;

    SingleAuction(String title, String description, int price, String photo, String category){
        this.title =title;
        this.description = description;
        this.price = price;
        this.photo = photo;
        this.category=category;
    }

    SingleAuction(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
