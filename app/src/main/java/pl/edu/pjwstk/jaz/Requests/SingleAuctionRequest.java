package pl.edu.pjwstk.jaz.Requests;

public class SingleAuctionRequest {


    private String title;

    public SingleAuctionRequest(String title){
        this.title = title;
    }

    SingleAuctionRequest(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
