package pl.edu.pjwstk.jaz.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.Requests.BranchNameRequest;
import pl.edu.pjwstk.jaz.Requests.BranchRequest;
import pl.edu.pjwstk.jaz.Services.BranchService;

import java.util.List;


@RestController
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/addBranch")
    public void addNewBranch(@RequestBody BranchRequest branchRequest) {
        branchService.addBranch(branchRequest.getName());
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/editBranch")
    public void editBranch(@RequestBody BranchNameRequest branchNameRequest) {
        branchService.editBranch(branchNameRequest.getName(), branchNameRequest.getNewBranchName());
    }

    @PreAuthorize("hasAnyAuthority('basic')")
    @GetMapping("/listAllBranches")
    @ResponseBody
    public String listBranches() {
        return branchService.showBranches().toString();
    }

    @PreAuthorize("hasAnyAuthority('basic')")
    @PostMapping("/removeBranch")
    public void removeBranch(@RequestBody BranchRequest branchRequest) {
        branchService.removeBranch(branchRequest.getName());
    }

}