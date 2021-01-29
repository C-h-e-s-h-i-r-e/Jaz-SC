package pl.edu.pjwstk.jaz;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.Entities.UserEntity;
import pl.edu.pjwstk.jaz.Services.UserService;

@RestController
public class RegisterController {

    private final Users users;
    private final UserService userService;

    public RegisterController(Users users, UserService userService) {
        this.users = users;
        this.userService = userService;
    }

    @PostMapping("register")
    public void register(@RequestBody RegisterRequest registerRequest) {

        Number sum = userService.users();
        int usersNumber = sum.intValue();
        System.out.println("Number od users: " + sum);
        //zarejestrowac users.isEmpty()
        if(usersNumber == 0) {
            User user = new User(registerRequest.getUsername(), registerRequest.getPassword());
            user.addAuthorities("admin");
            users.add(user);
            System.out.println("User authorities : " + user.getAuthorities());
            // dodanie do bazy i wyciagniecie z bazy
            userService.saveUser(registerRequest.getUsername(),registerRequest.getPassword(),user.getAuthorities());
            UserEntity singleResult = userService.findUserByUsername(registerRequest.getUsername());
            System.out.println("add admin" + singleResult);

        }// !users.nameExist(registerRequest.getUsername())
        else if (!userService.userExist(registerRequest.getUsername()) ){
            User user = new User(registerRequest.getUsername(), registerRequest.getPassword());
            user.addAuthorities("user");
            users.add(user);
            System.out.println("User authorities : " + user.getAuthorities());
            // dodanie do bazy i wyciagniecie z bazy
            userService.saveUser(registerRequest.getUsername(),registerRequest.getPassword(),user.getAuthorities());
            UserEntity singleResult = userService.findUserByUsername(registerRequest.getUsername());
            System.out.println("add user" + singleResult);
        }

    }
    // remove
    @GetMapping("/register")
    public java.util.ArrayList<User> usersInHashMap() {
        return users.usersInHashMap();
    }

}
