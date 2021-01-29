package pl.edu.pjwstk.jaz.EndPoints;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RestController
public class ReadinessController {
    @PersistenceContext
    private EntityManager em;

    @PreAuthorize("permitAll()")
    @Transactional
    @GetMapping("auth0/article/1")
    public void readinessTest() {

    }
}