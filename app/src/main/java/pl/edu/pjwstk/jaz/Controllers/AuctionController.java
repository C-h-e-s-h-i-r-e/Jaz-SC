package pl.edu.pjwstk.jaz.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.Entities.UserEntity;
import pl.edu.pjwstk.jaz.Requests.AuctionNameRequest;
import pl.edu.pjwstk.jaz.Requests.AuctionRequest;
import pl.edu.pjwstk.jaz.Requests.SingleAuctionRequest;
import pl.edu.pjwstk.jaz.Services.AuctionService;
import pl.edu.pjwstk.jaz.Services.CategoryService;
import pl.edu.pjwstk.jaz.Services.UserService;

import java.util.List;


@RestController
public class AuctionController {
    private final AuctionService auctionService;
    private final UserService userService;
    private final CategoryService categoryService;

    public AuctionController(AuctionService auctionService, UserService userService, CategoryService categoryService) {
        this.auctionService = auctionService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyAuthority('basic')")
    @PostMapping("/addAuction")
    public void addNewAuction(@RequestBody AuctionRequest auctionRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userService.findUserByUsername(auth.getName());

        //System.out.println(auctionRequest.getCategory() + " " + auctionRequest.getTitle() + " " + auctionRequest.getDescription() + " " + auctionRequest.getPrice() + " " + userEntity + " " + auctionRequest.getPhotos() + " " + auctionRequest.getParameters());
        auctionService.addAuction(categoryService.findCategoryBYName(auctionRequest.getCategory()), auctionRequest.getTitle(), auctionRequest.getDescription(), auctionRequest.getPrice(), userEntity, auctionRequest.getPhotos(), auctionRequest.getParameters());
    }

    @PreAuthorize("hasAnyAuthority('basic')")
    @PostMapping("/editAuction")
    public void editAuction(@RequestBody AuctionNameRequest auctionNameRequest) {

        //UserEntity userEntity = userService.findUserByUsername("admin");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userService.findUserByUsername(auth.getName());

        //System.out.println(auctionNameRequest.getNewCategory() + " " + auctionNameRequest.getTitle() + " " + auctionNameRequest.getNewTitle() + " " + auctionNameRequest.getNewDescription() + " " + auctionNameRequest.getNewPrice() + " " + userEntity + " " + auctionNameRequest.getNewPhotos() + " " + auctionNameRequest.getNewParameters());
        auctionService.editAuction(categoryService.findCategoryBYName(auctionNameRequest.getNewCategory()), auctionNameRequest.getTitle(), auctionNameRequest.getNewTitle(), auctionNameRequest.getNewDescription(), auctionNameRequest.getNewPrice(), userEntity, auctionNameRequest.getNewPhotos(), auctionNameRequest.getNewParameters());
    }

    @PreAuthorize("hasAnyAuthority('basic')")
    @GetMapping("/listAllAuctions")
    @ResponseBody
    public String listAuctions() {

        //UserEntity userEntity = userService.findUserByUsername("admin");

        return auctionService.listAllAuctions().toString();
    }

    @PreAuthorize("hasAnyAuthority('basic')")
    @GetMapping("/showAuction")
    @ResponseBody
    public List<List<String>> showAuction(@RequestParam(value = "title", required = false) String title) {

        //UserEntity userEntity = userService.findUserByUsername("admin");

        return auctionService.getAuction(title);
    }

    @PreAuthorize("hasAnyAuthority('basic')")
    @PostMapping("/removeAuction")
    public void removeAuction(@RequestBody SingleAuctionRequest singleAuctionRequest) {

        //UserEntity userEntity = userService.findUserByUsername("admin");

        auctionService.removeAuction(singleAuctionRequest.getTitle());
    }


}
