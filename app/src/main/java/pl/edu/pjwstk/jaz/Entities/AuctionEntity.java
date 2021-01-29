package pl.edu.pjwstk.jaz.Entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "auction")
public class AuctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    private final Set<PhotoEntity> photos = new HashSet<>();

    @OneToMany(mappedBy = "auctionEntity")
    Set<Auction_ParameterEntity> auction_ParameterEntities = new HashSet<>();

    public Set<Auction_ParameterEntity> getAuction_ParameterEntities() {
        return auction_ParameterEntities;
    }

    public void setAuction_ParameterEntities(Set<Auction_ParameterEntity> auctionParameterEntities) {
        this.auction_ParameterEntities = auctionParameterEntities;
    }

    public void addAuction_Parameter(Auction_ParameterEntity auctionParameterEntity) {
        auction_ParameterEntities.add(auctionParameterEntity);
    }

    public Set<PhotoEntity> getPhotos() {
        return photos;
    }

    public AuctionEntity() {
    }

    public AuctionEntity(String title, String description, int price, UserEntity user, CategoryEntity category) {
        super();
        this.title = title;
        this.description = description;
        this.price = price;
        this.user = user;
        this.category = category;

    }

    public AuctionEntity(String title, String description, UserEntity userEntity, int price) {
        this.title = title;
        this.description = description;
        this.user = userEntity;
        this.price = price;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

}

