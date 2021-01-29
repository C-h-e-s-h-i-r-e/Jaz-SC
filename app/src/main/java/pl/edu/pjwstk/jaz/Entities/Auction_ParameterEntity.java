package pl.edu.pjwstk.jaz.Entities;

import javax.persistence.*;

@Entity
@Table(name = "auction_parameter")
public class Auction_ParameterEntity {

    @EmbeddedId
    Auction_ParameterKey auction_parameter_id;

    @ManyToOne
    @MapsId("auctionId")
    @JoinColumn(name = "auction_id")
    AuctionEntity auctionEntity;

    @ManyToOne
    @MapsId("parameterId")
    @JoinColumn(name = "parameter_id")
    ParameterEntity parameterEntity;

    private String para_value;

    public Auction_ParameterEntity(Auction_ParameterKey auction_parameter_id, String para_value) {
        this.auction_parameter_id = auction_parameter_id;
        this.para_value = para_value;
    }

    public Auction_ParameterEntity() {
    }

    public Auction_ParameterKey getAuction_parameter_id() {
        return auction_parameter_id;
    }

    public void setAuction_parameter_id(Auction_ParameterKey auction_parameter_id) {
        this.auction_parameter_id = auction_parameter_id;
    }

    public AuctionEntity getAuctionEntity() {
        return auctionEntity;
    }

    public void setAuctionEntity(AuctionEntity auctionEntity) {
        this.auctionEntity = auctionEntity;
    }

    public ParameterEntity getParameterEntity() {
        return parameterEntity;
    }

    public void setParameterEntity(ParameterEntity parameterEntity) {
        this.parameterEntity = parameterEntity;
    }
    public String getPara_value() {
        return para_value;
    }

    public void setPara_value(String para_value) {
        this.para_value = para_value;
    }
}
