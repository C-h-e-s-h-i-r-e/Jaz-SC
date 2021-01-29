package pl.edu.pjwstk.jaz;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.jaz.Entities.RoleEntity;
import pl.edu.pjwstk.jaz.Entities.UserEntity;
import pl.edu.pjwstk.jaz.Services.UserService;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class AuthenticationService {

    final UserSession userSession;
    final UserService userService;
    final Users users;

    public AuthenticationService(UserSession userSession, UserService userService, Users users) {
        this.userSession = userSession;
        this.userService = userService;
        this.users = users;
    }

    public boolean login(String username, String password) {

        if (userService.userExist(username)) {
            if ((userService.passwordFindUserByUsername(username, password))) {
                userSession.logIn();

                UserEntity userEntity = userService.findUserByUsername(username);
                User userFromDatabase = new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRole().stream().map(RoleEntity::getRole).collect(Collectors.toSet()));
                SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(userFromDatabase));
                return true;
            }

        }

        return false;
    }
}
