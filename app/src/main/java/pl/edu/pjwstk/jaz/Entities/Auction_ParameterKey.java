package pl.edu.pjwstk.jaz.Entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Auction_ParameterKey implements Serializable {

    Long auctionId;

    Long parameterId;

    public Auction_ParameterKey(Long auctionId, Long parameterId) {
        this.auctionId = auctionId;
        this.parameterId = parameterId;
    }

    public Auction_ParameterKey() {
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Long getParameterId() {
        return parameterId;
    }

    public void setParameterId(Long parameterId) {
        this.parameterId = parameterId;
    }
}
