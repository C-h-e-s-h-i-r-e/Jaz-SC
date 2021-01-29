package pl.edu.pjwstk.jaz.Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "parameter")
public class ParameterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "parameter_name")
    private String name;


    @OneToMany(mappedBy = "parameterEntity")
    Set<Auction_ParameterEntity> auction_ParameterEntities;


    public ParameterEntity() {

    }

    public Set<Auction_ParameterEntity> getAuction_ParameterEntities() {
        return auction_ParameterEntities;
    }

    public void setAuction_ParameterEntities(Set<Auction_ParameterEntity> auction_ParameterEntities) {
        this.auction_ParameterEntities = auction_ParameterEntities;
    }

    public ParameterEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameter_name() {
        return name;
    }

    public void setParameter_name(String name) {
        this.name = name;
    }

}
