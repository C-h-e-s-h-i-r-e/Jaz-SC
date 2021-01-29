package pl.edu.pjwstk.jaz.Services;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.jaz.Entities.*;
import pl.edu.pjwstk.jaz.Repositories.AuctionRepository;
import pl.edu.pjwstk.jaz.Repositories.BranchRepository;
import pl.edu.pjwstk.jaz.Repositories.CategoryRepository;
import pl.edu.pjwstk.jaz.Repositories.ParameterRepository;
import pl.edu.pjwstk.jaz.UnauthorizedException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CategoryService {


    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;
    private final AuctionRepository auctionRepository;
    private final ParameterRepository parameterRepository;
    private final EntityManager entityManager;

    public CategoryService(BranchRepository branchRepository, CategoryRepository categoryRepository, AuctionRepository auctionRepository, ParameterRepository parameterRepository, EntityManager entityManager) {
        this.branchRepository = branchRepository;
        this.categoryRepository = categoryRepository;
        this.auctionRepository = auctionRepository;
        this.parameterRepository = parameterRepository;
        this.entityManager = entityManager;
    }

    public CategoryEntity findCategoryBYName(String name) {
        if(categoryRepository.findByName(name).isEmpty()){
            throw new UnauthorizedException();
        } else {
            return entityManager.createQuery("select ce from CategoryEntity ce where ce.name = :name", CategoryEntity.class)
                    .setParameter("name", name)//name
                    .getSingleResult();
        }
    }

    public void addCategory(String branchName, String category) {
        BranchEntity branchEntity = branchRepository.findByName(branchName).orElseGet(BranchEntity::new);
        if (branchEntity.getName() == null) {
            System.out.println("No branch name like that");
            throw new UnauthorizedException();
        } else {
            CategoryEntity categoryEntity = categoryRepository.findByName(category).orElseGet(CategoryEntity::new);
            if(categoryEntity.getBranch() !=null && categoryEntity.getName().equals(category)){
                System.out.println("Category with this name already exists.");
                throw new UnauthorizedException();
            } else {

                System.out.println("Added Category : " + category);
                categoryEntity = new CategoryEntity(category, branchEntity);
                System.out.println(categoryEntity);
                branchEntity.getCategories().add(categoryEntity);
            }
        }
    }

    public void editCategory(String categoryName, String newCategoryName) {
        CategoryEntity categoryEntity = categoryRepository.findByName(categoryName).orElseGet(CategoryEntity::new);
        if (categoryEntity.getName() == null) {
            System.out.println("No category like that");
            throw new UnauthorizedException();
        } else {
            System.out.println("Changed Category Name from : " + categoryName + " to " + newCategoryName);
            categoryEntity.setName(newCategoryName);
            this.categoryRepository.save(categoryEntity);
        }
    }


    public List<List<String>> showCategories() {
        List<CategoryEntity> all_categories = categoryRepository.findAll();

        List<SingleCategory> category_list = new ArrayList<>();
        for (CategoryEntity c : all_categories) {
            SingleCategory singleCategory = new SingleCategory(c.getName(), c.getBranch().getName());
            category_list.add(singleCategory);
        }

        List<List<String>> cat = new ArrayList<>();
        for (SingleCategory c : category_list) {
            List<String> categories = new ArrayList<>();
            System.out.println("Category name : " + c.name + " Branch name : " + c.branch);
            categories.add(c.getName());
            categories.add(c.getBranch());
            cat.add(categories);
        }
        System.out.println("Ca : " + cat);
        return cat;
    }
}
