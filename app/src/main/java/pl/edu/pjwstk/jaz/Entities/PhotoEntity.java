package pl.edu.pjwstk.jaz.Entities;

import pl.edu.pjwstk.jaz.Entities.AuctionEntity;

import javax.persistence.*;

@Entity
@Table(name = "photo")
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String link;

    private Long place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private AuctionEntity auction;

    PhotoEntity() {
        super();
    }

    public PhotoEntity(AuctionEntity auction, String link, Long place) {
        this.auction = auction;
        this.link = link;
        this.place = place;
    }

    public PhotoEntity(String photo) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public AuctionEntity getAuction() {
        return auction;
    }

    public void setAuction(AuctionEntity auction) {
        this.auction = auction;
    }

    public Long getPlace() {
        return place;
    }

    public void setPlace(Long place) {
        this.place = place;
    }

}
