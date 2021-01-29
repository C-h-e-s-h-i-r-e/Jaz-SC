package pl.edu.pjwstk.jaz.Services;

import liquibase.pro.packaged.L;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.jaz.Entities.*;
import pl.edu.pjwstk.jaz.Repositories.*;
import pl.edu.pjwstk.jaz.UnauthorizedException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class AuctionService {
    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;
    private final AuctionRepository auctionRepository;
    private final ParameterRepository parameterRepository;
    private final EntityManager entityManager;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public AuctionService(BranchRepository branchRepository, CategoryRepository categoryRepository, AuctionRepository auctionRepository, ParameterRepository parameterRepository, EntityManager entityManager, PhotoRepository photoRepository, UserRepository userRepository, UserService userService) {
        this.branchRepository = branchRepository;
        this.categoryRepository = categoryRepository;
        this.auctionRepository = auctionRepository;
        this.parameterRepository = parameterRepository;
        this.entityManager = entityManager;
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public void addAuction(CategoryEntity categoryEntity, String title, String description, int price, UserEntity userEntity, List<String> photos, List<String> parameters) {
        AuctionEntity auctionEntity = auctionRepository.findByTitle(title).orElseGet(AuctionEntity::new);
        if (categoryEntity.getName() == null) {
            System.out.println("No category with this name.");
            throw new UnauthorizedException();
        } else {
            if (auctionEntity.getTitle() != null && auctionEntity.getTitle().equals(title)) {
                System.out.println("Auction with name like this already exists.");
                throw new UnauthorizedException();
            } else {
                System.out.println("UE : " + userEntity);
                auctionEntity = new AuctionEntity(title, description, price, userEntity, categoryEntity);
                System.out.println("AEU : " + auctionEntity.getUser().getId());
                categoryEntity.getAuctions().add(auctionEntity);
                Long place = (long) 1;
                for (String photo : photos) {
                    PhotoEntity photoEntity = new PhotoEntity(auctionEntity, photo, place++);
                    auctionEntity.getPhotos().add(photoEntity);
                }

                for (int i = 0; i < parameters.size(); i++) {
                    ParameterEntity parameterEntity = parameterRepository.findByName(parameters.get(i)).orElseGet(ParameterEntity::new);
                    if (parameterEntity.getParameter_name() == null) {
                        parameterEntity.setParameter_name(parameters.get(i));
                        this.parameterRepository.save(parameterEntity);
                    } else {
                        System.out.println("Parameter already in database.");
                    }

                    this.auctionRepository.save(auctionEntity);
                    this.parameterRepository.save(parameterEntity);
                    Auction_ParameterEntity auctionParameterEntity = new Auction_ParameterEntity(new Auction_ParameterKey(auctionEntity.getId(), parameterEntity.getId()), categoryEntity.getName());
                    auctionParameterEntity.setAuctionEntity(auctionEntity);
                    auctionParameterEntity.setParameterEntity(parameterEntity);
                    auctionEntity.addAuction_Parameter(auctionParameterEntity);
                    entityManager.merge(auctionParameterEntity);
                }
            }
        }
    }

    public void editAuction(CategoryEntity newCategoryEntity, String title, String newTitle, String newDescription, int newPrice, UserEntity userEntity, List<String> newPhotos, List<String> newParameters) {
        AuctionEntity auctionEntity = auctionRepository.findByTitle(title).orElseGet(AuctionEntity::new);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean can_edit = false;

        System.out.println("Logged user id : " + userService.findUserByUsername(auth.getName()).getId() + " Auction user id : " + auctionEntity.getUser().getId());

        if (auctionEntity.getUser().getId().equals(userService.findUserByUsername(auth.getName()).getId())) {
            can_edit = true;
        }
        for (GrantedAuthority a : auth.getAuthorities()) {
            if (a.toString().equals("admin")) {
                can_edit = true;
            }
        }
        if (can_edit == false) {
            System.out.println("U cant edit this.");
            throw new UnauthorizedException();
        } else {
            System.out.println("U are admin or owner u can edit this.");
            if (newCategoryEntity.getName() == null) {
                System.out.println("No category with this name.");
            } else if (auctionEntity.getTitle() == null || !auctionEntity.getTitle().equals(title)) {
                System.out.println("There is no Auction with name like this in database.");
                throw new UnauthorizedException();
            } else {
                auctionEntity.setCategory(newCategoryEntity);
                auctionEntity.setDescription(newDescription);
                auctionEntity.setPrice(newPrice);
                auctionEntity.setTitle(newTitle);

                List<PhotoEntity> auction_photos = photoRepository.findAll();

                Long place = new Long(0);
                for (PhotoEntity p : auction_photos) {
                    if (p.getAuction().getId().equals(auctionEntity.getId())) {
                        if (place < p.getPlace()) {
                            place = p.getPlace();
                        }
                    }
                }
                place = place + 1;

                for (String photo : newPhotos) {
                    PhotoEntity photoEntity = new PhotoEntity(auctionEntity, photo, place++);
                    auctionEntity.getPhotos().add(photoEntity);
                }

                for (int i = 0; i < newParameters.size(); i++) {
                    ParameterEntity parameterEntity = parameterRepository.findByName(newParameters.get(i)).orElseGet(ParameterEntity::new);
                    if (parameterEntity.getParameter_name() == null) {
                        parameterEntity.setParameter_name(newParameters.get(i));
                        this.parameterRepository.save(parameterEntity);
                    } else {
                        System.out.println("Parameter already in database.");
                    }

                    this.auctionRepository.save(auctionEntity);
                    this.parameterRepository.save(parameterEntity);
                    Auction_ParameterEntity auctionParameterEntity = new Auction_ParameterEntity(new Auction_ParameterKey(auctionEntity.getId(), parameterEntity.getId()), newCategoryEntity.getName());
                    auctionParameterEntity.setAuctionEntity(auctionEntity);
                    auctionParameterEntity.setParameterEntity(parameterEntity);
                    auctionEntity.addAuction_Parameter(auctionParameterEntity);
                    entityManager.merge(auctionParameterEntity);
                }
            }
        }
    }

    public List<List<String>> listAllAuctions() {
        List<AuctionEntity> all_auctions = auctionRepository.findAll();

        List<SingleAuction> auctions_list = new ArrayList<>();

        for (AuctionEntity auction : all_auctions) {
            String photo = "";
            for (PhotoEntity photoE : auction.getPhotos()) {
                if (photoE.getPlace().equals(new Long(1))) {
                    photo = photoE.getLink();
                }
            }
            SingleAuction singleAuction = new SingleAuction(auction.getTitle(), auction.getDescription(), auction.getPrice(), photo, auction.getCategory().getName());
            auctions_list.add(singleAuction);
        }
        List<List<String>> res = new ArrayList<>();
        for (SingleAuction a : auctions_list) {
            System.out.println("Title : " + a.title + " Decription : " + a.description + " Price : " + a.price + " Photo : " + a.photo + " Category : " + a.category);
            List<String> result = new ArrayList<>();
            result.add(a.getTitle());
            result.add(a.getDescription());
            result.add(String.valueOf(a.getPrice()));
            result.add(a.getPhoto());
            result.add(a.getCategory());
            res.add(result);
        }

        return res;



    }

    public List<List<String>> getAuction(String title) {
        AuctionEntity auctionEntity = auctionRepository.findByTitle(title).orElseGet(AuctionEntity::new);
        if (auctionEntity.getTitle() == null || !auctionEntity.getTitle().equals(title)) {
            System.out.println("There is no Auction with name like this in database.");
            throw new UnauthorizedException();
        } else {

            List<String> photos = new ArrayList<>();
            List<String> params = new ArrayList<>();

            for (PhotoEntity photo : auctionEntity.getPhotos()) {
                photos.add(photo.getLink());
            }

            for (Auction_ParameterEntity param : auctionEntity.getAuction_ParameterEntities()) {
                params.add(param.getParameterEntity().getParameter_name());
            }
            List<String> result = new ArrayList<>();
            result.add(auctionEntity.getTitle());
            result.add(auctionEntity.getDescription());
            result.add(String.valueOf(auctionEntity.getPrice()));
            result.add(String.valueOf(photos));
            result.add(String.valueOf(params));
            result.add(auctionEntity.getCategory().getName());
            List<List<String>> res = new ArrayList<>();
            res.add(result);

            System.out.println("Title : " + auctionEntity.getTitle() + " Description : " + auctionEntity.getDescription() + " Price : " + auctionEntity.getPrice() + " Photos : " + photos + " Params : " + params + " Cateogry : " + auctionEntity.getCategory().getName());
            return res;
        }
    }

    public void removeAuction(String title) {
        AuctionEntity auctionEntity = auctionRepository.findByTitle(title).orElseGet(AuctionEntity::new);
        if (auctionEntity.getTitle() == null || !auctionEntity.getTitle().equals(title)) {
            System.out.println("There is no Auction with name like this in database.");
        } else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean can_edit = false;
            System.out.println("Logged user id : " + userService.findUserByUsername(auth.getName()).getId() + " Auction user id : " + auctionEntity.getUser().getId());
            if (auctionEntity.getUser().getId().equals(userService.findUserByUsername(auth.getName()).getId())) {
                can_edit = true;
            }
            for (GrantedAuthority a : auth.getAuthorities()) {
                if (a.toString().equals("admin")) {
                    can_edit = true;
                }
            }
            if (can_edit == false) {
                System.out.println("U cant delete this.");
            } else {
                entityManager.remove(auctionEntity);
            }
        }
    }
}
