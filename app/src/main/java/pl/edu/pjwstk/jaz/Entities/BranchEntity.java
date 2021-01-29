package pl.edu.pjwstk.jaz.Entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "branch")
public class BranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;


    @OneToMany(

            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private Set<CategoryEntity> categories = new HashSet<>();

    public BranchEntity(String name) {
        this.name = name;
    }

    public BranchEntity() {

    }


    @Override
    public String toString() {
        return name;
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

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

}
