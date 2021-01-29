package pl.edu.pjwstk.jaz.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
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
import java.util.Set;

@Transactional
@Service
public class BranchService {


    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;
    private final AuctionRepository auctionRepository;
    private final ParameterRepository parameterRepository;
    private final EntityManager entityManager;

    public BranchService(BranchRepository branchRepository, CategoryRepository categoryRepository, AuctionRepository auctionRepository, ParameterRepository parameterRepository, EntityManager entityManager) {
        this.branchRepository = branchRepository;
        this.categoryRepository = categoryRepository;
        this.auctionRepository = auctionRepository;
        this.parameterRepository = parameterRepository;
        this.entityManager = entityManager;
    }

    public void addBranch(String branchName) {
        BranchEntity branchEntity = branchRepository.findByName(branchName).orElseGet(BranchEntity::new);
        if (branchEntity.getName() == null) {
            System.out.println("Added branch : " + branchName);
            branchEntity = new BranchEntity(branchName);
            this.branchRepository.save(branchEntity);
        } else {
            System.out.println("Branch with this name exists");
            throw new UnauthorizedException();
        }
    }

    public void editBranch(String branchName, String newBranchName) {
        BranchEntity branchEntity = branchRepository.findByName(branchName).orElseGet(BranchEntity::new);
        if (branchEntity.getName() == null) {
            System.out.println("No branch name like that");
            throw new UnauthorizedException();
        } else {
            System.out.println("Changed Branch Name from : " + branchName + " to " + newBranchName);
            branchEntity.setName(newBranchName);
            this.branchRepository.save(branchEntity);
        }
    }

    public List<String> showBranches(){
        List<BranchEntity> all_branches = branchRepository.findAll();

        List<String> branch_list =  new ArrayList<>();
        for(BranchEntity b : all_branches){
            branch_list.add(b.getName());
        }

        //System.out.println(branch_list);
        return branch_list;

    }

    public void removeBranch(String branchName) {
        BranchEntity branchEntity = branchRepository.findByName(branchName).orElseGet(BranchEntity::new);
        if (branchEntity.getName() == null) {
            System.out.println("No branch name like that");
        } else {

            Set<CategoryEntity> categories = branchEntity.getCategories();

            for (CategoryEntity category : categories) {
                System.out.println("Removed categories : " + category.getName());
                entityManager.remove(entityManager.contains(category) ? category : entityManager.merge(category));
            }

            if (branchEntity.getCategories() == null) {
                System.out.println("Removed branch : " + branchName);

                this.branchRepository.delete(branchEntity);
            }
        }
    }


}
