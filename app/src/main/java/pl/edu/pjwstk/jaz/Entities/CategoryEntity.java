package pl.edu.pjwstk.jaz.Entities;

import pl.edu.pjwstk.jaz.Entities.BranchEntity;
import pl.edu.pjwstk.jaz.Entities.AuctionEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private BranchEntity branch;

    @OneToMany(

            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Set<AuctionEntity> auctions = new HashSet<>();

    public CategoryEntity(String name, BranchEntity branch) {
        this.name = name;
        this.branch = branch;
    }

    public CategoryEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BranchEntity getBranch() {
        return branch;
    }

    public void setBranch(BranchEntity branch) {
        this.branch = branch;
    }

    public Set<AuctionEntity> getAuctions() {
        return auctions;
    }

    public void setAuctions(Set<AuctionEntity> auctions) {
        this.auctions = auctions;
    }
}
