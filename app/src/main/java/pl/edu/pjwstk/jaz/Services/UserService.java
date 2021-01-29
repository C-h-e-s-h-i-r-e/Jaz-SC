package pl.edu.pjwstk.jaz.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.Entities.RoleEntity;
import pl.edu.pjwstk.jaz.Entities.UserEntity;
import pl.edu.pjwstk.jaz.Repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
public class UserService {

    private UserRepository userRepository;
    private final EntityManager entityManager;

    @Autowired
    public UserService(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public UserService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserEntity findUserByUsername(String username) {
        return entityManager.createQuery("select ue from UserEntity ue where ue.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public boolean userExist(String username) {
        UserEntity name = userRepository.findByUsername(username).orElseGet(UserEntity::new);
        if (name.getUsername() == null)
            return false;
        return true;
    }

    public boolean passwordFindUserByUsername(String usernameInput, String password) {
        try {

            UserEntity name = userRepository.findByUsername(usernameInput).orElseGet(UserEntity::new);
            if (getPasswordEncoder().matches(password, name.getPassword()))
                return true;
            return false;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return false;
    }

    public Long getIdFromUser(String username) {
        UserEntity name = userRepository.findByUsername(username).orElseGet(UserEntity::new);
        return name.getId();
    }

    public Number users() {
        Query q = entityManager.createQuery("SELECT count(ue) FROM UserEntity ue");
        Number result = (Number) q.getSingleResult();
        return result;
    }

    public void saveUser(String username, String password, Set<String> authorities) {
        UserEntity userEntity = new UserEntity(username, passwordEncoder.encode(password));

        for (String role : authorities) {
            RoleEntity roleEntity = new RoleEntity(role);
            userEntity.getRole().add(roleEntity);
        }
        System.out.println(userEntity);
        this.userRepository.save(userEntity);
    }
}
