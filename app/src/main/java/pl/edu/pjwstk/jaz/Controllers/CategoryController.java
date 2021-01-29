package pl.edu.pjwstk.jaz.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.Requests.BranchRequest;
import pl.edu.pjwstk.jaz.Requests.CategoryNameRequest;
import pl.edu.pjwstk.jaz.Services.CategoryService;


@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/addCategory")
    public void addNewCategory(@RequestBody BranchRequest branchRequest) {
        categoryService.addCategory(branchRequest.getName(), branchRequest.getCategory());
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/editCategory")
    public void editCategory(@RequestBody CategoryNameRequest categoryNameRequest) {
        categoryService.editCategory(categoryNameRequest.getCategoryName(), categoryNameRequest.getCategoryNewName());
    }

    @PreAuthorize("hasAnyAuthority('basic')")
    @GetMapping("/listAllCategories")
    @ResponseBody
    public String listCategories() {
        return categoryService.showCategories().toString();
    }
}